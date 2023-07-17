package de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/*
Ein Buff der den Spieler kurze Zeit unverwundbar macht wenn er ihn einsammelt
 */
public class Schild extends PowerUp {
    private Sprite shieldSprite;

    public Schild(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context , R.color.Schild));
        SpriteSheet spriteSheet = new SpriteSheet(context, R.drawable.jw_schutzschild);
       shieldSprite = new Sprite(spriteSheet, new Rect(0,0,4245,4331));
    }

    @Override
    public void update() {
        moveDown();
    }

    @Override
    public void draw(Canvas canvas) {
        if(position.y > 2400) return;
        shieldSprite.draw(canvas, topLeft, bottomRight);
    }
}
