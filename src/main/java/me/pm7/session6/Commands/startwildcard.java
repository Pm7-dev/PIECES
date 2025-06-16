package me.pm7.session6.Commands;

import me.pm7.session6.Session6;
import me.pm7.session6.Tangerines.Dispensary;
import me.pm7.session6.Tangerines.HitListener;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class startwildcard extends BukkitRunnable implements CommandExecutor {
    private static final Session6 plugin = Session6.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player p)) return true;

        if(p.isOp()) {
            delay = 20;
            HitListener.enabled = false;
            this.runTaskTimer(plugin, 0L, 1L);
        } else {
            sender.sendMessage(ChatColor.RED + "I'm too tired to do another one");
        }
        return true;
    }

    private boolean tangerinesActive = false;

    private int titleNumber = 0;
    private int delay = 0;
    private int ticks = 0;
    private int totalTicks = 47;
    private boolean running = true;
    @Override
    public void run() {
        if(!running) return;

        //totalTicks+=(double) size /2;
        totalTicks += size/2;

        if(ticks < delay) {
            ticks++;

            switch (titleNumber) {
                case 4 -> {
                    size = 7;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if(!tangerinesActive) player.sendTitle(addMagic("A wildcard is active!", 0), "", 0, 50, 20);
                        else player.sendTitle(addMagic("A solution is applied", 0), "", 0, 50, 20);
                    }
                }
                case 5 -> {
                    size = 6;
                    StringBuilder builder = new StringBuilder();
                    if(!tangerinesActive) {
                        builder.append(addMagic("A wi", 0))
                                .append(addNonMagic("l", 4))
                                .append(addMagic("dcard is", 5))
                                .append(addNonMagic(" a", 13))
                                .append(addMagic("ctive!", 15));
                    } else {
                        builder.append(addMagic("A so", 0))
                                .append(addNonMagic("l", 4))
                                .append(addMagic("ution is", 5))
                                .append(addNonMagic(" a", 13))
                                .append(addMagic("pplied", 15));
                    }

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(builder.toString(), "", 0, 50, 20);
                    }
                }
                case 6 -> {
                    size = 5;
                    StringBuilder builder = new StringBuilder();
                    if(!tangerinesActive) {
                        builder.append(addMagic("A wi", 0))
                                .append(addNonMagic("l", 4))
                                .append(addMagic("dca", 5))
                                .append(addNonMagic("r", 8))
                                .append(addMagic("d is ", 9))
                                .append(addNonMagic("a", 14))
                                .append(addMagic("cti", 15))
                                .append(addNonMagic("v", 18))
                                .append(addMagic("e!", 19));
                    } else {
                        builder.append(addMagic("A so", 0))
                                .append(addNonMagic("l", 4))
                                .append(addMagic("uti", 5))
                                .append(addNonMagic("o", 8))
                                .append(addMagic("n is ", 9))
                                .append(addNonMagic("a", 14))
                                .append(addMagic("ppl", 15))
                                .append(addNonMagic("i", 18))
                                .append(addMagic("ed", 19));
                    }

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(builder.toString(), "", 0, 50, 20);
                    }
                }
                case 7 -> {
                    size = 4;
                    StringBuilder builder = new StringBuilder();
                    if(!tangerinesActive) {
                        builder.append(addMagic("A wi", 0))
                                .append(addNonMagic("l", 4))
                                .append(addMagic("d", 5))
                                .append(addNonMagic("c", 6))
                                .append(addMagic("a", 7))
                                .append(addNonMagic("rd", 8))
                                .append(addMagic(" is ", 10))
                                .append(addNonMagic("a", 14))
                                .append(addMagic("cti", 15))
                                .append(addNonMagic("v", 18))
                                .append(addMagic("e!", 19));
                    } else {
                        builder.append(addMagic("A so", 0))
                                .append(addNonMagic("l", 4))
                                .append(addMagic("u", 5))
                                .append(addNonMagic("t", 6))
                                .append(addMagic("i", 7))
                                .append(addNonMagic("on", 8))
                                .append(addMagic(" is ", 10))
                                .append(addNonMagic("a", 14))
                                .append(addMagic("ppl", 15))
                                .append(addNonMagic("i", 18))
                                .append(addMagic("ed", 19));
                    }

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(builder.toString(), "", 0, 50, 20);
                    }
                }
                case 8 -> {
                    size = 3;
                    StringBuilder builder = new StringBuilder();
                    if(!tangerinesActive) {
                        builder.append(addMagic("A", 0))
                                .append(addNonMagic(" w", 1))
                                .append(addMagic("i", 3))
                                .append(addNonMagic("l", 4))
                                .append(addMagic("d", 5))
                                .append(addNonMagic("c", 6))
                                .append(addMagic("a", 7))
                                .append(addNonMagic("rd", 8))
                                .append(addMagic(" i", 10))
                                .append(addNonMagic("s a", 12))
                                .append(addMagic("cti", 15))
                                .append(addNonMagic("ve!", 18));
                    } else {
                        builder.append(addMagic("A", 0))
                                .append(addNonMagic(" s", 1))
                                .append(addMagic("o", 3))
                                .append(addNonMagic("l", 4))
                                .append(addMagic("u", 5))
                                .append(addNonMagic("t", 6))
                                .append(addMagic("i", 7))
                                .append(addNonMagic("on", 8))
                                .append(addMagic(" i", 10))
                                .append(addNonMagic("s a", 12))
                                .append(addMagic("ppl", 15))
                                .append(addNonMagic("ied", 18));
                    }

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(builder.toString(), "", 0, 50, 20);
                    }
                }
                case 9 -> {
                    size = 2;
                    StringBuilder builder = new StringBuilder();
                    if(!tangerinesActive) {
                        builder.append(addNonMagic("A wil", 0))
                                .append(addMagic("d", 5))
                                .append(addNonMagic("c", 6))
                                .append(addMagic("a", 7))
                                .append(addNonMagic("rd is ac", 8))
                                .append(addMagic("ti", 16))
                                .append(addNonMagic("ve!", 18));
                    } else {
                        builder.append(addNonMagic("A sol", 0))
                                .append(addMagic("u", 5))
                                .append(addNonMagic("t", 6))
                                .append(addMagic("i", 7))
                                .append(addNonMagic("on is ap", 8))
                                .append(addMagic("pl", 16))
                                .append(addNonMagic("ied", 18));
                    }

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(builder.toString(), "", 0, 50, 20);
                    }
                }
                case 10 -> {
                    size = 1;
                    StringBuilder builder = new StringBuilder();
                    if(!tangerinesActive) builder.append(addNonMagic("A wildcard is active!", 0));
                    else builder.append(addNonMagic("A solution is applied", 0));

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(builder.toString(), "", 0, 50, 20);
                    }
                }
            }
            return;
        }
        ticks = 0;

        if (titleNumber >= 10) { // Stop the runnable when we finish all actions

            if(tangerinesActive) {
                // Actually start the thing
                plugin.getPieceMaker().start();
                plugin.start(); // used for invincibility bits

                // Set the config
                plugin.getConfig().set("wildcardActive", true);
                plugin.saveConfig();

                HitListener.enabled = true;

                // stop loop
                this.cancel();
                return;
            } else {
                running = false;
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    HitListener.enabled = true;

                    for(Player p : Bukkit.getOnlinePlayers()) {
                        p.playSound(p.getLocation().clone().add(0, 500, 0), Sound.BLOCK_BEACON_ACTIVATE, 99999, 1);
                    }

                    new Dispensary(new Vector(-122.5, 65, -99.5));
                    new Dispensary(new Vector(-189.5, 68, -96.5));
                    new Dispensary(new Vector(-236.5, 79, -176.5));
                    new Dispensary(new Vector(-85.5, 89, -198.5));
                    new Dispensary(new Vector(-35.5, 67, -68.5));
                    new Dispensary(new Vector(-88.5, 64, 13.5));
                    new Dispensary(new Vector(-172.5, 120, 87.5));
                    new Dispensary(new Vector(-222.5, 120, 190.5));
                    new Dispensary(new Vector(-61.5, 71, 221));
                    new Dispensary(new Vector(150.5, 65, 234.5));
                    new Dispensary(new Vector(248.5, 79, -247.5));

                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        tangerinesActive = true; // this is getting really messy, really quickly, but this needs to be here


                        ComponentBuilder b = new ComponentBuilder()
                                .append("\n")
                                .append("[SURVEY]: Are you satisfied with this wildcard?\n")
                                .color(net.md_5.bungee.api.ChatColor.YELLOW)
                                .append("NO!!")
                                .color(net.md_5.bungee.api.ChatColor.RED)
                                .bold(true)
                                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/think"))
                                .append("     ")
                                .append("YESS!!!! :D")
                                .color(net.md_5.bungee.api.ChatColor.GREEN)
                                .bold(true)
                                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/think"))
                                .append("\n");
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            p.spigot().sendMessage(b.build());
                            p.playSound(p.getLocation().clone().add(0, 2, 0), "pieces:storm_watch_notif", SoundCategory.RECORDS, 1, 1);
                        }

                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            Random rand = new Random();
                            for(Player p : Bukkit.getOnlinePlayers()) {
                                ComponentBuilder b2 = new ComponentBuilder()
                                        .append("Studies show that " + rand.nextInt(61, 85) + " percent of responders find this wildcard ")
                                        .color(net.md_5.bungee.api.ChatColor.YELLOW)
                                        .append("unsatisfactory")
                                        .color(net.md_5.bungee.api.ChatColor.RED)
                                        .append("... A solution will be applied shortly.")
                                        .color(net.md_5.bungee.api.ChatColor.YELLOW);


                                p.playSound(p.getLocation().clone().add(0, 2, 0), "pieces:storm_watch_notif", SoundCategory.RECORDS, 1, 1);


                                p.spigot().sendMessage(b2.build());
                            }

                            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                titleNumber = 0;
                                ticks = 0;
                                totalTicks = 47;

                                delay = 20;

                                //this.runTaskTimer(plugin, 0L, 1L);
                                running = true;

                                HitListener.enabled = false;

                            }, 160L);

                        }, 600L);

                    }, 6000L);  //TODO: 6000
                }, 40L);
            }
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
                    player.playSound(soundLoc, Sound.BLOCK_TRIAL_SPAWNER_OMINOUS_ACTIVATE, 99999f, 1f);
                    player.playSound(soundLoc, Sound.BLOCK_VAULT_OPEN_SHUTTER, 99999f, 1f);
                    player.playSound(soundLoc, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 99999f, 1f);
                    delay = 8;
                }
            }
        }

        titleNumber++;
    }

    private String addMagic(String string, int index) {
        StringBuilder newString = new StringBuilder();
        for(int i=0; i<string.length(); i++) {
            if(shouldBeBlack(index + i)) {
                newString.append("§r§0§l§k").append(string.charAt(i));
            } else {
                newString.append("§r§f§l§k").append(string.charAt(i));
            }
        }
        return newString.toString();
    }

    private String addNonMagic(String string, int index) {
        StringBuilder newString = new StringBuilder();
        for(int i=0; i<string.length(); i++) {
            if(shouldBeBlack(index + i)) {
                newString.append("§r§0§l").append(string.charAt(i));
            } else {
                newString.append("§r§f§l").append(string.charAt(i));
            }
        }
        return newString.toString();
    }

    // A wildcard is active
    private int size;
    private boolean shouldBeBlack(int index) {
        int animationTicks = ((int)totalTicks)%(22);
        int ticky = animationTicks - index;
        while(ticky < 0) {ticky += 21;}
        return ticky < size;
    }
}
