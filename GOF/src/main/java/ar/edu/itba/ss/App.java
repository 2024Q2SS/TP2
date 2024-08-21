package ar.edu.itba.ss;

import java.io.FileReader;

import com.google.gson.Gson;
import java.nio.file.Paths;

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

    public static void main(String[] args) {
        App app = new App();
        app.setUp();
    }
}
