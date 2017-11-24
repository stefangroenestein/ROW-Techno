
#ifndef COLUMN_I2CDEVICE_H
#define COLUMN_I2CDEVICE_H


#include <cstddef>
//.h defineert de class en word in .cpp ingevuld
class I2CDevice {
public:
    I2CDevice(int id);

    virtual void init(); //dit is virutal omdat je hem moet kunnen overriden
    int send(const void *data, size_t size);
    int writeReg8(int reg, int data);
    int readReg8(int reg);
    int readReg16(int reg);

private:
    int id;
protected:
    int handle; //revereert naar de i2c device
};


#endif //COLUMN_I2CDEVICE_H

