#ifndef COLUMN_BUMPERS_H
#define COLUMN_BUMPERS_H
//.h defineert de class en word in .cpp ingevuld
class Bumper {
public: //kan ook in andere classes gebruikt worden
	Bumper(int pin);
	void init();
	int getBumperState();
	void *InterruptHandlerUp();
	void *InterruptHandlerDown();
private: //alleen in deze class
	int pin = 0;
	int state;
};


#endif //COLUMN_BUMPERS_H
