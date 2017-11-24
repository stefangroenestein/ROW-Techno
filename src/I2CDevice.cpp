#include <wiringPiI2C.h>
#include <unistd.h>
#include "I2CDevice.h"

I2CDevice::I2CDevice(int id) {
    this->id = id; //zet id uit de .h hier neer
}

void I2CDevice::init() {
    this->handle = wiringPiI2CSetup(this->id); //geeft id mee aan de functie wiringpii2csetup en die zet het weer in de handle
}

int I2CDevice::send(const void *data, size_t size) {
    return (int) write(this->handle, data, size); //speelt de data door naar de i2cdevice (de handle is voor welk device)
}

int I2CDevice::writeReg8(int reg, int data) {
    return wiringPiI2CWriteReg8(this->handle, reg, data); //schrijft in register van i2c device (handle is voor welk device)
}

int I2CDevice::readReg8(int reg) {
    return wiringPiI2CReadReg8(this->handle, reg); //leest het register van i2c deivice (8 bits)
}

int I2CDevice::readReg16(int reg) {
    return wiringPiI2CReadReg16(this->handle, reg); //leest het register van i2c deivice (16 bits)
}
