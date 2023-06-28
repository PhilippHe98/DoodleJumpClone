package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class stationaryEnemy extends Enemy {

    public stationaryEnemy(Context context, Vector2D position, double width, double height) {
        super(context, position, width, height, ContextCompat.getColor(context, R.color.enemy));
    }

    @Override
    public void update() {

    }

}
