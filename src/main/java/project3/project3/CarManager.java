package project3.project3;

/* JavaFX Traffic Simulation
 * Author: Yelena Monson
 * Date: April 30, 2024
 * File name CarManager.java manages car objects, the mehtods include adding one car, adding multiple cars,
 * and updating car positions.
 */

import java.util.*;

public class CarManager {
    private List<Car> cars;
    private final TrafficLightManager tlManager;

    public CarManager() {
        this.cars = new ArrayList<>();
        this.tlManager = new TrafficLightManager();
    }

    public void addCar(float initialX) {
        int carNum = cars.size() + 1;
        String name = carNum + ":";
        Random rand = new Random();
        int initialSpeed = 30 + rand.nextInt(10);
        Car newCar = new Car(initialX, initialSpeed, name);
        cars.add(newCar);
    }

    public void addCars(int numberOfCars) {
        float initialX = 10;
        float spacing = 100;
        for (int i = 0; i < numberOfCars; i++) {
            addCar(initialX + i * spacing);
        }
    }

    public void updateCarBehavior(float deltaTime, List<TrafficLight> trafficLights) {
        stopCarsAtRedLight(trafficLights);
        slowDownCarsWhenApproachingOthers();
        updateCarPositions(deltaTime);
    }

    private void stopCarsAtRedLight(List <TrafficLight> trafficLights) {
        Collections.sort(cars, Comparator.comparing(Car::getX));
        for (Car car : cars) {
            boolean nearRedLight = false;
            for (TrafficLight light : trafficLights) {
                if ((car.getX() + 20 < light.getX()) && Math.abs(car.getX() - light.getX()) < 35 && light.isRed()) {
                    nearRedLight = true;
                    break;
                }
            }
            if (nearRedLight) {
                car.stopOnRed();
            } else {
                car.resumeOnGreen();
            }
        }
    }

    private void slowDownCarsWhenApproachingOthers() {
        Collections.sort(cars, Comparator.comparing(Car::getX));
        for (int i = 0; i < cars.size() - 1; i++) {
            Car currentCar = cars.get(i);
            Car nextCar = cars.get(i + 1);

            float distance = nextCar.getX() - currentCar.getX();

            if (distance < 0) {
                distance += 1000;
            }
            if (distance < 50) {
                float speedReduction = (50 - distance) / 50 * 10; // Max reduction is 10
                currentCar.setSpeedX((int) Math.max(currentCar.getSpeedX() - speedReduction, 0));
            } else {
                if (currentCar.getSpeedX() < 30) {
                    currentCar.setSpeedX(currentCar.getSpeedX() + 1);
                } else if (currentCar.getSpeedX() > 40) {
                    currentCar.setSpeedX(currentCar.getSpeedX() - 1);
                } else {
                    Random rand = new Random();
                    currentCar.setSpeedX(30 + rand.nextInt(10));
                }
            }
        }
    }

    private void updateCarPositions(float deltaTime) {
        for (Car car : cars) {
            car.updatePosition(deltaTime);
        }

    }

    public void pause() {
        for (Car car : cars) {
            car.pauseCarSim();
        }
    }

    // Resume button
    public void resume() {
        for (Car car : cars) {
            car.resumeCarSim();
        }
    }

    // Start button
    public void restart() {
        //cars.removeAll(cars);
        cars.clear();
        addCars(3);
    }

    public List<Car> getCars() {
        return cars;
    }
}
