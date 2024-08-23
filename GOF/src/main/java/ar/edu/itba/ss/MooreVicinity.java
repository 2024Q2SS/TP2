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

    public HashMap<Coordinates, Set<Coordinates>> getNeighbours(Board board) {
        HashMap<Coordinates, Set<Coordinates>> neighbourCoordinates = new HashMap<>();
        Integer dimensions = board.getDimensions();
        if (dimensions == 2) {
            for (Cell cell : board.getCells()) {
                for (int x = 0; x <= radius; x++) {
                    if (x + cell.getCoordinates().getX() == board.getSize())
                        continue;
                    for (int y = -radius; y <= radius; y++) {
                        if (x == 0 && (y == -radius || y == 0))
                            continue;
                        if (y + cell.getCoordinates().getY() == board.getSize() || y + cell.getCoordinates().getY() < 0)
                            break;
                        Cell neighbour = board.getCell(
                                new Coordinates2D(x + cell.getCoordinates().getX(), y + cell.getCoordinates().getY()));
                        if (neighbour != null) {
                            addRelationToMap(neighbourCoordinates, cell, neighbour);
                        } else {
                            throw new RuntimeException("Cell:" + new Coordinates2D(x + cell.getCoordinates().getX(),
                                    y + cell.getCoordinates().getY()) + " not found");
                        }
                    }
                }
            }
        } else if (dimensions == 3) {
            for (Cell cell : board.getCells()) {
                for (int x = -radius; x <= radius; x++) {
                    if (x + cell.getCoordinates().getX() == board.getSize() || x + cell.getCoordinates().getX() < 0)
                        continue;
                    for (int y = -radius; y <= radius; y++) {
                        if (y + cell.getCoordinates().getY() == board.getSize() || y + cell.getCoordinates().getY() < 0)
                            continue;
                        for (int z = -radius; z <= radius; z++) {
                            if (z + cell.getCoordinates().getZ() == board.getSize()
                                    || z + cell.getCoordinates().getZ() < 0)
                                continue;
                            if (x == 0 && y == 0 && z == 0)
                                continue;
                            Cell neighbour = board.getCell(new Coordinates3D(x + cell.getCoordinates().getX(),
                                    y + cell.getCoordinates().getY(), z + cell.getCoordinates().getZ()));
                            if (neighbour != null) {
                                addRelationToMap(neighbourCoordinates, cell, neighbour);
                            } else {
                                throw new RuntimeException("Cell:"
                                        + new Coordinates3D(x + cell.getCoordinates().getX(),
                                                y + cell.getCoordinates().getY(), z + cell.getCoordinates().getZ())
                                        + " not found");
                            }
                        }
                    }
                }
            }
        }
        return neighbourCoordinates;
    }
}
