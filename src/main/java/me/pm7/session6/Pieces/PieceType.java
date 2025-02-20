package me.pm7.session6.Pieces;

// Piece names are modified from this random page of the blokus pieces I found lol https://blokusstrategy.com/piece-names/

/*
    Each "true" represents a block being placed in that position. I wish I could just have used 1/0 because it would be
    more readable, but eh, whatever. It's not like this has to be read by a human outside of the acual piece modeling
    process.
 */

import org.bukkit.block.structure.Mirror;

public enum PieceType {
    PLUS_5(new boolean[][]{
            {false, true, false},
            {true,  true,  true},
            {false, true, false}
    }),

    X_5(new boolean[][]{
            { true, false,  true},
            {false,  true, false},
            { true, false,  true},
    }),

    I_4(new boolean[][]{
            {false, false, false, false},
            { true,  true,  true,  true},
            {false, false, false, false},
            {false, false, false, false}
    }),

    N_6(new boolean[][]{
            {false, false, false, false},
            {false,  true,  true,  true},
            {false,  true, false, false},
            { true,  true, false, false}
    }),

    Z_4(new boolean[][]{
            {false, false, false},
            {false,  true,  true},
            { true,  true, false}
    }),

    T_5(new boolean[][]{
            {false,  true, false},
            {false,  true, false},
            { true,  true,  true}
    }),

    V_5(new boolean[][]{
            { true,  true,  true},
            { true, false, false},
            { true, false, false}
    }),

    P_5(new boolean[][] {
            { true,  true, false},
            { true,  true, false},
            { true, false, false}
    }),

    W_5(new boolean[][] {
            { true, false, false},
            { true,  true, false},
            {false,  true,  true}
    }),

    F_5(new boolean[][] { // who looked at this and went "yeah that resembles an F"
            {false,  true, false},
            { true,  true,  true},
            { true, false, false}
    }),

    U_5(new boolean[][] {
            { true,  true,  true},
            { true, false,  true},
            {false, false, false}
    }),

    Y_5(new boolean[][]{ // Not really the Y type block, needed to make it different from L
            {false, false,  true, false},
            { true,  true,  true,  true},
            {false,  true, false, false},
            {false, false, false, false}
    }),

    S_4(new boolean[][]{
            {false, false,  true, false},
            {false,  true, false, false},
            {false, false,  true, false},
            {false,  true, false, false}
    }),

    L_5(new boolean[][]{
            {false, false, false, false},
            { true,  true,  true,  true},
            { true, false, false, false},
            {false, false, false, false}
    }),

    O_4(new boolean[][] {
            {true, true},
            {true, true}
    }),

    O_18(new boolean[][] {
            { true,  true,  true,  true,  true},
            { true, false, false, false,  true},
            { true, false,  true, false,  true},
            { true, false, false, false,  true},
            { true,  true,  true,  true,  true}
    }),

    CHECKER(new boolean[][] {
            {false, true, false, true, false},
            {false, false, true, false, false},
            {false, true, false, true, false},
            {false, false, true, false, false},
            {false, true, false, true, false}
    }),

    A_10(new boolean[][] {
            {true, true, true, true},
            {true, false, true, false},
            {true, true, true, true},
            {false, false, false, false}
    }),

    COLON_3(new boolean[][] {
            {false, false, false, true, true},
            {false, true, false, false, true},
            {false, false, false, true, false},
            {false, true, false, false, true},
            {false, false, false, true, true}
    }),

    I_9(new boolean[][]{
            {false, true, true, true, false},
            {false, false, true, false, false},
            {false, false, true, false, false},
            {false, false, true, false, false},
            {false, true, true, true, false}
    }),

    LUKIN(new boolean[][] {
            {false, true, true, true, false},
            {true, false, true, false, true},
            {false, true, true, true, false},
            {false, false, false, false, false},
            {false, false, false, false, false}

    }),

//    PIFFIN(new boolean[][] {
//            {true, true, false, true, true},
//            {true, false, false, false, true},
//            {false, true, false, true, false},
//            {true, false, false, false, true},
//            {true, true, false, true, true}
//    }),

    DOGBONE(new boolean[][] {
            {false, true, false, false, false},
            {true, true, false, false, false},
            {false, false, true, false, false},
            {false, false, false, true, true},
            {false, false, false, true, false},
    }),

    KO(new boolean[][] {
            {false, false, false, true},
            {true, false, true, false},
            {false, true, true, true},
            {true, true, true, false},
    }),

    SMILE(new boolean[][] {
            {false, true, false, true, false},
            {false, true, false, true, false},
            {false, false, false, false, false},
            {true, false, false, false, true},
            {false, true, true, true, false},
    }),

    DOUBLE_SQUARE(new boolean[][] {
            {true, true, false},
            {true, true, true},
            {false, true, true}
    }),

    ARROW(new boolean[][] {
            {false, false, false, true},
            {true, false, true, false},
            {true, true, false, false},
            {true, true, true, false},
    }),

    D_6(new boolean[][] {
            {false, true, true},
            {true, false, true},
            {false, true, true},
    }),

    CIRCLE(new boolean[][] { // heavy quotes
            {false, true, true, false},
            {true, true, true, true},
            {true, true, true, true},
            {false, true, true, false},
    }),

    DERP(new boolean[][] {
            {false, false, false, false},
            {true, false, false, true},
            {false, true, true, false},
            {true, true, true ,true}
    })


    ;PieceType(boolean[][] model) {this.model = model;}

    private final boolean[][] model;
    public boolean[][] getModel() {return this.model;}

    public static PieceType getRandom() {return values()[(int) (Math.random() * values().length)];}

    // holy inefficient rotation method!
    public static boolean[][] rotateModel(boolean[][] model, int times90) {
        times90%=4;
        if(times90==0) return model;

        int size = model.length;
        boolean[][] rotated = new boolean[size][size];
        for (int x=0;x<size;x++) {for (int y=0;y<size;y++) {
            rotated[x][-y+size-1]=model[y][x];
        }}

        if(times90==1) return rotated;
        else return rotateModel(rotated, times90-1);
    }

    public static boolean[][] mirrorModel(boolean[][] model, Mirror mirror) {
        int size = model.length;
        return switch (mirror) {
            case NONE: {yield model;}
            case FRONT_BACK: {
                boolean[][] mirrored = new boolean[size][size];
                for (int x=0;x<size;x++) {for (int y=0;y<size;y++) {
                    mirrored[-y+size-1][x]=model[y][x];
                }}
                yield mirrored;
            }
            case LEFT_RIGHT: {
                boolean[][] mirrored = new boolean[size][size];
                for (int x=0;x<size;x++) {for (int y=0;y<size;y++) {
                    mirrored[y][-x+size-1]=model[y][x];
                }}
                yield mirrored;
            }
        };
    }
}
