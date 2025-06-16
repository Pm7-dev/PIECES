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
import java.util.Objects;

public class stormsettings implements CommandExecutor {
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

        if(args.length == 2) {
            if(args[0].equals("add")) {
                // TODO: in both of these, switch through args[1] and add/subtract using += -= on the difficulty object's data
            } else if(Objects.equals(args[0], "sub")) {

            }
        }


        //TODO: chat window stuff here & clicky events and stuff


        return true;
    }
}
