package ca.skynetcloud.electricfencemod.items;

import ca.skynetcloud.electricfencemod.Electricfencemod;
import ca.skynetcloud.electricfencemod.block.fences.BasicElectricFence;
import ca.skynetcloud.electricfencemod.block.fences.ElectricalCabinet;
import ca.skynetcloud.electricfencemod.block.fences.gate.ElectricFenceGate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WrenchTool extends Item{

 public WrenchTool()	{
     super(new Item.Properties().stacksTo(1).tab(Electricfencemod.electricfencemodTab.MAIN));
 }



    @Override
    public InteractionResult useOn(UseOnContext ctx)
    {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Player player = ctx.getPlayer();
        if (!level.isClientSide && player != null)
        {
            BlockState target = level.getBlockState(pos);
            ItemStack stack = player.getMainHandItem();

            if (stack.getItem() instanceof WrenchTool && player.isCrouching())
            {
                Block block = target.getBlock();
                if (removeIfValid(block, level, pos))
                {
                    if (block instanceof BasicElectricFence || block instanceof ElectricFenceGate || block instanceof ElectricalCabinet)
                    {
                        if (!player.addItem(new ItemStack(block)))
                            Block.popResource(level, player.blockPosition(), new ItemStack(block));
                        return InteractionResult.SUCCESS;
                    }
                    Block.popResource(level, pos, new ItemStack(target.getBlock()));
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useOn(ctx);
    }

    private boolean removeIfValid(Block block, Level world, BlockPos pos)
    {
        if (block instanceof BasicElectricFence || block instanceof ElectricFenceGate || block instanceof ElectricalCabinet)
        {
            world.removeBlock(pos, false);
            return true;
        }
        return false;
    }
}
