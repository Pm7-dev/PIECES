package me.pm7.session6.Granite;

import me.pm7.session6.Session6;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class volcanicroc implements CommandExecutor {
    private static final Session6 plugin = Session6.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender.isOp()) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.YELLOW + "\n[Rock Collector]: I have cursed you all with good looks >;)\n");
                p.playSound(p.getLocation().clone().add(0, 2, 0), "pieces:storm_watch_notif", SoundCategory.RECORDS, 1, 1);
                new BlockPlayer(p);
            }

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if(BlockPlayer.blockPlayers.isEmpty()) return;
                for(int i=0; i<BlockPlayer.blockPlayers.size(); i++) {
                    BlockPlayer.blockPlayers.get(i).kill();
                    i--;
                }
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.YELLOW + "\n[Cleanup Crew]: We figured out how to remove the rock guy's curse. Carry on\n");
                    p.playSound(p.getLocation().clone().add(0, 2, 0), "pieces:storm_watch_notif", SoundCategory.RECORDS, 1, 1);
                }
            }, 3700L);
        }
        return true;
    }
}
