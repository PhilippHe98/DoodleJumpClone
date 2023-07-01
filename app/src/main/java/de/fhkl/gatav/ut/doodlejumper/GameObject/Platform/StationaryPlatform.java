package de.fhkl.gatav.ut.doodlejumper.GameObject.Platform;

import android.content.Context;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class StationaryPlatform extends Platform {


    public StationaryPlatform(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.stationary_plattform));
    }

    @Override
    public void update() {
        moveDown();
    }
}
