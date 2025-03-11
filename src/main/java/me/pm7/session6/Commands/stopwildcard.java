package me.pm7.session6.Commands;

import me.pm7.session6.Session6;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class stopwildcard implements CommandExecutor {
    private static final Session6 plugin = Session6.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player p)) return true;

        if(p.isOp()) {
            sender.sendMessage(ChatColor.GREEN + "Yippee!");
            plugin.getPieceMaker().stop();
            plugin.getPieceKeeper().stop();
        }
        return true;
    }
}
