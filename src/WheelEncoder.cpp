#include "WheelEncoder.h"

#define PI (3.141592653589793)

/**
 * @note The unit in this object depends on the unit of the value of the wheelSize
 * @param pin The pin that the wheelencoder is connected to
 * @param wheelSize This is the diameter of the wheel that is connected to the wheelencoder
 */
WheelEncoder::WheelEncoder(int pin, double wheelSize) {
    this->pin = pin;
    this->circumference = wheelSize * PI; //pin en wheelSize uit de main worden hier gebruikt.
}

/**
 * @brief Setup the object
 */
void WheelEncoder::setup() {
    pinMode(this->pin, INPUT); //gebruik de GPIO pin als input
    pulses = 0;
    timeOld = millis();
    wiringPiISR(this->pin, INT_EDGE_RISING, (void (*)(void)) this->onPulse);
}

/**
 * @brief Increments pulse and prints to screen
 */
void *WheelEncoder::onPulse(void) { //calculates speed of the car
    ++pulses; //telt het aantal pulsen
    printf("%d ", pulses);
    fflush(stdout);
}

/**
 * @brief Calculating the current speed
 * @note The scale depends on the scale of the wheelSize given in the constructor
 * @return double The measured speed
 */
double WheelEncoder::getSpeed() {
    double seconds = millis() - timeOld;
    seconds /= 1000;
    double pulsesPerSecond = pulses / seconds;
    double speed = pulsesPerSecond / pulsesPerTurn * circumference;
    timeOld = millis();
    pulses = 0;
    return speed;
}