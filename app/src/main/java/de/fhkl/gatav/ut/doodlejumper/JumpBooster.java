package de.fhkl.gatav.ut.doodlejumper;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/*
Ein Buff der eingesammelt werden kann und dem Spielercharakter kurze Zeit
ermöglicht höher zu springen
 */
public class JumpBooster extends Rectangle {
    Player player;
    static Vector2D newPosition = new Vector2D(500, 500);
    private int duration; // Dauer des Power-Ups in Sekunden
    private double y;
    private double x;

    /**
     * Konstruktor des Jetpacks der ein Jetpack-Objekt erzeugt mit einer Dauer die von der aufgerufenen Klasse PowerUp
     * übergeben wird.
     *
     * @param duration
     */
    public JumpBooster(int duration) {
        super();
        this.duration = duration;
    }

    /**
     * zum setzen der Koordinaten an denen das Jetpack sein soll
     *
     * @param x
     * @param y
     */
    void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        if (isColliding(player)) {
            player.setPosition(newPosition);
        }

    }
    public boolean isColliding(Rectangle other) {
        return false;
    }

    public void used(Player player) {
        Vector2D position = player.getPosition();
        double playerX = position.x;
        double playerY = position.y;
        playerX = playerX;
        playerY = playerY + 200;
    }
}


