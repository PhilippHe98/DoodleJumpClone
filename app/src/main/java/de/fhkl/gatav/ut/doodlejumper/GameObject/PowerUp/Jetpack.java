package de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp;

import android.content.Context;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Jetpack extends PowerUp {

    public Jetpack(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.Jetpack));
    }

    @Override
    public void update() {
        moveDown();
    }
}