package me.pm7.session6.Commands;

import me.pm7.session6.Session6;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class startwildcard extends BukkitRunnable implements CommandExecutor {
    private static final Session6 plugin = Session6.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player p)) return true;

        if(p.isOp()) {
            delay = 20;
            this.runTaskTimer(plugin, 0L, 1L);
        } else {
            sender.sendMessage(ChatColor.RED + "I'm too tired to do another one");
        }
        return true;
    }

    private int titleNumber = 0;
    private int delay = 0;
    private int ticks = 0;
    @Override
    public void run() {

        if(ticks < delay) {
            ticks++;
            return;
        }
        ticks = 0;

        if (titleNumber >= 10) { // Stop the runnable when we finish all actions

            // Actually start the thing
            plugin.getPieceMaker().start();
            plugin.start(); // used for invincibility bits

            this.cancel();
            return;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location soundLoc = player.getLocation().clone().add(0, 500, 0);
            switch (titleNumber) {
                case 0 -> {
                    player.sendTitle("§a.", "", 0, 100, 0);
                    player.playSound(soundLoc, Sound.ITEM_LODESTONE_COMPASS_LOCK, 99999f, 1f);
                }
                case 1 -> {
                    player.sendTitle("§a. §e.", "", 0, 100, 0);
                    player.playSound(soundLoc, Sound.ITEM_LODESTONE_COMPASS_LOCK, 99999f, 1f);
                }
                case 2 -> {
                    player.sendTitle("§a. §e. §c.", "", 0, 100, 0);
                    player.playSound(soundLoc, Sound.ITEM_LODESTONE_COMPASS_LOCK, 99999f, 1f);
                    delay = 25;
                }
                case 3 -> {
                    player.sendTitle("§6§kA wildcard is active!", "", 0, 50, 20);
                    player.playSound(soundLoc, Sound.BLOCK_TRIAL_SPAWNER_OMINOUS_ACTIVATE, 99999f, 1f);
                    player.playSound(soundLoc, Sound.BLOCK_VAULT_OPEN_SHUTTER, 99999f, 1f);
                    player.playSound(soundLoc, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 99999f, 1f);
                    delay = 8;
                }
                case 4 -> player.sendTitle("§6§k§lA wi§r§6§ll§r§6§l§kdcard is §r§6§la§r§6§l§kctive!", "", 0, 50, 20);
                case 5 -> player.sendTitle("§6§k§lA wi§r§6§ll§r§6§l§kdca§r§6§lr§r§6§l§kd is §r§6§la§r§6§l§kcti§r§6§lv§r§6§l§ke!", "", 0, 50, 20);
                case 6 -> player.sendTitle("§6§k§lA wi§r§6§ll§r§6§l§kd§r§6§lc§r§6§l§ka§r§6§lrd§r§6§l§k is §r§6§la§r§6§l§kcti§r§6§lve§r§6§l§k!", "", 0, 50, 20);
                case 7 -> player.sendTitle("§6§k§lA§r§6§l w§r§6§l§ki§r§6§ll§r§6§l§kd§r§6§lc§r§6§l§ka§r§6§lrd§r§6§l§k i§r§6§ls a§r§6§l§kcti§r§6§lve!", "", 0, 50, 20);
                case 8 -> player.sendTitle("§6§lA wil§r§6§l§kd§r§6§lc§r§6§l§ka§r§6§lrd is ac§r§6§l§kti§r§6§lve!", "", 0, 50, 20);
                case 9 -> player.sendTitle("§6§lA wildcard is active!", "", 0, 50, 20);
            }
        }

        titleNumber++;
    }
}
