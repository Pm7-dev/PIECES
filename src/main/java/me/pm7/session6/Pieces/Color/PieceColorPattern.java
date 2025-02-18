package me.pm7.session6.Pieces.Color;

import org.bukkit.Color;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public enum PieceColorPattern {

    // Boring solid colors
    RED(new PieceColor(false, 0, false, List.of(Color.fromRGB(255, 81, 81)))),
    ORANGE(new PieceColor(false, 0, false, List.of(Color.fromRGB(255, 164, 81)))),
    YELLOW(new PieceColor(false, 0, false, List.of(Color.fromRGB(255, 255, 81)))),
    GREEN(new PieceColor(false, 0, false, List.of(Color.fromRGB(81, 255, 81)))),
    TEAL(new PieceColor(false, 0, false, List.of(Color.fromRGB(81, 255, 172)))),
    LIGHT_BLUE(new PieceColor(false, 0, false, List.of(Color.fromRGB(81, 216, 255)))),
    BLUE(new PieceColor(false, 0, false, List.of(Color.fromRGB(81, 85, 255)))),
    PURPLE(new PieceColor(false, 0, false, List.of(Color.fromRGB(164, 81, 255)))),
    PINK(new PieceColor(false, 0, false, List.of(Color.fromRGB(255, 81, 238)))),

    // I love these kinds of effects, but it's probably good to *not* obliterate any photosensitive people
//    ELECTRIC_FLASH(new PieceColor(false, 1, false,
//            Arrays.asList(
//            Color.fromARGB(255, 249, 255, 66),
//            Color.fromARGB(255, 66, 113, 255)
//    ))),

    ELECTRIC_FLASH(new PieceColor(false, 4, false,
            Arrays.asList(
            Color.fromARGB(255, 251, 255, 79),
            Color.fromARGB(255, 58, 112, 237)
    ))),

    COTTON_CANDY(new PieceColor(true, 30, true, Arrays.asList(
            Color.fromARGB(255, 91, 206, 250),
            Color.fromARGB(255, 245, 169, 184)
    ))),

    UNICORN_PUKE(new PieceColor(true, 20, false, Arrays.asList(
            Color.fromARGB(255, 255, 66, 66),
            Color.fromARGB(255, 66, 255, 66),
            Color.fromARGB(255, 66, 255, 255),
            Color.fromARGB(255, 66, 66, 255),
            Color.fromARGB(255, 255, 66, 255)
    ))),

    ORANGE_CREAM(new PieceColor(true, 10, true, Arrays.asList(
            Color.fromARGB(255, 255, 217, 90),
            Color.fromARGB(255, 255, 123, 60)
    ))),

    JOKER(new PieceColor(true, 10, true, Arrays.asList(
            Color.fromARGB(255, 67, 249, 47),
            Color.fromARGB(255, 67, 249, 47),
            Color.fromARGB(255, 67, 249, 47),
            Color.fromARGB(255, 139, 73, 199),
            Color.fromARGB(255, 139, 73, 199),
            Color.fromARGB(255, 139, 73, 199)
    ))),

    SPLATOON(new PieceColor(true, 8, true, Arrays.asList(
            Color.fromARGB(255, 187, 242, 72),
            Color.fromARGB(255, 247, 125, 243)
    ))),

    QUANTUM_TEAL(new PieceColor(false, 2, false, Arrays.asList(
            Color.fromARGB(255, 25, 255, 141),
            Color.fromARGB(0, 0, 0, 0),
            Color.fromARGB(0, 0, 0, 0)//,
            //Color.fromARGB(0, 0, 0, 0)
    ))),

    FADING_PINK(new PieceColor(true, 15, true, Arrays.asList(
            Color.fromARGB(255, 237, 97, 237),
            Color.fromARGB(0, 237, 97, 237)
    ))),

    MTN_DEW_OR_SOMETHING(new PieceColor(true, 22, true, Arrays.asList(
            Color.fromARGB(255, 255, 243, 71),
            Color.fromARGB(255, 16, 248, 242)
    ))),

    DARKNESS_FADE(new PieceColor(true, 40, true, Arrays.asList(
            Color.fromARGB(255, 74, 74, 255),
            Color.fromARGB(255, 0, 0, 0),
            Color.fromARGB(255, 74, 255, 74),
            Color.fromARGB(255, 0, 0, 0)
    ))),

    LIGHTNESS_STAIRS(new PieceColor(false, 5, false, Arrays.asList(
            Color.fromARGB(255, 255, 255, 255),
            Color.fromARGB(255, 224, 224, 224),
            Color.fromARGB(255, 192, 192, 192),
            Color.fromARGB(255, 160, 160, 160),
            Color.fromARGB(255, 128, 128, 128),
            Color.fromARGB(255, 96, 96, 96),
            Color.fromARGB(255, 64, 64, 64),
            Color.fromARGB(255, 32, 32, 32),
            Color.fromARGB(255, 0, 0, 0)
    ))),

    LIGHTNESS_FADE(new PieceColor(true, 7, true, Arrays.asList(
            Color.fromARGB(255, 255, 255, 255),
            Color.fromARGB(128, 255, 255, 255),
            Color.fromARGB(0, 255, 255, 255),
            Color.fromARGB(0, 0, 0, 0),
            Color.fromARGB(128, 0, 0, 0),
            Color.fromARGB(255, 0, 0, 0),
            Color.fromARGB(128, 0, 0, 0),
            Color.fromARGB(0, 0, 0, 0),
            Color.fromARGB(128, 255, 255, 255)
    ))),

    SNOWY_TUNDRA(new PieceColor(true, 20, true, Arrays.asList(
            Color.fromARGB(255, 211, 211, 211),
            Color.fromARGB(255, 126, 223, 231)
    ))),

    GHOST_GREEN(new PieceColor(false, 0, false, Arrays.asList(
            Color.fromARGB(100, 51, 102, 43)
    ))),

    DARKENING_RED(new PieceColor(true, 80, true, Arrays.asList(
            Color.fromARGB(255, 106, 23, 23),
            Color.fromARGB(255, 238, 72, 72)
    )))

    ;
    private final PieceColor color;
    public PieceColor getColor() {return color;}
    PieceColorPattern(PieceColor color) {this.color = color;}
    public static PieceColorPattern getRandom() {return values()[(int) (Math.random()*values().length)];}
}
