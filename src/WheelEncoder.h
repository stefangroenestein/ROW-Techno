#ifndef COLUMN_WHEELENCODER_H
#define COLUMN_WHEELENCODER_H


#include <stdint.h>
#include "stdio.h"
#include <wiringPi.h>

class WheelEncoder {
public:
    WheelEncoder(int pin, double wheelSize);
    void setup();
    void *onPulse(void);
    double getSpeed();

private:
    int pin;
    double circumference;
    volatile uint8_t pulses;
    unsigned int pulsesPerTurn = 20;
    unsigned long timeOld;
};


#endif //COLUMN_WHEELENCODER_H
