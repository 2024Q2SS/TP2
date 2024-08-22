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

    @Override
    public String toString() {
        return "Cell{" +
                "coordinates=" + coordinates +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cell cell = (Cell) o;
        return coordinates.equals(cell.coordinates) && state == cell.state;
    }

    @Override
    public int hashCode() {
        return coordinates.hashCode();
    }
}
