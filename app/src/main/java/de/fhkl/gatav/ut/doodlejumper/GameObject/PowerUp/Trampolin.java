package de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp;

import android.content.Context;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Trampolin extends PowerUp {

    public Trampolin(Context context, Vector2D position, double width, double height) {
        super(position, width, height,  ContextCompat.getColor(context, R.color.Trampolin));
    }
    @Override
    public void update(){
        moveDown();
    }
}

