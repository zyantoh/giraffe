import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from enum import Enum


def create_client() -> firestore.Client:
    cred = credentials.Certificate('D:/Documents/OpenCV/service_account.json')
    firebase_admin.initialize_app(cred)

    return firestore.client()


class TableStatus(Enum):
    CONFIDENT_TAKEN = "CONFIDENT_TAKEN"
    MAYBE_TAKEN = "MAYBE_TAKEN"
    CONFIDENT_NOT_TAKEN = "CONFIDENT_NOT_TAKEN"


def update_table_status(
        client: firestore.Client,
        canteen_name: str,
        table_id: str,
        new_status: TableStatus):
    doc_ref = client.collection(u"canteen").document(canteen_name) \
                .collection("tables").document(table_id)
    doc_ref.update({
        u"isTaken": new_status.value
    })
