package de.fhkl.gatav.ut.doodlejumper.Graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Sprite {
    private final SpriteSheet spriteSheet;
    private final Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, Vector2D topLeft, Vector2D bottomRight) {
        canvas.drawBitmap(spriteSheet.getBitmap(),
                rect,
                new Rect((int) topLeft.x, (int) topLeft.y, (int) bottomRight.x, (int) bottomRight.y),
                null);
    }
}
