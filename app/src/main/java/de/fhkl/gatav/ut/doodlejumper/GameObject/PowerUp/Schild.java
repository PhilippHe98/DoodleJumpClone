package de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/*
Ein Buff der den Spieler kurze Zeit unverwundbar macht wenn er ihn einsammelt
 */
public class Schild extends Rectangle {
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
    public Schild(int duration) {
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
        if(Rectangle.isColliding(this, player)){
            player.setPosition(newPosition);
        }

    }
    public void used(Player player, Enemy enemy){
        /**
         * Deaktivieren der Kollisionserkennung bei Gegnern
         */
        boolean hitbyEnemy = false;
        if(isColliding(this, enemy)){
            hitbyEnemy = true;
        }else{
            hitbyEnemy = false;
        }
        if(hitbyEnemy){
            /**
             * Kollision ignorieren
             */
        } else{
            /**
             * Kollision anerkennen
             */
        }
    }
}
