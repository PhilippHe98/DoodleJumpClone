package de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp;

import android.content.Context;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/*
Ein Buff der den Spieler kurze Zeit unverwundbar macht wenn er ihn einsammelt
 */
public class Schild extends PowerUp {

    public Schild(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context , R.color.Schild));
    }

    @Override
    public void update() {
        moveDown();
    }
}
