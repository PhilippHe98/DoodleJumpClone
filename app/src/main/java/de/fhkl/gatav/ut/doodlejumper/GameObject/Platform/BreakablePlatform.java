package de.fhkl.gatav.ut.doodlejumper.GameObject.Platform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.Game;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class BreakablePlatform extends Platform {


    private Sprite sprite;

    public BreakablePlatform(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.breakable_plattform));
        SpriteSheet spriteSheet = new SpriteSheet(context, R.drawable.plattform1);
        sprite = new Sprite(spriteSheet, new Rect(0,0,4046, 779));
    }

    @Override
    public void update() {
        moveDown();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, topLeft, bottomRight);
    }
}
