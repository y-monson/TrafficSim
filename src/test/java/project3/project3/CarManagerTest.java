package project3.project3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarManagerTest {
    private CarManager carManager;

    @BeforeEach
    public void setUp() {
        carManager = new CarManager();
        carManager.addCars(3); // Adding 3 cars initially
    }

    @Test
    public void testAddCars() {
        List<Car> cars = carManager.getCars();
        assertEquals(3, cars.size(), "Initial number of cars should be 3");
    }

    @Test
    public void testPauseAllCars() {
        carManager.pause();

        for (Car car : carManager.getCars()) {
            assertTrue(car.getSpeedX() == 0, "Car speed should be 0 after pausing");
            assertTrue(car.isStopped(), "Car should be marked as stopped");
        }
    }

    @Test
    public void testResumeAllCars() {
        carManager.pause();
        carManager.resume();

        for (Car car : carManager.getCars()) {
            assertTrue(car.getSpeedX() > 0, "Car speed should be greater than 0 after resuming");
            assertTrue(!car.isStopped(), "Car should not be marked as stopped");
        }
    }
}

