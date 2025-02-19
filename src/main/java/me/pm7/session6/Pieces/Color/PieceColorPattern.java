package me.pm7.session6.Pieces.Color;

import org.bukkit.Color;

import java.lang.reflect.Array;
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

    // Cooler custom colors
    ELECTRIC_FLASH,
    COTTON_CANDY,
    UNICORN_PUKE,
    ORANGE_CREAM,
    JOKER,
    SPLATOON,
    QUANTUM_TEAL,
    FADING_PINK,
    MTN_DEW_OR_SOMETHING,
    DARKNESS_FADE,
    LIGHTNESS_STAIRS,
    LIGHTNESS_FADE,
    GLACIER,
    DARKENING_RED,
    SALMON,
    MANGO;

    public PieceColor getColor() {
        return switch (this) {
            case RED -> new PieceColor(false, 0, false, List.of(Color.fromRGB(255, 81, 81)));
            case ORANGE -> new PieceColor(false, 0, false, List.of(Color.fromRGB(255, 164, 81)));
            case YELLOW -> new PieceColor(false, 0, false, List.of(Color.fromRGB(255, 255, 81)));
            case GREEN -> new PieceColor(false, 0, false, List.of(Color.fromRGB(81, 255, 81)));
            case TEAL -> new PieceColor(false, 0, false, List.of(Color.fromRGB(81, 255, 172)));
            case LIGHT_BLUE -> new PieceColor(false, 0, false, List.of(Color.fromRGB(81, 216, 255)));
            case BLUE -> new PieceColor(false, 0, false, List.of(Color.fromRGB(81, 85, 255)));
            case PURPLE -> new PieceColor(false, 0, false, List.of(Color.fromRGB(164, 81, 255)));
            case PINK -> new PieceColor(false, 0, false, List.of(Color.fromRGB(255, 81, 238)));

            case ELECTRIC_FLASH -> new PieceColor(false, 3, false, Arrays.asList(
                    Color.fromARGB(255, 251, 255, 79),
                    Color.fromARGB(255, 58, 112, 237)
            ));

            case COTTON_CANDY -> new PieceColor(true, 30, true, Arrays.asList(
                    Color.fromARGB(255, 91, 206, 250),
                    Color.fromARGB(255, 245, 169, 184)
            ));

            case UNICORN_PUKE -> new PieceColor(true, 20, false, Arrays.asList(
                    Color.fromARGB(255, 255, 66, 66),
                    Color.fromARGB(255, 66, 255, 66),
                    Color.fromARGB(255, 66, 255, 255),
                    Color.fromARGB(255, 66, 66, 255),
                    Color.fromARGB(255, 255, 66, 255)
            ));

            case ORANGE_CREAM -> new PieceColor(true, 45, true, Arrays.asList(
                    Color.fromARGB(255, 255, 217, 90),
                    Color.fromARGB(255, 255, 123, 60)
            ));

            case JOKER -> new PieceColor(true, 10, false, Arrays.asList(
                    Color.fromARGB(255, 91, 223, 77),
                    Color.fromARGB(255, 91, 223, 77),
                    Color.fromARGB(255, 129, 80, 198),
                    Color.fromARGB(255, 129, 80, 198)
            ));

            case SPLATOON -> new PieceColor(true, 8, true, Arrays.asList(
                    Color.fromARGB(255, 187, 242, 72),
                    Color.fromARGB(255, 247, 125, 243)
            ));

            case QUANTUM_TEAL -> new PieceColor(false, 3, false, Arrays.asList(
                    Color.fromARGB(255, 25, 255, 141),
                    Color.fromARGB(0, 0, 0, 0),
                    Color.fromARGB(0, 0, 0, 0)//,
                    //Color.fromARGB(0, 0, 0, 0)
            ));

            case FADING_PINK -> new PieceColor(true, 15, true, Arrays.asList(
                    Color.fromARGB(255, 237, 97, 237),
                    Color.fromARGB(80, 237, 97, 237)
            ));

            case MTN_DEW_OR_SOMETHING -> new PieceColor(true, 22, true, Arrays.asList(
                    Color.fromARGB(255, 255, 243, 71),
                    Color.fromARGB(255, 16, 248, 242)
            ));

            case DARKNESS_FADE -> new PieceColor(true, 40, true, Arrays.asList(
                    Color.fromARGB(255, 74, 74, 255),
                    Color.fromARGB(255, 0, 0, 0),
                    Color.fromARGB(255, 74, 255, 74),
                    Color.fromARGB(255, 0, 0, 0)
            ));

            case LIGHTNESS_STAIRS -> new PieceColor(false, 4, false, Arrays.asList(
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

            case LIGHTNESS_FADE -> new PieceColor(true, 5, false, Arrays.asList(
                    Color.fromARGB(255, 255, 255, 255),
                    Color.fromARGB(128, 255, 255, 255),
                    Color.fromARGB(0, 255, 255, 255),
                    Color.fromARGB(0, 0, 0, 0),
                    Color.fromARGB(128, 0, 0, 0),
                    Color.fromARGB(255, 0, 0, 0),
                    Color.fromARGB(128, 0, 0, 0),
                    Color.fromARGB(0, 0, 0, 0),
                    Color.fromARGB(0, 255, 255, 255),
                    Color.fromARGB(128, 255, 255, 255)
            ));

            case GLACIER -> new PieceColor(true, 20, true, Arrays.asList(
                    Color.fromARGB(255, 187, 198, 236),
                    Color.fromARGB(255, 94, 241, 242)
            ));


            case DARKENING_RED -> new PieceColor(true, 80, true, Arrays.asList(
                    Color.fromARGB(255, 106, 23, 23),
                    Color.fromARGB(255, 238, 72, 72)
            ));

            case SALMON -> new PieceColor(true, 24, true, Arrays.asList(
                    Color.fromARGB(255, 255, 65, 85),
                    Color.fromARGB(255, 222, 254, 255)
            ));

            case MANGO -> new PieceColor(true, 18, true, Arrays.asList(
                    Color.fromARGB(255, 97, 206, 37),
                    Color.fromARGB(255, 255, 247, 66),
                    Color.fromARGB(255, 251, 76, 95),
                    Color.fromARGB(255, 255, 247, 66)
            ));
        };
    }
    public static PieceColorPattern getRandom() {return values()[(int) (Math.random()*values().length)];}
}
