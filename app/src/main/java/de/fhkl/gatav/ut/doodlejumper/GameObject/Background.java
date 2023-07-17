package de.fhkl.gatav.ut.doodlejumper.GameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.EventListener;
import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Background extends Rectangle {

    private Sprite backgroundSprite;
    private boolean drawn = false;

    public Background(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.blue));

        SpriteSheet backgroundSpriteSheet = new SpriteSheet(context, R.drawable.hintergrund);
        backgroundSprite = new Sprite(backgroundSpriteSheet, new Rect(0,0,4688,10146));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        backgroundSprite.draw(canvas, topLeft, bottomRight);
    }
}
