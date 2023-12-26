package com.example

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mongodb.client.MongoClients
import org.junit.jupiter.api.Test
import org.occurrent.application.converter.jackson.JacksonCloudEventConverter
import org.occurrent.eventstore.api.blocking.EventStore
import org.occurrent.eventstore.mongodb.nativedriver.EventStoreConfig
import org.occurrent.eventstore.mongodb.nativedriver.MongoEventStore
import org.occurrent.mongodb.timerepresentation.TimeRepresentation
import java.net.URI
import java.util.*
import kotlin.streams.asStream

class PopulateDb {
    @Test
    fun `add 2000 events`() {
        val events = (1..2000).asSequence().map {
            val someId = UUID.randomUUID()
            val event = DomainEvent("Message for $someId")
            converter.toCloudEvent(event)
        }.asStream()
        eventStore.write("test-stream", events)
    }

    @Test
    fun `add 1 event`() {
        val someId = UUID.randomUUID()
        val event = converter.toCloudEvent(DomainEvent("Message for $someId"))
        eventStore.write("test-stream", event)
    }
}

val om = jacksonObjectMapper()
val converter = JacksonCloudEventConverter<DomainEvent>(om, URI.create("urn:company:occurrent-test"))
val eventStore: EventStore by lazy {
        val mongoClient = MongoClients.create(DEFAULT_MONGO_CONNECTION_STRING)
        MongoEventStore(mongoClient,
            DB_NAME, EVENTS_COLLECTION, EventStoreConfig.Builder().timeRepresentation(TimeRepresentation.RFC_3339_STRING).build())
    }