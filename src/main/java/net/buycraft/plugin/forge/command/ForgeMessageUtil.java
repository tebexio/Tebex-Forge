package net.buycraft.plugin.forge.command;

import net.minecraft.command.CommandSource;
import net.minecraft.util.text.ITextComponent;

final class ForgeMessageUtil {

    //Look idk, forge methods are still kinda busted in how they work, so a 1 method util it is \o/
    static void sendMessage(CommandSource source, ITextComponent message) {
        if(source.getEntity() != null) source.getEntity().sendMessage(message);
        else if (source.getServer() != null) source.getServer().sendMessage(message);
    }

}
