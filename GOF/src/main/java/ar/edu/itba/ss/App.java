package ar.edu.itba.ss;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
    private static Integer minAlive;
    private static Integer maxAlive;
    private static Integer newCellNum;

    public void setUp() {
        if (!Paths.get(configPath).isAbsolute()) {
            configPath = Paths.get(rootDir, configPath).toString();
        }

        try (FileReader reader = new FileReader(configPath)) {
            config = gson.fromJson(reader, Config.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Config.SystemConfig sysConfig = config.getSystemConfig(config.getSystem());

        minAlive = sysConfig.getMinAlive();
        maxAlive = sysConfig.getMaxAlive();
        newCellNum = sysConfig.getNewCell();
        System.out.println(sysConfig);
        board = new Board(config.getSize(), config.getDimensions());
    }

    public void GOF2D(Integer runNumber) {
        board.setCells(config.getDensity());
        Map<Coordinates, Set<Coordinates>> neighbours = new MooreVicinity(1).getNeighbours(board);
        Map<Coordinates, Cell> newMap = new HashMap<>();
        Integer count = 0;

        String path = Paths.get(rootDir, config.getSystem() + "_" + config.getDensity() + "_" + runNumber + ".csv")
                .toString();
        Double avgAliveNeighbours;
        try (PrintWriter csvWriter = new PrintWriter(new FileWriter(path))) {
            // Escribir encabezado en el CSV
            csvWriter.println("frame,average_alive_cells");

            while (!board.finalState() && board.getFrames() <= 10000) {
                System.out.println("Frame: " + board.getFrames());
                newMap = new HashMap<>();

                for (Cell cell : board.getCells()) {
                    Cell newCell = new Cell(cell.getCoordinates());
                    count += cell.getState() == State.ALIVE ? 1 : 0;

                    int aliveNeighbours = neighbours.get(cell.getCoordinates()).stream()
                            .mapToInt(c -> board.getCell(c).getState() == State.ALIVE ? 1 : 0)
                            .sum();

                    newCell.setState(cell.getState() == State.ALIVE
                            ? (aliveNeighbours >= minAlive && aliveNeighbours <= maxAlive ? State.ALIVE : State.DEAD)
                            : (aliveNeighbours == newCellNum ? State.ALIVE : State.DEAD));
                    newMap.put(cell.getCoordinates(), newCell);
                }

                // avgAliveNeighbours = count / Math.pow(board.getSize(),
                // board.getDimensions());
                // csvWriter.println(board.getFrames() + "," + avgAliveNeighbours);
                csvWriter.println(board.getFrames() + "," + count);

                board.update(newMap);
                count = 0;
            }
            for (Cell cell : board.getCells()) {
                count += cell.getState() == State.ALIVE ? 1 : 0;
            }

            // avgAliveNeighbours = count / Math.pow(board.getSize(),
            // board.getDimensions());
            // csvWriter.println(board.getFrames() + "," + avgAliveNeighbours);
            csvWriter.println(board.getFrames() + "," + count);

            board.update(newMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        board.reset();
    }

    public static void main(String[] args) {
        App app = new App();
        app.setUp();
        System.out.println("setup finished");
        System.out.println(config);
        if (config.getSystem().equals("conway")) {
            for (int i = 1; i <= config.getRuns(); i++)
                app.GOF2D(i);
        }

    }
}
