package ar.edu.itba.ss.interfaces;

import java.util.HashMap;
import java.util.Set;
import ar.edu.itba.ss.Board;
import ar.edu.itba.ss.Coordinates;

public interface VicinityRule {
    public HashMap<Coordinates, Set<Coordinates>> getNeighbours(Board board, Integer dimensions);
}
