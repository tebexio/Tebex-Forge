package net.buycraft.plugin.forge.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.buycraft.plugin.forge.BuycraftPlugin;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.stream.Stream;

public class InfoCmd implements Command<CommandSource> {
    private final BuycraftPlugin plugin;

    public InfoCmd(final BuycraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSource> context) {
        if (plugin.getApiClient() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(ForgeMessageUtil.format("generic_api_operation_error"))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 1;
        }

        if (plugin.getServerInformation() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(ForgeMessageUtil.format("information_no_server"))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 1;
        }

        String webstoreURL = plugin.getServerInformation().getAccount().getDomain();

        ITextComponent webstore = new TextComponentString(webstoreURL)
                .applyTextStyle(style -> {
                    style.setColor(TextFormatting.GREEN);
                    style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, webstoreURL));
                    style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(webstoreURL)));
                });

        ITextComponent server = new TextComponentString(plugin.getServerInformation().getServer().getName()).applyTextStyle(TextFormatting.GREEN);

        Stream.of(
                new TextComponentString(ForgeMessageUtil.format("information_title") + " ").applyTextStyle(TextFormatting.GRAY),
                new TextComponentString(ForgeMessageUtil.format("information_sponge_server") + " ").applyTextStyle(TextFormatting.GRAY).appendSibling(server),
                new TextComponentString(ForgeMessageUtil.format("information_currency", plugin.getServerInformation().getAccount().getCurrency().getIso4217()))
                        .applyTextStyle(TextFormatting.GRAY),
                new TextComponentString(ForgeMessageUtil.format("information_domain", "")).applyTextStyle(TextFormatting.GRAY).appendSibling(webstore)
        ).forEach(message -> ForgeMessageUtil.sendMessage(context.getSource(), message));

        return 1;
    }
}
