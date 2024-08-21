package ar.edu.itba.ss;

public class Coordinates2D extends Coordinates {

    private Integer x;
    private Integer y;

    public Coordinates2D(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public Integer getZ() {
        return 0;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Double euclideanDistance(Coordinates2D other) {
        return Math.sqrt(Math.pow(this.getX() - other.getX(), 2) + Math.pow(this.getY() - other.getY(), 2));
    }
}
