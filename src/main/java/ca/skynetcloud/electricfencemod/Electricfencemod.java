package ca.skynetcloud.electricfencemod;

import ca.skynetcloud.electricfencemod.init.BlockInit;
import ca.skynetcloud.electricfencemod.init.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("electricfencemod")
public class Electricfencemod {

    public static final String MODID = "electricfencemod";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public Electricfencemod() {
        final IEventBus FML = FMLJavaModLoadingContext.get().getModEventBus();

        FML.addListener(this::setup);
        FML.addListener(this::clientSetup);

        BlockInit.BLOCKS.register(FML);
        ItemInit.ITEMS.register(FML);
    }

    //This used to be the PreInit
    private void setup(FMLCommonSetupEvent event) {
        LOGGER.info("Setup Method Registered (PreInit)");

    }

    private void clientSetup(final FMLClientSetupEvent event) {

    }


    public static class electricfencemodTab {

        public static final CreativeModeTab MAIN = new CreativeModeTab("electricfencemod_main") {
            @Override
            public @NotNull ItemStack makeIcon() {
                return new ItemStack(ItemInit.wrench_tool.get());
            }
        };

    }


}
