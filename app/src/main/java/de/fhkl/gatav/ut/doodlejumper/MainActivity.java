package de.fhkl.gatav.ut.doodlejumper;


import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * MainActivity is the entry point of out application
 */
public class MainActivity extends Activity {

    private Game game;

    /**
     * Erstellen von SensorManager Objekt, Sensor Objekt und SensorEventListener Objekt
     * um die Neigung des Geräts zu erhalten und den Spieler damit nach links und rechts zu bewegen
     */
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private SensorEventListener SensorEventListener;

    /**
     * Create Methode die beim Start der App aufegrufen wird
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view to game, so that objects in the game class can be rendered to the screen
        game = new Game(this);
        setContentView(game);
    }

    /**
     * Nachdem pausiert wurde und auf Fortsetzen geklickt wird, regelt diese Methode was passiert
     */
    @Override
    protected void onResume() {
        super.onResume();
        game.registerSensorListener();

    }

    /**
     * Was passiert beim Pause drücken wird hier festgelegt
     */
    @Override
    protected void onPause() {
        super.onPause();
        game.unregisterSensorListener();
    }
}