package project3.project3;

//JavaFX Traffic Simulation

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class Main extends Application {
    private Label timerLabel;
    private volatile boolean running = false;
    private long accumulatedTime = 0;
    private long lastStartTime = 0;
    private VBox simulationArea;
    private HBox hBoxLights;
    private HBox hBoxCars;
    private CarDisplay carDisplay;
    private CarManager carManager;
    private AnimationTimer animationTimer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        simulationArea = new VBox(0);

        carManager = new CarManager();
        carManager.addCars(3);
        TrafficLightManager trafficLightManager = new TrafficLightManager();
        trafficLightManager.addTrafficLights(3);
        carDisplay = new CarDisplay(carManager);
        TrafficLightDisplay trafficLightDisplay = new TrafficLightDisplay(trafficLightManager);

        BorderPane root = new BorderPane();
        VBox timerBox = new VBox();
        setupTimer(timerBox);
        timerBox.setPadding(new Insets(20, 50, 20, 50));
        root.setTop(timerBox);

        hBoxLights = new HBox();
        hBoxLights.getChildren().add(trafficLightDisplay);
        hBoxCars = new HBox();
        hBoxCars.getChildren().add(carDisplay);
        simulationArea.getChildren().addAll(hBoxLights, hBoxCars);
        root.setCenter(simulationArea);

        //Buttons =========================================================
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            startTimer();
            carManager.restart();
            carDisplay.clearCarShapes();
            carDisplay.updateCarShapes();
            startSimulation();
        });
        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> {
            resetTimer();
            stopSimulation();
        });
        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> {
            carManager.resume();
            trafficLightDisplay.startAnimation();
            startTimer();
        });
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> {
            carManager.pause();
            trafficLightDisplay.stopLightAnimation();
            stopTimer();
        });

        Button addCarButton = new Button("Add Car");
        addCarButton.setOnAction(e -> {
            carManager.addCar(0);
            carDisplay.updateCarShapes();
        });

        List<Button> buttons = Arrays.asList(startButton, stopButton, resumeButton, pauseButton, addCarButton);
        Font buttonFont = Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 20);
        buttons.forEach(button -> button.setFont(buttonFont));

        HBox hButtonsBox = new HBox(10);
        hButtonsBox.setAlignment(Pos.CENTER);
        hButtonsBox.setPadding(new Insets(10,0,20,0));
        hButtonsBox.getChildren().addAll(startButton, stopButton, resumeButton, pauseButton, addCarButton);
        root.setBottom(hButtonsBox);

        Scene scene = new Scene(root, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        setupAnimationTimer(carManager, trafficLightManager);
        startTimer();
        animationTimer.start();
    }

    private void setupTimer(VBox timerBox) {
        timerLabel = new Label("0.00 seconds");
        Font font = Font.font("Veranda", FontWeight.BOLD, 25);
        timerLabel.setFont(font);
        timerBox.getChildren().add(timerLabel);
    }

    private void setupAnimationTimer(CarManager carManager, TrafficLightManager trafficLightManager) {
        animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;

                if (running) {
                    carManager.updateCarBehavior((float) deltaTime, trafficLightManager.getTrafficLights());
                    carDisplay.updateCarShapes();
                    trafficLightManager.updateTrafficLights();
                    long elapsedTime = System.nanoTime() - lastStartTime + accumulatedTime;
                    timerLabel.setText(String.format("%.2f seconds", elapsedTime / 1_000_000_000.0));
                }
                lastUpdate = now;
            }
        };
    }

    // Methods to control the timer ===============================
    public void startTimer() {
        lastStartTime = System.nanoTime();
        running = true;
    }

    public void stopTimer() {
        accumulatedTime += System.nanoTime() - lastStartTime;
        running = false;
    }

    public void resetTimer() {
        accumulatedTime = 0;  // Clear
        lastStartTime = System.nanoTime();
        timerLabel.setText("0.00 seconds");
        running = false;  //
    }

    // Methods to control simulation start and stop =================================================
    public void stopSimulation() {
        simulationArea.setStyle("-fx-background-color: lightgrey;");
        simulationArea.getChildren().clear();
        simulationArea.getChildren().addAll(new Label("Simulation Stopped"));
        if (carDisplay != null) {
            carDisplay.clearCarShapes();
        }
        carManager.getCars().clear();
    }

    public void startSimulation() {
        simulationArea.setStyle("-fx-background-color: white;");
        simulationArea.getChildren().clear();
        simulationArea.getChildren().addAll(hBoxLights, hBoxCars);
        carManager.restart();
        carDisplay.updateCarShapes();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
