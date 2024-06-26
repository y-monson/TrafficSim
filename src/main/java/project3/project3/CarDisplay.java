package project3.project3;

/* JavaFX Traffic Simulation
 * Author: Yelena Monson
 * Date: April 30, 2024
 * File name CarDisplay.java is responsible for visualizing cars in the jsvafx window. Methods include initCars()
 * that draws rectangles of specific size, startAnimation() updates car position in response to the timer.
 */

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class CarDisplay extends Pane {
    private final CarManager carManager;
    private final TrafficLightManager tlManager;
    private final List<Rectangle> carShapes = new ArrayList<>();
    private final List<Label> carLabels = new ArrayList<>();

    public CarDisplay(CarManager carManager) {
        this.carManager = carManager;
        this.tlManager = new TrafficLightManager();
        initCars();
        startAnimation();
    }

    private void initCars() {
        for (Car car : carManager.getCars()) {
            addCarShape(car);
        }
    }

    private void addCarShape(Car car) {
        Rectangle carShape = new Rectangle(car.getX(), car.getY(), 35, 20);
        carShape.setFill(Color.BLUE);
        carShapes.add(carShape);
        getChildren().add(carShape);

        Label carLabel = new Label(car.getName() + " " + car.getSpeedX());
        carLabel.setTextFill(Color.WHITE);
        carLabels.add(carLabel);

        StackPane stack = new StackPane(carShape, carLabel);
        stack.setLayoutX(car.getX());
        stack.setLayoutY(car.getY());
        getChildren().add(stack);
    }

    public void updateCarShapes() {
        List<Car> cars = carManager.getCars();
        while (carShapes.size() < cars.size()) {
            addCarShape(cars.get(carShapes.size()));
        }

        for (int i = 0; i < carManager.getCars().size(); i++) {
            Car car = carManager.getCars().get(i);
            Rectangle shape = carShapes.get(i);
            Label label = carLabels.get(i);

            // Update the position of both shape and label
            shape.setX(car.getX());
            shape.setY(car.getY());
            label.setText(car.getName() + " " + car.getSpeedX()); // Update the label text with current speed

            // Update the layout of the StackPane
            StackPane stackPane = (StackPane) getChildren().get(i);
            stackPane.setLayoutX(car.getX());
            stackPane.setLayoutY(car.getY());
        }
    }

    public void clearCarShapes() {
        getChildren().clear();
        carShapes.clear();
        carLabels.clear();
    }

    private void startAnimation() {
        final long[] lastUpdate = {0};
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate[0] == 0) {
                    lastUpdate[0] = now;
                    return;
                }
                double deltaTime = (now - lastUpdate[0]) / 1_000_000_000.0;
                for (Car car : carManager.getCars()) {
                    car.updatePosition((float) deltaTime);
                }
                updateCarShapes();
                lastUpdate[0] = now;
            }
        };
        timer.start();
    }

}
