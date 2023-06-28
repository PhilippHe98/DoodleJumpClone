package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/*
Diese Klasse erstellt zufällig Plattformen im Spiel
Einheitliche Göße der Plattformen ist festegelegt als: Höhe: 30 Pixel und Breite 75 Pixel
 */
public class Platform extends Rectangle {

    private static final double VERTICA_SPEED = 1000.0 / GameLoop.MAX_UPS;
    private static boolean moveDown;

    private static final double UPDATES_PER_SPAWN = 40;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;

    public Platform(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.plattform));
        velocity = new Vector2D();
    }
    public double pointOnScreen(Context context) {
        /*
        Methode sucht zufälligen Punkt auf dem Bildschirm
        point -> immer int left eines Rechtecks
         */
        double point;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        /*
        Screenbreite in Pixeln
         */
        int screenwidth = displayMetrics.widthPixels;
        /*
        Screenhöhe in Pixeln
         */
        int screenheight = displayMetrics.heightPixels;
        /*
        Einen zufälligen Punkt auf dem Bildschirm bestimmen, mit diesem dann die restlichen 3 berechnen
        und daraus eine Plattform machen
         */
        point = Math.random()*screenwidth;
        return point;
    }
    public void calculatePoints(double pointL){
        double pointR = pointL + 75;
        double pointT = pointL;
        double pointB = pointR - 30;
        /*
        Anhand dieser Punkte Rechteck zeichnen
         */
    }

    public void update(){
        if(moveDown) velocity.set(velocity.x, VERTICA_SPEED);
        if(!moveDown) velocity.set(velocity.x, 0);
       position.add(velocity);
       calculateNewTopLeftAndBottomRight();
    }

    public static void setMoveDown(boolean b) {
        moveDown = b;
    }

    public static boolean readyToSpawn(){
        if(updatesUntilNextSpawn <= 0){
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        }else {
            updatesUntilNextSpawn--;
            return false;
        }
    }

}


