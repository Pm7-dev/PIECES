package me.pm7.blockstorm.Listener;

import me.pm7.blockstorm.Pieces.Piece;
import me.pm7.blockstorm.BlockStorm;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener {
    private static final BlockStorm plugin = BlockStorm.getPlugin();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if(Piece.getDead().contains(p.getUniqueId())) {
            e.setDeathMessage(p.getDisplayName() + " felt under the weather");
            Piece.getDead().remove(p.getUniqueId());
        }
        for(int i=0; i< e.getDrops().size(); i++) {
            ItemStack item = e.getDrops().get(i);
            if(item.getType() == Material.POPPED_CHORUS_FRUIT && item.getItemMeta() != null) {
                if(item.getItemMeta().getItemName().equals(" ")) {
                    e.getDrops().remove(i);
                    i--;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if(plugin.isStarted()) {
            Player p = e.getPlayer();
            plugin.getPieceKeeper().setImmunity(p, 1200);
        }
    }
}
