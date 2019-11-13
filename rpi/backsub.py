from __future__ import print_function
import cv2 
import argparse
import numpy as np
import glob

#Setting up and retrieving argument
'''
parser = argparse.ArgumentParser()
parser.add_argument('--input', type=str, help='Path to a video or a sequence of image.', default='vtest.avi')
args = parser.parse_args()
'''

#Creating background subtractor
backSub = cv2.createBackgroundSubtractorMOG2()

images = [cv2.imread(file) for file in glob.glob("images2/*.jpg")]
 
for i in images:
    fgMask = backSub.apply(cv2.GaussianBlur(i,(5,5),0))

    thresh = cv2.threshold(fgMask, 0, 255,
            cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)[1]
    # Setting up the parameters of the blob detector 
    params = cv2.SimpleBlobDetector_Params()
    params.filterByColor = True
    params.blobColor = 0
    params.filterByArea = True
    params.minArea = 400
    params.filterByConvexity = True
    params.minConvexity = 0.01


    # Detecting the blobs
    detector = cv2.SimpleBlobDetector_create(params)
    keypoints = detector.detect(thresh)

    # Draw detected blobs as red circles.
    # cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS ensures the size of the circle corresponds to the size of blob
    im_with_keypoints = cv2.drawKeypoints(thresh, keypoints, np.array([]), (0,0,255), cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS)

    cv2.rectangle(im_with_keypoints,(180,330),(460,680),(0,255,0),2)
    cv2.rectangle(im_with_keypoints,(460,330),(740,680),(0,0,255),2)
    cv2.rectangle(im_with_keypoints,(740,330),(1050,680),(255,0,0),2)

    #Output 2
    #cv2.imshow('FG Mask', fgMask)
    #cv2.imshow('Thresh', thresh)
    cv2.imshow("Keypoints", im_with_keypoints)
    cv2.waitKey(1000)
    cv2.destroyAllWindows()
    for i in keypoints:
        x = i.pt[0] 
        y = i.pt[1]
        if 330<y<680:
            if 180<x<460:
                print('keypoint at Box A')
            elif 460<x<740:
                print('keypoint at Box B')
            elif 740<x<1050:
                print('keypoint at Box C')
        else:
            print('keypoint is outside boxes')
        
