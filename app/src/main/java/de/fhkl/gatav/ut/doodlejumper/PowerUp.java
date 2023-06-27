package de.fhkl.gatav.ut.doodlejumper;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.core.content.ContextCompat;


/*
* Die PowerUP Klasse stellt die PowerUps und deren Fähigkeiten zur Verfügung, außerdem kümmert
* sie sich um die Platzierung der Objekte (nur auf Plattformen möglich)
* */
public class PowerUp extends Rectangle {
    Paint paint;
    /* Duration gibt in Sekunden an, wie lange ein PowerUp hält je nachdem welcher Typ es hat
    dauert es unterschiedlich lange
     Springstiefel - 10sek
     Jetpack - 7sek
     Trampolin (Einmalnutzung)
     Schild - 15sek
     JumpBoost - 5sek
     */

    private int durationJetpack = 7;
    private int durationSprungstiefel = 10;
    private int durationTrampolin; // konstant -> einmal nutzung andere Umsetzung nötig
    private int durationSchild = 15;
    private int durationJumpBoost = 5;
    double left;
    double right;
    double top;
    double bottom;


    /* Der Typ ist eine Zahl die von 0 bis 6 geht und folgendes bedeutet:
     0 - Springstiefel
     1 - Jetpack
     2 - Trampolin
     3 - Schild
     4 - JumpBoost
     5 - Bleistift der geworfen wird
     6 -
     */

    private int type;
    PowerUp powerUp;


    public PowerUp(Context context, double posX, double posY, double left, double right, double bottom, double top) {
        super();
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
        super();

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

    @Override
    public boolean isColliding(Rectangle other){
        /*
        Die Kanten der beiden Rechtecke speichern um sie mit einer If Anfrage zu überprüfen
        wie bei Player und Plattform
         */
        RectF RectJet = this.getBounds();
        RectF RectPlay = other.getBounds();
        boolean isPlayerHittingPowerUp = (this.bottomRight.y < other.topLeft.y);
        if(!isPlayerHittingPowerUp){
            return RectJet.intersect(RectPlay);
        }
        return false;
    }

    /*
    Prüfen ob unter dem PowerUp eine Plattform existiert auf die man es drawen kann
     */
//    public void isPlattform(Platform plattform, PowerUp powerUp){
//        if(plattform.getTop() == powerUp.bottom){
//            Canvas canvas = null;
//            powerUp.draw(canvas);
//        } else {
//
//        }
//    }

    // update ist verantwortlich für das aktualisieren des Powerups während das Spiel läuft
    public void update() {

    }
    public void placePowerUp(Canvas canvas, Player player){
        //bestimmen einer Zufallszahl um den Typ zu bestimmen
        double type = Math.random()*10;
        // Konvertieren der Zufallszahl in ein Int um es mit einem Switch Case überprüfen zu können
        int typo = (int) type;

        switch(typo){
            case 0:
                // Erzeugen eines Springschuh-Objekts und platzieren des Objektes im Spiel
                Springschuh springschuh = new Springschuh(7);

                break;
            case 1:
                // Erzeugen eines Jetpack-Objekts und platzieren des Objektes im Spiel
                Jetpack jetpack = new Jetpack(durationJetpack);
                break;
            case 2:
                // Erzeugen eines Trampolin-Onjektes und platzieren des Objektes im Spiel
                //Trampolin trampolin = new Trampolin();
            break;

            case 3:
                // Erzeugen eines Schild-Objektes und platzieren des Objektes im Spiel
                Schild schild = new Schild(15);
                break;

            case 4:
                // Erzeugen eines JumpBoost-Objektes und platzieren des Objektes im Spiel
                JumpBooster jumpbooster = new JumpBooster(10);
                break;
            case 5:
                // Erzeuegen eines ...-Objektes und platzieren des Objektes im Spiel
            case 6:
                // Erzeugen eines ...-Objektes und platzieren des Objektes im Spiel
            default: return;
        }
    }
  //  public static boolean isCollected(Player player){
//        if(player.getPosition() == )
//    }


}
