package de.fhkl.gatav.ut.doodlejumper;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity is the entry point of out application
 */
public class MainActivity extends AppCompatActivity {

    private Game game;
    public static int displayHeight;
    public static int displayWidth;

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
//       background = BitmapFactory.decodeResource(getResources(), R.id.background);
         getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        DisplayMetrics displayMetrics = new DisplayMetrics();

        // on below line we are getting metrics for display using window manager.
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // on below line we are getting height
        // and width using display metrics.
        displayHeight = displayMetrics.heightPixels;
        displayWidth = displayMetrics.widthPixels;
        System.out.println("Height/Width: " + displayHeight + "/" + displayWidth);
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