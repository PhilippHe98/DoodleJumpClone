package de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy;

import android.content.Context;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class stationaryEnemy extends Enemy {
    private Sprite stationaryEnemySprite;
    public stationaryEnemy(Context context, Vector2D position, double width, double height, Sprite sprite) {
        super(position, width, height, ContextCompat.getColor(context, R.color.enemy), sprite);
    }

    @Override
    public void update() {
        moveDown();
    }
}
