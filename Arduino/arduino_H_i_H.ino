int lastInputKey0 = HIGH;
int lastInputKey1 = HIGH;
int lastInputKey2 = HIGH;
int lastInputKey3 = HIGH;
int lastInputKey4 = HIGH;
int lastInputKey5 = HIGH;
int lastInputKey6 = HIGH;
int lastInputKey7 = HIGH;
int lastInputKey8 = HIGH;


int input;
bool wasPressed = false;

void setup() {
  Serial.begin(9600);
  //pinMode(key1, INPUT);
  //pinMode(key2, INPUT);

}

void loop() {
  input = digitalRead(D0);
  if(input == LOW && lastInputKey0 == HIGH)
  {
    wasPressed = true;
    Serial.println("key0:p"); 
    lastInputKey0 = LOW;   
  }
  else if(input == HIGH && lastInputKey0 == LOW)
  {
    lastInputKey0 = HIGH;
    Serial.println("key0:r");
  }


   input = digitalRead(D1);
  if(input == LOW && lastInputKey1 == HIGH)
  {
    wasPressed = true;
    Serial.println("key1:p"); 
    lastInputKey1 = LOW;   
  }
  else if(input == HIGH && lastInputKey1 == LOW)
  {
    lastInputKey1 = HIGH;
    Serial.println("key1:r");
  }


   input = digitalRead(D2);
  if(input == LOW && lastInputKey2 == HIGH)
  {
    wasPressed = true;
    Serial.println("key2:p"); 
    lastInputKey2 = LOW;   
  }
  else if(input == HIGH && lastInputKey2 == LOW)
  {
    lastInputKey2 = HIGH;
    Serial.println("key2:r");
  }


   input = digitalRead(D3);
  if(input == LOW && lastInputKey3 == HIGH)
  {
    wasPressed = true;
    Serial.println("key3:p"); 
    lastInputKey3 = LOW;   
  }
  else if(input == HIGH && lastInputKey3 == LOW)
  {
    lastInputKey3 = HIGH;
    Serial.println("key3:r");
  }


   input = digitalRead(D4);
  if(input == LOW && lastInputKey4 == HIGH)
  {
    wasPressed = true;
    Serial.println("key4:p"); 
    lastInputKey4 = LOW;   
  }
  else if(input == HIGH && lastInputKey4 == LOW)
  {
    lastInputKey4 = HIGH;
    Serial.println("key4:r");
  }


   input = digitalRead(D5);
  if(input == LOW && lastInputKey5 == HIGH)
  {
    wasPressed = true;
    Serial.println("key5:p"); 
    lastInputKey5 = LOW;   
  }
  else if(input == HIGH && lastInputKey5 == LOW)
  {
    lastInputKey5 = HIGH;
    Serial.println("key5:r");
  }


   input = digitalRead(D6);
  if(input == LOW && lastInputKey6 == HIGH)
  {
    wasPressed = true;
    Serial.println("key6:p"); 
    lastInputKey6 = LOW;   
  }
  else if(input == HIGH && lastInputKey6 == LOW)
  {
    lastInputKey6 = HIGH;
    Serial.println("key6:r");
  }


   input = digitalRead(D7);
  if(input == LOW && lastInputKey7 == HIGH)
  {
    wasPressed = true;
    Serial.println("key7:p"); 
    lastInputKey7 = LOW;   
  }
  else if(input == HIGH && lastInputKey7 == LOW)
  {
    lastInputKey7 = HIGH;
    Serial.println("key7:r");
  }


   input = digitalRead(D8);
  if(input == LOW && lastInputKey8 == HIGH)
  {
    wasPressed = true;
    Serial.println("key8:p"); 
    lastInputKey8 = LOW;   
  }
  else if(input == HIGH && lastInputKey8 == LOW)
  {
    lastInputKey8 = HIGH;
    Serial.println("key8:r");
  }
  

  if(wasPressed == true)
  {
    delay(150);
    wasPressed = false;
  }
}
