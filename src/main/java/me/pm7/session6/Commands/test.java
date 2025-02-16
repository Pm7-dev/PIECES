package me.pm7.session6.Commands;

import me.pm7.session6.Pieces.Color.ColorPattern;
import me.pm7.session6.Pieces.Piece;
import me.pm7.session6.Pieces.PieceType;
import org.bukkit.Location;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.Locale;


// TODO: /summon text_display ~-0.5 ~0.5 ~-2 {width:0f,billboard:"fixed",transformation:{left_rotation:[0f,0f,0f,1f],right_rotation:[0f,0f,0f,1f],translation:[0f,0f,0f],scale:[40f,2f,1f]},text:'{"text":"\\n"}',background:1694433280}


public class test implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        Location loc = p.getLocation().getBlock().getLocation();

        if(args.length>0) {
            new Piece(loc.getWorld(), loc.getBlockX(), loc.getBlockZ(), 20, 2, PieceType.getRandom().getModel(), ColorPattern.COTTON_CANDY.getColor());
            return true;
        }

        TextDisplay PLUS_Z = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        PLUS_Z.setBillboard(Display.Billboard.FIXED);
        PLUS_Z.setDefaultBackground(false);
        PLUS_Z.setShadowed(false);
        PLUS_Z.setSeeThrough(false);
        PLUS_Z.setAlignment(TextDisplay.TextAlignment.LEFT);
        PLUS_Z.setBrightness(new Display.Brightness(15, 15));
        PLUS_Z.setTransformation(new Transformation(new Vector3f(-10f, -10f, 10f), new AxisAngle4f(), new Vector3f(40*20, 2*20, 1), new AxisAngle4f()));
        PLUS_Z.setText("\n");
        PLUS_Z.setBackgroundColor(Color.fromARGB(255, 255, 0, 0));

        loc.setYaw(loc.getYaw() + 180);
        TextDisplay MINUS_Z = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        MINUS_Z.setBillboard(Display.Billboard.FIXED);
        MINUS_Z.setDefaultBackground(false);
        MINUS_Z.setShadowed(false);
        MINUS_Z.setSeeThrough(false);
        MINUS_Z.setAlignment(TextDisplay.TextAlignment.LEFT);
        MINUS_Z.setBrightness(new Display.Brightness(15, 15));
        MINUS_Z.setTransformation(new Transformation(new Vector3f(-10f, -10f, 10f), new AxisAngle4f(), new Vector3f(40*20, 2*20, 1), new AxisAngle4f()));
        MINUS_Z.setText("\n");
        MINUS_Z.setBackgroundColor(Color.fromARGB(255, 255, 0, 0));

        loc.setYaw(loc.getYaw() + 90);
        TextDisplay PLUS_X = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        PLUS_X.setBillboard(Display.Billboard.FIXED);
        PLUS_X.setDefaultBackground(false);
        PLUS_X.setShadowed(false);
        PLUS_X.setSeeThrough(false);
        PLUS_X.setAlignment(TextDisplay.TextAlignment.LEFT);
        PLUS_X.setBrightness(new Display.Brightness(15, 15));
        PLUS_X.setTransformation(new Transformation(new Vector3f(-10f, -10f, 10f), new AxisAngle4f(), new Vector3f(40*20, 2*20, 1), new AxisAngle4f()));
        PLUS_X.setText("\n");
        PLUS_X.setBackgroundColor(Color.fromARGB(255, 255, 0, 0));

        loc.setYaw(loc.getYaw() + 180);
        TextDisplay MINUS_X = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        MINUS_X.setBillboard(Display.Billboard.FIXED);
        MINUS_X.setDefaultBackground(false);
        MINUS_X.setShadowed(false);
        MINUS_X.setSeeThrough(false);
        MINUS_X.setAlignment(TextDisplay.TextAlignment.LEFT);
        MINUS_X.setBrightness(new Display.Brightness(15, 15));
        MINUS_X.setTransformation(new Transformation(new Vector3f(-10f, -10f, 10f), new AxisAngle4f(), new Vector3f(40*20, 2*20, 1), new AxisAngle4f()));
        MINUS_X.setText("\n");
        MINUS_X.setBackgroundColor(Color.fromARGB(255, 255, 0, 0));

        loc.setPitch(-90);
        TextDisplay TOP = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        TOP.setBillboard(Display.Billboard.FIXED);
        TOP.setDefaultBackground(false);
        TOP.setShadowed(false);
        TOP.setSeeThrough(false);
        TOP.setAlignment(TextDisplay.TextAlignment.LEFT);
        TOP.setBrightness(new Display.Brightness(15, 15));
        TOP.setTransformation(new Transformation(new Vector3f(-10f, -10f, 10f), new AxisAngle4f(), new Vector3f(40*20, 2*20, 1), new AxisAngle4f()));
        TOP.setText("\n");
        TOP.setBackgroundColor(Color.fromARGB(255, 255, 0, 0));

        loc.setPitch(90);
        TextDisplay BOTTOM = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        BOTTOM.setBillboard(Display.Billboard.FIXED);
        BOTTOM.setDefaultBackground(false);
        BOTTOM.setShadowed(false);
        BOTTOM.setSeeThrough(false);
        BOTTOM.setAlignment(TextDisplay.TextAlignment.LEFT);
        BOTTOM.setBrightness(new Display.Brightness(15, 15));
        BOTTOM.setTransformation(new Transformation(new Vector3f(-10f, -10f, 10f), new AxisAngle4f(), new Vector3f(40*20, 2*20, 1), new AxisAngle4f()));
        BOTTOM.setText("\n");
        BOTTOM.setBackgroundColor(Color.fromARGB(255, 255, 0, 0));

        loc.setPitch(0);
        loc.setYaw(0);
        outerLayer(loc);
        return true;
    }

    void outerLayer(Location loc) {
        TextDisplay PLUS_Z = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        PLUS_Z.setBillboard(Display.Billboard.FIXED);
        PLUS_Z.setDefaultBackground(false);
        PLUS_Z.setShadowed(false);
        PLUS_Z.setSeeThrough(false);
        PLUS_Z.setAlignment(TextDisplay.TextAlignment.LEFT);
        PLUS_Z.setBrightness(new Display.Brightness(15, 15));
        PLUS_Z.setTransformation(new Transformation(new Vector3f(-10.5f, -10.5f, 10.5f), new AxisAngle4f(), new Vector3f(40*21, 2*21, 1), new AxisAngle4f()));
        PLUS_Z.setText("\n");
        PLUS_Z.setBackgroundColor(Color.fromARGB(80, 255, 0, 0));

        loc.setYaw(loc.getYaw() + 180);
        TextDisplay MINUS_Z = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        MINUS_Z.setBillboard(Display.Billboard.FIXED);
        MINUS_Z.setDefaultBackground(false);
        MINUS_Z.setShadowed(false);
        MINUS_Z.setSeeThrough(false);
        MINUS_Z.setAlignment(TextDisplay.TextAlignment.LEFT);
        MINUS_Z.setBrightness(new Display.Brightness(15, 15));
        MINUS_Z.setTransformation(new Transformation(new Vector3f(-10.5f, -10.5f, 10.5f), new AxisAngle4f(), new Vector3f(40*21, 2*21, 1), new AxisAngle4f()));
        MINUS_Z.setText("\n");
        MINUS_Z.setBackgroundColor(Color.fromARGB(80, 255, 0, 0));

        loc.setYaw(loc.getYaw() + 90);
        TextDisplay PLUS_X = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        PLUS_X.setBillboard(Display.Billboard.FIXED);
        PLUS_X.setDefaultBackground(false);
        PLUS_X.setShadowed(false);
        PLUS_X.setSeeThrough(false);
        PLUS_X.setAlignment(TextDisplay.TextAlignment.LEFT);
        PLUS_X.setBrightness(new Display.Brightness(15, 15));
        PLUS_X.setTransformation(new Transformation(new Vector3f(-10.5f, -10.5f, 10.5f), new AxisAngle4f(), new Vector3f(40*21, 2*21, 1), new AxisAngle4f()));
        PLUS_X.setText("\n");
        PLUS_X.setBackgroundColor(Color.fromARGB(80, 255, 0, 0));

        loc.setYaw(loc.getYaw() + 180);
        TextDisplay MINUS_X = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        MINUS_X.setBillboard(Display.Billboard.FIXED);
        MINUS_X.setDefaultBackground(false);
        MINUS_X.setShadowed(false);
        MINUS_X.setSeeThrough(false);
        MINUS_X.setAlignment(TextDisplay.TextAlignment.LEFT);
        MINUS_X.setBrightness(new Display.Brightness(15, 15));
        MINUS_X.setTransformation(new Transformation(new Vector3f(-10.5f, -10.5f, 10.5f), new AxisAngle4f(), new Vector3f(40*21, 2*21, 1), new AxisAngle4f()));
        MINUS_X.setText("\n");
        MINUS_X.setBackgroundColor(Color.fromARGB(80, 255, 0, 0));

        loc.setPitch(-90);
        TextDisplay TOP = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        TOP.setBillboard(Display.Billboard.FIXED);
        TOP.setDefaultBackground(false);
        TOP.setShadowed(false);
        TOP.setSeeThrough(false);
        TOP.setAlignment(TextDisplay.TextAlignment.LEFT);
        TOP.setBrightness(new Display.Brightness(15, 15));
        TOP.setTransformation(new Transformation(new Vector3f(-10.5f, -10.5f, 10.5f), new AxisAngle4f(), new Vector3f(40*21, 2*21, 1), new AxisAngle4f()));
        TOP.setText("\n");
        TOP.setBackgroundColor(Color.fromARGB(80, 255, 0, 0));

        loc.setPitch(90);
        TextDisplay BOTTOM = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        BOTTOM.setBillboard(Display.Billboard.FIXED);
        BOTTOM.setDefaultBackground(false);
        BOTTOM.setShadowed(false);
        BOTTOM.setSeeThrough(false);
        BOTTOM.setAlignment(TextDisplay.TextAlignment.LEFT);
        BOTTOM.setBrightness(new Display.Brightness(15, 15));
        BOTTOM.setTransformation(new Transformation(new Vector3f(-10.5f, -10.5f, 10.5f), new AxisAngle4f(), new Vector3f(40*21, 2*21, 1), new AxisAngle4f()));
        BOTTOM.setText("\n");
        BOTTOM.setBackgroundColor(Color.fromARGB(80, 255, 0, 0));

        loc.setPitch(0);
        loc.setYaw(0);
        outerLayer2(loc);
    }

    void outerLayer2(Location loc) {
        TextDisplay PLUS_Z = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        PLUS_Z.setBillboard(Display.Billboard.FIXED);
        PLUS_Z.setDefaultBackground(false);
        PLUS_Z.setShadowed(false);
        PLUS_Z.setSeeThrough(false);
        PLUS_Z.setAlignment(TextDisplay.TextAlignment.LEFT);
        PLUS_Z.setBrightness(new Display.Brightness(15, 15));
        PLUS_Z.setTransformation(new Transformation(new Vector3f(-11.5f, -11.5f, 11.5f), new AxisAngle4f(), new Vector3f(40*23, 2*23, 1), new AxisAngle4f()));
        PLUS_Z.setText("\n");
        PLUS_Z.setBackgroundColor(Color.fromARGB(40, 255, 0, 0));

        loc.setYaw(loc.getYaw() + 180);
        TextDisplay MINUS_Z = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        MINUS_Z.setBillboard(Display.Billboard.FIXED);
        MINUS_Z.setDefaultBackground(false);
        MINUS_Z.setShadowed(false);
        MINUS_Z.setSeeThrough(false);
        MINUS_Z.setAlignment(TextDisplay.TextAlignment.LEFT);
        MINUS_Z.setBrightness(new Display.Brightness(15, 15));
        MINUS_Z.setTransformation(new Transformation(new Vector3f(-11.5f, -11.5f, 11.5f), new AxisAngle4f(), new Vector3f(40*23, 2*23, 1), new AxisAngle4f()));
        MINUS_Z.setText("\n");
        MINUS_Z.setBackgroundColor(Color.fromARGB(40, 255, 0, 0));

        loc.setYaw(loc.getYaw() + 90);
        TextDisplay PLUS_X = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        PLUS_X.setBillboard(Display.Billboard.FIXED);
        PLUS_X.setDefaultBackground(false);
        PLUS_X.setShadowed(false);
        PLUS_X.setSeeThrough(false);
        PLUS_X.setAlignment(TextDisplay.TextAlignment.LEFT);
        PLUS_X.setBrightness(new Display.Brightness(15, 15));
        PLUS_X.setTransformation(new Transformation(new Vector3f(-11.5f, -11.5f, 11.5f), new AxisAngle4f(), new Vector3f(40*23, 2*23, 1), new AxisAngle4f()));
        PLUS_X.setText("\n");
        PLUS_X.setBackgroundColor(Color.fromARGB(40, 255, 0, 0));

        loc.setYaw(loc.getYaw() + 180);
        TextDisplay MINUS_X = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        MINUS_X.setBillboard(Display.Billboard.FIXED);
        MINUS_X.setDefaultBackground(false);
        MINUS_X.setShadowed(false);
        MINUS_X.setSeeThrough(false);
        MINUS_X.setAlignment(TextDisplay.TextAlignment.LEFT);
        MINUS_X.setBrightness(new Display.Brightness(15, 15));
        MINUS_X.setTransformation(new Transformation(new Vector3f(-11.5f, -11.5f, 11.5f), new AxisAngle4f(), new Vector3f(40*23, 2*23, 1), new AxisAngle4f()));
        MINUS_X.setText("\n");
        MINUS_X.setBackgroundColor(Color.fromARGB(40, 255, 0, 0));

        loc.setPitch(-90);
        TextDisplay TOP = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        TOP.setBillboard(Display.Billboard.FIXED);
        TOP.setDefaultBackground(false);
        TOP.setShadowed(false);
        TOP.setSeeThrough(false);
        TOP.setAlignment(TextDisplay.TextAlignment.LEFT);
        TOP.setBrightness(new Display.Brightness(15, 15));
        TOP.setTransformation(new Transformation(new Vector3f(-11.5f, -11.5f, 11.5f), new AxisAngle4f(), new Vector3f(40*23, 2*23, 1), new AxisAngle4f()));
        TOP.setText("\n");
        TOP.setBackgroundColor(Color.fromARGB(40, 255, 0, 0));

        loc.setPitch(90);
        TextDisplay BOTTOM = (TextDisplay) loc.getWorld().spawnEntity(loc.clone().add(10, 10, 10), EntityType.TEXT_DISPLAY);
        BOTTOM.setBillboard(Display.Billboard.FIXED);
        BOTTOM.setDefaultBackground(false);
        BOTTOM.setShadowed(false);
        BOTTOM.setSeeThrough(false);
        BOTTOM.setAlignment(TextDisplay.TextAlignment.LEFT);
        BOTTOM.setBrightness(new Display.Brightness(15, 15));
        BOTTOM.setTransformation(new Transformation(new Vector3f(-11.5f, -11.5f, 11.5f), new AxisAngle4f(), new Vector3f(40*23, 2*23, 1), new AxisAngle4f()));
        BOTTOM.setText("\n");
        BOTTOM.setBackgroundColor(Color.fromARGB(40, 255, 0, 0));
    }
}
