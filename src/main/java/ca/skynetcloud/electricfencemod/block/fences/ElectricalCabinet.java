package ca.skynetcloud.electricfencemod.block.fences;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ElectricalCabinet extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty SUPPLYING = BooleanProperty.create("supplying");

    public ElectricalCabinet() {
        super(BlockBehaviour.Properties.of(Material.METAL).noOcclusion());
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(SUPPLYING, false).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()), 2);
    }


    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SUPPLYING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(world, pos);
        switch ((Direction) state.getValue(FACING)) {
            case SOUTH :
            default :
                return Shapes.or(box(0, 0, 0, 16, 16, 16), box(0, 0, 0, 16, 21, 16)).move(offset.x, offset.y, offset.z);
            case NORTH :
                return Shapes.or(box(0, 0, 0, 16, 16, 16), box(0, 0, 0, 16, 21, 16)).move(offset.x, offset.y, offset.z);
            case EAST :
                return Shapes.or(box(0, 0, 0, 16, 16, 16), box(0, 0, 0, 16, 21, 16)).move(offset.x, offset.y, offset.z);
            case WEST :
                return Shapes.or(box(0, 0, 0, 16, 16, 16), box(0, 0, 0, 16, 21, 16)).move(offset.x, offset.y, offset.z);
        }
    }


}
