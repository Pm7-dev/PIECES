package me.pm7.blockstorm.Commands;

import me.pm7.blockstorm.Pieces.SpawnerDifficulty;
import me.pm7.blockstorm.BlockStorm;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class stormsettings implements CommandExecutor {
    private static final BlockStorm plugin = BlockStorm.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!(sender instanceof Player p)) {
            sender.sendMessage(ChatColor.RED + "This command must be run by a player");
            return true;
        }

        if(!p.isOp()) {
            sender.sendMessage(ChatColor.RED + "You must be operator to run this command.");
            return true;
        }

        SpawnerDifficulty difficulty = plugin.getPieceMaker().getDifficulty();

        if(args.length == 2) {

            p.playSound(p, Sound.BLOCK_LEVER_CLICK, 1, 2);

            if(args[0].equals("add")) {
                switch (args[1]) {
                    case "secondsBetweenSpawns": {
                        difficulty.secondsBetweenSpawns += 1;
                        break;
                    }
                    case "spawnMultiplier": {
                        difficulty.spawnMultiplier += 0.25;
                        break;
                    }
                    case "minSize": {
                        if(difficulty.minSize == difficulty.maxSize) break;
                        difficulty.minSize += 1;
                        break;
                    }
                    case "maxSize": {
                        difficulty.maxSize += 1;
                        break;
                    }
                    case "minSpeed": {
                        if(difficulty.minSpeed == difficulty.maxSpeed) break;
                        difficulty.minSpeed += 1.0;
                        break;
                    }
                    case "maxSpeed": {
                        difficulty.maxSpeed += 1.0;
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } else if(Objects.equals(args[0], "sub")) {
                switch (args[1]) {
                    case "secondsBetweenSpawns": {
                        if(difficulty.secondsBetweenSpawns == 1) break;
                        difficulty.secondsBetweenSpawns -= 1;
                        break;
                    }
                    case "spawnMultiplier": {
                        if(difficulty.spawnMultiplier == 0.25d) break;
                        difficulty.spawnMultiplier -= 0.25;
                        break;
                    }
                    case "minSize": {
                        if(difficulty.minSize == 1) break;
                        difficulty.minSize -= 1;
                        break;
                    }
                    case "maxSize": {
                        if(difficulty.minSize == difficulty.maxSize) break;
                        difficulty.maxSize -= 1;
                        break;
                    }
                    case "minSpeed": {
                        if(difficulty.minSpeed == 1) break;
                        difficulty.minSpeed -= 1.0;
                        break;
                    }
                    case "maxSpeed": {
                        if(difficulty.minSpeed == difficulty.maxSpeed) break;
                        difficulty.maxSpeed -= 1.0;
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }

            difficulty.saveToConfig(plugin.getConfig().getConfigurationSection("difficulty"));
        }

        ComponentBuilder builder = new ComponentBuilder()
                .append("\n\n\n\n\n\n\n\n\n\n\n\n\nSTORM DIFFICULTY SETTINGS: \n")
                .color(ChatColor.GOLD.asBungee()).bold(true)

                .append("[+]")
                .color(ChatColor.GREEN.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings add secondsBetweenSpawns"))
                .append("[-]")
                .color(ChatColor.RED.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings sub secondsBetweenSpawns"))
                .append(" Seconds between spawns: ")
                .color(ChatColor.YELLOW.asBungee()).bold(false)
                .append(String.valueOf(difficulty.secondsBetweenSpawns))
                .color(ChatColor.GREEN.asBungee())
                .append("\n")

                .append("[+]")
                .color(ChatColor.GREEN.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings add spawnMultiplier"))
                .append("[-]")
                .color(ChatColor.RED.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings sub spawnMultiplier"))
                .append(" Spawn Multiplier: ")
                .color(ChatColor.YELLOW.asBungee()).bold(false)
                .append(String.valueOf(difficulty.spawnMultiplier))
                .color(ChatColor.GREEN.asBungee())
                .append("\n")

                .append("[+]")
                .color(ChatColor.GREEN.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings add minSize"))
                .append("[-]")
                .color(ChatColor.RED.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings sub minSize"))
                .append(" Minimum Piece Size: ")
                .color(ChatColor.YELLOW.asBungee()).bold(false)
                .append(String.valueOf(difficulty.minSize))
                .color(ChatColor.GREEN.asBungee())
                .append("\n")

                .append("[+]")
                .color(ChatColor.GREEN.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings add maxSize"))
                .append("[-]")
                .color(ChatColor.RED.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings sub maxSize"))
                .append(" Maximum Piece Size: ")
                .color(ChatColor.YELLOW.asBungee()).bold(false)
                .append(String.valueOf(difficulty.maxSize))
                .color(ChatColor.GREEN.asBungee())
                .append("\n")

                .append("[+]")
                .color(ChatColor.GREEN.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings add minSpeed"))
                .append("[-]")
                .color(ChatColor.RED.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings sub minSpeed"))
                .append(" Minimum Piece Speed: ")
                .color(ChatColor.YELLOW.asBungee()).bold(false)
                .append(String.valueOf(difficulty.minSpeed))
                .color(ChatColor.GREEN.asBungee())
                .append("\n")

                .append("[+]")
                .color(ChatColor.GREEN.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings add maxSpeed"))
                .append("[-]")
                .color(ChatColor.RED.asBungee()).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stormsettings sub maxSpeed"))
                .append(" Maximum Piece Speed: ")
                .color(ChatColor.YELLOW.asBungee()).bold(false)
                .append(String.valueOf(difficulty.maxSpeed))
                .color(ChatColor.GREEN.asBungee())

                .append("\n")
        ;

        p.spigot().sendMessage(builder.build());

        return true;
    }
}
