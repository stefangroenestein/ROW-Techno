#include <unistd.h>
#include <cstdint>
#include <cmath>
#include <iostream>
#include "Motors.h"

Motors::Motors(int id) : I2CDevice(id) {}

void Motors::init() {
    I2CDevice::init();

	//motor power settings
    uint8_t TotalPower[2] = {4, 230};
    uint8_t SoftStart[3] = {0x91, 23, 0};

    this->send(&TotalPower[0], 2);
    this->send(&SoftStart[0], 3);
}

void Motors::sendSpeed() {
    int p1H, p2H, p1L, p2L, d1, d2;

	//convert right and left to msb/lsb
    p2H = (int) this->rightSpeed >> 8;
    p2L = (int) this->rightSpeed & 0xFF;

    p1H = (int) this->leftSpeed >> 8;
    p1L = (int) this->leftSpeed & 0xFF;

	//either zero or two, so if two make it one
    d1 = this->leftDir ? 2 : 1;
    d2 = this->rightDir ? 2 : 1;

	//force everything into unsigned 8 bit int and send to 7
    uint8_t data[7] = {7, (uint8_t) p1H, (uint8_t) p1L, (uint8_t) d1, (uint8_t) p2H, (uint8_t) p2L, (uint8_t) d2};

    this->send(data, 7);
}

void Motors::calculateSpeed(int rotation, double speed) {
    double ls = speed, rs = speed;

	//if we're driving left, leftDir gets 1, same for right.
    this->leftDir = rotation > -45 && rotation <= 135;
    this->rightDir = rotation > -135 && rotation <= 45;

    if (rotation > -180 && rotation <= -135) {
        double d = (rotation * -1. - 135.) / 45.;
        rs = rs * d;

    } else if (rotation > -135 && rotation <= -90) {
        double d = fabs((rotation * -1. - 90.) / 45. - 1.); //fabs geeft je de absolute waarde en doet het x -1
        rs = rs * d;                                        //voorbeeld: fabs(-10.25) = [-10.25] = 10.25

    } else if (rotation > -90 && rotation <= -45.) {
        double d = (rotation * -1. - 45.) / 45.;
        ls = ls * d;

    } else if (rotation > -45 && rotation <= 0) {
        double d = fabs((rotation * -1.) / 45. - 1.);
        ls = ls * d;

    } else if (rotation > 0 && rotation <= 45.) {
        double d = fabs(rotation / 45. - 1.);
        rs = rs * d;

    } else if (rotation > 45 && rotation <= 90) {
        double d = (rotation - 45.) / 45.;
        rs = rs * d;

    } else if (rotation > 90 && rotation <= 135) {
        double d = fabs((rotation - 90.) / 45. - 1.);
        ls = ls * d;

    } else if (rotation > 135 && rotation <= 180) {
        double d = (rotation - 135.) / 45.;
        ls = ls * d;

    }

    this->rightSpeed = rs;
    this->leftSpeed = ls;
}

bool Motors::getDirection () {
    //return 1 if we're driving backwards for safety beeper
	return !this->leftDir && !this->rightDir;
}

void Motors::calculateAndSend(int rotation, double speed) {
	//interface / coupler for main code
    this->calculateSpeed(rotation, speed);
    this->sendSpeed();
}
