import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore, storage
from enum import Enum


FOOD_CLUB_ID = "RUVNqof5cTdIkX5krJYP"
FOOD_CLUB_FILE = "RUVNqof5cTdIkX5krJYP.jpg"
BUCKET_NAME = "giraffe-1.appspot.com"


def create_client() -> firestore.Client:
    cred = credentials.Certificate('service_account.json')
    firebase_admin.initialize_app(cred, {
        "storageBucket": "giraffe-1.appspot.com",
    })

    return firestore.client()


def update_table_occupancy(
        client: firestore.Client,
        occupied: int):
    canteen_id = FOOD_CLUB_ID
    doc_ref = client.collection(u"canteens").document(
        canteen_id)
    doc_ref.update({
        "occupiedTables": occupied
    })


def upload_image(
    filename: str,
):
    bucket = storage.bucket(BUCKET_NAME)
    blob = bucket.blob(f"canteens/{FOOD_CLUB_FILE}")
    blob.upload_from_filename(filename)
