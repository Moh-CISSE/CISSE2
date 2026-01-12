package com.nous.snake;


// cette classe ? 
//Sauvegarde la progression 
//Débloque les niveaux 
public class Progress {

    private static int unlockedLevel = 0;

    public static boolean isUnlocked(int level) {
        return level <= unlockedLevel;
    }

    public static void unlockNextLevel(int currentLevel) {
        if (currentLevel >= unlockedLevel) {
            unlockedLevel = currentLevel + 1;
        }
    }

    public static void reset() {
        unlockedLevel = 0;
    }
}
