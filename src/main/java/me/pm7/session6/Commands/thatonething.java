package me.pm7.session6.Commands;

import me.pm7.session6.Session6;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

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
        //sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "(There seem to be no other Funky Mode FAQs)");


        // Alphabetical order to hide my color bias hehehe
        ComponentBuilder builder = new ComponentBuilder()
                .append("People who unknowingly contributed colors to funky mode: ").color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("ATV").color(net.md_5.bungee.api.ChatColor.of(new Color(7, 70, 133)))
                .append(", ").color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("d<3").color(net.md_5.bungee.api.ChatColor.of(new Color(194, 120, 255)))
                .append(", ").color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("Dedede").color(net.md_5.bungee.api.ChatColor.of(new Color(132, 37, 147)))
                .append(", ").color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("Enter Name Here").color(net.md_5.bungee.api.ChatColor.of(new Color(30, 129, 176)))
                .append(", ").color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("LizardLyd").color(net.md_5.bungee.api.ChatColor.of(new Color(255, 71, 169)))
                .append(", ").color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("Magma").color(net.md_5.bungee.api.ChatColor.of(new Color(255, 136, 26)))
                .append(", ").color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("MillionBucks1").color(net.md_5.bungee.api.ChatColor.of(new Color(66, 200, 245)))
                .append(", ").color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("Oliver").color(net.md_5.bungee.api.ChatColor.of(new Color(49, 97, 36)))
                .append(", ").color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("Raven").color(net.md_5.bungee.api.ChatColor.of(new Color(255, 255, 255)))
                .append(", and ").color(net.md_5.bungee.api.ChatColor.GRAY)
                .append("Trap").color(net.md_5.bungee.api.ChatColor.of(new Color(235, 97, 35)))
                ;


        sender.spigot().sendMessage(builder.build());
        return true;
    }
}
