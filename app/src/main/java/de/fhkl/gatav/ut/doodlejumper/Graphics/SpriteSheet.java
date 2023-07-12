package de.fhkl.gatav.ut.doodlejumper.Graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import de.fhkl.gatav.ut.doodlejumper.R;

public class SpriteSheet {
    // Bitmap ist das was der Computer als Bild versteht, besteht aus einer Matrix, in der jedes Feld ein Pixel ist und jeder Pixel hat einen RGB wert.
    private Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jw_normal, bitmapOptions);
    }

    public Sprite getPlayerSprite() {
        return new Sprite(this, new Rect(0,0,3009,3716));
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
