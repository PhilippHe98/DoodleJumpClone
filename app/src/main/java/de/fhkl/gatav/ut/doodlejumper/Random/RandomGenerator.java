package de.fhkl.gatav.ut.doodlejumper.Random;
/*
Kann verschiedenste zufällige Objekte/ Zahlen generieren um die Level mit unterschiedlichen
Objekten zu füllen und Plattformen die gespawnt werden dynamisch zur Laufzeit ausgewählt werden
 */
public class RandomGenerator {
    public RandomGenerator() {

    }
    /*
    Generieren einer zufälligen ganzen Zahl zwischen 0 und 10 und gibt diese beim Methodenaufruf zurück
     */
    public int generateRandomInt(){
        double randomd = Math.random()*10;
        int random = (int) randomd;
        return random;
    }
}