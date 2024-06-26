# TrafficSim

## Overview
TrafficSim is a JavaFX-based traffic simulation project. The application simulates the behavior of cars and traffic lights using JavaFX for visualization. This project demonstrates basic principles of multithreading, animation, and GUI development in Java.

## Features
- **Car Simulation**: Multiple cars moving across the screen, pausing at red lights and resuming at green lights.
- **Traffic Light Management**: Traffic lights that change colors periodically, controlling car behavior.
- **User Controls**: Buttons to start, stop, pause, and resume the simulation, as well as add new cars.

## Installation
### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Maven
- Git

### Steps
1. **Clone the Repository**
    ```bash
    git clone git@github.com:y-monson/TrafficSim.git
    cd TrafficSim
    ```

2. **Build the Project**
    ```bash
    mvn clean install
    ```

3. **Run the Application**
    ```bash
    mvn javafx:run
    ```

## Usage
- **Start**: Initializes and starts the traffic simulation.
- **Stop**: Stops the simulation and clears the screen.
- **Pause**: Pauses all cars and traffic lights.
- **Resume**: Resumes the simulation from the paused state.
- **Add Car**: Adds a new car to the simulation.

## Project Structure

TrafficSim/
├── src/
` ├── main/
`` ├── java/
``` └── project3/
``` └── project3/
``` ├── Car.java
``` ├── CarDisplay.java
``` ├── CarManager.java
``` ├── Main.java
``` ├── TrafficLight.java
``` ├── TrafficLightDisplay.java
``` └── TrafficLightManager.java
` ├── test/
`` ├── java/
``` └── project3/
``` └── project3/
``` └── CarManagerTest.java
├── .gitignore
├── pom.xml
└── README.md
