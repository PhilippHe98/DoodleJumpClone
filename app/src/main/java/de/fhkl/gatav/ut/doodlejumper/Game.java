package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.http.SslCertificate;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Background;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.hoveringEnemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.Platform;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.StationaryPlatform;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.PowerUp;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Projectile;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.stationaryEnemy;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/**
 * Hauptklasse der App die das Game und dessen Inhalte verwaltet
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    private int score = 0;
    public int death = 0;
    private Paint scorePaint = new Paint();

    private final Player player;
    /**
     * festlegen der Startposition des Spielers, dabei werden x und y in der Update Methode überschrieben
     */
    private final Vector2D playerStartPosition = new Vector2D(500,1500);
    Vector2D playerPosition = new Vector2D(playerStartPosition.x, playerStartPosition.y);

    public static ArrayList<Platform> platforms = new ArrayList<>();
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Projectile> projectiles = new ArrayList<>();
    public static ArrayList<PowerUp> powerUps = new ArrayList<>();
    private static ArrayList<Platform> platformsToRemove = new ArrayList<>();
    private static ArrayList<PowerUp> powerUpsToRemove = new ArrayList<>();
    private static ArrayList<Projectile> projectilesToRemove = new ArrayList<>();
    private static ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
    /**
     * Sensoren und Manager um die Neigung zu handlen
     */
    private SensorManager sensorManager;
    private Sensor accelerometer;   //Deutsch: Beschleunigungsmesser
    private float accelerationX;

    private SpawnManager spawnManager;

    private static AudioAttributes attrs = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

    private static SoundPool soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(attrs)
                    .build();

    protected static int[] soundIds = new int[10];
    private MediaPlayer backgroundMusic;

    private final GameLoop gameLoop;
//    private ArrayList<PlatformWithPowerUp> platformsPowerUp = new ArrayList<PlatformWithPowerUp>();

    private boolean isMainMenuVisible = true;
    private boolean isPaused = false;
    protected static Context context;
    private Vector2D deathLine = new Vector2D(2400);
    private boolean GameOverScreenVisible;

    private Background background;
    private Paint textPaint;


    public Game(Context context) {
        super(context);
        this.context = context;

        //Init SensorManager
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Init and start background music
        backgroundMusic = MediaPlayer.create(context, R.raw.background);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
        //Init SoundPool
        soundIds[0] = soundPool.load(context, R.raw.deathh, 1);
        soundIds[1] = soundPool.load(context, R.raw.pain6,1);
        soundIds[2] = soundPool.load(context, R.raw.gun_shot,1);
        soundIds[3] = soundPool.load(context, R.raw.jetpack, 1);
        soundIds[4] = soundPool.load(context, R.raw.powerup_pickup, 1);
        soundIds[5] = soundPool.load(context, R.raw.trampolin_sound, 1);
        soundIds[6] = soundPool.load(context, R.raw.jumppp11, 1);
        soundIds[7] = soundPool.load(context, R.raw.movingshield_sound, 1);

        // get surface Holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);
        soundPool.play(soundIds[8],1,1,1,-1,1);


        //Init Player, height 100 und width 123 haben das selbe verhältnis wie die 3009x3716 bei den Spritepixeln
        player = new Player(getContext(), playerPosition, 100,189);

        spawnManager = new SpawnManager(getContext());

        // Init one Platform
        Platform platform1 = new StationaryPlatform(getContext(), new Vector2D(200,1800), 150, 50);
        Platform platform2 = new StationaryPlatform(getContext(), new Vector2D(500,1600), 150, 50);
        Platform platform3 = new StationaryPlatform(getContext(), new Vector2D(700,2000), 150, 50);
        Platform platform4 = new StationaryPlatform(getContext(), new Vector2D(500, 1000), 150,50);
        Platform platform5 = new StationaryPlatform(getContext(), new Vector2D(500, 600), 150,50);
        Platform platform6 = new StationaryPlatform(getContext(), new Vector2D(900, 400), 150,50);
        Platform platform7 = new StationaryPlatform(getContext(), new Vector2D(300, 100), 150,50);


        platforms.add(platform1);
        platforms.add(platform2);
        platforms.add(platform3);
        platforms.add(platform4);
        platforms.add(platform5);
        platforms.add(platform6);
        platforms.add(platform7);


        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(40);
        setFocusable(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);


        background = new Background(context,new Vector2D(540, 1088), 1080, 2177);
        spawnManager.setPlayer(player);
        backgroundMusic.start();
    }
    /**
     * updatet kontinuierlich den Game State
     */
    public void update() {

        if(!isPaused && !isMainMenuVisible) {

            for(Platform platform: platforms) {
                platform.setPlayer(player);
            }
            for(PowerUp powerUp: powerUps) {
                powerUp.setPlayer(player);
            }
            for(Enemy enemy: enemies) {
                enemy.setPlayer(player);
            }

            processSensorData(accelerationX);
            player.update();
            spawnManager.update();
            updateProjectiles();
            updatePlatforms();
            updateEnemies();
            updatePowerUps();
            handleProjectileCollisionWithEnemy();
            removePlatforms();
            removePowerUps();
            removeProjectiles();
            removeEnemies();
            checkGameOver();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // Umschreiben zu Switch Case mit death als Variable um Todesfälle zu unterscheiden.
        super.draw(canvas);
        switch(death){
            // wenn Spieler ausserhalb des Bildschirms ist
            case 1: GameLoop.stopLoop(); drawGameOver(canvas);
                    backgroundMusic.pause();
                    soundPool.play(soundIds[0], 1F,1F,1, 0,1.0F);
                    break;
            // Spieler wurde von Gegner getroffen
            case 2: GameLoop.stopLoop();
                    drawGameOver(canvas);
                    backgroundMusic.pause();
                    soundPool.play(soundIds[0], 1F,1F,1, 0,1.0F);
                    break;
            default: if(canvas != null){
                // Spielinhalte auf den Canvas zeichnen
                if (isPaused) {
                    drawPauseMenu(canvas);
                } else if (isMainMenuVisible) {
                    drawMainMenu(canvas);
                } else{
                    drawGameContent(canvas);
                }
//                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    private void updatePowerUps() {
        ArrayList<PowerUp> powerUpsToRemove = new ArrayList<>();
        if(player.getPosition().y <= 1300) {
            PowerUp.setMoveDown(true);
        } else {
            PowerUp.setMoveDown(false);
        }
        for (PowerUp powerUp: powerUps) {
            powerUp.update();
            //Wenn Plattform ausheralb des Bildschirms ist-> zum zerstören Vormerken
            if(powerUp.getPosition().y > 2400) {
                powerUpsToRemove.add(powerUp);
            }
        }
        for (PowerUp powerUp: powerUpsToRemove) {
            powerUp.removeListener();
            powerUps.remove(powerUp);
        }
    }

    private void updatePlatforms() {
        if(player.getPosition().y <= 1300) {
            Platform.setMoveDown(true);
            // increase score
            score+= 1;
        } else {
            Platform.setMoveDown(false);
        }
        for (Platform platform: platforms) {
            platform.update();
            //Wenn Plattform ausheralb des Bildschirms ist-> zum zerstören Vormerken
            if(platform.getPosition().y > 2000) {
                addPlatformsToRemove(platform);
            }
        }
    }

    public static void addPlatformsToRemove(Platform platform) {
        platformsToRemove.add(platform);
    }

    private void removePlatforms() {
        for(Platform platform : platformsToRemove) {
            platform.removeListener();
            platforms.remove(platform);
        }
    }

    private void removeEnemies() {
        for (Enemy enemy: enemiesToRemove) {
            enemy.removeListener();
            enemies.remove(enemy);
        }
    }

    public static void addPowerUpToRemove(PowerUp powerUp) {
        powerUpsToRemove.add(powerUp);
    }

    private void removePowerUps() {
        for(PowerUp powerUp : powerUpsToRemove) {
            powerUp.removeListener();
            powerUps.remove(powerUp);
        }
    }

    private void updateEnemies() {
        if(player.getPosition().y <= 1300) {
            Enemy.setMoveDown(true);
        } else {
            Enemy.setMoveDown(false);
        }
        for (Enemy enemy: enemies) {
            enemy.update();
            //Wenn Plattform ausheralb des Bildschirms ist-> zum zerstören Vormerken
            if(enemy.getPosition().y > 2500) {
                enemiesToRemove.add(enemy);
            }
        }
    }

    private void updateProjectiles() {

        Iterator<Projectile> iterator = projectiles.iterator();
        while(iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update();

            if (projectile.getPosition().y < 0) {
                projectilesToRemove.add(projectile);
            }
        }
    }

    private void removeProjectiles() {
        for(Projectile projectile : projectilesToRemove) {
            projectiles.remove(projectile);
        }
    }

    private boolean checkGameOver() {
        double playerPosy = player.getPosition().y;
        if (playerPosy > deathLine.y) {
            death = 1;
            return true;
        }
        for (Enemy enemy: enemies) {
            if(Rectangle.isColliding(player,enemy)) {
                if(!player.isShielded()) {
                    death = 2;
                    return true;
                }
            }
        }
        return false;
    }

    private void drawPauseMenu(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(90);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Pause", canvas.getWidth() / 2f, canvas.getHeight() / 7f, paint);
    }

    private void drawMainMenu(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(90);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Jumping John", canvas.getWidth() / 2f, canvas.getHeight() / 7f, paint);

            Paint paint2 = new Paint();
            paint2.setColor(Color.WHITE);
            paint2.setTextSize(60);
            paint2.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("TAP TO PLAY", canvas.getWidth() / 2f, canvas.getHeight() / 2f, paint2);
        }
    private void drawGameOver(Canvas canvas) {
        GameOverScreenVisible = true;

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(90);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("GAME OVER!", canvas.getWidth() / 2f, canvas.getHeight() / 2f, paint);

        Paint paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setTextSize(65);
        paint2.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Dein Score ist: " + score, canvas.getWidth()/2f, canvas.getHeight()/1.5f, paint2);

        canvas.drawText("Tap to Play Again", canvas.getWidth()/2f, canvas.getHeight()/1.4f, paint2);
    }

    private void drawGameContent(Canvas canvas) {
        // Was oben steht wird zuerst gerendert, kann also vom unten stehenden Objekten überlagert werden
        super.draw(canvas);
        background.draw(canvas);

        for (Platform platform: platforms) {
            platform.draw(canvas);
        }

        for(Enemy enemy : enemies){
            enemy.draw(canvas);
        }

        for(Projectile projectile: projectiles) {
            projectile.draw(canvas);
        }
        for(PowerUp powerUp: powerUps) {
            powerUp.draw(canvas);
        }
        player.draw(canvas);

        canvas.drawText(""+score, 900, 50, textPaint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch event actions
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isMainMenuVisible) {
                // Starte das Spiel, wenn der Bildschirm berührt wird und das Hauptmenü sichtbar ist
                isMainMenuVisible = false;
            } else if(GameOverScreenVisible){
                GameOverScreenVisible = false;
            }
            else {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        // Schieße ein Projektil, wenn der Bildschirm berührt wird und das Spiel läuft
                        Vector2D direction = new Vector2D(event.getX(),event.getY()).subtract(playerPosition);
                        direction.normalize();
                        System.out.println(direction);
                        Projectile projectile = new Projectile(getContext(), player.getPosition(), direction, 20,20);
                        soundPool.play(soundIds[2], 1F,1F,1, 0,1.0F);
                        projectiles.add(projectile);
                        System.out.println("Feuer!");
                        break;
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * kümmert sich um den SUrface des Bildschirms und startet den Game Loop
     * @param surfaceHolder
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
    protected void handleProjectileCollisionWithEnemy() {
        for (Projectile projectile : projectiles) {
            for(Enemy enemy : enemies) {
                if(Rectangle.isColliding(projectile, enemy)) {
                    enemiesToRemove.add(enemy);
                    projectilesToRemove.add(projectile);
                    soundPool.play(soundIds[1], 1F,1F,1, 0,1.0F);
                }
            }
        }
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.white);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS,100, 100, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.white);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS,100, 200, paint);
    }

    private void processSensorData(float accelerationX) {
        player.moveSideways(accelerationX);
    }

    //Methoden für Sensor
    public void registerSensorListener() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterSensorListener() {
        sensorManager.unregisterListener(this);
    }

    /**
     * getter für die Variable AccelartionX
     * @return accelerationX
     */
    public float getAccelerationX() {
        return accelerationX;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerationX = sensorEvent.values[0];
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Keine Aktion erforderlich
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public void setIsPaused(boolean b) {
        isPaused = b;
    }

    public int getScore() {
        return score;
    }

    public Player getPlayer() {
        return player;
    }
    public static SoundPool getSoundPool(){return soundPool;}
    public static int[] getSoundIds(){ return soundIds;}
}
