package de.fhkl.gatav.ut.doodlejumper.Graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Sprite {
    private final SpriteSheet spriteSheet;
    private final Rect rect;

    private Bitmap cachedBitmap; // Zwischengespeicherte Bitmap

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, Vector2D topLeft, Vector2D bottomRight) {
        if(topLeft.y > 2400) return;
        /*Bitmap-Caching: Wenn du häufig dieselbe Bitmap zeichnest, kannst du die skalierte Version zwischenspeichern,
         anstatt sie bei jedem Frame neu zu skalieren. Dies kann die Leistung verbessern, indem die erforderliche Verarbeitung reduziert wird.*/
        if (cachedBitmap == null) {
            // Skalierte Bitmap erstellen, falls noch nicht zwischengespeichert
            cachedBitmap = Bitmap.createBitmap(spriteSheet.getBitmap());
        }
        canvas.drawBitmap(cachedBitmap,
                rect,
                new Rect((int) topLeft.x, (int) topLeft.y, (int) bottomRight.x, (int) bottomRight.y),
                null);
    }
}
