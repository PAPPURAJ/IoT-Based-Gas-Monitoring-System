#include "FirebaseESP8266.h"
#include <ESP8266WiFi.h>

#define FIREBASE_HOST "gas-monitoring-system-b1977-default-rtdb.asia-southeast1.firebasedatabase.app" 
#define FIREBASE_AUTH "49qEXUALzp3LPAu2PvzTj8NmK3iC1nC19tnF8i4B"
#define WIFI_SSID "PAPPURAJ"
#define WIFI_PASSWORD "0000000000Z"



FirebaseData firebaseData;
FirebaseJson json;


void setup() {
  
    Serial.begin(9600);



  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(300);
  }
  
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);

}






void writeDB(String field,String value){
 Firebase.setString(firebaseData, "/"+field,value );
  
}



void loop() {
  
      process("1");

}




String d;
void process(String no){
    if(Serial.available()){

    d=Serial.readString();

   if(d.substring(0,3)=="CO2")
      writeDB("Data/CO2-"+no,d.substring(4));



  else if(d.substring(0,2)=="CO")
      writeDB("Data/CO-"+no,d.substring(3));



  else if(d.substring(0,3)=="CH4")
      writeDB("Data/CH4-"+no,d.substring(4));

  else if(d.substring(0,3)=="TEM")
      writeDB("Data/Temp-"+no,d.substring(4));


  else if(d.substring(0,3)=="LAT")
      writeDB("Location/Lat-"+no,d.substring(4));


  else if(d.substring(0,3)=="LON")
      writeDB("Location/Lon-"+no,d.substring(4));
  

    
  else
    writeDB("Error "+no,d);
 
   delay(1700);

}
}
