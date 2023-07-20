package de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class hoveringEnemy extends Enemy {

    private static final double PIXEL_PER_UPDATE = 3;

    private Vector2D velocity = new Vector2D(PIXEL_PER_UPDATE,0);

    private Sprite enemySprite;

    public hoveringEnemy(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.white));
        SpriteSheet spriteSheet = new SpriteSheet(context, R.drawable.enemy2);
        enemySprite = new Sprite(spriteSheet, new Rect(0,0,1985,3771));
    }

    @Override
    public void update() {
        if(position.x < 0) moveRight();
        else if(position.x > 1000) moveLeft();
        else{
            moveRight();
        }
        moveDown();
    }
    @Override
    public void draw(Canvas canvas) {
        if(position.y > 2400) return;
        enemySprite.draw(canvas, topLeft, bottomRight);
    }

    private void moveRight() {
        velocity.set(PIXEL_PER_UPDATE, velocity.y);
    }

    private void moveLeft() {
        velocity.set(-PIXEL_PER_UPDATE, velocity.y);
    }

    @Override
    public Sprite getSprite() {
        return enemySprite;
    }
}
