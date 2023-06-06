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

    public Platform(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.plattform));
    }
    public double pointOnScreen(Context context){
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

    }
}


