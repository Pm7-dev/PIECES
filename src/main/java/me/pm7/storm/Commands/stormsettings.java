package me.pm7.storm.Commands;

import me.pm7.storm.Pieces.SpawnerDifficulty;
import me.pm7.storm.Storm;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class stormsettings implements CommandExecutor, TabExecutor {
    private static final Storm plugin = Storm.getPlugin();

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

        if(args[0].equalsIgnoreCase("get")) {
            sender.sendMessage(ChatColor.GREEN + "The current storm difficulty is " + plugin.getPieceMaker().getDifficulty());
        }

        else if(args[0].equalsIgnoreCase("set")) {
            if(args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Not enough arguments");
                return true;
            }

            int newDiff;
            try {
                newDiff = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e) {
                //sender.sendMessage(ChatColor.RED + "You must have a number lol");
                plugin.getPieceMaker().setDifficulty(SpawnerDifficulty.valueOf(args[1].toUpperCase()));
                return true;
            }

            if(newDiff >= 1 && newDiff <= 6) {
                plugin.getPieceMaker().setDifficulty(SpawnerDifficulty.fromInt(newDiff));
            }
            else {
                sender.sendMessage(ChatColor.RED + "There is no difficulty for that number.");
            }
        }
        return true;
    }
}
