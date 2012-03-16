/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightmagic.magics;

import bluenova.fairytailcraft.plugin.MagePluginEvent;
import bluenova.fairytailcraft.plugin.MagePluginRegion;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author Sven
 */
public class FairyLaw extends MagePluginEvent {

    @Override
    public boolean callPlayerInteractEvent(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getPlayer().getLocation().getWorld().getEnvironment() == Environment.NORMAL) {
            Player play = event.getPlayer();
            List<Entity> nearbyEntities = event.getPlayer().getNearbyEntities(100, 256, 100);
            List<Player> warnList = new ArrayList<Player>();
            for (Entity ent : nearbyEntities) {
                if (ent instanceof LivingEntity) {
                    if (ent instanceof Player) {
                        warnList.add((Player) ent);
                    }
                }
            }

            for (Player pl : warnList) {
                pl.sendMessage(ChatColor.GOLD + "Fairy Law casted by " + event.getPlayer().getName());
                pl.sendMessage(ChatColor.GOLD + "You have 10 seconds to get enough distance!");
            }
            play.sendMessage(ChatColor.GOLD + "Fairy Law casted by " + event.getPlayer().getName());
            int j = 10;
            for (int i = 0; i < 10; i++, j--) {
                for (Player pl : warnList) {
                    pl.sendMessage(ChatColor.GOLD + "Fairy Law casted by " + event.getPlayer().getName() + " in " + j);
                }
                play.sendMessage(ChatColor.GOLD + "Fairy Law casted by " + event.getPlayer().getName() + " in " + j);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FairyLaw.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (event.getPlayer().isDead()) {
                    break;
                }
            }
            if (!event.getPlayer().isDead()) {
                nearbyEntities = event.getPlayer().getNearbyEntities(100, 256, 100);
                List<LivingEntity> toKill = new ArrayList<LivingEntity>();
                for (Entity ent : nearbyEntities) {
                    if (ent instanceof LivingEntity) {
                        toKill.add((LivingEntity) ent);
                    }
                }
                int hight = event.getPlayer().getLocation().getWorld().getMaxHeight();
                Location loc = event.getPlayer().getLocation();
                Location loc1 = new Location(loc.getWorld(), loc.getBlockX() - 100, hight, loc.getBlockZ() - 100);
                Location loc2 = new Location(loc.getWorld(), loc.getBlockX() + 100, hight, loc.getBlockZ() + 100);
                MagePluginRegion regio = new MagePluginRegion(loc1, loc2, loc.getWorld());
                List<Block> blocks = regio.getBlocks();
                for (Block blk : blocks) {
                    if (blk.getType() != Material.AIR) {
                        blocks.remove(blk);
                    }
                }
                for (Block blk : blocks) {
                    blk.setType(Material.GLOWSTONE);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FairyLaw.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Player pl : warnList) {
                    pl.sendMessage(ChatColor.GOLD + "Fairy Law activated!");
                }
                play.sendMessage(ChatColor.GOLD + "Fairy Law activated!");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FairyLaw.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (LivingEntity ent : toKill) {
                    ent.damage(ent.getHealth() + 10000, event.getPlayer());
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FairyLaw.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Block blk : blocks) {
                    blk.setType(Material.AIR);
                }
            }
        }
        return false;
    }
}
