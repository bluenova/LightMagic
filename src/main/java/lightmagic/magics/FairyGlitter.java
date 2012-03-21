/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightmagic.magics;

import bluenova.fairytailcraft.plugin.MagePluginEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

/**
 *
 * @author Sven
 */
public class FairyGlitter extends MagePluginEvent {

    @Override
    public boolean callPlayerInteractEvent(PlayerInteractEvent event, Integer level) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getPlayer().getLocation().getWorld().getEnvironment() == Environment.NORMAL) {
            Player pl = event.getPlayer();
            int range = 40;
            LivingEntity ent = null;
            List<Entity> nearbyE = pl.getNearbyEntities(range,
                    range, range);
            ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();

            for (Entity e : nearbyE) {
                if (e instanceof LivingEntity) {
                    livingE.add((LivingEntity) e);
                }
            }

            ent = null;
            BlockIterator bItr = new BlockIterator(pl, range);
            Block block;
            Location loc;
            int bx, by, bz;
            double ex, ey, ez;
            // loop through player's line of sight
            while (bItr.hasNext()) {
                block = bItr.next();
                bx = block.getX();
                by = block.getY();
                bz = block.getZ();
                // check for entities near this block in the line of sight
                for (LivingEntity e : livingE) {
                    loc = e.getLocation();
                    ex = loc.getX();
                    ey = loc.getY();
                    ez = loc.getZ();
                    if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75) && (by - 1 <= ey && ey <= by + 2.5)) {
                        // entity is close enough, set target and stop
                        ent = e;
                        break;
                    }
                }
            }

            if (ent != null) {
                new fairyLawThread(ent).start();
                return true;
            }
        }



        return false;
    }

    class fairyLawThread extends Thread {

        public LivingEntity target;

        public fairyLawThread(LivingEntity target) {
            this.target = target;
        }

        public void run() {
            Location l = target.getLocation();
            List<Location> locs = new ArrayList<Location>();
            Location l1 = new Location(l.getWorld(), l.getBlockX() + 1, l.getBlockY() + 1, l.getBlockZ() + 1);
            Location l2 = new Location(l.getWorld(), l.getBlockX() - 1, l.getBlockY() + 1, l.getBlockZ() - 1);
            Location l3 = new Location(l.getWorld(), l.getBlockX() + 1, l.getBlockY() + 1, l.getBlockZ() - 1);
            Location l4 = new Location(l.getWorld(), l.getBlockX() - 1, l.getBlockY() + 1, l.getBlockZ() + 1);
            if(l1.getBlock().getType() == Material.AIR) {
                locs.add(l1);
            }
            if(l2.getBlock().getType() == Material.AIR) {
                locs.add(l1);
            }
            if(l3.getBlock().getType() == Material.AIR) {
                locs.add(l1);
            }
            if(l4.getBlock().getType() == Material.AIR) {
                locs.add(l1);
            }
            
            for(Location lo : locs) {
                lo.getBlock().setType(Material.GLOWSTONE);
            }
            target.damage(target.getHealth() + 10000);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(FairyGlitter.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(Location lo : locs) {
                lo.getBlock().setType(Material.AIR);
            }
        }
    }
}
