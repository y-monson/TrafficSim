package project3.project3;

/* JavaFX Traffic Simulation
* Author: Yelena Monson
* Date: April 30, 2024
* File name Car.java defines the Car. The attibutes are float x to capture x coordinate location, float y is hardcoded.
* Another attribure is speedX to capture the speed and boolean isStopped to determine if the car is stopped or moving.
* The class provides getters and setters as well as the method to stop, resume, and update position of the car.
* This class encapsulated the state and behavior of a car.
*/

public class Car {
    private float x;
    private float y;
    private int speedX;
    private boolean isStopped = false;
    private int lastSpeed;
    private static final int SCREEN_WIDTH = 1000;
    private String name;

    public Car(float x, int initialSpeed, String name) {
        this.x = x;
        this.y = 0;
        speedX = initialSpeed;
        this.lastSpeed = initialSpeed;
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void updatePosition(float deltaTime) {
        if (!isStopped) {
            this.x += speedX * deltaTime;
        }
        if (this.x > SCREEN_WIDTH) {  // loop around the screen
            this.x = -35;
        }
    }

    public void stopOnRed() {
        this.isStopped = true;
    }

    public void resumeOnGreen() {
        this.isStopped = false;
    }

    public void pauseCarSim() {
        if (!isStopped) {
            lastSpeed = speedX;
            speedX = 0;
            isStopped = true;
        }
    }

    public void resumeCarSim() {
        this.speedX = lastSpeed;
        isStopped = false;
    }

    public String getName() {
        return name;
    }

    public boolean isStopped() {
        return isStopped;
    }
}
