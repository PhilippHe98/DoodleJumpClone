package de.fhkl.gatav.ut.doodlejumper.GameLogic;


import android.os.Bundle;

import de.fhkl.gatav.ut.doodlejumper.GameLogic.Game;

/**
 * MainActivity is the entry point of out application
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view to game, so that objects in the game class can be rendered to the screen
        // setContentView(new Game(this));
    }
}