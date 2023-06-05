package de.fhkl.gatav.ut.doodlejumper.GameLogic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import de.fhkl.gatav.ut.doodlejumper.Enemies.Enemy;
import de.fhkl.gatav.ut.doodlejumper.Enemies.hoveringEnemy;
import de.fhkl.gatav.ut.doodlejumper.Enemies.stationaryEnemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle.Plattform;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle.Player;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.Random.RandomGenerator;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final Player player;
    private Plattform plattform;
    private final GameLoop gameLoop;
    Context context;

    private List <Enemy> enemyList = new ArrayList<>();

    public Game(Context context) {
        super(context);
        this.context = context;

        // get surface golder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //Init Player
        player = new Player(getContext(), 200, 2000, 900, 1500);
        // Init one Platform
        plattform = new Plattform(getContext(), 100, 1450,650, 1400);
        setFocusable(true);

    }

    public void update() {

        //Update game state
        player.update();

        //Spawn enemies when it is time to spawn
        if(Enemy.readyToSpawn()){
            int whichEnemy = 0;
            switch(RandomGenerator.generateRandomInt(2)){
                case 1 :
                    enemyList.add(new stationaryEnemy(context, RandomGenerator.generateRandomInt(10),RandomGenerator.generateRandomInt(10)));
                    break;
                case 2 :
                    enemyList.add(new hoveringEnemy(context, RandomGenerator.generateRandomInt(10),RandomGenerator.generateRandomInt(10)));
                    break;
            }
        }

        //Update state of each enemy
        for (Enemy enemy : enemyList){
            enemy.update();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch event actions
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                player.jump();
                player.reset();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
        player.draw(canvas);
        for(Enemy enemy : enemyList){
            enemy.draw(canvas);
        }
        plattform.draw(canvas);
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
