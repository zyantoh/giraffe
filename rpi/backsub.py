#Imports
from __future__ import print_function
import cv2 
import argparse
import numpy as np
import glob

from firestore import database

from P2database import *
def compare(firebase_client=None):
    #Retrieving images
    images = [cv2.imread(file) for file in glob.glob("/home/pi/Desktop/giraffe/*.jpg")]
    #images.extend([cv2.imread(file) for file in glob.glob("/home/pi/Desktop/giraffe/*.jpg")])

    #Creating background subtractor
    backSub = cv2.createBackgroundSubtractorMOG2()

    #Creating blob detector 
    params = cv2.SimpleBlobDetector_Params()
    params.filterByColor = True
    params.blobColor = 0
    params.filterByArea = True
    params.minArea = 3000
    params.maxArea = 200000
    params.filterByCircularity = True
    params.minCircularity = 0.000001
    
    detector = cv2.SimpleBlobDetector_create(params)

    #Creating foreground mask
    for i in images:
        fgMask = backSub.apply(cv2.GaussianBlur(i,(159,159),0))

    #Threshing 
    thresh = cv2.threshold(fgMask, 0, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)[1]

    # Detecting the blobs
    keypoints = detector.detect(thresh)

    # Drawing the blobs
    im_with_keypoints = cv2.drawKeypoints(thresh, keypoints, np.array([]), (0,0,255), cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS)

    #Lists
    topleft = [(0,0),(475,0),(1250,0),(2140,0),(475,740),(1250,740),(2140,740),(475,1620),(1250,1620),(2140,1620)]
    botright = [(0,0),(1065,400),(1840,400),(2592,400),(1065,1300),(1840,1300),(2592,1300),(1065,1944),(1840,1944),(2592,1944)]
    countinglist = [0,0,0,0,0,0,0,0,0]
    
    #Drawing Parameters
    for i in range (0,10):
        cv2.rectangle(im_with_keypoints,topleft[i],botright[i],(0,0,255),2)
    

    #Output

    for i in keypoints:
        x = i.pt[0]
        y = i.pt[1]
        for z in range(1,10):
            if (topleft[z][1] <= y <= botright[z][1]) and (topleft[z][0] <= x <= botright[z][0]):
                countinglist[z-1] += 1       
     
     # Image File
    image = cv2.imread("/home/pi/Desktop/giraffe/Result/basic.jpg")
    # Tables 1- 9
    table = [(240, 108),(484, 108),(769, 108),(240, 368),(484, 368),(769, 368),(240, 637),(484, 637),(769, 637)]

    # Table Status
    o =(0,0,255) # Occupied 
    u =(0,255,0) # Unoccupied

    for i in range(0,9):
        if countinglist[i] == 0:
            cv2.circle (image,table[i],50,u,-1)
        else :
             cv2.circle(image,table[i],50,o,-1)


    cv2.imshow('result',image) #Remove after validation
    output_image = "/home/pi/Desktop/giraffe/Result/result.jpg"
    cv2.imwrite(output_image,image)
    if firebase_client:
        database.upload_image(output_image)
        occupants = sum([1 for x in countinglist if x > 0])
        database.update_table_occupancy(firebase_client, occupants)
    
    
    cv2.imshow("Keypoints", im_with_keypoints) #Remove after validation
    cv2.imwrite(("/home/pi/Desktop/giraffe/Result/keypoints.jpg"),im_with_keypoints)
    cv2.waitKey(5000)
    cv2.destroyAllWindows()

