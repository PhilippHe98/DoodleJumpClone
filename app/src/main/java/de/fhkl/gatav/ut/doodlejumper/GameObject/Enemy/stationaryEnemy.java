package de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class stationaryEnemy extends Enemy {
    private Sprite enemySprite;
    public stationaryEnemy(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.enemy));
        SpriteSheet spriteSheet = new SpriteSheet(context, R.drawable.enemy1);
        enemySprite = new Sprite(spriteSheet, new Rect(0,0,1927, 3602));
    }

    @Override
    public void update() {
        moveDown();
    }

    @Override
    public void draw(Canvas canvas) {
        if(position.y > 2400) return;
        enemySprite.draw(canvas, topLeft, bottomRight);
    }
}
