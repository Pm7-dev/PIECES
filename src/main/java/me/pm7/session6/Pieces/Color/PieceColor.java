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

            final float fraction = (float)tick/(float)fadeTicks;
            final int interpolatedAlpha = interpolate(baseColor.getAlpha(), finalColor.getAlpha(), fraction, fadeEasing);
            final int interpolatedRed = interpolate(baseColor.getRed(), finalColor.getRed(), fraction, fadeEasing);
            final int interpolatedGreen = interpolate(baseColor.getGreen(), finalColor.getGreen(), fraction, fadeEasing);
            final int interpolatedBlue = interpolate(baseColor.getBlue(), finalColor.getBlue(), fraction, fadeEasing);

            currentColor = Color.fromARGB(interpolatedAlpha, interpolatedRed, interpolatedGreen, interpolatedBlue);
        }
    }

    public int interpolate(double v1, double v2, float fraction, boolean smooth) {
        if(smooth) return (int) (v1+(v2-v1)*(-(Math.cos(Math.PI*fraction)-1)/2)); // sinusoidal interpolation
        else return (int) (v1+(v2-v1)*fraction); // linear interpolation
    }

    public Color getColor() {return currentColor;}
    public boolean doesFading() {return doFading;}
}
