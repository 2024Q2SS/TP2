package ar.edu.itba.ss;

import java.util.Map;
import java.util.List;

public class Config {
    private final Integer size;
    private final Integer dimensions;
    private final List<String> systems;
    private final Float density;
    private final Integer runs;
    private Map<String, SystemConfig> systemConfig;

    public Config(Integer size, Integer dimensions, List<String> systems, Float density, Integer runs,
            Map<String, SystemConfig> systemConfig) {
        this.size = size;
        this.dimensions = dimensions;
        this.systems = systems;
        this.density = density;
        this.systemConfig = systemConfig;
        this.runs = runs;
    }

    @Override
    public String toString() {
        return "Config{" +
                "size=" + size +
                ", dimensions=" + dimensions +
                ", systems='" + systems + '\'' +
                ", density=" + density +
                ", systemConfig=" + systemConfig +
                '}';
    }

    public Integer getSize() {
        return size;
    }

    public Integer getDimensions() {
        return dimensions;
    }

    public List<String> getSystems() {
        return systems;
    }

    public Float getDensity() {
        return density;
    }

    public SystemConfig getSystemConfig(String systemName) {
        return systemConfig.get(systemName);
    }

    public Integer getRuns() {
        return runs;
    }

    public static class SystemConfig {
        private int minAlive;
        private int maxAlive;
        private int newCell;

        // Getters
        public int getMinAlive() {
            return minAlive;
        }

        public int getMaxAlive() {
            return maxAlive;
        }

        public int getNewCell() {
            return newCell;
        }

        @Override
        public String toString() {
            return "SystemConfig{" +
                    "minAlive=" + minAlive +
                    ", maxAlive=" + maxAlive +
                    ", newCell=" + newCell +
                    '}';
        }
    }
}
