package ar.edu.itba.ss;

import java.io.FileReader;

import com.google.gson.Gson;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class App {
    private Board board;
    private static Config config;
    private String configPath = "../config.json";
    private final static String rootDir = System.getProperty("user.dir");
    private final static Gson gson = new Gson();

    public void setUp() {
        if (!Paths.get(configPath).isAbsolute()) {
            configPath = Paths.get(rootDir, configPath).toString();
        }

        try (FileReader reader = new FileReader(configPath)) {
            config = gson.fromJson(reader, Config.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        board = new Board(config.getSize());
    }

    public void GOF2D() {
        board.setCells(config.getDimensions(), config.getDensity());
        Map<Coordinates, Set<Coordinates>> neighbours = new MooreVicinity(1).getNeighbours(board,
                config.getDimensions());
        Map<Coordinates, Cell> newMap;
        while (!board.finalState() && board.getFrames() <= 10000) {
            System.out.println("Frame: " + board.getFrames());
            newMap = new HashMap<>();
            for (Cell cell : board.getCells()) {
                Cell newCell = new Cell(cell.getCoordinates());

                int aliveNeightbours = neighbours.get(cell.getCoordinates()).stream()
                        .mapToInt(c -> board.getCell(c).getState() == State.ALIVE ? 1 : 0)
                        .sum();

                if (cell.getCoordinates().getX() == 4 && cell.getCoordinates().getY() == 4) {
                    System.out.println("Cell: " + cell.getCoordinates());
                    System.out.println("Alive Neighbours: " + aliveNeightbours);
                    neighbours.get(cell.getCoordinates())
                            .forEach(c -> System.out.println(c + " state: " + board.getCell(c).getState()));

                }
                newCell.setState(cell.getState() == State.ALIVE
                        ? (aliveNeightbours > 1 && aliveNeightbours < 4 ? State.ALIVE : State.DEAD)
                        : (aliveNeightbours == 3 ? State.ALIVE : State.DEAD));
                newMap.put(cell.getCoordinates(), newCell);
            }
            board.update(newMap);
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.setUp();
        System.out.println("setup finished");
        System.out.println(config);
        if (config.getDimensions() == 2) {
            app.GOF2D();
        }

    }
}
