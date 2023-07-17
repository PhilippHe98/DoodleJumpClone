package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.hoveringEnemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.stationaryEnemy;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.BreakablePlatform;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.MovingPlatform;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.Platform;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.StationaryPlatform;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.Jetpack;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.PowerUp;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.Schild;
import de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp.Trampolin;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
import de.fhkl.gatav.ut.doodlejumper.Random.RandomGenerator;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class SpawnManager implements EventListener {
    private static final int MAX_ENEMIES = 2;
    private static List<Enemy> enemies = new ArrayList<>();

    private static final int MAX_PLATFORMS = 10;
    private static final int MIN_PLATFORMS = 4;
    private static List<Platform> platforms = new ArrayList<>();
    private static List<PowerUp> powerUps = new ArrayList<>();
    Context context;

    private static final int ENEMY_UPDATES_PER_SPAWN = 450;
    private static int enemyUpdatesUntilNextSpawn;

    private static final int PLATFORM_UPDATES_PER_SPAWN = 20;
    private static int platformUpdatesUntilNextSpawn;

    private static Player player;
    private static boolean playerTrampolin = false;
    private boolean playerJetpack = false;


    public SpawnManager(Context context) {
        this.context = context;
    }

    public void update() {
        getEnemies();
        getPlatforms();
        getPowerUps();
        spawnPlatforms();
        spawnEnemies();
    }

    private void generatePowerUp(Vector2D spawnPos) {
        PowerUp powerUp;
        switch (RandomGenerator.generateRandomInt(3)) {
            case 1:
                powerUp = new Schild(context, spawnPos, 100, 100);
                break;
            case 2:
                powerUp = new Jetpack(context, spawnPos, 100, 100);
                break;
            default:
                powerUp = new Trampolin(context, spawnPos, 90, 50);
        }
        powerUp.setPlayer(player);
        powerUp.reactToEvent();
        //
        powerUps.add(powerUp);
    }

    private void spawnPlatforms() {
        Vector2D spawnPos;
        if (readyToSpawnPlatform()) {
            spawnPos = new Vector2D(Math.random()*1000,-200);
            if(checkSpawnPosition(spawnPos)) {
                switch(RandomGenerator.generateRandomInt(4)){
                    case 1:
                        Platform platform = new BreakablePlatform(context, spawnPos, 150, 50);
                        platforms.add(platform);
                        break;
                    case 2:  platforms.add(new MovingPlatform(context, spawnPos, 150, 50));
                        break;
                    default: //case 3 und 4 -> macht stationary wahrscheinlicher
                        platforms.add(new StationaryPlatform(context, spawnPos, 150, 50));
                        if(RandomGenerator.generateRandomInt(6) == 1) // 1/5 chance auf Powerup wenn Plattform spawnt
                        generatePowerUp(new Vector2D(spawnPos.x, spawnPos.y - 60));
                        break;
                }
            }
        }
    }
    private boolean checkSpawnPosition(Vector2D pos) {
        for (Platform platform: platforms) {
            if(Math.abs(pos.y - platform.getPosition().y) <= 150) {
                if(Math.abs(pos.x - platform.getPosition().x) < 300)
                    return false;
            }
        }
        return true;
    }

    private boolean readyToSpawnPlatform() {

        if((playerJetpack || playerTrampolin) && platforms.size() <= MAX_PLATFORMS) return true;
        if(platforms.size() < MIN_PLATFORMS) return true;
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
        Enemy enemy = null;
        if(readyToSpawnEnemy()) {
            switch(RandomGenerator.generateRandomInt(2)){
                case 0 :
                    enemy = new stationaryEnemy(context ,new Vector2D((Math.random()*1000),-400),100, 182);
                    break;
                case 1 :
                    enemy = new hoveringEnemy(context, new Vector2D((Math.random()*1000), -400),100, 182);
                    break;
                default:
            }
            enemy.setPlayer(player);
            enemy.reactToEvent();
            enemies.add(enemy);
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
    public void getPowerUps() { powerUps = Game.powerUps;}

    public void setPlayer(Player player) {
        if(this.player != null) {
            this.player.removeListener(this);
        }

        this.player = player;

        if(this.player != null) {
            this.player.registerListener(this);
        }
    }
    public void removeListener() {
        if(this.player != null) {
            this.player.removeListener(this);
        }
    }
    @Override
    public void reactToEvent() {
        if(player != null) {
            switch (player.getState()) {
                case "TRAMPOLIN":
                    // Das setzen auf true, lässt mehr Plattformen spawnen indem der Timer ignoriert wird, wenn playerTrampolin true ist
                    playerTrampolin = true;
                    break;
                case "JETPACK:":
                    playerJetpack = true;
                    break;
                case "DEFAULT":
                    playerTrampolin = false;
                    playerJetpack = false;
                    break;
            }
        }
    }
}
