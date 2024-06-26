package project3.project3;

/* JavaFX Traffic Simulation
 * Author: Yelena Monson
 * Date: April 30, 2024
 * File name TrafficLight.java defines traffic light. It has an attribute of x and y coordinats, y coordinate is hardcoded.
 * It also has attribure Color color and a list of colors consiting of green and red. In addition, Circle lamp defines
 * defines the traffic light.
 * Methods include getters and setters, a method to get the next color, and a boolean method isRed. It is used
 * to stop the car.
 * This class encapsulates the state and behavior of a traffic light.
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.List;

public class TrafficLight {
    private float x;
    private float y;
    private Color color;
    private Circle lamp;
    private List<Color> colors = Arrays.asList(Color.GREEN, Color.RED);
    private int currentColorIndex = 0;

    public TrafficLight(float x, Color color) {
        this.x = x;
        this.y = 150;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public void setColor(Color newColor) {
        this.color = newColor;
        this.lamp.setFill(newColor);
    }

    public void setLamp(Circle lamp) {
        this.lamp = lamp;
    }
    public Color getNextColor() {
        // Cycle through the colors
        currentColorIndex = (currentColorIndex + 1) % colors.size();
        return colors.get(currentColorIndex);
    }

    public boolean isRed() {
        return Color.RED.equals(color);
    }
}
