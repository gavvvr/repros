db = db.getSiblingDB('test-db')

const howMuchToGenerate = 200
db.events.insert(prepareEvents(howMuchToGenerate))

function prepareEvents(howMuch) {
    return Array.from(Array(howMuch).keys()).map(idx => buildExampleEventDocument(idx + 1))
}

function buildExampleEventDocument(streamVersion) {
    return {
        "specversion": "1.0",
        "id": crypto.randomUUID(),
        "source": "urn:company:occurrent-test",
        "type": "com.example.DomainEvent",
        "datacontenttype": "application/json",
        "time": new Date().toISOString(),
        "data": {
            "message": `Message for ${crypto.randomUUID()}`
        },
        "streamid": "test-stream",
        "streamversion": Long(streamVersion)
    }
}
