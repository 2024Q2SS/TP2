package ar.edu.itba.ss;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Board {
    private Integer size;
    private Integer frames;
    private Map<Coordinates, Cell> cellMap = new HashMap<>();
    private static final Double spawnSize = 0.25;

    private static final List<Map<Coordinates, Cell>> pastStates = new ArrayList<>();

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
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Cell aux = new Cell(new Coordinates2D(i, j));
                    State state = State.DEAD;
                    if (Math.floor((1 - spawnSize) * size / 2) < i
                            && Math.floor((1 + spawnSize) * size / 2) > i
                            && Math.floor((1 - spawnSize) * size / 2) < j
                            && Math.floor((1 + spawnSize) * size / 2) > j) {
                        if (Math.random() > 1 - density) {
                            state = State.ALIVE;
                        }

                    }
                    aux.setState(state);
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

        if (pastStates.contains(cellMap)) {
            return true;
        }
        return false;
    }

    public Collection<Cell> getCells() {
        return cellMap.values();

    }

    public Integer getFrames() {
        return frames;
    }

    public Cell getCell(Coordinates coordinates) {
        return cellMap.get(coordinates);
    }

    public void update(Map<Coordinates, Cell> map) {
        Gson gson = new Gson();
        String path = "output/frame"+ frames + ".json";
        String rootDir = System.getProperty("user.dir");
 
        if (!Paths.get(path).isAbsolute()) {
            path = Paths.get(rootDir,path ).toString();
        }
        try (FileWriter writer = new FileWriter(path)){
            gson.toJson(cellMap.values(), writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        frames++;
        pastStates.add(cellMap);
        cellMap = map;
    }

    public void printMap() {
        Gson gson = new Gson();
        System.out.println(gson.toJson(cellMap.values()));

    }
}
