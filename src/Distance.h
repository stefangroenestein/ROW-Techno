
#ifndef COLUMN_DISTANCE_H
#define COLUMN_DISTANCE_H


#include <cstdint>
#include "I2CDevice.h"
//.h defineert de class en word in .cpp ingevuld
class Distance : public I2CDevice { //extend i2c device
public:
    Distance(int id);

    virtual void init() override; //dit is virtual omdat je hem override

    void startMeasurement();
    uint8_t readDistance(); //Integer type with a width of exactly 8 bits. (unsigned)
};


#endif //COLUMN_DISTANCE_H
