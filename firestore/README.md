# Firestore Database
Firestore is a NoSQL document database. This means that data is stored in a
format very similar to JSON.

## Data Structure
```json
{
    "collections": {
        "canteens": [
            {
                "name": "Food Club",
                "location": "Blk 22",
                "tables": [
                    {
                        "type": "long",
                        "x": 123,
                        "y": 123,
                        "isTaken": "NO"
                    }
                ]
            }
        ]
    }
}
```

## Data Access
The database has public read access. However, if one wants to write to the
database, they will need to use a service account. A service account is a
special Google account used for Google services, such as calling APIs. The
service account has a set of credentials which needs to be loaded before one is
allowed to write to the database.
