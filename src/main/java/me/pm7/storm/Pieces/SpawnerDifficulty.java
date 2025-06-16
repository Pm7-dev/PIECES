package me.pm7.storm.Pieces;

import java.util.Random;

public class SpawnerDifficulty {

    private static final Random random = new Random();
    public int secondsBetweenSpawns;
    public double spawnMultiplier;
    public int minSize;
    public int maxSize;
    public double minSpeed;
    public double maxSpeed;

    SpawnerDifficulty(int secondsBetweenSpawns, double spawnMultiplier, int minSize, int maxSize, double minSpeed, double maxSpeed) {
        this.secondsBetweenSpawns = secondsBetweenSpawns;
        this.spawnMultiplier = spawnMultiplier;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    // !!
    public int getRandomSize() {return random.nextInt(minSize, maxSize + 1);}
    public double getRandomSpeed() {return (random.nextDouble(minSpeed, maxSpeed));}
}
