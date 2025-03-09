package me.pm7.session6.Pieces;

import java.util.Random;

public enum SpawnerDifficulty {
    LEVEL_1(1,
            4,
            1.0,
            10,
            15,
            6,
            8
            ),
    LEVEL_2(2,
            3,
            1.2,
            12,
            18,
            7,
            10
    ),
    LEVEL_3(3,
            3,
            1.5,
            15,
            20,
            8,
            12
    ),
    LEVEL_4(4,
            3,
            2.0,
            16,
            22,
            8,
            15
    ),
    LEVEL_5(5,
            3,
            2.0,
            19,
            26,
            12,
            17
    ),
    LEVEL_6(6,
            2,
            2.5,
            18,
            24,
            8,
            13
    ),

    // Special difficulty settings during anomalies
    ANOMALY_SPEED(null,
            4,
            2.0,
            8,
            12,
            18,
            20
    ),
    ANOMALY_COUNT(null,
            4,
            5.0,
            10,
            12,
            5,
            9
    ),
    ANOMALY_FREQUENCY(null,
            1,
            2.0,
            8,
            11,
            5,
            10
    ),
    ANOMALY_SCALE(null,
            4,
            1.0,
            30,
            45,
            7,
            10
    ),
    ANOMALY_TOTAL_CHAOS(6,
            2,
            3.0,
            24,
            32,
            11,
            16
    );

    private static final Random random = new Random();
    private final Integer difficultyNumber;
    private final int secondsBetweenSpawns;
    private final double spawnMultiplier;
    private final int minSize;
    private final int maxSize;
    private final double minSpeed;
    private final double maxSpeed;

    SpawnerDifficulty(Integer difficulty, int secondsBetweenSpawns, double spawnMultiplier, int minSize, int maxSize, double minSpeed, double maxSpeed) {
        this.difficultyNumber = difficulty;
        this.secondsBetweenSpawns = secondsBetweenSpawns;
        this.spawnMultiplier = spawnMultiplier;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
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

    // !!
    public int getRandomSize() {return random.nextInt(getMinSize(), getMaxSize() + 1);}
    public double getRandomSpeed() {return (random.nextDouble(getMinSpeed(), getMaxSpeed()));}
}
