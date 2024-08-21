package ar.edu.itba.ss;

import ar.edu.itba.ss.interfaces.VicinityRule;

public class MooreVicinity implements VicinityRule {

    private final int radius;

    public MooreVicinity(int radius) {
        this.radius = radius;
    }

    @Override
    public boolean areNeighbours(Cell c1, Cell c2) {
        return Math.abs(c1.getCoordinates().getX() - c2.getCoordinates().getX()) <= radius &&
                Math.abs(c1.getCoordinates().getY() - c2.getCoordinates().getY()) <= radius;
    }
}
