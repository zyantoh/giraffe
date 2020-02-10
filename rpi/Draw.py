import cv2
# Image File
image = cv2.imread("/home/pi/Desktop/giraffe/background/realtime.jpg")

result = [0,1,2,3,4,5,6,7,8]
# Tables 1- 9
table = [(101, 229),(357, 229),(613, 229),(101, 480),(357, 480),(613, 480),(101, 764),(357, 764),(613, 764)]

# Table Status
o =(0,0,255) # Occupied 
u =(0,255,0) # Unoccupied

for i in range(0,9):
    if result[i] == 0:
        cv2.circle (image,table[i],50,u,-1)
    else :
         cv2.circle(image,table[i],50,o,-1)


cv2.imshow('Test image',image) # removed after validation
cv2.imwrite('result.jpg',image)
cv2.waitKey(0)
cv2.destroyAllWindows()
