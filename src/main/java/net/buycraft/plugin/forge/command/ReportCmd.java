package net.buycraft.plugin.forge.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.buycraft.plugin.forge.BuycraftPlugin;
import net.buycraft.plugin.shared.util.ReportBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextComponentString;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportCmd implements Command<CommandSource> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

    private final BuycraftPlugin plugin;

    public ReportCmd(final BuycraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(plugin.getI18n().get("report_wait")).setStyle(BuycraftPlugin.SUCCESS_STYLE));

        plugin.getPlatform().executeAsync(() -> {
            String serverIP = plugin.getServer().getServerHostname().trim().isEmpty() ? "0.0.0.0" : plugin.getServer().getServerHostname().trim();
            int serverPort = plugin.getServer().getServerPort();

            ReportBuilder builder = ReportBuilder.builder()
                    .client(plugin.getHttpClient())
                    .configuration(plugin.getConfiguration())
                    .platform(plugin.getPlatform())
                    .duePlayerFetcher(plugin.getDuePlayerFetcher())
                    .ip(serverIP)
                    .port(serverPort)
                    .listingUpdateTask(plugin.getListingUpdateTask())
                    .serverOnlineMode(plugin.getServer().isServerInOnlineMode())
                    .build();

            String filename = "report-" + DATE_FORMAT.format(new Date()) + ".txt";
            Path p = plugin.getBaseDirectory().resolve(filename);
            String generated = builder.generate();

            try (BufferedWriter w = Files.newBufferedWriter(p, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW)) {
                w.write(generated);
                ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(plugin.getI18n().get("report_saved", p.toAbsolutePath().toString())).setStyle(BuycraftPlugin.INFO_STYLE));
            } catch (IOException e) {
                ForgeMessageUtil.sendMessage(context.getSource(), new TextComponentString(plugin.getI18n().get("report_cant_save")).setStyle(BuycraftPlugin.ERROR_STYLE));
                plugin.getLogger().info(generated);
            }
        });
        return 1;
    }
}
