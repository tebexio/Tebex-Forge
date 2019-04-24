package net.buycraft.plugin.forge.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.buycraft.plugin.forge.BuycraftPlugin;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

public class BuyCommand implements Command<CommandSource> {

    private BuycraftPlugin plugin;

    public BuyCommand(BuycraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        if(plugin.getServerInformation() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(plugin.getI18n().get("information_no_server"))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 1;
        }

        ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString("                                            ").applyTextStyles(TextFormatting.STRIKETHROUGH));
        ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString("To view the webstore, click this link: ").applyTextStyle(style -> {
            style.setColor(TextFormatting.GREEN);
            style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, plugin.getServerInformation().getAccount().getDomain()));
        }));
        ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(plugin.getServerInformation().getAccount().getDomain()).applyTextStyle(style -> {
            style.setColor(TextFormatting.BLUE);
            style.setUnderlined(true);
            style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, plugin.getServerInformation().getAccount().getDomain()));
        }));
        ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString("                                            ").applyTextStyles(TextFormatting.STRIKETHROUGH));
        return 1;
    }
}
