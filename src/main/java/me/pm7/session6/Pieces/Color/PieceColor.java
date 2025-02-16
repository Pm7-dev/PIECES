package me.pm7.session6.Pieces.Color;

import org.bukkit.Color;

import java.util.List;

public class PieceColor {
    private Color currentColor;
    private final List<Color> colorSequence;
    private final boolean doFading;
    private final int fadeTicks;
    private final boolean fadeEasing;

    public PieceColor(boolean doFading, int fadeTicks, boolean fadeEasing, List<Color> colorSequence) {
        this.currentColor = colorSequence.getFirst();
        this.colorSequence = colorSequence;
        this.doFading = doFading;
        this.fadeTicks = fadeTicks;
        this.fadeEasing = fadeEasing;
    }

    private int tick=0;
    private int index = 0;
    public void tick() {
        if(colorSequence.size() == 1) return;

        tick++;
        if(tick>=fadeTicks) {
            tick=0;
            index++;
            if(index>=colorSequence.size()) index = 0;
            currentColor = colorSequence.get(index);
        } else {
            if(!doFading) return;

            final Color baseColor = colorSequence.get(index);
            final Color finalColor;
            if(index + 1 >= colorSequence.size()) finalColor = colorSequence.getFirst();
            else finalColor = colorSequence.get(index + 1);

            final int interpolatedAlpha;
            final int interpolatedRed;
            final int interpolatedGreen;
            final int interpolatedBlue;

            if(fadeEasing) {
                interpolatedAlpha = (int) (baseColor.getAlpha() + (finalColor.getAlpha()-baseColor.getAlpha()) * (-(Math.cos(Math.PI * ((double) tick/fadeTicks)) - 1) / 2));
                interpolatedRed = (int) (baseColor.getRed() + (finalColor.getRed()-baseColor.getRed()) * (-(Math.cos(Math.PI * ((double) tick/fadeTicks)) - 1) / 2));
                interpolatedGreen = (int) (baseColor.getGreen() + (finalColor.getGreen()-baseColor.getGreen()) * (-(Math.cos(Math.PI * ((double) tick/fadeTicks)) - 1) / 2));
                interpolatedBlue = (int) (baseColor.getBlue() + (finalColor.getBlue()-baseColor.getBlue()) * (-(Math.cos(Math.PI * ((double) tick/fadeTicks)) - 1) / 2));
            } else {
                interpolatedAlpha = baseColor.getAlpha() + (finalColor.getAlpha()-baseColor.getAlpha() * (tick/fadeTicks)); // Might be some truncating errors here
                interpolatedRed = baseColor.getRed() + (finalColor.getRed()-baseColor.getRed() * (tick/fadeTicks));
                interpolatedGreen = baseColor.getGreen() + (finalColor.getGreen()-baseColor.getGreen() * (tick/fadeTicks));
                interpolatedBlue = baseColor.getBlue() + (finalColor.getBlue()-baseColor.getBlue() * (tick/fadeTicks));
            }
            currentColor = Color.fromARGB(interpolatedAlpha, interpolatedRed, interpolatedGreen, interpolatedBlue);
        }
    }
    public Color getColor() {return currentColor;}
    public boolean doesFading() {return doFading;}
}
