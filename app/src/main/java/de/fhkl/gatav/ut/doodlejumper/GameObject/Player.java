package de.fhkl.gatav.ut.doodlejumper.GameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
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
    private final double DEFAULT_JUMP_FORCE = 38;
    private final double TRAMPOLIN_JUMP_FORCE = 60;
    private final double JETPACK_VELOCITY = 50;
    private double jumpForce = DEFAULT_JUMP_FORCE;

    MediaPlayer jumpSound;

    private PlayerState playerState = PlayerState.DEFAULT;
    private List<EventListener> listeners = new ArrayList<>();
    /** counter ist eine Variable die dazu dient, den Spielerstatus von Trampolin auf Default zurückzusetzen.
     * Der Counter wird bei der Kollision der zweiten Plattfom nach Einsammeln des Trampolin-Powerups zurück auf Null gesetzt.
     * Da die Plattform, auf die das Trampolin steht, schon mitgezählt wird, setzt die nächste Plattform, die kein Trampolin hat, den Counter zurück.
     * (Gibt bestimmt einen besseren Ansatz, aber mir fällt nix ein =) )
    **/
    private static int counter = 0;

    private Sprite playerSprite;
    private Sprite playerShieldedSprite;
    private Sprite playerJumpingSprite;
    private Sprite jetpackSprite;
    private Sprite gunLeftSprite;
    private Sprite gunRightSprite;
    private Sprite gunRightJumpSprite;
    private Sprite gunLeftJumpSprite;

    private boolean shootLeft;
    private boolean shootRight;
    private boolean isShielded = false;
    private double shieldDuration = MAX_SHIELD_DURATION;
    private boolean isJetpack = false;
    private double jetpackDuration = MAX_JETPACK_DURATION;
    private int[] soundIds;


    public Player(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.magenta));
        soundIds = Game.getSoundIds();
        velocity = new Vector2D(0, MAX_SPEED);
        jumpSound = MediaPlayer.create(context, R.raw.jumppp11);

        //Spritesheet ist das eigentliche Bild
        SpriteSheet playerSpriteSheet = new SpriteSheet(context, R.drawable.jw_normal);
        //Sprite gibt dem Spritesheet einen Rahmen (Dimension in Pixeln) und ist für das Zeichnen verantwortlich
        playerSprite = new Sprite(playerSpriteSheet, new Rect(0,0,2026,3838));

        SpriteSheet spriteSheet = new SpriteSheet(context, R.drawable.jw_schutzschild);
        playerShieldedSprite = new Sprite(spriteSheet, new Rect(0,0,4245,4331));

        spriteSheet = new SpriteSheet(context, R.drawable.jw_jumping);
        playerJumpingSprite = new Sprite(spriteSheet, new Rect(0,0,2937,3716));

        spriteSheet = new SpriteSheet(context, R.drawable.jetpack);
        jetpackSprite = new Sprite(spriteSheet,new Rect(0,0,2291, 2363));

        spriteSheet = new SpriteSheet(context, R.drawable.waffe_links_nicht_springend);
        gunLeftSprite = new Sprite(spriteSheet, new Rect(0,0,2590, 3838));

        spriteSheet = new SpriteSheet(context, R.drawable.waffe_rechts_nicht_springend);
        gunRightSprite = new Sprite(spriteSheet, new Rect(0,0,2602,3838));

        spriteSheet = new SpriteSheet(context, R.drawable.waffe_rechts_springend);
        gunRightJumpSprite = new Sprite(spriteSheet, new Rect(0,0,2506,3716));

        spriteSheet = new SpriteSheet(context, R.drawable.waffe_links_springed);
        gunLeftJumpSprite = new Sprite(spriteSheet, new Rect(0,0,2493,3716));
    }

    public void update() {

        StartShieldCountdown();
        StartJetpackCountdown();

        // Seitenwechsel
        if (position.x < -50) position.set(1050, position.y);
        if (position.x > 1050) position.set(-50, position.y);

        if(position.y < 1300) position.set(position.x, 1300);

        if (velocity.y > 0) isJumping = false;
        if (velocity.y <= 0) isJumping = true;

        // PowerUp Logik
        for(PowerUp powerUp: Game.powerUps) {
            if(isColliding(this, powerUp)) {
                if(powerUp.getClass().toString().contains("Schild")) {
                    isShielded = true;
                    shieldDuration = MAX_SHIELD_DURATION;
                    Game.getSoundPool().play(soundIds[4], 1F,1F,1, 0,1.0F);
                    Game.getSoundPool().play(soundIds[7], 1F,1F,1, 1,1.0F);
                    Game.addPowerUpToRemove(powerUp);
                }

                if(powerUp.getClass().toString().contains("Jetpack")){
                    isJetpack = true;
                    jetpackDuration = MAX_JETPACK_DURATION;
                    Game.getSoundPool().play(soundIds[4], 1F,1F,1, 0,1.0F);
                    Game.getSoundPool().play(soundIds[3], 1F,1F,1, 1,1.0F);
                    Game.addPowerUpToRemove(powerUp);
                }

                if(this.bottomRight.y > powerUp.topLeft.y) {
                    if(!isJumping) {
                        if(powerUp.getClass().toString().contains("Trampolin")) {
                            setState(PlayerState.TRAMPOLIN);
                            counter = 0;
                            jumpForce = TRAMPOLIN_JUMP_FORCE;
                            Game.getSoundPool().play(soundIds[5], 1F, 1F, 1, 0, 1.0F);
                            Game.addPowerUpToRemove(powerUp);
                        }
                    }
                }
            }
        }

        // Kollisionen resetten den Jump Timer
        for (Platform platform : Game.platforms) {
            if (isColliding(this, platform)) {
                if(this.bottomRight.y > platform.topLeft.y) {
                    if (!isJumping) {
                        velocity.y = -jumpForce;
                        Game.getSoundPool().play(soundIds[6], 1,1,1,0,1);
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
        if(isJetpack) {
            jetpackSprite.draw(canvas, new Vector2D(position.x - 100, position.y -100), new Vector2D(position.x + 100, position.y + 100));
            if(shootLeft) {
                gunLeftJumpSprite.draw(canvas,topLeft,bottomRight);
                return;
            }
            if (shootRight) {
                gunRightJumpSprite.draw(canvas,topLeft,bottomRight);
                return;
            }
            this.width = 100;
            playerSprite.draw(canvas, topLeft, bottomRight);
            return;
        }
        if(shootLeft) {
            gunLeftJumpSprite.draw(canvas,topLeft,bottomRight);
            return;
        }
        if (shootRight) {
            gunRightJumpSprite.draw(canvas,topLeft,bottomRight);
            return;
        }
        if(isShielded) {
            //breitenanpassen damit bild gescheit aussieht
            this.width = 189;
            playerShieldedSprite.draw(canvas, topLeft, bottomRight);
            return;
        }
        if(isJumping) {
            this.width = 145;
            playerJumpingSprite.draw(canvas,topLeft,bottomRight);
            return;
        }
        this.width = 100;
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

    public void setShootLeft(boolean b) {
        shootLeft = b;
    }

    public void setShootRight(boolean b) {
        shootRight = b;
    }
}
