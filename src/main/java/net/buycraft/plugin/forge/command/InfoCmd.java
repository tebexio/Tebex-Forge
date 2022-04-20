package net.buycraft.plugin.forge.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.buycraft.plugin.forge.BuycraftPlugin;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TextComponent;

import java.util.stream.Stream;

public class InfoCmd implements Command<CommandSourceStack> {
    private final BuycraftPlugin plugin;

    public InfoCmd(final BuycraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        if (plugin.getApiClient() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new TextComponent(ForgeMessageUtil.format("generic_api_operation_error"))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 1;
        }

        if (plugin.getServerInformation() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new TextComponent(ForgeMessageUtil.format("information_no_server"))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 1;
        }

        String webstoreURL = plugin.getServerInformation().getAccount().getDomain();

        Component webstore = new TextComponent(webstoreURL)
                .withStyle(style -> {
                    return style.withColor(ChatFormatting.GREEN)
                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, webstoreURL))
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent(webstoreURL)));
                });

        Component server = new TextComponent(plugin.getServerInformation().getServer().getName()).withStyle(ChatFormatting.GREEN);

        Stream.of(
                new TextComponent(ForgeMessageUtil.format("information_title") + " ").withStyle(ChatFormatting.GRAY),
                new TextComponent(ForgeMessageUtil.format("information_sponge_server") + " ").withStyle(ChatFormatting.GRAY).append(server),
                new TextComponent(ForgeMessageUtil.format("information_currency", plugin.getServerInformation().getAccount().getCurrency().getIso4217()))
                        .withStyle(ChatFormatting.GRAY),
                new TextComponent(ForgeMessageUtil.format("information_domain", "")).withStyle(ChatFormatting.GRAY).append(webstore)
        ).forEach(message -> ForgeMessageUtil.sendMessage(context.getSource(), message));

        return 1;
    }
}
