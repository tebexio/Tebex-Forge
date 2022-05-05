package ca.skynetcloud.electricfencemod.datagen;

import ca.skynetcloud.electricfencemod.init.BlockInit;
import ca.skynetcloud.electricfencemod.init.ItemInit;
import ca.skynetcloud.electricfencemod.init.TagsInit;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.Arrays;
import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider {


    public ModRecipes(DataGenerator p_125973_) {
        super(p_125973_);
    }
    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(BlockInit.ElectricFence.get())
                .pattern("iii")
                .pattern("bbb")
                .pattern("iii")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('b', Blocks.IRON_BARS)
                .group("electricfencemod")
                .unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.ElectricFenceGate.get())
                .pattern("ii ")
                .pattern("bb ")
                .pattern("ii ")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('b', Blocks.IRON_BARS)
                .group("electricfencemod")
                .unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockInit.ElectricFenceTop.get())
                .pattern("i i")
                .pattern(" b ")
                .pattern(" i ")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('b', Blocks.IRON_BARS)
                .group("electricfencemod")
                .unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(BlockInit.WHITE_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.WHITE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.ORANGE_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.ORANGE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.MAGENTA_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.MAGENTA_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.LIGHT_BLUE_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.LIGHT_BLUE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.YELLOW_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.YELLOW_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.LIME_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.LIME_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.PINK_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.PINK_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.GRAY_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.GRAY_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.LIGHT_GRAY_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.LIGHT_GRAY_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.CYAN_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.CYAN_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.PURPLE_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.PURPLE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.BLUE_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.BLUE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.BROWN_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.BROWN_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.GREEN_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.GREEN_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.RED_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.RED_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.BLACK_Electric_Fence.get()).requires(BlockInit.ElectricFence.get()).requires(Items.BLACK_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(BlockInit.WHITE_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.WHITE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.ORANGE_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.ORANGE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.MAGENTA_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.MAGENTA_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.LIGHT_BLUE_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.LIGHT_BLUE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.YELLOW_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.YELLOW_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.LIME_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.LIME_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.PINK_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.PINK_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.GRAY_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.GRAY_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.LIGHT_GRAY_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.LIGHT_GRAY_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.CYAN_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.CYAN_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.PURPLE_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.PURPLE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.BLUE_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.BLUE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.BROWN_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.BROWN_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.GREEN_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.GREEN_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.RED_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.RED_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.BLACK_Electric_FenceTop.get()).requires(BlockInit.ElectricFenceTop.get()).requires(Items.BLACK_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(BlockInit.WHITE_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.WHITE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.ORANGE_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.ORANGE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.MAGENTA_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.MAGENTA_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.LIGHT_BLUE_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.LIGHT_BLUE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.YELLOW_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.YELLOW_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.LIME_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.LIME_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.PINK_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.PINK_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.GRAY_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.GRAY_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.LIGHT_GRAY_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.LIGHT_GRAY_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.CYAN_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.CYAN_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.PURPLE_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.PURPLE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.BLUE_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.BLUE_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.BROWN_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.BROWN_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.GREEN_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.GREEN_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.RED_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.RED_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(BlockInit.BLACK_Electric_FenceGate.get()).requires(BlockInit.ElectricFenceGate.get()).requires(Items.BLACK_DYE).group("electricfencemod").unlockedBy("electrified", InventoryChangeTrigger.TriggerInstance.hasItems(ItemInit.ElectricCabinet.get())).save(consumer);




    }
}
