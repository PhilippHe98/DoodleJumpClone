package de.fhkl.gatav.ut.doodlejumper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    TextView gameOverText;
    TextView scoreText;
    Button restartButton;
    Button exitButton;
    ImageView jwKO;
    //private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameoverscreen);
        scoreText = findViewById(R.id.scoreText);
        gameOverText = findViewById(R.id.gameoverText);
        int points = getIntent().getExtras().getInt("score");
        scoreText.setText("Dein Score: " + points);
        //sharedPreferences = getSharedPreferences("highest", 0);


    }
    public void restartGame(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void exitGame(View view){
        finish();
    }
}
