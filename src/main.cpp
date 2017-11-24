#include <wiringPi.h>
#include <pthread.h>
#include <iostream>
#include "Server.h"
#include "Motors.h"
#include "Compass.h"
#include "Distance.h"
#include "wheelEncoder.h"

using namespace std;

pthread_t sensor_thread, beep_thread;

Compass *compass;
Distance *distance_sensor;
Motors *motors;
Server *server;
WheelEncoder *wheel_encoder;

int client;

bool stopping = false;

void *sensorRun(void *vd) {
    size_t size = sizeof(int) * 2 + sizeof(double);
    char *data = (char *) malloc(size);

    while (!stopping) { //als de client verbinding heeft blijft hij dit doen
        int angle = (int) compass->readAngle(); //compass uitlezen
        *((int *) data) = angle;
        data += sizeof(int);

        int dist = distance_sensor->readDistance(); //distance sensor uitlezen
        *((int *) data) = dist;
        data += sizeof(int);

        double speed = wheel_encoder->getSpeed(); //wheelencoder snelheid berekenen
        *((double *) data) = speed;
        data += sizeof(double);

        data -= size;
        server->writePacket(client, data, size); //write packet naar client

        if (!stopping) {
            usleep(100000); //delay zodat hij niet de sensoren super snel achter elkaar uitleest
        }
    }
}
////////////////////////////////////
void bleep() {
    digitalWrite(2, 1);
    usleep(5000);
    digitalWrite(2, 0);
}

void bleep2() {
    digitalWrite(2, 1);
    usleep(100000);
    digitalWrite(2, 0);
}

void *beeper(void *vd) {
    while (!stopping) {
        if (motors->getDirection ()) { //als achteruit rijden: bleep2 uitvoeren (vrachtwagen effect)
            bleep2 ();
        }
        sleep(1);
    }
}

void onClose() {
    bleep();

    motors->calculateAndSend(0, 0); //zorgt ervoor dat de robot stil staat na het stoppen, anders blijft hij rijden
}
//////////////////////////////////////////////////////
void onPacket(char *data, size_t size) {
    char angle = data[0];
    char speed = data[1];
    char buzz  = data[2];

    printf("%02X%02X%02X\n", data[0], data[1], data[2]); //print de data die hij krijgt van de client (joystick)

    int16_t angle2 = (int16_t) (angle / 100.0 * 360.0 - 180.0); //berekening stand joystick

    printf("%d %d %d\n", angle2, speed, buzz);

    motors->calculateAndSend(angle2, sqrt(speed) * 100); //vult de gekregen gegevens in in de class motors in de methode
                                                         //calculateAndSend
    digitalWrite(2, buzz); //schrijft naar de GPIO
}

void onClient(int socket) {
    bleep();
    usleep(100000);
    bleep();

    client = socket;

    if (pthread_create(&sensor_thread, NULL, sensorRun, NULL)) {
        cerr << "Error creating sensor thread" << std::endl;
        return;
    }

    if (pthread_create(&beep_thread, NULL, beeper, NULL)) {
        cerr << "Error creating sensor thread" << std::endl;
        return;
    }

    server->handlePackets(socket, onPacket, onClose);
}

/**
 * @brief Sets up the sensors and actuators
 */
void setupHardware() {
    wiringPiSetup();
    pullUpDnControl(0, PUD_DOWN);

    pinMode(2, OUTPUT);

    motors = new Motors(0x32); //id i2c device
    motors->init();
    motors->calculateAndSend(0, 0); //stil staan van de robot

    compass = new Compass(0x1e); //id i2c device
    compass->init();

    distance_sensor = new Distance(0x70); //id i2c device
    distance_sensor->init();

    wheel_encoder = new WheelEncoder(17, 13.01); //pin, wheelsize voor wheel encoder
    wheel_encoder->setup();
}

/**
 * @brief Sets up the server
 */
void setupServer() {
    server = new Server(1337); //zegt dat de server port 1337 moet gebruiken
    if (!server->bindPort()) {
        cerr << "Failed to bind port" << std::endl; //als dit niet lukt zegt hij dit
        return;
    }

    cout << "Port bound" << std::endl; //als het wel lukt zegt hij dit
}

/**
 * @brief Here is where it all begins
 * @param argc The amount of arguments
 * @param argv The arguments
 * @return Process exit code
 */
int main(int argc, char *argv[]) { //hier worden alle methodes uitgevoerd

    setupHardware();
    setupServer();

    bleep();

    server->acceptClient(onClient);

    stopping = true;

    pthread_join(sensor_thread, NULL);
    pthread_join(beep_thread, NULL);

    return 0;
}
