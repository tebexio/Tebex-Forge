package net.buycraft.plugin.forge.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.buycraft.plugin.forge.BuycraftPlugin;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextComponentString;

public class RefreshCmd implements Command<CommandSource> {
    private final BuycraftPlugin plugin;

    public RefreshCmd(final BuycraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        if (plugin.getApiClient() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(plugin.getI18n().get("need_secret_key")).setStyle(BuycraftPlugin.ERROR_STYLE));
        } else {
            plugin.getPlatform().executeAsync(plugin.getListingUpdateTask());
            ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(plugin.getI18n().get("refresh_queued")).setStyle(BuycraftPlugin.SUCCESS_STYLE));
        }
        return 0;
    }
}
