package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.hoveringEnemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.stationaryEnemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.Platform;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.PowerUp;
import de.fhkl.gatav.ut.doodlejumper.Random.RandomGenerator;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class SpawnManager {
    private static final int MAX_ENEMIES = 5;
    List<Enemy> enemies = new ArrayList<>();
    List<Platform> platforms = new ArrayList<>();
    Context context;

    private static final int ENEMY_UPDATES_PER_SPAWN = 300;
    private static int enemyUpdatesUntilNextSpawn;

    private static final int PLATFORM_UPDATES_PER_SPAWN = 40;
    private static int platformUpdatesUntilNextSpawn;


    public SpawnManager(Context context) {
        this.context = context;
    }

    public void update() {
        getEnemies();
        getPlatforms();
        spawnPlatforms();
        spawnEnemies();
    }

    private void spawnPlatforms() {
        Vector2D spawnPos;
        if (readyToSpawnPlatform()) {
            spawnPos = new Vector2D(Math.random()*1000,-200);
            if(checkSpawnPosition(spawnPos)) {
                platforms.add(new Platform(context, spawnPos, 150, 50));
            }
        }
    }
    private boolean checkSpawnPosition(Vector2D pos) {
        for (Platform platform: platforms) {
            if(pos.y == platform.getPosition().y) {
                if(!(pos.x > platform.getPosition().x +150)||!(pos.x > platform.getPosition().x -150));
                return false;
            }
        }
        return true;
    }

    private boolean readyToSpawnPlatform() {
        if(platformUpdatesUntilNextSpawn <= 0){
            platformUpdatesUntilNextSpawn += PLATFORM_UPDATES_PER_SPAWN;
            return true;
        }else {
            platformUpdatesUntilNextSpawn--;
            return false;
        }
    }

    public void spawnEnemies(){
        //Spawn enemies when ready
        if(readyToSpawnEnemy() && enemies.size() < MAX_ENEMIES) {
            switch(RandomGenerator.generateRandomInt()){
                case 1 :
                    enemies.add(new stationaryEnemy(context ,new Vector2D((Math.random()*1000),-200),90, 90));
                    break;
                case 2 :
                    enemies.add(new hoveringEnemy(context,new Vector2D((Math.random()*1000),(Math.random()*1000)),90, 90));
                    break;
            }
        }
    }

    public static boolean readyToSpawnEnemy(){

        if(enemyUpdatesUntilNextSpawn <= 0){
            enemyUpdatesUntilNextSpawn += ENEMY_UPDATES_PER_SPAWN;
            return true;
        }else {
            enemyUpdatesUntilNextSpawn--;
            return false;
        }
    }


    public void getEnemies() {
        enemies = Game.enemies;
    }
    public void getPlatforms() {
        platforms = Game.platforms;
    }

}
