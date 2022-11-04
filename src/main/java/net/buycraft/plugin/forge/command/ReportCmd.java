package net.buycraft.plugin.forge.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.buycraft.plugin.forge.BuycraftPlugin;
import net.buycraft.plugin.shared.util.ReportBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportCmd implements Command<CommandSourceStack> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

    private final BuycraftPlugin plugin;

    public ReportCmd(final BuycraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("report_wait"))
                .setStyle(BuycraftPlugin.SUCCESS_STYLE));

        plugin.getPlatform().executeAsync(() -> {
            String serverIP = plugin.getServer().getLocalIp().trim().isEmpty() ? "0.0.0.0" : plugin.getServer().getLocalIp().trim();

            ReportBuilder builder = ReportBuilder.builder()
                    .client(plugin.getHttpClient())
                    .configuration(plugin.getConfiguration())
                    .platform(plugin.getPlatform())
                    .duePlayerFetcher(plugin.getDuePlayerFetcher())
                    .ip(serverIP)
                    .port(plugin.getServer().getPort())
                    .serverOnlineMode(plugin.getServer().usesAuthentication())
                    .build();

            String filename = "report-" + DATE_FORMAT.format(new Date()) + ".txt";
            Path p = plugin.getBaseDirectory().resolve(filename);
            String generated = builder.generate();

            try (BufferedWriter w = Files.newBufferedWriter(p, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW)) {
                w.write(generated);
                ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("report_saved", p.toAbsolutePath().toString()))
                        .setStyle(BuycraftPlugin.INFO_STYLE));
            } catch (IOException e) {
                ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("report_cant_save"))
                        .setStyle(BuycraftPlugin.ERROR_STYLE));
                plugin.getLogger().info(generated);
            }
        });
        return 1;
    }
}
