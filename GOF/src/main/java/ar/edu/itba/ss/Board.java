package ar.edu.itba.ss;

import java.util.Map;
import java.util.HashMap;

public class Board {
    private Integer size;
    private Integer frames;
    private Map<Coordinates, Cell> cellMap = new HashMap<>();

    public Board(Integer length) {
        this.size = length;
        this.frames = 0;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void addCell(Cell cell) {
        cellMap.put(cell.getCoordinates(), cell);
    }

    public void setCells(Integer dimensions) {

    }
}
