package de.fhkl.gatav.ut.doodlejumper.GameObject.Platform;

import de.fhkl.gatav.ut.doodlejumper.EventListener;
import de.fhkl.gatav.ut.doodlejumper.Game;
import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public abstract class Platform extends Rectangle implements EventListener {

    protected Player player = null;
    protected static final double DEFAULT_VERTICAL_SPEED = 1000.0 / GameLoop.MAX_UPS;
    protected static final double TRAMPOLIN_VERTICAL_SPEED = 1700 / GameLoop.MAX_UPS;
    protected static double vertical_speed = DEFAULT_VERTICAL_SPEED;
    private static boolean moveDown;

    public Platform(Vector2D position, double width, double height, int color) {
        super(position, width, height, color);
    }

    protected void moveDown() {
        if(moveDown) velocity.set(velocity.x, vertical_speed);
        if(!moveDown) velocity.set(velocity.x, 0);

        position.add(velocity);
        calculateNewTopLeftAndBottomRight();
    }

    public static void setMoveDown(boolean b) {
        moveDown = b;
    }

    public void setPlayer(Player player) {
        if(this.player != null) {
            this.player.removeListener(this);
        }

        this.player = player;

        if(this.player != null) {
            this.player.registerListener(this);
        }
    }
    public void removeListener() {
        if(this.player != null) {
            this.player.removeListener(this);
        }
    }
    @Override
    public void reactToEvent() {
        if(player != null) {
            switch (player.getState()) {
                case "TRAMPOLIN":
                    vertical_speed = TRAMPOLIN_VERTICAL_SPEED;
                    break;
                case "DEFAULT":
                    vertical_speed = DEFAULT_VERTICAL_SPEED;
                    break;
            }
        }
    }
}


