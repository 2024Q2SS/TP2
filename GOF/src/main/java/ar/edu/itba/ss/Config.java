package ar.edu.itba.ss;

public class Config {
    private final Integer size;
    private final Integer dimensions;
    private final String system;
    private final Float density;

    public Config(Integer size, Integer dimensions, String system, Float density) {
        this.size = size;
        this.dimensions = dimensions;
        this.system = system;
        this.density = density;
    }

    @Override
    public String toString() {
        return "Config{" +
                "size=" + size +
                ", dimensions=" + dimensions +
                ", system='" + system + '\'' +
                ", density=" + density + 
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

    public Float getDensity(){
        return density;
    }
}
