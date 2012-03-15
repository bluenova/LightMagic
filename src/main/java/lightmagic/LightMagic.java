package lightmagic;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import bluenova.fairytailcraft.event.MageEventType;
import bluenova.fairytailcraft.plugin.MagePlugin;
import bluenova.fairytailcraft.plugin.MagePluginManager;
import lightmagic.magics.GlowStoneMagic;
import lightmagic.magics.LightWall;
import lightmagic.magics.SummonGlowstones;
import lightmagic.magics.SummonTorches;
import lightmagic.magics.TorchMagic;

/**
 *
 * @author Sven
 */
public class LightMagic implements MagePlugin {
    private String magicName = "LightMagic";
    private MagePluginManager manager;
    public void setPluginManager(MagePluginManager manager) {
        this.manager = manager;
    }

    public void loadPlugin() {
        manager.registerMagic("torchmagic", magicName, 1, 10, new TorchMagic(), MageEventType.INTERACT, false);
        manager.registerMagic("glowstonemagic", magicName, 5, 15, new GlowStoneMagic(), MageEventType.INTERACT, false);
        manager.registerMagic("summontorches", magicName, 10, 20, new SummonTorches(), MageEventType.INTERACT, false);
        manager.registerMagic("summonglowstones", magicName, 15, 25, new SummonGlowstones(), MageEventType.INTERACT, false);
        manager.registerMagic("lightwall", magicName, 20, 30, new LightWall(), MageEventType.INTERACT, false);
        System.out.println("LightMagic Successfully Load!");
    }

    public void unloadPlugin() {
        System.out.println("LightMagic Successfully Unload!");
    }

    public String getMagicName() {
        return magicName;
    } 
}
