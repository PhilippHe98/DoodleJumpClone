package de.fhkl.gatav.ut.doodlejumper;

import android.graphics.RectF;

import de.fhkl.gatav.ut.doodlejumper.Player;
import de.fhkl.gatav.ut.doodlejumper.PowerUp;
import de.fhkl.gatav.ut.doodlejumper.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/**
Power Up mit dem der Spielercharakter kurze Zeit senkrecht an Plattformen
 vorbeifliegen kann jedoch weiterhin verwundbar ist
 **/
public class Jetpack extends Rectangle{

    Player player;
    static Vector2D newPosition = new Vector2D(500, 500);
    private int duration; // Dauer des Power-Ups in Sekunden
    private double y;
    private double x;

    /**
     * Konstruktor des Jetpacks der ein Jetpack-Objekt erzeugt mit einer Dauer die von der aufgerufenen Klasse PowerUp
     * übergeben wird.
     * @param duration
     */
    public Jetpack(int duration) {
        super();
        this.duration = duration;
    }

    /**
     * zum setzen der Koordinaten an denen das Jetpack sein soll
     * @param x
     * @param y
     */
    void set(double x, double y){
        this.x = x;
        this.y = y;
    }
    @Override
    public void update() {
        if(isColliding(player)){
            player.setPosition(newPosition);
        }

    }
    public void used(Player player){
        Vector2D position = player.getPosition();
        double playerX = position.x;
        double playerY = position.y;
        playerX = playerX;
        playerY = playerY + 200;
    }

    public boolean isColliding(Rectangle other) {
        return false;
    }
}
