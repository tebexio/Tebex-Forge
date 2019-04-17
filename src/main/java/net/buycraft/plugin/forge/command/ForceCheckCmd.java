package net.buycraft.plugin.forge.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.buycraft.plugin.forge.BuycraftPlugin;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextComponentString;

public class ForceCheckCmd implements Command<CommandSource> {
    private final BuycraftPlugin plugin;

    public ForceCheckCmd(final BuycraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSource> context) {
        if (plugin.getApiClient() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(plugin.getI18n().get("need_secret_key"))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 1;
        }

        if (plugin.getDuePlayerFetcher().inProgress()) {
            ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(plugin.getI18n().get("already_checking_for_purchases"))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 1;
        }

        plugin.getExecutor().submit(() -> plugin.getDuePlayerFetcher().run(false));
        ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(plugin.getI18n().get("forcecheck_queued"))
                .setStyle(BuycraftPlugin.SUCCESS_STYLE));
        return 1;
    }
}
