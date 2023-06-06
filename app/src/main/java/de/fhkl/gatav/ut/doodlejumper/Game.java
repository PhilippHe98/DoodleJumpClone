package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import de.fhkl.gatav.ut.doodlejumper.Random.RandomGenerator;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private static final int MAX_ENEMIES = 5;
    private final Player player;
    private final Vector2D playerStartPosition = new Vector2D(500,1000); //x und y werden in update überschrieben,
    Vector2D playerPosition = new Vector2D(playerStartPosition.x, playerStartPosition.y);
    public static ArrayList<Platform> platforms = new ArrayList<>();
    public static  ArrayList<Enemy> enemies = new ArrayList<>();
    private final GameLoop gameLoop;

    public Game(Context context) {
        super(context);


        // get surface golder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //Init Player
        player = new Player(getContext(), playerPosition, 100,100);

        // Init one Platform
        Platform platform1 = new Platform(getContext(), new Vector2D(500,1500), 200, 50);
        Platform platform2 = new Platform(getContext(), new Vector2D(500,750), 200, 50);
        Platform platform3 = new Platform(getContext(), new Vector2D(500,2000), 200, 50);
        platforms.add(platform1);
        platforms.add(platform2);
        platforms.add(platform3);

        setFocusable(true);
    }
    //Update game state
    public void update() {
        player.update();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch event actions
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:

        }
        return super.onTouchEvent(event);
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
    }

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
}
