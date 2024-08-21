package ar.edu.itba.ss;

public class Config {
    private final Integer size;
    private final Integer dimensions;
    private final String system;

    public Config(Integer size, Integer dimensions, String system) {
        this.size = size;
        this.dimensions = dimensions;
        this.system = system;
    }

    @Override
    public String toString() {
        return "Config{" +
                "size=" + size +
                ", dimensions=" + dimensions +
                ", system='" + system + '\'' +
                '}';
    }

    public Integer getSize() {
        return size;
    }

    public Integer getDimensions() {
        return dimensions;
    }

    public String getSystem() {
        return system;
    }
}
