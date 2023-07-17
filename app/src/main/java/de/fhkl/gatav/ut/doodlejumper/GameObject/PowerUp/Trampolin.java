package de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Trampolin extends PowerUp {
    private Sprite trampolinSprite;

    public Trampolin(Context context, Vector2D position, double width, double height) {
        super(position, width, height,  ContextCompat.getColor(context, R.color.Trampolin));
        SpriteSheet spriteSheet = new SpriteSheet(context,R.drawable.trampolin);
        trampolinSprite = new Sprite(spriteSheet, new Rect(0,0,1881,1092));
    }
    @Override
    public void update(){
        moveDown();
    }
    @Override
    public void draw(Canvas canvas) {
        if(position.y > 2400) return;
        trampolinSprite.draw(canvas,topLeft,bottomRight);
    }
}

