package ca.skynetcloud.electricfencemod.block.fences;


import ca.skynetcloud.electricfencemod.init.DamageInit;
import ca.skynetcloud.electricfencemod.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class BasicElectricFence extends Block {

    public static final IntegerProperty ELECTRIC_POWER = IntegerProperty.create("electric_power", 0, 15);

    public BasicElectricFence(Properties property) {
        super(property.noOcclusion().lightLevel((p) -> 6));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(ELECTRIC_POWER);
    }

    public static int calculatePower(Level worldIn, BlockPos pos) {
        int fencePower = 0;
        for (Direction direction : Direction.values()) {
            BlockState state = worldIn.getBlockState(pos.relative(direction));
            Block block = state.getBlock();
            if (block instanceof ElectricalCabinet && state.getValue(ElectricalCabinet.SUPPLYING))
                return 15;
            if (block instanceof BasicElectricFence && state.hasProperty(ELECTRIC_POWER)) {
                int power = state.getValue(ELECTRIC_POWER);
                if (power > fencePower)
                    fencePower = power - 1;
            }
        }
        return fencePower;
    }


        public void entityInside(@Nullable BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
            if (state.getValue(ELECTRIC_POWER) > 0 && worldIn.getGameTime() % 8L == 0L) {
                if (entityIn instanceof LivingEntity _entGetArmor) {

                    if (_entGetArmor.getItemBySlot(EquipmentSlot.CHEST).getItem() == Items.LEATHER_CHESTPLATE) {
                        entityIn.isInvulnerableTo(DamageInit.ELECTRIC_FENCE);
                    } else {
                        if (worldIn.isRaining() && worldIn.canSeeSky(pos))  {

                            entityIn.hurt(DamageInit.ELECTRIC_FENCE, 5.0F);
                            if (worldIn.isClientSide)
                                doCollideAnimation(pos, worldIn, 1, ParticleTypes.ELECTRIC_SPARK, SoundInit.ELECTRIC_FENCE_SPARK,
                                        0.75F, 50F);
                        } else {
                            entityIn.hurt(DamageInit.ELECTRIC_FENCE, 2.5F);
                            if (worldIn.isClientSide)
                                doCollideAnimation(pos, worldIn, 1, ParticleTypes.ELECTRIC_SPARK, SoundInit.ELECTRIC_FENCE_SPARK, 0.55F,
                                        20F);
                        }
                    }

                } else {
                    entityIn.remove(Entity.RemovalReason.KILLED);
                    if (worldIn.isClientSide)
                        doCollideAnimation(pos, worldIn, 7, ParticleTypes.ELECTRIC_SPARK, SoundInit.ELECTRIC_FENCE_SPARK, 0.8F, 20F);
                }
            }
        }

    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (world instanceof ServerLevel _level) {
            LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
            entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) x, (int) y, (int) z)));
            entityToSpawn.setVisualOnly(false);
            _level.addFreshEntity(entityToSpawn);
        }
    }



    private void doCollideAnimation(BlockPos pos, Level worldIn, int amount, SimpleParticleType particle,
                                    SoundEvent sound, float volume, float pitch) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        Random random = new Random();
        // worldIn.playSound(x + 0.5, y + 0.5, z + 0.5,
        // SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, volume,
        // pitch, false);
        worldIn.playLocalSound(x + 0.5, y + 0.5, z + 0.5, sound, SoundSource.BLOCKS, volume, pitch, false);
        for (int i = 0; i < amount; i++)
            worldIn.addParticle(particle, x + random.nextFloat(), y + random.nextFloat(), z + random.nextFloat(), 0.0D,
                    0.0D, 0.0D);
    }

    @Override
    public void animateTick(BlockState blockState, Level lvl, BlockPos pos, Random rand)  {
        if (blockState.getValue(ELECTRIC_POWER) > 0 && rand.nextInt(350) == 1)
            if (lvl.isClientSide){
                doCollideAnimation(pos, lvl, 1, ParticleTypes.ELECTRIC_SPARK, SoundInit.ELECTRIC_FENCE_IDLE, 0.05F, 0.5F);
            }
    }

    @Override
    public void appendHoverText(ItemStack p_49816_, @Nullable BlockGetter p_49817_, List<Component> p_49818_, TooltipFlag p_49819_) {
        super.appendHoverText(p_49816_, p_49817_, p_49818_, p_49819_);
        p_49818_.add(new TextComponent(
                "can be dismantled by wrench, connect to a powered energy supplier or electric fence to activate"));
    }
}
