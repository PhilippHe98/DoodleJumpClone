package de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import de.fhkl.gatav.ut.doodlejumper.R;

/*
Diese Klasse erstellt zufällig Plattformen im Spiel
Einheitliche Göße der Plattformen ist festegelegt als: Höhe: 30 Pixel und Breite 75 Pixel
 */
public class Plattform extends Rectangle {

    private double top;
    private double left;
    private double right;
    private double bottom;
    Paint paint = new Paint();
    List<Plattform> platforms = new ArrayList<Plattform>();

    public double getTop() {
        return top;
    }

    public Plattform(Context context, double left, double top, double right, double bottom) {
        super();
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.plattform);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
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

    public void draw(Canvas canvas) {

        /*
        Zeichnen eines Rechtecks das gelb gefüllt ist auf den Bildschirm
         */
    canvas.drawRect((float) left,
                    (float) top,
                    (float) right,
                    (float) bottom,
                    paint);
    }
    public void update(){

    }
}


