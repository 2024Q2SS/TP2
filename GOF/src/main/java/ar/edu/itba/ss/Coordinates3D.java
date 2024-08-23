package ar.edu.itba.ss;

import java.util.Objects;

public class Coordinates3D extends Coordinates {
    private Integer x;
    private Integer y;
    private Integer z;

    public Coordinates3D(Integer x, Integer y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public Integer getZ() {
        return this.z;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public Double euclideanDistance(Coordinates3D other) {
        return Math.sqrt(Math.pow(this.getX() - other.getX(), 2) + Math.pow(this.getY() - other.getY(), 2)
                + Math.pow(this.getZ() - other.getZ(), 2));
    }

    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ", " + this.getZ() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Coordinates3D that = (Coordinates3D) o;
        return x.equals(that.x) && y.equals(that.y) && z.equals(that.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

}
