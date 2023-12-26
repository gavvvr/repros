package com.example

import com.mongodb.client.MongoClients
import org.occurrent.eventstore.mongodb.internal.OccurrentCloudEventMongoDocumentMapper
import org.occurrent.eventstore.mongodb.nativedriver.EventStoreConfig
import org.occurrent.eventstore.mongodb.nativedriver.MongoEventStore
import org.occurrent.mongodb.timerepresentation.TimeRepresentation
import org.occurrent.retry.RetryStrategy
import org.occurrent.subscription.blocking.durable.DurableSubscriptionModel
import org.occurrent.subscription.blocking.durable.catchup.CatchupSubscriptionModel
import org.occurrent.subscription.blocking.durable.catchup.CatchupSubscriptionModelConfig
import org.occurrent.subscription.blocking.durable.catchup.SubscriptionPositionStorageConfig.useSubscriptionPositionStorage
import org.occurrent.subscription.mongodb.nativedriver.blocking.NativeMongoSubscriptionModel
import org.occurrent.subscription.mongodb.nativedriver.blocking.NativeMongoSubscriptionPositionStorage
import java.util.concurrent.Executors


fun main() {
    val connectionString = System.getenv("MONGO_CONNECTION_STRING") ?: DEFAULT_MONGO_CONNECTION_STRING

    val mongoClient = MongoClients.create(connectionString)
    val dbName = DB_NAME
    val database = mongoClient.getDatabase(dbName)
    val exportedCollection = database.getCollection("exported-events")

    val eventsCollectionName = EVENTS_COLLECTION
    val eventStore =
        MongoEventStore(mongoClient, dbName, eventsCollectionName, EventStoreConfig(TimeRepresentation.RFC_3339_STRING))

    val nativeMongoSubscriptionModel = NativeMongoSubscriptionModel(
        database,
        eventsCollectionName,
        TimeRepresentation.RFC_3339_STRING,
        Executors.newCachedThreadPool(),
        RetryStrategy.retry().maxAttempts(1),
    )
    val positionStorage =
        NativeMongoSubscriptionPositionStorage(database, "subscription-position-collection")
    val wrappedSubscriptionModel = DurableSubscriptionModel(
        nativeMongoSubscriptionModel,
        positionStorage,
    )
    val catchupSubscriptionModel = CatchupSubscriptionModel(
        wrappedSubscriptionModel,
        eventStore,
        CatchupSubscriptionModelConfig(useSubscriptionPositionStorage(positionStorage))
    )

    catchupSubscriptionModel.subscribe("kafka-exporter") { e ->
        println("Event number=${e.id} received!")
        exportedCollection.insertOne(OccurrentCloudEventMongoDocumentMapper.convertToDocument(TimeRepresentation.RFC_3339_STRING, "whatever", 1, e))
        Thread.sleep(200)
    }.waitUntilStarted()
}
