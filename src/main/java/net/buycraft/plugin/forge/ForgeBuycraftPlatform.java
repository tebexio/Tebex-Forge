package net.buycraft.plugin.forge;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.buycraft.plugin.BuyCraftAPI;
import net.buycraft.plugin.IBuycraftPlatform;
import net.buycraft.plugin.UuidUtil;
import net.buycraft.plugin.data.QueuedPlayer;
import net.buycraft.plugin.data.responses.ServerInformation;
import net.buycraft.plugin.execution.placeholder.PlaceholderManager;
import net.buycraft.plugin.execution.strategy.CommandExecutor;
import net.buycraft.plugin.platform.PlatformInformation;
import net.buycraft.plugin.platform.PlatformType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.versions.forge.ForgeVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ForgeBuycraftPlatform implements IBuycraftPlatform {

    private static final Map<Level, org.apache.logging.log4j.Level> LOG_LEVEL_MAP = new HashMap<Level, org.apache.logging.log4j.Level>() {{
        put(Level.OFF, org.apache.logging.log4j.Level.OFF);
        put(Level.SEVERE, org.apache.logging.log4j.Level.ERROR);
        put(Level.WARNING, org.apache.logging.log4j.Level.WARN);
        put(Level.INFO, org.apache.logging.log4j.Level.INFO);
        put(Level.FINE, org.apache.logging.log4j.Level.DEBUG);
        put(Level.FINER, org.apache.logging.log4j.Level.TRACE);
        put(Level.ALL, org.apache.logging.log4j.Level.ALL);
    }};

    private final BuycraftPlugin plugin;

    public ForgeBuycraftPlatform(BuycraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public BuyCraftAPI getApiClient() {
        return plugin.getApiClient();
    }

    @Override
    public PlaceholderManager getPlaceholderManager() {
        return plugin.getPlaceholderManager();
    }

    @Override
    public void dispatchCommand(String command) {
        plugin.getServer().addScheduledTask(() -> { //Ensure main
            try {
                plugin.getServer().getCommandManager().getDispatcher().execute(command, plugin.getServer().getCommandSource());
            } catch (CommandSyntaxException e) {
                log(Level.SEVERE, "Failed to dispatch command \'" + command + "\'", e);
            }
        });
    }

    @Override
    public void executeAsync(Runnable runnable) {
        plugin.getExecutor().submit(runnable);
    }

    @Override
    public void executeAsyncLater(Runnable runnable, long l, TimeUnit timeUnit) {
        plugin.getExecutor().schedule(runnable, l, timeUnit);
    }

    @Override
    public void executeBlocking(Runnable runnable) {
        plugin.getServer().addScheduledTask(runnable);
    }

    @Override
    public void executeBlockingLater(Runnable runnable, long l, TimeUnit timeUnit) {
        plugin.getExecutor().schedule(() -> plugin.getServer().addScheduledTask(runnable), l, timeUnit);
    }

    private EntityPlayerMP getPlayer(QueuedPlayer player) {
        if (player.getUuid() != null && plugin.getServer().isServerInOnlineMode()) {
            UUID uuid = UuidUtil.mojangUuidToJavaUuid(player.getUuid());
            return plugin.getServer().getPlayerList().getPlayers()
                    .stream()
                    .filter(entityPlayerMP -> entityPlayerMP.getUniqueID().equals(uuid))
                    .findFirst().orElse(null);
        } else {
            return plugin.getServer().getPlayerList().getPlayers()
                    .stream()
                    .filter(entityPlayerMP -> entityPlayerMP.getName().getString().equalsIgnoreCase(player.getName()))
                    .findFirst().orElse(null);
        }
    }

    @Override
    public boolean isPlayerOnline(QueuedPlayer queuedPlayer) {
        return getPlayer(queuedPlayer) != null;
    }

    @Override
    public int getFreeSlots(QueuedPlayer queuedPlayer) {
        InventoryPlayer inventory = getPlayer(queuedPlayer).inventory;
        return (int) (inventory.mainInventory.size() - inventory.mainInventory.stream().filter(stack -> stack == ItemStack.EMPTY).count());
    }

    @Override
    public void log(Level level, String s) {
        plugin.getLogger().log(LOG_LEVEL_MAP.get(level), s);
    }

    @Override
    public void log(Level level, String s, Throwable throwable) {
        plugin.getLogger().log(LOG_LEVEL_MAP.get(level), s, throwable);
    }

    @Override
    public CommandExecutor getExecutor() {
        return plugin.getCommandExecutor();
    }

    @Override
    public PlatformInformation getPlatformInformation() {
        return new PlatformInformation(PlatformType.FORGE, plugin.getServer().getMinecraftVersion() + "-" + ForgeVersion.getVersion());
    }

    @Override
    public String getPluginVersion() {
        return plugin.getPluginVersion();
    }

    @Override
    public ServerInformation getServerInformation() {
        return plugin.getServerInformation();
    }
}
