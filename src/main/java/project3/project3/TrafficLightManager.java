package project3.project3;

/* JavaFX Traffic Simulation
 * Author: Yelena Monson
 * Date: April 30, 2024
 * File name TrafficLightManager.java is responsible for managing traffic light objects. It handles operations to
 * add traffic lights, cycle through colors (green and red).
 */

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class TrafficLightManager {
    private List<TrafficLight> trafficLights;

    public TrafficLightManager() {
        this.trafficLights = new ArrayList<>();
    }

    public void addTrafficLight(float initialX, Color color) {
        TrafficLight newTrafficLight = new TrafficLight(initialX, Color.GREEN);
        trafficLights.add(newTrafficLight);
    }

    public void addTrafficLights(int numberOfLights) {
        float initialX = 300;
        float spacing = 250;
        for (int i = 0; i < numberOfLights; i++) {
            addTrafficLight( initialX + i * spacing, Color.GREEN);
        }
    }

    public List<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

    public void updateTrafficLights() {
    }
}
