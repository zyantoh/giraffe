#Imports
from skimage.measure import compare_ssim
import argparse
import imutils
import cv2
import numpy as np


#UPLOADING IMAGES
#Uploading through command prompt
'''
ap = argparse.ArgumentParser()
ap.add_argument("-f", "--first", required=True,
	help="first input image")
ap.add_argument("-s", "--second", required=True,
	help="second")
args = vars(ap.parse_args())

imageA = cv2.imread(args["first"])
imageB = cv2.imread(args["second"])
 '''
#Uploading directky through files
imageA = cv2.imread('Empty.jpg', -1)
imageB = cv2.imread('TwoFools.jpg', -1)


#convert the images to grayscale
grayA = cv2.cvtColor(imageA, cv2.COLOR_BGR2GRAY)
grayB = cv2.cvtColor(imageB, cv2.COLOR_BGR2GRAY)


#Creating the threshold of the difference in the images
(score, diff) = compare_ssim(grayA, grayB, full=True)
diff = (diff * 255).astype("uint8")
thresh = cv2.threshold(diff, 0, 255,
	cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)[1]


# Setting up the parameters of the blob detector 
params = cv2.SimpleBlobDetector_Params()
params.minThreshold = 10;
params.maxThreshold = 200;
params.filterByCircularity = True
params.minCircularity = 0.2


# Detecting the blobs
detector = cv2.SimpleBlobDetector_create(params)
keypoints = detector.detect(thresh)

# Draw detected blobs as red circles.
# cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS ensures the size of the circle corresponds to the size of blob
im_with_keypoints = cv2.drawKeypoints(thresh, keypoints, np.array([]), (0,0,255), cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS)


# Showing the images
cv2.imshow("Original", imageA)
cv2.imshow("Modified", imageB)
cv2.imshow("Thresh", thresh)
cv2.imshow("Keypoints", im_with_keypoints)
cv2.waitKey(0)
cv2.destroyAllWindows()
