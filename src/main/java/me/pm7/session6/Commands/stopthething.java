package me.pm7.session6.Commands;

import me.pm7.session6.Session6;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class stopthething implements CommandExecutor {
    private static final Session6 plugin = Session6.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player p)) return true;

        if(sender.getName().equals("Piffin380") || !p.isOp()) {
            sender.sendMessage(ChatColor.RED + "I'm too tired to do another one");
            return true;
        }

        if(args.length == 0 || !args[0].equalsIgnoreCase("confirm")) {
            sender.sendMessage(ChatColor.RED + "Cancel funky mode!?!?! How dare you! (do confirm to continue)");
            return true;
        }

        plugin.getPieceMaker().cancelTheFunk();

        return true;
    }
}
