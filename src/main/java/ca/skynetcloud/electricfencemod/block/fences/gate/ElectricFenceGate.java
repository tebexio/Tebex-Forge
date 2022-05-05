package ca.skynetcloud.electricfencemod.block.fences.gate;

import ca.skynetcloud.electricfencemod.block.fences.BasicElectricFence;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class ElectricFenceGate extends Block {
    public static final DirectionProperty HORIZONTAL_FACING = DirectionProperty.create("facing",
            Direction.Plane.HORIZONTAL);
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");

    public static final VoxelShape FRAME_Z = Shapes.or(Block.box(0.0D, 0.0D, 6.0D, 1.0D, 16.0D, 10.0D),
            Block.box(15.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D));
    public static final VoxelShape FRAME_X = Shapes.or(Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 1.0D),
            Block.box(6.0D, 0.0D, 15.0D, 10.0D, 16.0D, 16.0D));
    public static final VoxelShape FRAME_TOP_Z = Block.box(0.0D, 15.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    public static final VoxelShape FRAME_TOP_X = Block.box(6.0D, 15.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public static final VoxelShape DOOR_CLOSE_Z = Block.box(1.0D, 0.0D, 7.0D, 15.0D, 16.0D, 9.0D);
    public static final VoxelShape DOOR_CLOSE_X = Block.box(7.0D, 0.0D, 1.0D, 9.0D, 16.0D, 15.0D);

    public static final VoxelShape DOOR_NEGATIVE_Z = Block.box(1.0D, 0.0D, 1.0D, 3.0D, 16.0D, 15.0D);
    public static final VoxelShape DOOR_POSITIVE_Z = Block.box(13.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    public static final VoxelShape DOOR_NEGATIVE_X = Block.box(1.0D, 0.0D, 13.0D, 15.0D, 16.0D, 15.0D);
    public static final VoxelShape DOOR_POSITIVE_X = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 3.0D);

    public ElectricFenceGate() {
        super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(30.0F).noOcclusion());
        this.registerDefaultState(stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH)
                .setValue(OPEN, Boolean.valueOf(false)).setValue(IS_TOP, Boolean.valueOf(false)));
    }

    public ElectricFenceGate(DyeColor value) {
        super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(30.0F).noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, OPEN, IS_TOP);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
                                  BlockPos currentPos, BlockPos facingPos) {
        if (!checkValid(currentPos, (Level) worldIn))
            worldIn.destroyBlock(currentPos, !stateIn.getValue(IS_TOP));
        BlockState state = worldIn.getBlockState(stateIn.getValue(IS_TOP) ? currentPos.below() : currentPos.above());
        if (state.getBlock() instanceof ElectricFenceGate) {
            return defaultBlockState().setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                    .setValue(OPEN, state.getValue(OPEN)).setValue(IS_TOP, stateIn.getValue(IS_TOP));
        }
        return stateIn;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        if (blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context)) {
            return defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection())
                    .setValue(OPEN, false).setValue(IS_TOP, false);
        }
        return null;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!checkValid(pos, worldIn)) {
            worldIn.destroyBlock(pos, !state.getValue(IS_TOP));
            return InteractionResult.FAIL;
        }
        if (isPowered(worldIn, pos)) {
            worldIn.setBlockAndUpdate(pos,
                    defaultBlockState().setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                            .setValue(OPEN, !state.getValue(OPEN)).setValue(IS_TOP, state.getValue(IS_TOP)));
        } else
            return InteractionResult.FAIL;
        worldIn.levelEvent(player, state.getValue(OPEN) ? 1005 : 1011, pos, 0);
        return InteractionResult.SUCCESS;
    }

    private boolean isPowered(Level world, BlockPos pos) {
        return BasicElectricFence.calculatePower(world, pos) > 0;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer,
                            ItemStack stack) {
        worldIn.setBlockAndUpdate(pos.above(), state.setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                .setValue(OPEN, state.getValue(OPEN)).setValue(IS_TOP, true));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos,
                                        CollisionContext context) {
        return getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        VoxelShape shape;
        Direction facing = state.getValue(HORIZONTAL_FACING);
        boolean open = state.getValue(OPEN);
        boolean top = state.getValue(IS_TOP);
        switch (facing) {
            case NORTH:
            default:
                shape = DOOR_CLOSE_Z;
                if (open)
                    shape = (top) ? toTopShape(DOOR_NEGATIVE_Z) : DOOR_NEGATIVE_Z;
                break;
            case SOUTH:
                shape = DOOR_CLOSE_Z;
                if (open)
                    shape = (top) ? toTopShape(DOOR_POSITIVE_Z) : DOOR_POSITIVE_Z;
                break;
            case WEST:
                shape = DOOR_CLOSE_X;
                if (open)
                    shape = (top) ? toTopShape(DOOR_NEGATIVE_X) : DOOR_NEGATIVE_X;
                break;
            case EAST:
                shape = DOOR_CLOSE_X;
                if (open)
                    shape = (top) ? toTopShape(DOOR_POSITIVE_X) : DOOR_POSITIVE_X;
                break;
        }
        switch (facing.getAxis()) {
            case Z:
            default:
                shape = add(shape, FRAME_Z);
                if (top)
                    shape = add(shape, FRAME_TOP_Z);
                break;
            case X:
                shape = add(shape, FRAME_X);
                if (top)
                    shape = add(shape, FRAME_TOP_X);
                break;
        }
        return shape;
    }

    private VoxelShape add(VoxelShape shape, VoxelShape shape2) {
        return Shapes.or(shape, shape2);
    }

    private VoxelShape toTopShape(VoxelShape shape) {
        AABB aabb = shape.bounds();
        return Block.box(aabb.minX * 16, aabb.minY * 16 - 1, aabb.minZ * 16, aabb.maxX * 16, aabb.maxY * 16 - 1,
                aabb.maxZ * 16);
    }

    private boolean checkValid(BlockPos pos, Level world) {
        BlockState state = world.getBlockState(pos);
        BlockState state2 = world.getBlockState(state.getValue(IS_TOP) ? pos.below() : pos.above());
        return state2.getBlock() instanceof ElectricFenceGate && (state.getValue(IS_TOP) != state2.getValue(IS_TOP));
    }

    @Override
    public void appendHoverText(ItemStack stack, BlockGetter worldIn, List<Component> tooltip,
                                TooltipFlag flagIn) {
        tooltip.add(new TextComponent("can be dismantled by wrench"));
    }
}
