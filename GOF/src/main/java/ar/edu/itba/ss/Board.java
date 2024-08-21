package ar.edu.itba.ss;

import java.util.Map;
import java.util.HashMap;

import java.util.Collection;

import com.google.gson.Gson;

public class Board {
    private Integer size;
    private Integer frames;
    private Map<Coordinates, Cell> cellMap = new HashMap<>();
    private static final Double spawnSize = 0.25;

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

    public Boolean inSpawnArea(Integer x, Integer y, Integer spawnArea) {
        if ((x >= 0.5 * size - spawnArea && y >= 0.5 * size - spawnArea)
                && (x <= 0.5 * size + spawnArea && y <= 0.5 * size + spawnArea))
            return true;
        return false;
    }

    public void setCells(Integer dimensions, Float density) {
        if (dimensions == 2) {
            Integer spawnArea = (int) Math.ceil(Math.pow(size * spawnSize, 2)); // area inicial para las celdas
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Cell aux = new Cell(new Coordinates2D(i, j));
                    if (inSpawnArea(i, j, spawnArea))
                        if (Math.random() > 1 - density)
                            aux.setState(State.ALIVE);
                        else
                            aux.setState(State.DEAD);
                    this.addCell(aux);
                }
            }
        }
    }

    public Boolean finalState() {
        for (int x = 0; x < size; x += size - 1) {
            for (int y = 0; y < size; y++) {
                if (cellMap.get(new Coordinates2D(x, y)).getState() == State.ALIVE)
                    return true;
            }
        }
        for (int y = 0; y < size; y += size - 1) {
            for (int x = 0; x < size; x++) {
                if (cellMap.get(new Coordinates2D(x, y)).getState() == State.ALIVE)
                    return true;
            }
        }
        return false;
    }

    public Collection<Cell> getCells() {
        return cellMap.values();
    }

    public Cell getCell(Coordinates coordinates) {
        return cellMap.get(coordinates);
    }

    public void update(Map<Coordinates, Cell> map) {
        cellMap = map;
        frames++;
        Gson gson = new Gson();
        System.out.println(gson.toJson(cellMap));

    }

}
