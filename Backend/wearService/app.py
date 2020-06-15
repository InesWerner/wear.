from bo.tag import Tag
from database.tag_mapper import TagMapper
import paho.mqtt.client as mqtt
import re

"""
WHAT THIS PROGRAM DOES:
1. Receive tagID with MQTT Consumer
2. If a tagID is received, check if it exists in database
2.1. If TRUE: change the status
2.2. If FALSE: insert a new one

The process of these steps is triggered in def on_message

"""

tagMapper = TagMapper()
MQTT_SERVER = "192.168.0.124"
MQTT_PATH = "wearrfid"

MQTT_AUTH = {
  'username':"pi",
  'password':"weariot"
}


def cleanMessage(message):
    con_list = re.findall('\d+',message)
    con_string = ''.join(con_list)  # converting list into string
    con_int = int(con_string)
    return con_int



# The callback for when the client receives a CONNACK response from the server.
def on_connect(client,userdata,flags,rc):
    print("Connected with result code " + str(rc))

    # Subscribing in on_connect() means that if we lose the connection and
    # reconnect then subscriptions will be renewed.
    client.subscribe(MQTT_PATH)

# The callback for when a PUBLISH message is received from the server.
def on_message(client,userdata,msg):
    print("Incoming Message:" + msg.topic + " " + str(msg.payload))
    tagID = cleanMessage(str(msg.payload))
    print("Seperate tagID:", tagID)
    existence = tagMapper.checkExistence(tagID)
    print(tagID, "exists: ", existence)
    if existence == False:
        tagMapper.insertByTagID(tagID)
    else:
        tagMapper.updateByTagID(tagID)



try:
    client = mqtt.Client()
    client.on_connect = on_connect
    client.on_message = on_message
    client.connect(MQTT_SERVER,1883,60)
        # Blocking call that processes network traffic, dispatches callbacks and
        # handles reconnecting.
        # Other loop*() functions are available that give a threaded interface and a
        # manual interface.
    client.loop_forever()
except Exception as e:
    print(e.__str__())

except KeyboardInterrupt as ki:
    print(ki.__str__())







