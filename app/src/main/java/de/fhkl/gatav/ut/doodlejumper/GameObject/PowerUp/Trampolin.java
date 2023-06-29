package de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Trampolin extends Rectangle {
    public Trampolin() {
        super();
    }

    @Override
    public void update(){

    }
    public void used(Player player){
        Vector2D position = player.getPosition();
        double playerX = position.x;
        double playerY = position.y;
        playerX = playerX;
        playerY = playerY + 200;
    }
}

