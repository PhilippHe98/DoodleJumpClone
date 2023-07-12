package de.fhkl.gatav.ut.doodlejumper.Graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import de.fhkl.gatav.ut.doodlejumper.R;

public class SpriteSheet {
    // Bitmap ist das was der Computer als Bild versteht, besteht aus einer Matrix, in der jedes Feld ein Pixel ist und jeder Pixel hat einen RGB wert.
    private Bitmap bitmap;

    public SpriteSheet(Context context, int drawable) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), drawable, bitmapOptions);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
