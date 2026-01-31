import com.mongodb.kotlin.client.MongoClient
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.Test
import org.testcontainers.mongodb.MongoDBContainer
import org.testcontainers.utility.DockerImageName

class MongoSerializationTest {
    @Test
    fun `throws CodecConfigurationExceptionWithKotlin v2-3-0`() {
        val mongoContainer = MongoDBContainer(DockerImageName.parse("mongo:8.2.4"))
        mongoContainer.start()

        val client = MongoClient.create(mongoContainer.connectionString)
        val db = client.getDatabase("test")
        val collectionName = "data"
        db.createCollection(collectionName)

        val collection = db.getCollection<DataDocument>(collectionName)

        collection.find().forEach { println(it) }
    }
}

@Serializable
data class DataDocument(
    val parent: ResourceLink<String>,
)

@Serializable
data class ResourceLink<ID_TYPE>(
    val id: ID_TYPE,
)