package de.fhkl.gatav.ut.doodlejumper;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;


/**
 * MainActivity is the entry point of out application
 */
public class MainActivity extends Activity {

    private Game game;
    public static Bitmap background;

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
        //Äquivalent zu GameView Klassen wie aus Tutorials bekannt
        game = new Game(this);
        setContentView(game);
//        background = BitmapFactory.decodeResource(getResources(), R.id.background);
          getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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

    @Override
    public void onBackPressed() {
        // Pausiere das Spiel, wenn der Zurück-Button der Handy-Taskleiste gedrückt wird
        game.setIsPaused(!game.getIsPaused());
        if (game.getIsPaused()) {
            // Das Spiel ist pausiert
            // Führe hier weitere Aktionen für die Pausierung des Spiels aus
        } else {
            // Das Spiel wird fortgesetzt
            // Führe hier weitere Aktionen für die Fortsetzung des Spiels aus
        }
    }

}