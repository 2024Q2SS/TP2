package ar.edu.itba.ss;

public class Cell {
    private final Coordinates coordinates;
    private State state;

    public Cell(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
