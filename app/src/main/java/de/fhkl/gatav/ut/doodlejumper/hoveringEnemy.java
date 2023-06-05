package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class hoveringEnemy extends Enemy {

    private static final double PIXEL_PER_UPDATE = 1;
    private static final double UPDATES_PER_SIDE = 400/PIXEL_PER_UPDATE;
    private Vector2D startPosition;
    private double width;
    private Vector2D moveRight = new Vector2D(PIXEL_PER_UPDATE,0);
    private Vector2D moveLeft = new Vector2D(-PIXEL_PER_UPDATE,0);
    private double updatesTillSwap = UPDATES_PER_SIDE;

    public hoveringEnemy(Context context, Vector2D position, double width, double height) {
        super(context, position, width, height, ContextCompat.getColor(context, R.color.white));
        startPosition = position;
        this.width = width;
    }

    public boolean switchSide(){
        if(updatesTillSwap <= 0){
            updatesTillSwap += UPDATES_PER_SIDE;
            return true;
        }else {
            updatesTillSwap--;
            return false;
        }
    }
    @Override
    void update() {
        if((switchSide())){
            addTopLeft(moveRight);
            addBottomRight(moveRight);
        }else{
            addTopLeft(moveLeft);
            addBottomRight(moveLeft);
        }
    }
}
