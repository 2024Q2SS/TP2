package ar.edu.itba.ss;

import java.io.File;
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
    private Integer dimensions;
    private Map<Coordinates, Cell> cellMap = new HashMap<>();
    private static final Double spawnSize = 0.25;

    private static final List<Map<Coordinates, Cell>> pastStates = new ArrayList<>();

    public Board(Integer length, Integer dimensios) {
        this.size = length;
        this.frames = 0;
        this.dimensions = dimensios;
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

    public void setCells(Float density) {
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
        } else if (dimensions == 3) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        Cell aux = new Cell(new Coordinates3D(i, j, k));
                        State state = State.DEAD;
                        if (Math.floor((1 - spawnSize) * size / 2) < i
                                && Math.floor((1 + spawnSize) * size / 2) > i
                                && Math.floor((1 - spawnSize) * size / 2) < j
                                && Math.floor((1 + spawnSize) * size / 2) > j
                                && Math.floor((1 - spawnSize) * size / 2) < k
                                && Math.floor((1 + spawnSize) * size / 2) > k) {
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
    }

    public Integer getDimensions() {
        return dimensions;
    }

    public Boolean finalState() {
        if (dimensions == 2) {
            for (int x = 0; x < size; x += size - 1) {
                for (int y = 0; y < size; y++) {
                    if (cellMap.get(new Coordinates2D(x, y)).getState() == State.ALIVE) {
                        System.out.println("termino porque toco el borde");
                        return true;
                    }
                }
            }
            for (int y = 0; y < size; y += size - 1) {
                for (int x = 0; x < size; x++) {
                    if (cellMap.get(new Coordinates2D(x, y)).getState() == State.ALIVE) {
                        System.out.println("termino porque toco el borde");
                        return true;
                    }
                }
            }
        } else if (dimensions == 3) {
            for (int x = 0; x < size; x += size - 1) {
                for (int y = 0; y < size; y++) {
                    for (int z = 0; z < size; z++) {
                        if (cellMap.get(new Coordinates3D(x, y, z)).getState() == State.ALIVE) {
                            System.out.println("termino porque toco el borde");
                            return true;
                        }
                    }
                }
            }
            for (int y = 0; y < size; y += size - 1) {
                for (int x = 0; x < size; x++) {
                    for (int z = 0; z < size; z++) {
                        if (cellMap.get(new Coordinates3D(x, y, z)).getState() == State.ALIVE) {
                            System.out.println("termino porque toco el borde");
                            return true;
                        }
                    }
                }
            }
            for (int z = 0; z < size; z += size - 1) {
                for (int y = 0; y < size; y++) {
                    for (int x = 0; x < size; x++) {
                        if (cellMap.get(new Coordinates3D(x, y, z)).getState() == State.ALIVE) {
                            System.out.println("termino porque toco el borde");
                            return true;
                        }
                    }
                }
            }
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

    public void update(Map<Coordinates, Cell> map, String system, Integer runNumber, Float density) {
        Gson gson = new Gson();
        String path = "output/" + system + "/" + density + "/" + runNumber + "/frame" + frames + ".json";
        String rootDir = System.getProperty("user.dir");

        if (!Paths.get(path).isAbsolute()) {
            path = Paths.get(rootDir, path).toString();
        }
        final File file = new File(path);
        final File parentDirectory = file.getParentFile();
        if (!parentDirectory.exists() && !parentDirectory.mkdirs()) {
            throw new IllegalStateException("Couldn't create directory: " + parentDirectory);
        }

        try (FileWriter writer = new FileWriter(file)) {
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

    public void reset() {
        frames = 0;
        cellMap.clear();
        pastStates.clear();
    }
}
