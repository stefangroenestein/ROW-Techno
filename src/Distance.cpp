
#include <cstdint>
#include "Distance.h"

Distance::Distance(int id) : I2CDevice(id) {} //id word ingevuld in i2cdevice
                                              //: betekend dat hij hem extend

void Distance::init() {
    I2CDevice::init(); //hier word het ingevuld in de handle van i2c device

    this->startMeasurement(); //start de measurement
}

void Distance::startMeasurement() {
    this->writeReg8(0, 0x51); //op positie 0 in het register van het i2c device schrijft hij 0x51 (instellingen)
                              //0x51 is een command om de meting te starten
}

uint8_t Distance::readDistance() {
    uint8_t dist = (uint8_t) this->readReg8(0x03); //hij leest register 3 (dit is distance)
    //Integer type with a width of exactly 8 bits. (unsinged)
    this->startMeasurement(); //start een nieuwe measurement

    return dist; //geeft de distance terug
}
