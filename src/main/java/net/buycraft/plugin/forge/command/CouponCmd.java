package net.buycraft.plugin.forge.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.buycraft.plugin.data.Coupon;
import net.buycraft.plugin.forge.BuycraftPlugin;
import net.buycraft.plugin.shared.util.CouponUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;


import java.io.IOException;

public class CouponCmd {
    private final BuycraftPlugin plugin;

    public CouponCmd(final BuycraftPlugin plugin) {
        this.plugin = plugin;
    }

    public int create(CommandContext<CommandSourceStack> context) {
        if (plugin.getApiClient() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("generic_api_operation_error"))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 0;
        }

        final Coupon coupon;
        try {
            coupon = CouponUtil.parseArguments(StringArgumentType.getString(context, "data").split(" "));
        } catch (Exception e) {
            ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("coupon_creation_arg_parse_failure", e.getMessage()))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 0;
        }

        plugin.getPlatform().executeAsync(() -> {
            try {
                plugin.getApiClient().createCoupon(coupon).execute();
                ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("coupon_creation_success", coupon.getCode()))
                        .setStyle(BuycraftPlugin.SUCCESS_STYLE));
            } catch (IOException e) {
                ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("generic_api_operation_error"))
                        .setStyle(BuycraftPlugin.ERROR_STYLE));
            }
        });

        return 1;
    }

    public int delete(CommandContext<CommandSourceStack> context) {
        if (plugin.getApiClient() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("generic_api_operation_error"))
                    .setStyle(BuycraftPlugin.ERROR_STYLE));
            return 0;
        }

        String code = StringArgumentType.getString(context, "code");
        plugin.getPlatform().executeAsync(() -> {
            try {
                plugin.getApiClient().deleteCoupon(code).execute();
                ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("coupon_deleted")).setStyle(BuycraftPlugin.SUCCESS_STYLE));
            } catch (Exception e) {
                ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(e.getMessage()).setStyle(BuycraftPlugin.ERROR_STYLE));
            }
        });

        return 1;
    }
}
