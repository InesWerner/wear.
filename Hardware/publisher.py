import paho.mqtt.publish as publish
 

MQTT_SERVER = "192.168.0.124"
MQTT_PATH = "wearrfid"

class publisher():
    


    def publishMessage(self,message):
        
        try:
            publish.single(MQTT_PATH, message, hostname=MQTT_SERVER)
            
        except Exception as e:
            print("Publisher:",e.message)
