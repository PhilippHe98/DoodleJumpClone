package de.fhkl.gatav.ut.doodlejumper.GameObject.Platform;

import android.content.Context;

import java.util.List;


import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.Jetpack;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.JumpBooster;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.Schild;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.Springschuh;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.Trampolin;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;

import de.fhkl.gatav.ut.doodlejumper.Random.RandomGenerator;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/**
 * Diese Klasse stellt eine Plattform dar die ein zufällig ausgewähltes PowerUp hat dar, sie wird zur
 * Laufzeit dynamisch gespawnt.
 */

public class PlatformWithPowerUp extends Rectangle {

//    private final List currentPlatform;
    private double x;
    private double y;
    double platformX;
    double platformY;
//    ArrayList platforms = new ArrayList<PlatformWithPowerUp>();

    public PlatformWithPowerUp(Context context, Vector2D position, double width, double height) {
        super();
//        currentPlatform = selectPowerUp(platforms);

    }

    public List<PlatformWithPowerUp> selectPowerUp(List<PlatformWithPowerUp> platformwPU) {
        /**
         * Erzeugen einer Zufallszahl mithilfe der Hilfsklasse RandomGenerator um den Typ des PowerUps
         * zu bestimmen und die entsprechende PLattform mit passendem Item zu spawnen
         */
        int type = RandomGenerator.generateRandomInt(7);
        /**
         * Durchlaufen eines Switch Cases um passenden Fall für die generierte Zahl zu finden
         */
        switch(type){
            case 0:
                /**
                 * Plattform mit einem Jetpack der Liste mit Plattformen hinzufügen.
                 */
                PlatformWithPowerUp platformWithJetpack = null;
                platformWithJetpack.set(500, 400);
                /**
                 * Position der Plattform holen um sie dem Jetpack zu übergeben
                 */
                platformX = platformWithJetpack.getX();
                platformY = platformWithJetpack.getY();
                /**
                 * Jetpack auf die Mitte der PLattform setzen und der Liste der Plattformen mit PowerUps hinzufügen
                 */
                Jetpack jetpack = new Jetpack(15);
                jetpack.setPosition(new Vector2D(platformX/2, platformY));

                platformwPU.add(platformWithJetpack);
                return platformwPU;
            case 1:
                /**
                 * Plattform mit einem Springschuh der Liste mit Plattformen hinzufügen.
                 */
                PlatformWithPowerUp platformWithSpringschuh = null;
                platformWithSpringschuh.set(500, 400);
                /**
                 * Position der Plattform holen um sie dem Jetpack zu übergeben
                 */
                platformX = platformWithSpringschuh.getX();
                platformY = platformWithSpringschuh.getY();
                /**
                 * Jetpack auf die Mitte der PLattform setzen und der Liste der Plattformen mit PowerUps hinzufügen
                 */
                Springschuh sprung = new Springschuh(7);
                sprung.setPosition(new Vector2D(platformX/2, platformY));

                platformwPU.add(platformWithSpringschuh);
                return platformwPU;

            case 2:
                /**
                 * Plattform mit einem Jetpack der Liste mit Plattformen hinzufügen.
                 */
                PlatformWithPowerUp platformWithJumpBooster = null;
                platformWithJumpBooster.set(500, 400);
                /**
                 * Position der Plattform holen um sie dem Jetpack zu übergeben
                 */
                platformX = platformWithJumpBooster.getX();
                platformY = platformWithJumpBooster.getY();
                /**
                 * Jetpack auf die Mitte der PLattform setzen und der Liste der Plattformen mit PowerUps hinzufügen
                 */
                JumpBooster jump = new JumpBooster(10);
                jump.setPosition(new Vector2D(platformX/2, platformY));

                platformwPU.add(platformWithJumpBooster);
                return platformwPU;

            case 3:
                /**
                 * Plattform mit einem Jetpack der Liste mit Plattformen hinzufügen.
                 */
                PlatformWithPowerUp platformWithSchild = null;
                platformWithSchild.set(500, 400);
                /**
                 * Position der Plattform holen um sie dem Jetpack zu übergeben
                 */
                platformX = platformWithSchild.getX();
                platformY = platformWithSchild.getY();
                /**
                 * Jetpack auf die Mitte der PLattform setzen und der Liste der Plattformen mit PowerUps hinzufügen
                 */
                Schild schild = new Schild(15);
                schild.setPosition(new Vector2D(platformX/2, platformY));

                platformwPU.add(platformWithSchild);
                return platformwPU;


            case 4:
                /**
                 * Plattform mit einem Jetpack der Liste mit Plattformen hinzufügen.
                 */
                PlatformWithPowerUp platformWithTrampolin = null;
                platformWithTrampolin.set(500, 400);
                /**
                 * Position der Plattform holen um sie dem Jetpack zu übergeben
                 */
                platformX = platformWithTrampolin.getX();
                platformY = platformWithTrampolin.getY();
                /**
                 * Jetpack auf die Mitte der PLattform setzen und der Liste der Plattformen mit PowerUps hinzufügen
                 */
                Trampolin tramp = new Trampolin();
                tramp.setPosition(new Vector2D(platformX/2, platformY));

                platformwPU.add(platformWithTrampolin);
                return platformwPU;

            case 5:
                // Erzeuegen eines ...-Objektes und platzieren des Objektes im Spiel
                return platformwPU;

            case 6:
                // Erzeugen eines ...-Objektes und platzieren des Objektes im Spiel
                return platformwPU;

            default: return platformwPU;
        }
    }

    private double getY() {
        return y;
    }

    private double getX() {
        return x;
    }

    private void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {

    }
}

