package net.buycraft.plugin.forge.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.buycraft.plugin.forge.BuycraftPlugin;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class ForceCheckCmd implements Command<CommandSourceStack> {
    private final BuycraftPlugin plugin;

    public ForceCheckCmd(final BuycraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        if (plugin.getApiClient() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("need_secret_key"))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 1;
        }

        if (plugin.getDuePlayerFetcher().inProgress()) {
            ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("already_checking_for_purchases"))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 1;
        }

        plugin.getExecutor().submit(() -> plugin.getDuePlayerFetcher().run(false));
        ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("forcecheck_queued"))
                .setStyle(BuycraftPlugin.SUCCESS_STYLE));
        return 1;
    }
}
