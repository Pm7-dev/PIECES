package me.pm7.blockstorm.Commands;

import me.pm7.blockstorm.Pieces.Piece;
import me.pm7.blockstorm.BlockStorm;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class stopstorm implements CommandExecutor {
    private static final BlockStorm plugin = BlockStorm.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender.isOp()) {

            if(!plugin.isStarted()) {
                sender.sendMessage("The Storm is not running");
                return true;
            }

            for(World world : Bukkit.getWorlds()) {

                // Kill any remaining piece faces
                List<Entity> remove = new ArrayList<>();
                for(Entity e : world.getEntities()) {
                    PersistentDataContainer c = e.getPersistentDataContainer();
                    if(c.has(Piece.getPieceID(), PersistentDataType.BOOLEAN) && (Boolean.TRUE.equals(c.get(Piece.getPieceID(), PersistentDataType.BOOLEAN)))) {
                        remove.add(e);
                    }
                }
                for(Entity e : remove) e.remove();

                if(world.getPluginChunkTickets().containsKey(plugin)) {
                    for (Chunk chunk : world.getPluginChunkTickets().get(plugin)) {
                        chunk.removePluginChunkTicket(plugin);
                    }
                }
            }

            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.RED + "The Storm is ending!");
                p.playSound(p.getLocation().clone().add(0, 2, 0), "pieces:storm_watch_notif", SoundCategory.RECORDS, 1, 1);
            }

            plugin.getPieceMaker().stop();
            plugin.getPieceKeeper().stop();
            plugin.stop();

        } else {
            sender.sendMessage(ChatColor.RED + "You must be operator to use this command.");
        }
        return true;
    }
}
