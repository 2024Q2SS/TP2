package ar.edu.itba.ss;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board{
    private Integer length;
    private Set<Particle> particles =  new HashSet<>(); 
    public Board(Integer length){
        this.length = length;
    }

    public Integer getLength(){
        return length;
    }
    
    public void setLength(Integer length){
        this.length = length;
    }
    

    public void addParticle(Particle particle){
        particles.add(particle);
    }
    
}
