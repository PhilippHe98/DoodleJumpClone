package de.fhkl.gatav.ut.doodlejumper.GameObject.Platform;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/*
Diese Klasse erstellt zufällig Plattformen im Spiel
Einheitliche Göße der Plattformen ist festegelegt als: Höhe: 30 Pixel und Breite 75 Pixel
 */
public abstract class Platform extends Rectangle {

    private static final double VERTICAL_SPEED = 1000.0 / GameLoop.MAX_UPS;
    private static boolean moveDown;

    public Platform(Vector2D position, double width, double height, int color) {
        super(position, width, height, color);
    }

    protected void moveDown() {
        if(moveDown) velocity.set(velocity.x, VERTICAL_SPEED);
        if(!moveDown) velocity.set(velocity.x, 0);

        position.add(velocity);
        calculateNewTopLeftAndBottomRight();
    }

    public static void setMoveDown(boolean b) {
        moveDown = b;
    }
}


