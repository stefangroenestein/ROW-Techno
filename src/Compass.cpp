#include <math.h>
#include "Compass.h"

Compass::Compass(int id) : I2CDevice(id) {}

void Compass::init() {
    I2CDevice::init();

    this->writeReg8(0x00, 0x70);
    this->writeReg8(0x01, 0xA0);
    this->writeReg8(0x02, 0x01);
    this->writeReg8(0x02, 0x01);
}

float Compass::readAngle() {
    //read all registers of magnetometer so it will refresh them
    int xm = this->readReg8(0x03);
    int xl = this->readReg8(0x04);
    int ym = this->readReg8(0x07);
    int yl = this->readReg8(0x08);
    int zm = this->readReg8(0x05);
    int zl = this->readReg8(0x06);

	//put read pointer in magneto meter back for next read
    this->writeReg8(0x02, 0x01);

    // Convert from msb/lsb to int
    short x = (short) ((xm << 8) | xl);
    short y = (short) ((ym << 8) | yl);
    short z = (short) ((zm << 8) | zl);

    float angle = (float) ((atan2(y, x)) * 180 / M_PI);
    //With just the above formula, we will get the angle between the values -180 to 180.
    //We will use the if statement below to change this to a angle value between 0 and 360.
    if (angle < 0) {
        angle += 360;
    }

    return angle;
}

