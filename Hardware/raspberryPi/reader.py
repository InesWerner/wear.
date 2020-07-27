#!/usr/bin/env python

import RPi.GPIO as GPIO
import sys
import time
sys.path.append("/home/pi/publisher")
from publisher import publisher as Publisher
from datetime import datetime
from mfrc522 import SimpleMFRC522


reader = SimpleMFRC522()
mqttpublisher = Publisher()

def read():
    try:
            id, text = reader.read()
            return id
    except RuntimeWarning:
        print(RuntimeWarning)
    finally:
            GPIO.cleanup()
            
    
counter = 0
def readConstantly():
    while True:
        global counter
        counter += 1
        print("Literation",counter)
        id = read()
        sendID = id
        print("Reading",id)
        mqttpublisher.publishMessage(sendID)
        print("Sending",id)
        time.sleep(2)
        
    
    
readConstantly()
