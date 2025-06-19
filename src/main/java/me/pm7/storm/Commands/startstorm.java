package me.pm7.storm.Commands;

import me.pm7.storm.Storm;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class startstorm implements CommandExecutor {
    private static final Storm plugin = Storm.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if(sender.isOp()) {
            if(plugin.isStarted()) {
                sender.sendMessage(ChatColor.RED + "The storm is already running");
                return true;
            }

            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.RED + "The Storm is starting!");
                p.playSound(p.getLocation().clone().add(0, 2, 0), "pieces:storm_watch_notif", SoundCategory.RECORDS, 1, 1);
            }
            plugin.getPieceMaker().start();
            plugin.getPieceKeeper().start();
            plugin.start(); // used for invincibility bits
        } else {
            sender.sendMessage(ChatColor.RED + "You must be operator to run this command.");
        }
        return true;
    }
}
