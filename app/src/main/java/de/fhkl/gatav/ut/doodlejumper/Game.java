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
    public static  ArrayList<Enemy> enemies = new ArrayList<>();
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

        setFocusable(true);
    }

    /**
     * updatet kontinuierlich den Game State
     */
    public void update() {

        processSensorData(accelerationX);
        player.update();
        spawnAndUpdateEnemies();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch event actions
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:

        }
        return super.onTouchEvent(event);
    }

    /**
     * kümmert sich um das zeichnen der Objekte auf den Bildschirm
     * @param canvas
     */
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

    /**
     * spawnen und updaten der Gegner im Spiel
     */
    public void spawnAndUpdateEnemies(){
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
        //Update state of each enemy
        for(Enemy enemy : enemies){
            enemy.update();
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
