package me.pm7.session6.Pieces.Color;

import org.bukkit.Color;

import java.util.Arrays;
import java.util.List;

public enum PieceColorPattern {

    // Boring solid colors
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    TEAL,
    LIGHT_BLUE,
    BLUE,
    PURPLE,
    PINK,

    // Cooler custom colors (gradients n' stuff)
    ELECTRIC_FLASH,
    COTTON_CANDY,
    UNICORN_PUKE,
    ORANGE_CREAM,
    JOKER,
    SPLATOON,
    QUANTUM_TEAL,
    FADING_PINK,
    MTN_DEW_OR_SOMETHING,
    LIGHTNESS_STAIRS,
//    LIGHTNESS_FADE,
    GLACIER,
    DARKENING_RED,
    SALMON,
    MANGO,
    PURPLE_SPREAD,
    BLORANGE,
    PURP_TO_RED,
    SIERRA_MIST, //SODA!!!
    KINDA_PRISMARINE,
    OUT_OF_NAMES,
    hOtGuY,
    QUICKFLASH,
    DYING_FIRE,
    MODERN_TENNIS_SHOE,
    UNNATURAL_CREAM,
    EMBER,
    FIREFLY,
    ROSE,
    BANANA,
    FADING_DENIM,
    DREAM_CREAM,
    ROCK,
    YET_ANOTHER_BLUE_GRADIENT,


    // !!
    PIFFIN_VIEWER_EXTRAVAGANZA_1,
    PIFFIN_VIEWER_EXTRAVAGANZA_2,
    PIFFIN_VIEWER_EXTRAVAGANZA_3,


    ;

    private static final boolean test = false;
    public PieceColor getColor() {

        if(test) { // Used to test new piece colors, makes it a lot easier to see in game
            return new PieceColor(false, 3, Arrays.asList(
                    Color.fromARGB(255, 251, 255, 79),
                    Color.fromARGB(255, 58, 112, 237)
            ));
        }


        return switch (this) {
            case RED -> new PieceColor(false, 0, List.of(Color.fromRGB(255, 81, 81)));
            case ORANGE -> new PieceColor(false, 0, List.of(Color.fromRGB(255, 164, 81)));
            case YELLOW -> new PieceColor(false, 0, List.of(Color.fromRGB(255, 255, 81)));
            case GREEN -> new PieceColor(false, 0, List.of(Color.fromRGB(81, 255, 81)));
            case TEAL -> new PieceColor(false, 0, List.of(Color.fromRGB(81, 255, 172)));
            case LIGHT_BLUE -> new PieceColor(false, 0, List.of(Color.fromRGB(81, 216, 255)));
            case BLUE -> new PieceColor(false, 0, List.of(Color.fromRGB(81, 85, 255)));
            case PURPLE -> new PieceColor(false, 0, List.of(Color.fromRGB(164, 81, 255)));
            case PINK -> new PieceColor(false, 0, List.of(Color.fromRGB(255, 81, 238)));

            case ELECTRIC_FLASH -> new PieceColor(false, 3, Arrays.asList(
                    Color.fromARGB(255, 251, 255, 79),
                    Color.fromARGB(255, 58, 112, 237)
            ));

            case COTTON_CANDY -> new PieceColor(true, 30, Arrays.asList(
                    Color.fromARGB(255, 91, 206, 250),
                    Color.fromARGB(255, 245, 169, 184)
            ));

            case UNICORN_PUKE -> new PieceColor(true, 12, Arrays.asList(
                    Color.fromARGB(255, 255, 66, 66),
                    Color.fromARGB(255, 66, 255, 66),
                    Color.fromARGB(255, 66, 255, 255),
                    Color.fromARGB(255, 66, 66, 255),
                    Color.fromARGB(255, 255, 66, 255)
            ));

            case ORANGE_CREAM -> new PieceColor(true, 45, Arrays.asList(
                    Color.fromARGB(255, 255, 217, 90),
                    Color.fromARGB(255, 255, 123, 60)
            ));

            case JOKER -> new PieceColor(true, 10, Arrays.asList(
                    Color.fromARGB(255, 91, 223, 77),
                    Color.fromARGB(255, 91, 223, 77),
                    Color.fromARGB(255, 129, 80, 198),
                    Color.fromARGB(255, 129, 80, 198)
            ));

            case SPLATOON -> new PieceColor(true, 8, Arrays.asList(
                    Color.fromARGB(255, 187, 242, 72),
                    Color.fromARGB(255, 247, 125, 243)
            ));

            case QUANTUM_TEAL -> new PieceColor(false, 3, Arrays.asList(
                    Color.fromARGB(255, 25, 255, 141),
                    Color.fromARGB(0, 0, 0, 0)
            ));

            case FADING_PINK -> new PieceColor(true, 15, Arrays.asList(
                    Color.fromARGB(255, 237, 97, 237),
                    Color.fromARGB(80, 237, 97, 237)
            ));

            case MTN_DEW_OR_SOMETHING -> new PieceColor(true, 22, Arrays.asList(
                    Color.fromARGB(255, 255, 243, 71),
                    Color.fromARGB(255, 16, 248, 242)
            ));

            case LIGHTNESS_STAIRS -> new PieceColor(false, 4, Arrays.asList(
                    Color.fromARGB(255, 255, 255, 255),
                    Color.fromARGB(255, 224, 224, 224),
                    Color.fromARGB(255, 192, 192, 192),
                    Color.fromARGB(255, 160, 160, 160),
                    Color.fromARGB(255, 128, 128, 128),
                    Color.fromARGB(255, 96, 96, 96),
                    Color.fromARGB(255, 64, 64, 64),
                    Color.fromARGB(255, 32, 32, 32),
                    Color.fromARGB(255, 0, 0, 0)
            ));

//            case LIGHTNESS_FADE -> new PieceColor(true, 5, false, Arrays.asList(
//                    Color.fromARGB(255, 255, 255, 255),
//                    Color.fromARGB(128, 255, 255, 255),
//                    Color.fromARGB(0, 255, 255, 255),
//                    Color.fromARGB(0, 0, 0, 0),
//                    Color.fromARGB(128, 0, 0, 0),
//                    Color.fromARGB(255, 0, 0, 0),
//                    Color.fromARGB(128, 0, 0, 0),
//                    Color.fromARGB(0, 0, 0, 0),
//                    Color.fromARGB(0, 255, 255, 255),
//                    Color.fromARGB(128, 255, 255, 255)
//            ));

            case GLACIER -> new PieceColor(true, 20, Arrays.asList(
                    Color.fromARGB(255, 187, 198, 236),
                    Color.fromARGB(255, 94, 241, 242)
            ));

            case DARKENING_RED -> new PieceColor(true, 80, Arrays.asList(
                    Color.fromARGB(255, 106, 23, 23),
                    Color.fromARGB(255, 238, 72, 72)
            ));

            case SALMON -> new PieceColor(true, 24, Arrays.asList(
                    Color.fromARGB(255, 255, 65, 85),
                    Color.fromARGB(255, 222, 254, 255)
            ));

            case MANGO -> new PieceColor(true, 22, Arrays.asList(
                    Color.fromARGB(255, 97, 206, 37),
                    Color.fromARGB(255, 255, 247, 66),
                    Color.fromARGB(255, 251, 76, 95)
            ));

            case PURPLE_SPREAD -> new PieceColor(true, 25, Arrays.asList(
                    Color.fromARGB(255, 88, 45, 230),
                    Color.fromARGB(255, 255, 68, 244)
            ));

            case BLORANGE -> new PieceColor(true, 32, Arrays.asList(
                    Color.fromARGB(255, 45, 126, 230),
                    Color.fromARGB(255, 255, 102, 68)
            ));

            case PURP_TO_RED -> new PieceColor(true, 17, Arrays.asList(
                    Color.fromARGB(255, 83, 83, 255),
                    Color.fromARGB(255, 255, 83, 255),
                    Color.fromARGB(255, 255, 83, 83)
            ));

            case SIERRA_MIST -> new PieceColor(true, 18, Arrays.asList(
                    Color.fromARGB(255, 75, 87, 255),
                    Color.fromARGB(180, 91, 241, 108)
            ));

            case KINDA_PRISMARINE -> new PieceColor(true, 55, Arrays.asList(
                    Color.fromARGB(255, 16, 228, 166),
                    Color.fromARGB(255, 75, 212, 251)
            ));

            case OUT_OF_NAMES -> new PieceColor(true, 20, Arrays.asList(
                    Color.fromARGB(255, 38, 206, 170),
                    Color.fromARGB(180, 255, 255, 255),
                    Color.fromARGB(255, 80, 73, 204),
                    Color.fromARGB(180, 255, 255, 255)
            ));

            case hOtGuY -> new PieceColor(true, 30, Arrays.asList(
                    Color.fromARGB(255, 236, 205, 0),
                    //Color.fromARGB(255, 255, 255, 255),
                    Color.fromARGB(255, 98, 174, 220)
            ));

            // Hopefully this is easy enough on the eyes
            case QUICKFLASH -> new PieceColor(false, 1, List.of(
                    Color.fromARGB(255, 255, 255, 255),
                    Color.fromARGB(255, 255, 215, 215)
            ));

            case DYING_FIRE -> new PieceColor(true, 40, List.of(
                    Color.fromARGB(255, 240, 101, 67),
                    Color.fromARGB(120, 232, 233, 235)
            ));

            case MODERN_TENNIS_SHOE -> new PieceColor(true, 20, List.of(
                    Color.fromARGB(255, 68, 255, 210),
                    Color.fromARGB(145, 97, 97, 99),
                    Color.fromARGB(255, 140, 68, 255),
                    Color.fromARGB(145, 97, 97, 99)
            ));

            case UNNATURAL_CREAM -> new PieceColor(true, 45, List.of(
                    Color.fromARGB(255, 221, 180, 246),
                    Color.fromARGB(255, 141, 208, 252)
            ));

            case EMBER -> new PieceColor(true, 6, List.of(
                    Color.fromARGB(255, 232, 157, 31),
                    Color.fromARGB(255, 228, 233, 93)
            ));

            case FIREFLY -> new PieceColor(false, 12, List.of(
                    Color.fromARGB(255, 0, 0, 0),
                    Color.fromARGB(255, 255, 255, 0)
            ));

            case ROSE -> new PieceColor(true, 22, List.of(
                    Color.fromARGB(255, 249, 171, 143),
                    Color.fromARGB(140, 244, 7, 82)
            ));

            case BANANA -> new PieceColor(true, 10, List.of(
                    Color.fromARGB(255, 251, 208, 124),
                    Color.fromARGB(255, 247, 247, 121)
            ));

            case FADING_DENIM -> new PieceColor(true, 10, List.of(
                    Color.fromARGB(255, 85, 124, 147),
                    Color.fromARGB(170, 8, 32, 62)
            ));

            case DREAM_CREAM -> new PieceColor(true, 32, List.of(
                    Color.fromARGB(255, 109, 81, 165),
                    Color.fromARGB(255, 228, 167, 197)
            ));

            case ROCK -> new PieceColor(true, 14, List.of(
                    Color.fromARGB(255, 133, 142, 150),
                    Color.fromARGB(255, 96, 105, 107),
                    Color.fromARGB(255, 188, 197, 199)
            ));

            case YET_ANOTHER_BLUE_GRADIENT -> new PieceColor(true, 23, List.of(
                    Color.fromARGB(255, 111, 227, 225),
                    Color.fromARGB(86, 82, 87, 229)
            ));

            /*
                These next few colors were compiled exclusively from hex color suggestions by Piffin viewers. I know
                having your name immortalized in code doesn't mean much, and I'll try to get your names somewhere else,
                but thank you all for providing!
                22/03/25 - Found a good spot to plop you guys!!

                Especially thank you Trap for being the only person who managed to get their hex color live during the
                24/02/25 piffin stream :D
             */

            // Noticed a pretty blue/orange pattern that could be made
            case PIFFIN_VIEWER_EXTRAVAGANZA_1 -> new PieceColor(true, 18, List.of(
                    Color.fromARGB(255, 235, 97, 35),  // From Trap
                    Color.fromARGB(255, 30, 129, 176), // From Enter Name Here
                    Color.fromARGB(255, 255, 136, 26), // From Magma (magmanugget)
                    Color.fromARGB(255, 66, 200, 245)  // From millionbucks1
            ));

            // I really like these colors :3
            case PIFFIN_VIEWER_EXTRAVAGANZA_2 -> new PieceColor(true, 18, List.of(
                    Color.fromARGB(255, 132, 37, 147),   // From Dedede (dedede2)
                    Color.fromARGB(255, 194, 120, 255),  // From d<3 (lolstoney)
                    Color.fromARGB(255, 255, 71, 169),   // From LizardLyd
                    Color.fromARGB(255, 240, 26, 97),    // From LizardLyd
                    Color.fromARGB(255, 255, 71, 169)    // From LizardLyd
            ));

            // Kinda like colors of nature or something... also contains Raven's suggestion
            case PIFFIN_VIEWER_EXTRAVAGANZA_3 -> new PieceColor(true, 18, List.of(
                    Color.fromARGB(255, 7, 70, 133),    // From ATV
                    Color.fromARGB(255, 49, 97, 36),    // From Oliver
                    Color.fromARGB(255, 255, 255, 255)  // From Raven
            ));
        };
    }
    public static PieceColorPattern getRandom() {return values()[(int) (Math.random()*values().length)];}


    private static final List<PieceColorPattern> boringColors = List.of(RED, ORANGE, YELLOW, GREEN, TEAL, LIGHT_BLUE, BLUE, PURPLE, PINK);
    public static PieceColorPattern getRandomBoring() {return boringColors.get((int) (Math.random() * boringColors.size()));}
}
