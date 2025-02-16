package me.pm7.session6.Pieces.Color;

import me.pm7.session6.Pieces.Piece;
import org.bukkit.Color;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public enum ColorPattern {
    RED(new PieceColor(false, 0, false, List.of(Color.RED))),

    ELECTRIC_FLASH(new PieceColor(false, 1, false,
            Arrays.asList(
            Color.fromARGB(255, 249, 255, 66),
            Color.fromARGB(255, 66, 113, 255)
    ))),

    COTTON_CANDY(new PieceColor(true, 20, true,
            Arrays.asList(
            Color.fromARGB(255, 91, 206, 250),
            Color.fromARGB(255, 245, 169, 184)
    )))

    ;
    private final PieceColor color;
    public PieceColor getColor() {return color;}
    ColorPattern(PieceColor color) {this.color = color;}
}
