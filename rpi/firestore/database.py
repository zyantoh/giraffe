import firebase_admin
from firebase_admin import credentials
from google.cloud import firestore
from enum import Enum


def create_client() -> firestore.Client:
    cred = credentials.Certificate('service_account.json')
    firebase_admin.initialize_app(cred)

    return firebase_admin.firestore.client()


class TableStatus(Enum):
    CONFIDENT_TAKEN = "CONFIDENT_TAKEN"
    MAYBE_TAKEN = "MAYBE_TAKEN"
    CONFIDENT_NOT_TAKEN = "CONFIDENT_NOT_TAKEN"


def update_table_status(
        client: firestore.Client,
        canteen_name: str,
        table_id: str,
        new_status: TableStatus):
    doc_ref = db.collection(u"canteen").document(
        canteen_name, "tables", table_id, "isTaken")
    doc_ref.set(new_status.value)
