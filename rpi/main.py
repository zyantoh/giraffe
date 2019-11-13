import os
import time
FRAMES = 10
TIMEBETWEEN = 10

frameCount = 0
while frameCount < FRAMES:
    imagenumber = str(frameCount).zfill(3)
    os.system("raspistill -o image%s.jpg"%(imagenumber))
    frameCount += 1
    time.sleep(TIMEBETWEEN - 5)
    if frameCount == 10:
        frameCount = 0
        continue
