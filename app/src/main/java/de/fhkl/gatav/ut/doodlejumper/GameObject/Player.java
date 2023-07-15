package de.fhkl.gatav.ut.doodlejumper.GameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.GnssAntennaInfo;
import android.media.MediaPlayer;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import de.fhkl.gatav.ut.doodlejumper.EventListener;
import de.fhkl.gatav.ut.doodlejumper.Game;
import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.Platform;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.PowerUp;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;


enum PlayerState {
    TRAMPOLIN, SPRUNGFEDER, SCHILD, JUMPBOOSTER, JETPACK, DEFAULT;
}
/**
 * Die Klasse Player ist ein GameObject, das vom Spieler gesteuert.
 */
public class Player extends Rectangle {
    private static final double SPEED_PIXELS_PER_SECOND = 1200.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SIDE_MOVE_SPEED = 300.0 / GameLoop.MAX_UPS;
    private static final double MAX_SHIELD_DURATION = 700;
    private final double MAX_JETPACK_DURATION = 500;
    private boolean isJumping = false;
    private double gravityValue = 0.8;
    private final double DEFAULT_JUMP_FORCE = 35;
    private final double TRAMPOLIN_JUMP_FORCE = 50;
    private final double JETPACK_VELOCITY = 50;
    private double jumpForce = DEFAULT_JUMP_FORCE;

    MediaPlayer mediaPlayer;

    private PlayerState playerState = PlayerState.DEFAULT;
    private List<EventListener> listeners = new ArrayList<>();
    /** counter ist eine Variable die dazu dient, den Spielerstatus von Trampolin auf Default zurückzusetzen.
     * Der Counter wird bei der Kollision der zweiten Plattfom nach Einsammeln des Trampolin-Powerups zurück auf Null gesetzt.
     * Da die Plattform, auf die das Trampolin steht, schon mitgezählt wird, setzt die nächste Plattform, die kein Trampolin hat, den Counter zurück.
     * (Gibt bestimmt einen besseren Ansatz, aber mir fällt nix ein =) )
    **/
    private static int counter = 0;

    private Sprite playerSprite;
    private boolean isShielded = false;
    private double shieldDuration = MAX_SHIELD_DURATION;
    private final Paint shieldPaint;
    private boolean isJetpack = false;
    private double jetpackDuration = MAX_JETPACK_DURATION;


    public Player(Context context, Vector2D position, double width, double height, Sprite sprite) {
        super(position, width, height, ContextCompat.getColor(context, R.color.magenta));
        velocity = new Vector2D(0, MAX_SPEED);
        mediaPlayer = MediaPlayer.create(context, R.raw.jump_sound_cut);
        this.playerSprite = sprite;

        shieldPaint = new Paint();
        shieldPaint.setColor(ContextCompat.getColor(context, R.color.Schild));
        shieldPaint.setStyle(Paint.Style.STROKE);
    }

    public void update() {

        StartShieldCountdown();
        StartJetpackCountdown();

        // Seitenwechsel
        if (position.x < -50) position.set(1050, position.y);
        if (position.x > 1050) position.set(-50, position.y);

        if(position.y < 1300) position.set(position.x, 1300);

        if (velocity.y > 1) isJumping = false;
        if (velocity.y <= 1) isJumping = true;

        // PowerUp Logik
        for(PowerUp powerUp: Game.powerUps) {
            if(isColliding(this, powerUp)) {
                if(powerUp.getClass().toString().contains("Schild")) {
                    isShielded = true;
                    shieldDuration = MAX_SHIELD_DURATION;
                    Game.addPowerUpToRemove(powerUp);
                }

                if(powerUp.getClass().toString().contains("Jetpack")){
                    isJetpack = true;
                    jetpackDuration = MAX_JETPACK_DURATION;
                    Game.addPowerUpToRemove(powerUp);
                }

                if(this.bottomRight.y > powerUp.topLeft.y) {
                    if(!isJumping) {
                        if(powerUp.getClass().toString().contains("Trampolin")) {
                            setState(PlayerState.TRAMPOLIN);
                            counter = 0;
                            jumpForce = TRAMPOLIN_JUMP_FORCE;
                        }
                    }
                }
            }
        }

        // Kollisionen resetten den Jump Timer
        for (Platform platform : Game.platforms) {
            if (isColliding(this, platform)) {
                if(this.bottomRight.y -5 > platform.topLeft.y) {
                    if (!isJumping) {
                        System.out.println("this: " + this.bottomRight.y + " other: "+ platform.topLeft.y);
                        velocity.y = -jumpForce;
                        mediaPlayer.start();
                        // Zerstöre Plattform wenn richtiger Plattformtyp
                        if(platform.getClass().toString().contains("BreakablePlatform")) {
                            Game.addPlatformsToRemove(platform);
                        }
                        if((playerState +"").equals("TRAMPOLIN") && counter <= 1) {
                            counter++;
                        } if(counter > 1) {
                            setState(PlayerState.DEFAULT);
                            System.out.println(getState() +"");
                            counter = 0;
                        }
                    }
                }
            }
        }

        velocity.y += gravityValue;
        //Begrenzung der Geschwindigkeit
        if (velocity.y > 20) velocity.y = 20;

        // Wenn Jetpack dann velocity immer JETPACK VELOCITY, egal was vorher berechnet wurde
        if(isJetpack) {
            setState(PlayerState.JETPACK);
            velocity.y = -JETPACK_VELOCITY;
        }

        // der aktuellen Position des Spielers die festgelegte Velocity dazuaddieren
        position.add(velocity);
        // berechnen der neuen Maße des Rechtecks
        calculateNewTopLeftAndBottomRight();
        //Jumpforce wieder auf normalWert setzen
        jumpForce = DEFAULT_JUMP_FORCE;
    }

    @Override
    public void draw(Canvas canvas) {
        if(isShielded) {
            canvas.drawRect((float) topLeft.x, (float) topLeft.y, (float) bottomRight.x, (float) bottomRight.y, shieldPaint);
        }
        playerSprite.draw(canvas, topLeft, bottomRight);
    }

    public void moveSideways(float accelerationX) {
        velocity.set(-accelerationX * SIDE_MOVE_SPEED, velocity.y);
    }

    private void StartJetpackCountdown() {
        if(!isJetpack) return;
        if(jetpackDuration <= 0) {
            isJetpack = false;
            setState(PlayerState.DEFAULT);
        } else {
            jetpackDuration--;
        }
    }

    private void StartShieldCountdown(){
        if(!isShielded) return;
        if(shieldDuration <= 0){
            isShielded = false;
        }else {
            shieldDuration--;
        }
    }

    // Code für Beobachtermuster, alle Beobachter oder hier Listener werden benachrichtigt, wenn der Player ein PowerUp aufsammelt. Hier in setState()
    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(EventListener listener) {
        listeners.remove(listener);
    }
    public void notifyListeners() {
        for(EventListener listener:listeners) {
            listener.reactToEvent();
        }
    }
    public String getState() {
        return playerState + "";
    }
    private void setState(PlayerState state) {
        this.playerState = state;
        notifyListeners();
    }

    public boolean isShielded() {
        return isShielded;
    }
}
