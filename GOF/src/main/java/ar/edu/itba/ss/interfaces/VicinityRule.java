package ar.edu.itba.ss.interfaces;

import ar.edu.itba.ss.Cell;

import java.util.HashMap;
import java.util.Set;
import ar.edu.itba.ss.Board;

public interface VicinityRule {
    public HashMap<Cell, Set<Cell>> getNeighbours(Board board, Integer dimensions);
}
