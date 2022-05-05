package ca.skynetcloud.electricfencemod.block.fences;


import ca.skynetcloud.electricfencemod.block.fences.gate.ElectricFenceGate;
import ca.skynetcloud.electricfencemod.block.fences.top.ElectricFenceTop;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


public class ElectricFencesBlockBase extends BasicElectricFence {

    public static final BooleanProperty NORTH = CrossCollisionBlock.NORTH;
    public static final BooleanProperty EAST = CrossCollisionBlock.EAST;
    public static final BooleanProperty SOUTH = CrossCollisionBlock.SOUTH;
    public static final BooleanProperty WEST = CrossCollisionBlock.WEST;

    public static final VoxelShape P = Block.box(7.5D, 0.10D, 7.5D, 8.5D, 15.90D, 8.5D);
    public static final VoxelShape N_Z = Block.box(7.0D, 0.10D, 0.10D, 9.0D, 15.90D, 8.5D);
    public static final VoxelShape P_Z = Block.box(7.0D, 0.10D, 7.5D, 9.0D, 15.90D, 15.90D);
    public static final VoxelShape N_X = Block.box(0.10D, 0.10D, 7.0D, 8.5D, 15.90D, 9.0D);
    public static final VoxelShape P_X = Block.box(7.5D, 0.10D, 7.0D, 15.90D, 15.90D, 9.0D);

    public ElectricFencesBlockBase() {
        super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(30.0F));
        this.registerDefaultState(stateDefinition.any().setValue(ELECTRIC_POWER, 0)
                .setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE)
                .setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE));
    }

    public ElectricFencesBlockBase(DyeColor value) {
        super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(30.0F));
    }

    public boolean isFencesBlock(ElectricFencesBlockBase fence) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Deprecated
    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn,
                                           @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        return getState(this.defaultBlockState().setValue(ELECTRIC_POWER, calculatePower((Level) worldIn, currentPos)),
                (Level) worldIn, currentPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getState(
                this.defaultBlockState().setValue(ELECTRIC_POWER,
                        calculatePower(context.getLevel(), context.getClickedPos())),
                context.getLevel(), context.getClickedPos());
    }


    public boolean canConnectTo(Level world, BlockPos pos, Direction direction) {
        BlockState state = world.getBlockState(pos.relative(direction));
        Block block = state.getBlock();
        return ((block instanceof ElectricFencesBlockBase || block instanceof ElectricFenceGate || block instanceof ElectricalCabinet || block instanceof ElectricFenceTop)
                || state.isFaceSturdy(world, pos.relative(direction), direction.getOpposite()));
    }

    private BlockState getState(BlockState state, Level worldIn, BlockPos pos) {
        return state.setValue(NORTH, canConnectTo(worldIn, pos, Direction.SOUTH))
                .setValue(EAST, canConnectTo(worldIn, pos, Direction.WEST))
                .setValue(SOUTH, canConnectTo(worldIn, pos, Direction.NORTH))
                .setValue(WEST, canConnectTo(worldIn, pos, Direction.EAST));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        VoxelShape shape = P;
        if (state.getValue(NORTH))
            shape = Shapes.or(shape, P_Z);
        if (state.getValue(SOUTH))
            shape = Shapes.or(shape, N_Z);
        if (state.getValue(WEST))
            shape = Shapes.or(shape, P_X);
        if (state.getValue(EAST))
            shape = Shapes.or(shape, N_X);
        return shape;
    }
}

