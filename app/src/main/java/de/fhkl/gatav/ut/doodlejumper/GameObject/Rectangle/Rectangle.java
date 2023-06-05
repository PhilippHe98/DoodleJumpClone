package de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;

import android.graphics.Canvas;

import de.fhkl.gatav.ut.doodlejumper.GameObject.GameObject;

public abstract class Rectangle extends GameObject {
    public Rectangle(double posX, double posY) {
        super(posX, posY);
    }
    public abstract void update();
    public abstract void draw(Canvas canvas);

}
