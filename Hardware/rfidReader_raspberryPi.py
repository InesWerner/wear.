#!/usr/bin/env python

import RPi.GPIO as GPIO
import sys
sys.path.append('/home/pi/MFRC522-python')
from mfrc522 import SimpleMFRC522

reader = SimpleMFRC522()

#Simple method to read out just one time
def read():
    tagId = 0
    try:
        id, text = reader.read()
        tagId = id;
        print(tagId)
    except Exception as ex:
        print("Error reading ID")
        print(ex)
    return tagId

#Simple method to read out constantly until process is killed manual
def readConstantly():
    while True:
        tagId = read()
    

#STARTING POINT
#Cleans up all open ports on raspberryPi. Run always at the beginning of the application to have all ports accessible
GPIO.cleanup()
readConstantly()
    



