package ca.skynetcloud.electricfencemod.init;

import ca.skynetcloud.electricfencemod.Electricfencemod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class TagsInit {

    public static final TagKey<Block> Color_Fence;
    public static final TagKey<Block> Color_Fence_Top;
    public static final TagKey<Block> Color_Fence_Gate;

    static {
        Color_Fence_Top = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Electricfencemod.MODID, "colored_fences_top"));
        Color_Fence = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Electricfencemod.MODID, "colored_fences"));
        Color_Fence_Gate = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Electricfencemod.MODID, "colored_fences_gate"));
    }

}
