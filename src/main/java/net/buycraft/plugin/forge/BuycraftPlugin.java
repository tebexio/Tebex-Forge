package net.buycraft.plugin.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("buycraft")
public class BuycraftPlugin {

    private static final Logger LOGGER = LogManager.getLogger("Buycraft");

    public BuycraftPlugin() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    // As as close to an onEnable as we are ever going to get :(
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

}
