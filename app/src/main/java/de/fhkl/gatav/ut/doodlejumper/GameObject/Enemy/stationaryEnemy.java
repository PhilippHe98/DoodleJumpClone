package de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy;

import android.content.Context;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class stationaryEnemy extends Enemy {

    public stationaryEnemy(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.enemy));
    }

    @Override
    public void update() {
        moveDown();
    }
}
