package de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Jetpack extends PowerUp {

    private SpriteSheet jetpackSpriteSheet;
    private Sprite jetpackSprite;

    public Jetpack(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.Jetpack));
        jetpackSpriteSheet = new SpriteSheet(context, R.drawable.jetpack);
        jetpackSprite = new Sprite(jetpackSpriteSheet, new Rect(0,0,2291,2363));
    }

    @Override
    public void draw(Canvas canvas) {
        if(position.y > 2400) return;
        jetpackSprite.draw(canvas, topLeft,bottomRight);
    }

    @Override
    public void update() {
        moveDown();
    }
}