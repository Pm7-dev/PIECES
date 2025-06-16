package me.pm7.session6.Friend;

import me.pm7.session6.Session6;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class fren implements CommandExecutor {
    private static final Session6 plugin = Session6.getPlugin();
    private final List<UUID> alreadyGotOne = new ArrayList<>();
    private boolean enabled = false;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length > 0) {
            if(enabled) {
                if(sender instanceof Player p) {
                    if(alreadyGotOne.contains(p.getUniqueId())) return true;
                    alreadyGotOne.add(p.getUniqueId());

                    p.sendMessage(ChatColor.GREEN + "A Friend will soon be deployed. Please be gentle with it.");

                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        new Friend(p);
                    }, 60L);
                }
            }
        } else {
            if(sender.isOp()) {
                enabled = true;
                ComponentBuilder b = new ComponentBuilder()
                        .append("\n")
                        .append("[Friend Distributor]: You seem lonely. Would you like a Friend for FREE?\n")
                        .color(net.md_5.bungee.api.ChatColor.YELLOW)
                        .append("YES PLz GIvE ME Friend!!1!")
                        .color(ChatColor.GREEN)
                        .bold(true)
                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fren 1"))
                        .append("\n");
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(b.build());
                    p.playSound(p.getLocation().clone().add(0, 2, 0), "pieces:storm_watch_notif", SoundCategory.RECORDS, 1, 1);
                }
            }
        }
        return true;
    }
}
