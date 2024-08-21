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

    public void addRelationToMap(HashMap<Cell, Set<Cell>> neighbours, Cell cell, Cell neighbour) {
        // added to neighbour set
        if (!neighbours.containsKey(neighbour)) {
            neighbours.put(neighbour, new HashSet<>());
        }
        neighbours.get(neighbour).add(cell);
        // added to cell set
        if (!neighbours.containsKey(cell)) {
            neighbours.put(cell, new HashSet<>());
        }
        neighbours.get(cell).add(neighbour);
    }

    public HashMap<Cell, Set<Cell>> getNeighbours(Board board, Integer dimensions) {
        HashMap<Cell, Set<Cell>> neighbours = new HashMap<>();
        if (dimensions == 2) {
            for (Cell cell : board.getCells()) {
                for (int i = -1; i <= radius; i++) {
                    if (i + cell.getCoordinates().getX() == board.getSize() || i + cell.getCoordinates().getX() < 0)
                        break;
                    for (int j = 0; j <= radius; j++) {
                        if (i == 0 && j == 0)
                            continue;
                        if (j + cell.getCoordinates().getY() == board.getSize())
                            break;
                        Cell neighbour = board.getCell(
                                new Coordinates2D(i + cell.getCoordinates().getX(), j + cell.getCoordinates().getY()));
                        if (neighbour != null) {
                            addRelationToMap(neighbours, cell, neighbour);
                            if (!neighbours.containsKey(cell)) {
                                neighbours.put(cell, new HashSet<>());
                            }
                            neighbours.get(cell).add(neighbour);
                        } else {
                            throw new RuntimeException("Cell not found");
                        }
                    }
                }
            }
        }
        return neighbours;
    }
}
