package me.pm7.session6.Commands;

import me.pm7.session6.Session6;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class raisetheroof implements CommandExecutor  {
    private static final Session6 plugin = Session6.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player p)) return true;

        if(!p.isOp()) {
            sender.sendMessage(ChatColor.RED + "not 4 u >:3");
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
