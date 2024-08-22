package ar.edu.itba.ss;

import ar.edu.itba.ss.interfaces.VicinityRule;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class MooreVicinity implements VicinityRule {

    private final int radius;

    public MooreVicinity(int radius) {
        this.radius = radius;
    }

    public void addRelationToMap(HashMap<Coordinates, Set<Coordinates>> neighbours, Cell cell, Cell neighbour) {
        // added to neighbour set
        if (!neighbours.containsKey(neighbour.getCoordinates())) {
            neighbours.put(neighbour.getCoordinates(), new HashSet<>());
        }
        neighbours.get(neighbour.getCoordinates()).add(cell.getCoordinates());
        // added to cell set
        if (!neighbours.containsKey(cell.getCoordinates())) {
            neighbours.put(cell.getCoordinates(), new HashSet<>());
        }
        neighbours.get(cell.getCoordinates()).add(neighbour.getCoordinates());
    }

    public HashMap<Coordinates, Set<Coordinates>> getNeighbours(Board board, Integer dimensions) {
        HashMap<Coordinates, Set<Coordinates>> neighbourCoordinates = new HashMap<>();
        if (dimensions == 2) {
            for (Cell cell : board.getCells()) {
                for (int i = -1; i <= radius; i++) {
                    if (i + cell.getCoordinates().getX() == board.getSize() || i + cell.getCoordinates().getX() < 0)
                        continue;
                    for (int j = 0; j <= radius; j++) {
                        if (i == -1 && j == 0)
                            continue;
                        if (i == 0 && j == 0)
                            continue;
                        if (j + cell.getCoordinates().getY() == board.getSize())
                            break;
                        Cell neighbour = board.getCell(
                                new Coordinates2D(i + cell.getCoordinates().getX(), j + cell.getCoordinates().getY()));
                        if (neighbour != null) {
                            addRelationToMap(neighbourCoordinates, cell, neighbour);
                        } else {
                            throw new RuntimeException("Cell not found");
                        }
                    }
                }
            }
        }
        return neighbourCoordinates;
    }
}
