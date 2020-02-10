import cv2
import os
import backsub
import time
from firestore import database
'''
startTime = time.time()

webcam = cv2.VideoCapture(0)
directory = '/home/pi/Desktop/giraffe'
os.chdir(directory)
check, frame = webcam.read()
cv2.imwrite('foundation.jpg', frame)

#firebase_client = database.create_client()

while True:
    if time.time() - startTime >= 5:
        check, frame = webcam.read()
        cv2.imwrite('realtime.jpg',frame)
        backsub.compare() #backsub.compare(firebase_client)
        startTime = time.time()
'''
firebase_client = database.create_client()
TIMEBETWEEN = 10
directory = '/home/pi/Desktop/giraffe'
os.chdir(directory)
os.system('raspistill -n -o foundation.jpg')
while True:
    os.system('raspistill -n -o realtime.jpg')
    backsub.compare(firebase_client)
    time.sleep(TIMEBETWEEN -5)
    

    