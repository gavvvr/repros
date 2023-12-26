const adminDbName = 'admin'
db = db.getSiblingDB(adminDbName);
db.createUser(
    {
        user: "root",
        pwd: "root",
        roles: [
            {role: "root", db: adminDbName},
        ]
    }
)
db.createRole({
    role: "hostInfoRole",
    privileges: [{resource: {cluster: true}, actions: ["hostInfo"]}],
    roles: [],
})

const dbName = 'test-db'
db = db.getSiblingDB(dbName)
db.createUser(
    {
        user: "test-user",
        pwd: "pwd",
        roles: [
            {role: "readWrite", db: dbName},
//            {role: "hostInfoRole", db: "admin"},
        ]
    }
)
