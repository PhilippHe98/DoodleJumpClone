package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.http.SslCertificate;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.Platform;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.StationaryPlatform;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.PowerUp;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Projectile;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.stationaryEnemy;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/**
 * Hauptklasse der App die das Game und dessen Inhalte verwaltet
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    private int score = 0;
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
    /**
     * Sensoren und Manager um die Neigung zu handlen
     */
    private SensorManager sensorManager;
    private Sensor accelerometer;   //Deutsch: Beschleunigungsmesser
    private float accelerationX;

    private SpawnManager spawnManager;

    private final GameLoop gameLoop;
//    private ArrayList<PlatformWithPowerUp> platformsPowerUp = new ArrayList<PlatformWithPowerUp>();

    private boolean isMainMenuVisible = true;
    private boolean isPaused = false;
    protected static Context context;
    private Vector2D deathLine = new Vector2D(2000);


    public Game(Context context) {
        super(context);
        this.context = context;

        //Init SensorManager
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // get surface golder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //Init Player
        player = new Player(getContext(), playerPosition, 100,100);

        spawnManager = new SpawnManager(getContext());

        // Init one Platform
        Platform platform1 = new StationaryPlatform(getContext(), new Vector2D(200,1800), 200, 50);
        Platform platform2 = new StationaryPlatform(getContext(), new Vector2D(500,1600), 200, 50);
        Platform platform3 = new StationaryPlatform(getContext(), new Vector2D(700,2000), 200, 50);
        Platform platform4 = new StationaryPlatform(getContext(), new Vector2D(500, 1000), 200,50);
        Platform platform5 = new StationaryPlatform(getContext(), new Vector2D(500, 600), 200,50);
        Platform platform6 = new StationaryPlatform(getContext(), new Vector2D(900, 400), 200,50);
        Platform platform7 = new StationaryPlatform(getContext(), new Vector2D(300, 100), 200,50);


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

        spawnManager.setPlayer(player);
    }
    /**
     * updatet kontinuierlich den Game State
     */
    public void update() {

        if(!isPaused && !isMainMenuVisible) {
            // Nötiger Code für Beobachtermuster
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
            checkGameOver();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(canvas != null){
            // Spielinhalte auf den Canvas zeichnen
            if (isPaused) {
                drawPauseMenu(canvas);
            } else if (isMainMenuVisible) {
                drawMainMenu(canvas);
            } else {
                drawGameContent(canvas);
            }
            getHolder().unlockCanvasAndPost(canvas);
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
            if(powerUp.getPosition().y > 2500) {
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
            if(platform.getPosition().y > 2500) {
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

    private void updateEnemies() {
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
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
        for(Enemy enemy : enemiesToRemove) {
            enemy.removeListener();
            enemies.remove(enemy);
        }
    }

    private void updateProjectiles() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while(iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update();

            if (projectile.getPosition().y < 0) {
                iterator.remove();
            }
        }
    }

    private void checkGameOver() {
        Vector2D playerPos = player.getPosition();
        double playerPosy = playerPos.y;
        if (playerPosy > deathLine.y) {
            System.out.println("Game Over");
        }
        for (Enemy enemy: enemies) {
            if(Rectangle.isColliding(player,enemy)) {
                System.out.println("Game over");
            }
        }
    }

    private void drawPauseMenu(Canvas canvas) {
        // Hier kannst du das Pausemenü zeichnen
        // Zum Beispiel: Text anzeigen
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(90);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Pause", canvas.getWidth() / 2f, canvas.getHeight() / 7f, paint);
    }

    private void drawMainMenu(Canvas canvas) {
            // Hier kannst du das Hauptmenü zeichnen
            // Zum Beispiel: Text anzeigen
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

    private void drawGameContent(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
        player.draw(canvas);
        for(Enemy enemy : enemies){
            enemy.draw(canvas);
        }
        for (Platform platform: platforms) {
            platform.draw(canvas);
        }
        for(Projectile projectile: projectiles) {
            projectile.draw(canvas);
        }
        for(PowerUp powerUp: powerUps) {
            powerUp.draw(canvas);
        }
        // Score
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.drawText(""+score, 900, 50, paint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch event actions
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isMainMenuVisible) {
                // Starte das Spiel, wenn der Bildschirm berührt wird und das Hauptmenü sichtbar ist
                isMainMenuVisible = false;
            } else {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        // Schieße ein Projektil, wenn der Bildschirm berührt wird und das Spiel läuft
                        Vector2D direction = new Vector2D(event.getX(),event.getY()).subtract(playerPosition);
                        direction.normalize();
                        System.out.println(direction);
                        Projectile projectile = new Projectile(getContext(), player.getPosition(), direction, 20,20);
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
        List<Enemy> enemiesToRemove = new ArrayList<>();
        List<Projectile> projectilesToRemove = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            for(Enemy enemy : enemies) {
                if(Rectangle.isColliding(projectile, enemy)) {
                    enemiesToRemove.add(enemy);
                    projectilesToRemove.add(projectile);
                }
            }
        }
        for (Enemy enemy : enemiesToRemove) {
            enemies.remove(enemy);
        }
        for(Projectile projectile : projectilesToRemove) {
            projectiles.remove(projectile);
        }
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS,100, 100, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
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
}
