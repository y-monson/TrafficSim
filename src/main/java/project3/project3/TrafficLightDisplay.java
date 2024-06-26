package project3.project3;

/* JavaFX Traffic Simulation
 * Author: Yelena Monson
 * Date: April 30, 2024
 * File name TrafficLightDisplay.java visualizes traffic lights managed by TrafficLightManager.
 */

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class TrafficLightDisplay extends Pane {
    private final TrafficLightManager tlManager;
    private AnimationTimer timer;
    private long lastUpdate = 0;

    public TrafficLightDisplay(TrafficLightManager tlManager) {
        this.tlManager = tlManager;
        initTrafficLights();
        startAnimation();
    }

    private void initTrafficLights() {
        for (TrafficLight tl : tlManager.getTrafficLights()) {
            Line post = new Line(tl.getX(), tl.getY(), tl.getX(), tl.getY() - 30);
            post.setStrokeWidth(2);
            post.setStroke(tl.getColor());

            Circle lamp = new Circle(tl.getX(), tl.getY() - 30,10);
            lamp.setFill(tl.getColor());
            tl.setLamp(lamp);
            getChildren().addAll(post, lamp);
        }
    }

    public void startAnimation() {
        if (timer == null) {
            timer = new AnimationTimer() {
                private long lastUpdate = 0;
                @Override
                public void handle(long now) {
                    if (lastUpdate == 0) {
                        lastUpdate = now;
                    }
                    if (now - lastUpdate >= 2_000_000_000) {
                        for (TrafficLight tl : tlManager.getTrafficLights()) {
                            Color nextColor = tl.getNextColor();
                            tl.setColor(nextColor);
                        }
                        lastUpdate = now;
                    }
                }
            };
        }
        timer.start();
    }

    public void stopLightAnimation() {
        if (timer != null) {
            timer.stop();
        }
        lastUpdate = 0;
    }

}
