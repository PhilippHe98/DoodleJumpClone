package de.fhkl.gatav.ut.doodlejumper.Random;
/**
Kann verschiedenste zufällige Objekte/ Zahlen generieren um die Level mit unterschiedlichen
Objekten zu füllen und Plattformen die gespawnt werden dynamisch zur Laufzeit ausgewählt werden
 */
public class RandomGenerator {
    public RandomGenerator() {

    }
    /**
    Generieren einer zufälligen ganzen Zahl zwischen 0 und max-1 und gibt diese beim Methodenaufruf zurück
     */
    public static int generateRandomInt(int max){
        double randomd = Math.random()*max;
        int random = (int) randomd;
        return random;
    }
}
