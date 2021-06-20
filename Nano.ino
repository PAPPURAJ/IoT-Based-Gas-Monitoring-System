#include<SoftwareSerial.h>
#include <TinyGPS++.h>

SoftwareSerial myS(3,2);

TinyGPSPlus gps;





float Lat=10,Lon=11;
int Vo;
float R1 = 2252;
float logR2, R2, T;
float A = 1.484778004e-03, B = 2.348962910e-04, C = 1.006037158e-07;  // Steinhart-Hart and Hart Coefficients









void setup() {
  
 myS.begin(9600);
  Serial.begin(9600);
  delay(5000);
  pinMode(A0,INPUT);
  pinMode(A1,INPUT);
  pinMode(A2,INPUT);
  pinMode(A3,INPUT);

}String in;

  int Delay=3400;

void loop() {



   while(myS.available())              //While there are incoming characters  from the GPS
  {
    gps.encode(myS.read());           //This feeds the serial NMEA data into the library one char at a time
  }
  if(gps.location.isUpdated())          //This will pretty much be fired all the time anyway but will at least reduce it to only after a package of NMEA data comes in
  {
   
    Lat=gps.location.lat();
    Lon=gps.location.lng();

//    Serial.print("Satellite Count:");
//    Serial.println(gps.satellites.value());
//    Serial.print("Latitude:");

//    Serial.println(Lat, 6);
//    Serial.print("Longitude:");

//    Serial.println(Lon, 6);
//    
//    Serial.print("Altitude Feet:");
//    Serial.println(gps.altitude.feet());
//    Serial.println("");


    process();
  }


  


}






void process(){
    delay(Delay);
  in="CO2 "+toPer(analogRead(A0));
  myS.print(in);
  Serial.println(in);
 
     delay(Delay);

  
  in="CO "+toPer(analogRead(A1));
  myS.print(in);
  Serial.println(in);
   
    
    delay(Delay);
   
  in="CH4 "+toPer(analogRead(A2));
  myS.print(in);
  Serial.println(in);

    
    delay(Delay);
   
  in="TEM "+String(getTemp());
  myS.print(in);
  Serial.println(in);


    delay(Delay);
   
  in="LAT "+String(Lat,6);
  myS.print(in);
  Serial.println(in);


    delay(Delay);
   
  in="LON "+String(Lon,6);
  myS.print(in);
  Serial.println(in);

}






















String toPer(float input){

  float out=100-((1024-input)*100)/1024;
  return String(out);

}











void disLocation()
{
    while(myS.available())              //While there are incoming characters  from the GPS
  {
    gps.encode(myS.read());           //This feeds the serial NMEA data into the library one char at a time
  }
  if(gps.location.isUpdated())          //This will pretty much be fired all the time anyway but will at least reduce it to only after a package of NMEA data comes in
  {
    //Get the latest info from the gps object which it derived from the data sent by the GPS unit
    Serial.print("Satellite Count:");
    Serial.println(gps.satellites.value());
    Serial.print("Latitude:");
    Lat=gps.location.lat();
    Serial.println(Lat, 6);
    Serial.print("Longitude:");
    Lon=gps.location.lng();
    Serial.println(Lon, 6);
    
    Serial.print("Altitude Feet:");
    Serial.println(gps.altitude.feet());
    Serial.println("");

    Serial.print("Date: ");
  if (gps.date.isValid())
  {
    Serial.print(gps.date.month());
    Serial.print("/");
    Serial.print(gps.date.day());
    Serial.print("/");
    Serial.println(gps.date.year());
  }
  
  Serial.print("Time: ");
  if (gps.time.isValid())
  {
    if (gps.time.hour() < 10) Serial.print(F("0"));
    Serial.print(gps.time.hour());
    Serial.print(":");
    if (gps.time.minute() < 10) Serial.print(F("0"));
    Serial.print(gps.time.minute());
    Serial.print(":");
    if (gps.time.second() < 10) Serial.print(F("0"));
    Serial.print(gps.time.second());
    Serial.print(".GMT");
  }
  Serial.println("");
  }else{
  //  Serial.println("GPS Not updated");
  }
  

}



float getTemp(){
Vo = analogRead(A3);
R2 = R1* (1023.0 / (float)Vo - 1.0);
logR2 = log(R2);
T = (1.0 / (A + B*logR2 + C*logR2*logR2*logR2));  // Steinhart and Hart Equation. T  = 1 / {A + B[ln(R)] + C[ln(R)]^3}
T =  T - 273.15;

Serial.print("TEMP = ");
Serial.print(T);
Serial.println(" C");
return  T;
}
