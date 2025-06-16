package me.pm7.session6.LargeFernMinecart;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class fernace implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender.isOp()) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "\n[Mad Botanist]: I've been planting the seeds of vengeance, and it will soon be your turn to reap what you've sown. I've given them freedom, and they've deemed you all UNWORTHY of nature.\n");
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation().clone().add(0, 2, 0), "pieces:storm_watch_notif", SoundCategory.RECORDS, 1, 1);
            }

            Chunk[] chunks = Bukkit.getWorlds().getFirst().getLoadedChunks();
            int size = chunks.length;
            for(int i=0; i<50; i++) {
                Chunk c = chunks[(int) (Math.random() * size)];
                new Pack(c.getBlock(8, 0, 8).getLocation(), (int) ((Math.random() * 15) + 15));
            }


        }
        return true;
    }
}
