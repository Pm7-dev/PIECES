package me.pm7.session6.Commands;

import me.pm7.session6.Session6;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class stormdifficulty implements CommandExecutor, TabExecutor {
    private static final Session6 plugin = Session6.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player p)) return true;

        if(!p.isOp()) {
            sender.sendMessage(ChatColor.RED + "not 4 u >:3");
        }

        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Not enough arguments");
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
                sender.sendMessage(ChatColor.RED + "You must have a number lol");
                return true;
            }

            if(newDiff >= 0 && newDiff <= 5) {
                plugin.getPieceMaker().setDifficulty(newDiff);
                return true;
            }
            else if (newDiff == 6) {
                if(args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Death. Are you prepared? add 'confirm' for yes.");
                    return true;
                }

                if(args[2].equalsIgnoreCase("confirm")) {
                    sender.sendMessage(ChatColor.RED + "Death.");
                    plugin.getPieceMaker().setDifficulty(newDiff);
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "There is no difficulty for that number.");
                return true;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return getTabComplete(List.of("get", "set"), args[0]);
    }

    List<String> getTabComplete(List<String> list, String arg) {
        if(arg.isEmpty()) { return list; }
        arg = arg.toLowerCase();

        List<String> availableArgs = new ArrayList<>();
        List<String> argsThatStartWithTerm = new ArrayList<>();
        List<String> argsThatContainTerm = new ArrayList<>();
        for(String setting : list) {
            if(setting.toLowerCase().startsWith(arg)) { argsThatStartWithTerm.add(setting); }
            else if(setting.toLowerCase().contains(arg)) { argsThatContainTerm.add(setting); }
        }

        availableArgs.addAll(argsThatStartWithTerm);
        availableArgs.addAll(argsThatContainTerm);
        return availableArgs;
    }
}
