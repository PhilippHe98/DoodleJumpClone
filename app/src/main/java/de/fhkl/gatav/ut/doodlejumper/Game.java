package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.fhkl.gatav.ut.doodlejumper.Random.RandomGenerator;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/**
 * Hauptklasse der App die das Game und dessen Inhalte verwaltet
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    /**
     * legt die max. Anzahl an Gegner die auf dem Bildschirm zu sehen sind fest
     */
    private static final int MAX_ENEMIES = 5;
    /**
     * erstellen eines Player Objects
     */
    private final Player player;
    /**
     * festlegen der Startposition des Spielers, dabei werden x und y in der Update Methode überschrieben
     */
    private final Vector2D playerStartPosition = new Vector2D(500,1000);
    Vector2D playerPosition = new Vector2D(playerStartPosition.x, playerStartPosition.y);
    /**
     * Listen die Plattformen und Gegner während des Spiels halten
     */
    public static ArrayList<Platform> platforms = new ArrayList<>();
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Projectile> projectiles = new ArrayList<>();
    /**
     * Sensoren und Manager um die Neigung zu handlen
     */
    private SensorManager sensorManager;
    private Sensor accelerometer;   //Deutsch: Beschleunigungsmesser
    private float accelerationX;
    /**
     * Erstellen eines GameLoop Objects
     */
    private final GameLoop gameLoop;
    private ArrayList<PlatformWithPowerUp> platformsPowerUp = new ArrayList<PlatformWithPowerUp>();

    /**
     * Konstruktor des GameObjects das alle Inhalte des Spiels initialisiert
     * @param context
     */
    public Game(Context context) {
        super(context);

        //Init SensorManager
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // get surface golder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //Init Player
        player = new Player(getContext(), playerPosition, 100,100);



        // Init one Platform
        Platform platform1 = new Platform(getContext(), new Vector2D(200,1800), 200, 50);
        Platform platform2 = new Platform(getContext(), new Vector2D(500,1600), 200, 50);
        Platform platform3 = new Platform(getContext(), new Vector2D(700,2000), 200, 50);
        platforms.add(platform1);
        platforms.add(platform2);
        platforms.add(platform3);

        Enemy enemie1 = new stationaryEnemy(getContext(), new Vector2D(300,300), 100,100);
        Enemy enemie2 = new stationaryEnemy(getContext(), new Vector2D(700,300), 100,100);
        enemies.add(enemie2);
        enemies.add(enemie1);
        setFocusable(true);
    }

    /**
     * updatet kontinuierlich den Game State
     */
    public void update() {

        processSensorData(accelerationX);
        player.update();
        spawnEnemies();
        updateProjectiles();

        //Update state of each enemy
        for(Enemy enemy : enemies){
            enemy.update();
        }
        handleProjectileCollisionWithEnemy();
        checkGameOver();
    }

    private void checkGameOver() {
        for (Enemy enemy: enemies) {
            if(Rectangle.isColliding(player,enemy)) {
                System.out.println("Game over");
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

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
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch event actions
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Vector2D direction = new Vector2D(event.getX(),event.getY()).subtract(playerPosition);
                direction.normalize();
                System.out.println(direction);
                Projectile projectile = new Projectile(getContext(), player.getPosition(), direction, 20,20);
                projectiles.add(projectile);
                System.out.println("Feuer!");
                return true;

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
    // Crasht evtl lol
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
//        PlatformWithPowerUp platform = null;
//        platform.selectPowerUp(platformsPowerUp);
//        for (int i = 0; i < platformsPowerUp.size(); i++) {
//            PlatformWithPowerUp p = platformsPowerUp.get(i);
//            p.draw(canvas);
//        }
    }

    /**
     * brechnet die Updates Per Second und zeichnet diese auf den Bildschirm
     * @param canvas
     */
    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS,100, 100, paint);
    }

    /**
     * brechnet die Frames Per Second und zeichnet diese auf den Bildschirm
     * @param canvas
     */
    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS,100, 200, paint);
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

    /**
     * spawnen und updaten der Gegner im Spiel
     */
    public void spawnEnemies(){
        //Spawn enemies when ready
        if(Enemy.readyToSpawn() && enemies.size() < MAX_ENEMIES){
            switch(RandomGenerator.generateRandomInt()){
                case 1 :
                    enemies.add(new stationaryEnemy(getContext(),new Vector2D((Math.random()*1000),(Math.random()*1000)),90, 90));
                    break;
                case 2 :
                    enemies.add(new hoveringEnemy(getContext(),new Vector2D((Math.random()*1000),(Math.random()*1000)),90, 90));
                    break;
            }
        }
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

}
