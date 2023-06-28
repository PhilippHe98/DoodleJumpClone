package de.fhkl.gatav.ut.doodlejumper;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Trampolin extends Rectangle{
    private double x;
    private double y;

    public Trampolin() {
        super();
    }

    @Override
    public void update(){

    }
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void used(Player player){
        Vector2D position = player.getPosition();
        double playerX = position.x;
        double playerY = position.y;
        playerX = playerX;
        playerY = playerY + 200;
    }
}

