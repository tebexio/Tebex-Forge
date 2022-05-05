package ca.skynetcloud.electricfencemod.block.fences.top;

import ca.skynetcloud.electricfencemod.block.fences.BasicElectricFence;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ElectricFenceTop extends BasicElectricFence {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape TOP_X_AXIS = Shapes.or(Block.box(7.0D, 0.10D, 0.10D, 9.0D, 5.0D, 15.90D),
            Block.box(6.0D, 5.0D, 0.10D, 10.0D, 7.0D, 15.90D),
            Block.box(0.10D, 10.0D, 0.10D, 3.0D, 13.0D, 15.90D), Block.box(3.0D, 7.0D, 0.10D, 6.0D, 10.0D, 15.90D),
            Block.box(10.0D, 7.0D, 0.10D, 13.0D, 10.0D, 15.90D), Block.box(13.0D, 10.0D, 0.10D, 15.90D, 13.0D, 15.90D));
    public static final VoxelShape TOP_Z_AXIS = Shapes.or(Block.box(0.10D, 0.10D, 7.0D, 15.90D, 5.0D, 9.0D),
            Block.box(0.10D, 5.0D, 6.0D, 15.90D, 7.0D, 10.0D),
            Block.box(0.10D, 10.0D, 0.10D, 15.90D, 13.0D, 3.0D), Block.box(0.10D, 7.0D, 3.0D, 15.90D, 10.0D, 6.0D),
            Block.box(0.10D, 7.0D, 10.0D, 15.90D, 10.0D, 13.0D), Block.box(0.10D, 10.0D, 13.0D, 15.90D, 13.0D, 15.90D));

    public ElectricFenceTop() {
        super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(30.0F));
        registerDefaultState(this.stateDefinition.any().setValue(ELECTRIC_POWER, 0).setValue(FACING, Direction.NORTH));
    }

    public ElectricFenceTop(DyeColor value) {
        super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(30.0F).noOcclusion());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
                                  BlockPos currentPos, BlockPos facingPos) {
        return stateDefinition.any().setValue(FACING, stateIn.getValue(FACING)).setValue(ELECTRIC_POWER,
                calculatePower((Level) worldIn, currentPos));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return stateDefinition.any().setValue(FACING, context.getHorizontalDirection()).setValue(ELECTRIC_POWER,
                calculatePower(context.getLevel(), context.getClickedPos()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING).getAxis()) {
            case X:
            default:
                return TOP_X_AXIS;
            case Z:
                return TOP_Z_AXIS;
        }
    }

}
