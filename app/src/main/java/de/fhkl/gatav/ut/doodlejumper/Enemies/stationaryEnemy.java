package de.fhkl.gatav.ut.doodlejumper.Enemies;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameObject.GameObject;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.R;

/**
 * stationaryEnemy is a character which is a threat to the player.
 * stationaryEnemy extends Enemy, which extends Rectangle, which extends GameObject
 */
public class stationaryEnemy extends Enemy {

    private Player player;

    public stationaryEnemy(Context context, double posX, double posY, Player player) {
        super(context, posX, posY, ContextCompat.getColor(context, R.color.enemy), player);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
    }

}
