package me.pm7.session6.Pieces.Color;

import org.bukkit.Color;

import java.util.List;

public class PieceColor {
    private Color currentColor;
    private final List<Color> colorSequence;
    private final boolean doFading;
    private final int fadeTicks;

    public PieceColor(boolean doFading, int fadeTicks, List<Color> colorSequence) {
        this.currentColor = colorSequence.getFirst();
        this.colorSequence = colorSequence;
        this.doFading = doFading;
        this.fadeTicks = fadeTicks;
    }

    private int tick=0;
    private int index = 0;
    public void tick() {
        if(colorSequence.size() == 1) return;
        tick++;
        if(tick>=fadeTicks * 1.1) { // x1.1 to hold the color for a *small* amount of time
            tick=0;
            index++;
            if(index>=colorSequence.size()) index = 0;
            currentColor = colorSequence.get(index);
        }
    }

    public Color getColor() {return currentColor;}
    public boolean doesFading() {return doFading;}
    public int getFadeTicks() {return fadeTicks;}
}
