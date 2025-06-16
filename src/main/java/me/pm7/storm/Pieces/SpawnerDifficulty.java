package me.pm7.storm.Pieces;

import java.util.Random;

public enum SpawnerDifficulty {
    LEVEL_1(1,
            5,
            1.0,
            9,
            12,
            5,
            7,
            null
            ),
    LEVEL_2(2,
            4,
            1.2,
            11,
            16,
            7,
            10,
            null
    ),
    LEVEL_3(3,
            4,
            1.5,
            14,
            16,
            8,
            12,
            null
    ),



    //THESE SHOULD NOT BE USED DURING SESSION UNLESS PEOPLE ARE LIKE... REALLY GOOD AT THE GAME
    LEVEL_4(4,
            3,
            1.75,
            16,
            18,
            9,
            14,
            null
    ),
    LEVEL_5(5,
            3,
            2.0,
            19,
            26,
            12,
            17,
            null
    ),
    LEVEL_6(6,
            2,
            2.5,
            18,
            24,
            8,
            13,
            null
    ),

    // Special difficulty settings during anomalies
    ANOMALY_SPEED(null,
            3,
            2.0,
            13,
            18,
            25,
            32,
            "SPEED"
    ),
    ANOMALY_SCALE(null,
            5,
            1.25,
            32,
            40,
            7,
            10,
            "SCALE"
    ),
    ANOMALY_TOTAL_CHAOS(null,
            4,
            2.25,
            32,
            38,
            22,
            28,
            "TOTAL CHAOS"
    ),



    LAG_TEST(null,
            2,
            150.0,
            2,
            7,
            8,
            15,
            "Test for lag stuff"
    );

    private static final Random random = new Random();
    private final Integer difficultyNumber;
    private final int secondsBetweenSpawns;
    private final double spawnMultiplier;
    private final int minSize;
    private final int maxSize;
    private final double minSpeed;
    private final double maxSpeed;
    private final String name;

    SpawnerDifficulty(Integer difficulty, int secondsBetweenSpawns, double spawnMultiplier, int minSize, int maxSize, double minSpeed, double maxSpeed, String name) {
        this.difficultyNumber = difficulty;
        this.secondsBetweenSpawns = secondsBetweenSpawns;
        this.spawnMultiplier = spawnMultiplier;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.name = name;
    }

    // Could definitely have been a String.valueOf() sorta thing
    public static SpawnerDifficulty fromInt(int difficultyNumber) {
        return switch (difficultyNumber) {
            case 1 -> LEVEL_1;
            case 2 -> LEVEL_2;
            case 3 -> LEVEL_3;
            case 4 -> LEVEL_4;
            case 5 -> LEVEL_5;
            case 6 -> LEVEL_6;
            default -> null;
        };
    }

    // the
    public Integer getDifficultyNumber() {return difficultyNumber;}
    public int getSecondsBetweenSpawns() {return secondsBetweenSpawns;}
    public double getSpawnMultiplier() {return spawnMultiplier;}
    public int getMinSize() {return minSize;}
    public int getMaxSize() {return maxSize;}
    public double getMinSpeed() {return minSpeed;}
    public double getMaxSpeed() {return maxSpeed;}
    public String getName() {return name;}

    // !!
    public int getRandomSize() {return random.nextInt(getMinSize(), getMaxSize() + 1);}
    public double getRandomSpeed() {return (random.nextDouble(getMinSpeed(), getMaxSpeed()));}
}
