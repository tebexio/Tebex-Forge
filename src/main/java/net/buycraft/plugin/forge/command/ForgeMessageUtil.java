package net.buycraft.plugin.forge.command;

import com.google.common.collect.ImmutableMap;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.text.MessageFormat;
import java.util.Map;

public final class ForgeMessageUtil {

    //This is my lazy way to not have to change every single string in the project
    private static final Map<String, String> englishTranslations = ImmutableMap.<String, String>builder()
            .put("no_permission", "You don't have permission to use Buycraft administrative commands.")
            .put("usage", "Usage for the Buycraft plugin:")
            .put("usage_forcecheck", "Forces a purchase check.")
            .put("usage_information", "Retrieves public information about the webstore this server is associated with.")
            .put("usage_refresh", "Refreshes the list of categories and packages.")
            .put("usage_report", "Generates a report with debugging information you can send to support.")
            .put("usage_secret", "Sets the secret key to use for this server.")
            .put("usage_signupdate", "Forces an update to your recent purchase signs.")
            .put("usage_sponge_listing", "Lists all available packages.")
            .put("no_params", "This command does not accept any parameters.")
            .put("need_secret_key", "You must set your secret key first with /tebex secret <secret>.")
            .put("already_checking_for_purchases", "A purchase check is already in progress.")
            .put("forcecheck_queued", "Successfully queued purchase check.")
            .put("information_no_server", "No server information found.")
            .put("information_title", "Information on this server:")
            .put("information_server", "Server {0} for webstore {1}")
            .put("information_currency", "Server prices are in {0}")
            .put("information_sponge_server", "Server:")
            .put("information_domain", "Webstore domain: {0}")
            .put("refresh_queued", "Listing refresh queued.")
            .put("report_wait", "Please wait while we generate a report...")
            .put("report_saved", "Your report has been saved as {0}. Please include this information when contacting support.")
            .put("report_cant_save", "Unable to save report to the disk. The report has been sent to the console.")
            .put("secret_console_only", "For security reasons, your Buycraft secret key must be set via the console.")
            .put("secret_need_key", "You must specify a secret. You can find your secret at https://server.buycraft.net/settings/servers.")
            .put("secret_does_not_work", "The secret you provided does not work. Please check the secret and try again.")
            .put("secret_cant_be_saved", "Warning: The secret could not be saved to your configuration file.")
            .put("secret_success", "This server is now registered as server {0} for the web store {1}.")
            .put("sign_update_queued", "Successfully queued sign update.")
            .put("sponge_listing", "Listing")
            .put("categories", "Categories")
            .put("price", "Price")
            .put("amount_off", "({0} off!)")
            .put("previous_page", "Previous page")
            .put("next_page", "Next page")
            .put("view_all_categories", "View All Categories")
            .put("back_to_parent", "Back to Parent")
            .put("nothing_in_category", "There are no items in this category.")
            .put("cant_check_out", "An error occurred while generating your checkout link. Try again later.")
            .put("to_buy_this_package", "To buy your package, click this link:")
            .put("update_available", "A new version of BuycraftX ({0}) is available. Go to your server panel at https://server.buycraft.net/plugins to download the update.")
            .put("usage_coupon_subcommands", "Usage: /tebex coupon <create|delete>")
            .put("generic_api_operation_error", "An error occurred while communicating with the Buycraft API. Please ensure your secret key is correct.")
            .put("coupon_creation_arg_parse_failure", "There was an error while parsing your coupon creation command.")
            .put("coupon_creation_success", "Coupon {0} created!")
            .put("no_coupon_specified", "You did not specify a coupon code.")
            .put("usage_coupon", "Manage server coupons.")
            .put("coupon_listing", "Coupons: {0}")
            .put("coupon_not_found", "No coupon was found with that code.")
            .put("coupon_deleted", "Coupon deleted.")
            .put("to_view_this_category", "To view this category, click this link:")
            .put("usage_sendlink", "Sends a package or category link to a player.")
            .build();

    //Look idk, forge methods are still kinda busted in how they work, so a util it is \o/
    public static void sendMessage(CommandSourceStack source, Component message) {
        if (source.getEntity() != null) source.getEntity().sendSystemMessage(message);
        else source.getServer().sendSystemMessage(message);
    }

    public static String format(String message, Object... params) {
        return MessageFormat.format(englishTranslations.getOrDefault(message, message), params);
    }

}
