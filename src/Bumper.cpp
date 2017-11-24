#include "Bumper.h"

Bumper::Bumper(int pin) {
	this->pin = pin; //de pin id uit .h word hier ingevuld

                     //-> benaderen van opject variable en methodes via een pointer naar een opject

                     //this is een pointer naar de pin variable in de Bumper.h
};

void Bumper::init() {
	wiringPiISR (this->pin, INT_EDGE_FALLING, this->InterruptHandlerDown); //als edge_falling is runt hij de down
	wiringPiISR (this->pin, INT_EDGE_RISING, this->InterruptHandlerUp); //als edge rising is runt hij de up

                                                                        //:: benaderen van statische variable en
                                                                        //methodes van een class/structure/namespace
}

int Bumper::getBumperState () {
	return this->state; //geeft de state terug die hij van de interrupthandler heeft gekregen (deze kan je elders
}						//gebruiken om te kijken of hij ergens tegen aan zit. 1 is ergens tegen aan, 0 is nergens tegen aan)

void *Bumper::InterruptHandlerUp () {
	this->state = 1; //vult de state in, in de bumperstate methode
}

void *Bumper::InterruptHandlerDown () {
	this->state = 0; //vult de state in, in de bumperstate methode
}