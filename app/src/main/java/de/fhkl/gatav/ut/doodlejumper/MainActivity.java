package de.fhkl.gatav.ut.doodlejumper;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * MainActivity is the entry point of out application
 */
public class MainActivity extends Activity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view to game, so that objects in the game class can be rendered to the screen
        game = new Game(this);
        setContentView(game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.registerSensorListener();

    }

    @Override
    protected void onPause() {
        super.onPause();
        game.unregisterSensorListener();
    }
}