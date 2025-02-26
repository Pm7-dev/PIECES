package me.pm7.session6.Commands;

import me.pm7.session6.Session6;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class thatonething implements CommandExecutor {
    private static final Session6 plugin = Session6.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!plugin.getPieceMaker().isFunky()) return true;

        if(sender instanceof Player p) {
            Location soundLoc = p.getLocation().clone().add(0, 500, 0);
            p.playSound(soundLoc, "pieces:funk.faq", 99999, 1);
        }

        for(int i=0; i<12; i++) sender.sendMessage("");
        sender.sendMessage(ChatColor.YELLOW + "--------------------FUNKY MODE FAQ--------------------");
        sender.sendMessage(ChatColor.GOLD + "Q. What does Funky Mode do?");
        sender.sendMessage(ChatColor.GREEN + "    A. Funky Mode adds some cool new shapes and color gradients to the death things in the sky!");
        sender.sendMessage(ChatColor.GOLD + "");
        sender.sendMessage(ChatColor.GOLD + "Q. What else does Funky Mode do?");
        sender.sendMessage(ChatColor.GREEN + "    A. Nothing!");
        sender.sendMessage(ChatColor.GOLD + "");
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "(There seem to be no other Funky Mode FAQs)");
        return true;
    }
}
