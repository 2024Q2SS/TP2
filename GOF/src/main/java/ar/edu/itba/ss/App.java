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

    public void GOF2D() {
        board.setCells(config.getDensity());
        Map<Coordinates, Set<Coordinates>> neighbours = new MooreVicinity(1).getNeighbours(board);
        Map<Coordinates, Cell> newMap = new HashMap<>();
        Integer count = 0;
        Integer maxNeighbours = new MooreVicinity(1).maxNeighbours(board.getDimensions());
    
        String path = Paths.get(rootDir, "output.csv").toString();

        try (PrintWriter csvWriter = new PrintWriter(new FileWriter(path))) {
        // Escribir encabezado en el CSV
            csvWriter.println("Frame,AverageAliveNeighbours");

            while (!board.finalState() && board.getFrames() <= 10000) {
                System.out.println("Frame: " + board.getFrames());
                newMap = new HashMap<>();
                count = 0;

                for (Cell cell : board.getCells()) {
                    Cell newCell = new Cell(cell.getCoordinates());

                    int aliveNeighbours = neighbours.get(cell.getCoordinates()).stream()
                            .mapToInt(c -> board.getCell(c).getState() == State.ALIVE ? 1 : 0)
                            .sum();
                    count += aliveNeighbours;
                    newCell.setState(cell.getState() == State.ALIVE
                            ? (aliveNeighbours >= minAlive && aliveNeighbours <= maxAlive ? State.ALIVE : State.DEAD)
                            : (aliveNeighbours == newCellNum ? State.ALIVE : State.DEAD));
                    newMap.put(cell.getCoordinates(), newCell);
                }

                Double avgAliveNeighbours = count/Math.pow(board.getSize(),board.getDimensions()) ;
            
            // Escribir el frame y el promedio en el CSV
                csvWriter.println(board.getFrames() + "," + avgAliveNeighbours);
                
                board.update(newMap);
            }
            board.update(newMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        App app = new App();
        app.setUp();
        System.out.println("setup finished");
        System.out.println(config);
        if (config.getSystem().equals("conway")) {
            app.GOF2D();
        }

    }
}
