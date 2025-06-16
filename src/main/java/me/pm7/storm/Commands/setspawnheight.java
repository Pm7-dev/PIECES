package me.pm7.storm.Commands;

import me.pm7.storm.Storm;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setspawnheight implements CommandExecutor  {
    private static final Storm plugin = Storm.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You must be operator to run this command");
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Not enough arguments");
            return true;
        }

        int newSpawnHeight;
        try {
            newSpawnHeight = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "You must have a number lol");
            return true;
        }

        if(newSpawnHeight > 320) {
            sender.sendMessage(ChatColor.RED + "That is literally past the build height");
            return true;
        }

        plugin.getPieceMaker().setSpawnHeight(newSpawnHeight);
        return true;
    }
}
