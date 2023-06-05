package de.fhkl.gatav.ut.doodlejumper.PowerUps;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle.Plattform;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle.Rectangle;

/*
* Die PowerUP Klasse stellt die PowerUps und deren Fähigkeiten zur Verfügung, außerdem kümmert
* sie sich um die Platzierung der Objekte (nur auf Plattformen möglich)
* */
public class PowerUp extends Rectangle {
    private double left;
    private double right;
    private double bottom;
    private double top;

    Paint paint;
    /* Duration gibt in Sekunden an, wie lange ein PowerUp hält je nachdem welcher Typ es hat
    dauert es unterschiedlich lange
     Springstiefel - 10sek
     Jetpack - 7sek
     Trampolin (Einmalnutzung)
     Schild - 15sek
     JumpBoost - 5sek
     */

    private int duration;

    /* Der Typ ist eine Zahl die von 0 bis 6 geht und folgendes bedeutet:
     0 - Springstiefel
     1 - Jetpack
     2 - Trampolin
     3 - Schild
     4 - JumpBoost
     5 -
     6 -
     */

    private int type;
    PowerUp powerUp;


    public PowerUp(Context context, double posX, double posY, double left, double right, double bottom, double top) {
        super(posX, posY);
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.powerUps);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

    }

    public PowerUp() {

    }
     /*draw enthält die Methoden und Möglichkeiten zum bestimmen der Position und zum "zeichnen" der Power
     Ups auf das Canvas */

    public void draw(Canvas canvas) {
    canvas.drawRect(
            (float) left,
            (float) top,
            (float) right,
            (float) bottom,
            paint
    );

    }
    /*
    Prüfen ob unter dem PowerUp eine Plattform existiert auf die man es drawen kann
     */
    public void isPlattform(Plattform plattform, PowerUp powerUp){
        if(plattform.getTop() == powerUp.bottom){
            Canvas canvas = null;
            powerUp.draw(canvas);
        } else {

        }
    }

    // update ist verantwortlich für das aktualisieren des Powerups während das Spiel läuft
    public void update() {

    }
    public void placePowerUp(Canvas canvas){
        //bestimmen einer Zufallszahl um den Typ zu bestimmen
        double type = Math.random()*10;
        // Konvertieren der Zufallszahl in ein Int um es mit einem Switch Case überprüfen zu können
        int typo = (int) type;

        switch(typo){
            case 0:
                // Erzeugen eines Springschuh-Objekts und platzieren des Objektes im Spiel
                Springschuh springschuh = new Springschuh();

                break;
            case 1:
                // Erzeugen eines Jetpack-Objekts und platzieren des Objektes im Spiel
                Jetpack jetpack = new Jetpack();
                break;
            case 2:
                // Erzeugen eines Trampolin-Onjektes und platzieren des Objektes im Spiel
                Trampolin trampolin = new Trampolin();
            break;

            case 3:
                // Erzeugen eines Schild-Objektes und platzieren des Objektes im Spiel
                Schild schild = new Schild();
                break;

            case 4:
                // Erzeugen eines JumpBoost-Objektes und platzieren des Objektes im Spiel
                JumpBooster jumpbooster = new JumpBooster();
                break;
            case 5:
                // Erzeuegen eines ...-Objektes und platzieren des Objektes im Spiel
            case 6:
                // Erzeugen eines ...-Objektes und platzieren des Objektes im Spiel
            default: return;
        }
    }


}
