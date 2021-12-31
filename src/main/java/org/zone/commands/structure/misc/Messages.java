package org.zone.commands.structure.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.Identifiable;
import org.zone.ZonePlugin;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.context.ErrorContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.move.player.leaving.LeavingFlag;
import org.zone.region.group.Group;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

/**
 * Store house for all messages in this plugin {@link ZonePlugin} to make translation easier
 */
public final class Messages {

    private Messages() {
        throw new RuntimeException("Could not construct class");
    }
    //Universal Messages
    public static Component getUniversalPlayerOnlyCommandError() {
        return Component.text("Player only command").color(NamedTextColor.RED);
    }

    public static Component getUniversalZoneSavingError(ConfigurateException ce) {
        return Component.text("Error when saving: " + ce.getMessage()).color(NamedTextColor.RED);
    }

    public static Component getUniversalZoneRegionBuilderEnabled() {
        return Component
                .text("Region builder mode enabled. Run ")
                .append(Component
                                .text("'/zone create end'")
                                .color(NamedTextColor.AQUA));
    }

    public static Component getUniversalDuplicateNameError() {
        return Component
                .text("Cannot use that name")
                .color(NamedTextColor.RED);
    }

    public static Component getUniversalMessageTag() {
        return Component.text("Message: ");
    }

    public static Component getUniversalNoMessageSet() {
        return getUniversalMessageTag().append(Component.text("No message set by user")).color(NamedTextColor.RED);
    }

    public static Component getUniversalFlagMessageView(Component message) {
        return getUniversalMessageTag().append(message);
    }

    public static Component getUniversalUpdatedFlag(Identifiable type) {
        return Component.text("Updated " + type.getName()).color(NamedTextColor.AQUA);
    }
    //Universal Messages end
    //Universal only for some special classes
    public static Component getOnlyZoneCreateEndCommandError1(@SuppressWarnings("TypeMayBeWeakened") Zone parent) {
        return Component.text("Region must be within " + parent.getId()).color(NamedTextColor.RED);
    }
    //Universal only for some special classes end or will continue

    //Custom messages
    public static Component getLoadingZonesStart() {
        return Component.text("|---|Loading Zones|---|").color(NamedTextColor.AQUA);
    }

    public static Component getZonesLoadingfrom(String zonesFolderpath) {
        return Component.text("|- Loading from '" + zonesFolderpath + "'").color(NamedTextColor.DARK_AQUA);
    }

    public static Component getZonesLoadingFail(String keyfilepath) {
        return Component
                .text("Could not load zone of '" +
                              keyfilepath +
                              "'. Below is details on why (this is not a crash)")
                .color(NamedTextColor.RED);
    }

    public static Component getZonesLoaded(Collection<Zone> zoneCollection) {
        return Component
                .text("|---|Loaded " + zoneCollection.size() + " Zones|---|")
                .color(NamedTextColor.AQUA);
    }

    public static Component getZoneSpongeCommandnotEmptyErrorElse() {
        return Component.text("Unknown error").color(NamedTextColor.RED);
    }

    public static Component getZoneSpongeCommandnotgetHasPermission(ArgumentCommand argumentCommand) {
        return Component
                .text(" You do not have permission for that command" +
                              ". You" +
                              " require " +
                              argumentCommand.getPermissionNode())
                .color(NamedTextColor.RED);
    }

    public static Component getZonesCreateEndCommandrunopZoneEmptyError() {
        return Component.text("A region needs to be started. Use /zone create bounds " + "<name...>")
                .color(NamedTextColor.RED);
    }

    public static Component getZonesCreateEndCommandrunopParentEmptyError(Zone zone) {
        return Component.text("Could not find parent zone of " + zone.getParentId().orElse("None")).color(NamedTextColor.RED);
    }

    public static Component getZonesCreateEndCommandrunZoneCreated(@SuppressWarnings("TypeMayBeWeakened") Zone zone) {
        return Component
                .text("Created a new zone of ")
                .append(Component
                                .text(zone.getName())
                                .color(NamedTextColor.AQUA));
    }

    public static Component getCommandLauncherrunMethodError1Else() {
        return Component.text("Unknown error").color(NamedTextColor.RED);
    }

    public static Component getCommandLauncherrunMethodNoPermissionError(ArgumentCommand argumentCommand) {
        return Component
                .text("You do not have permission for that command. You " +
                              "require " +
                              argumentCommand.getPermissionNode())
                .color(NamedTextColor.RED);
    }

    public static Component getDefaultFlagFileFlagLoadFail(@SuppressWarnings("TypeMayBeWeakened") FlagType<?> type) {
        return Component
                .text("Failed to load flag of " + type.getId())
                .color(NamedTextColor.RED)
                .decorate(TextDecoration.BOLD);
    }

    public static Component getZoneFlagMemberGroupViewCommandrunPagelessthan1() {
        return Component.text("Page needs to be 1 or more").color(NamedTextColor.RED);
    }

    public static Component getZoneFlagMemberGroupViewCommand(@SuppressWarnings("TypeMayBeWeakened") Group group) {
        return Component
                .text("Group: ")
                .color(NamedTextColor.GOLD)
                .append(Component
                                .text(group.getName())
                                .color(NamedTextColor.AQUA));
    }

    public static Component getZoneFlagMemberGroupViewCommandTotal(Collection<UUID> memberIds) {
        return Component
                .text("Total: ")
                .color(NamedTextColor.GOLD)
                .append(Component
                                .text(memberIds.size())
                                .color(NamedTextColor.AQUA));
    }

    public static Component getZoneFlagMemberGroupViewCommandPages(int page) {
        return Component
                .text("Page: ")
                .color(NamedTextColor.GOLD)
                .append(Component.text(page).color(NamedTextColor.AQUA));
    }

    public static Component getZoneInfoCommandZoneName(Identifiable zone) {
        return Messages.getZonePluginInfoCommandPluginName(zone.getName());
    }

    public static Component getZoneInfoCommandZoneMembers(Collection<UUID> members) {
        return Component.text("Members: " + members.size()).color(NamedTextColor.AQUA);
    }

    public static Component getZonePluginInfoCommandPluginName(String pluginName) {
        return Component.text("Name: " + pluginName).color(NamedTextColor.AQUA);
    }

    public static Component getZonePluginInfoCommandPluginVersion(String pluginVersion) {
        return Component.text("Version: " + pluginVersion).color(NamedTextColor.AQUA);
    }

    public static Component getZonePluginInfoCommandPluginGithub(String pluginGithub) {
        return Component.text("Github: " + pluginGithub).color(NamedTextColor.AQUA);
    }

    public static Component getZonesPluginInfoCommandZonesNumber(String pluginZonesNumber) {
        return Component.text("Zones: " + pluginZonesNumber).color(NamedTextColor.AQUA);
    }

    public static Component getZoneFlagLeavingSetMessageCommandIfNoLeavingFlagFound() {
        return Component.text("Enter leaving message").color(NamedTextColor.RED);
    }

    public static Component getZoneFlagLeavingSetMessageCommandLeavingMessageSaved(Component message) {
        return Component.text("Leaving message is now ").append(message).color(NamedTextColor.AQUA);
    }

    public static Component getZoneFlagLeavingMessageViewCommandMessageViewMessage(LeavingFlag leavingFlag) {
        return Component
                .text("Message: ")
                .append(leavingFlag.getLeavingMessage()).color(NamedTextColor.AQUA);
    }

    public static Component getZoneFlagViewBalanceCommandBalanceMessage(@SuppressWarnings("TypeMayBeWeakened") Zone zone, BigDecimal decimal) {
        return Component
                .text(zone.getName())
                .color(NamedTextColor.AQUA)
                .append(Component
                                .text(" balance: ")
                                .color(NamedTextColor.AQUA)
                                .append(Component
                                                .text(decimal.toString())
                                                .color(NamedTextColor.GOLD)));
    }

    public static Component getZoneFlagGreetingsSetMesssageCommandSetMessage(Component message) {
        return Component
                .text("Zone greetings message set to: ")
                .append(message).color(NamedTextColor.AQUA);
    }

    public static Component getZoneFlagGreetingsRemoveCommandGreetingsRemovedMessage() {
        return Component.text("Zone greetings message removed from this " +
                                      "zone!").color(NamedTextColor.AQUA);
    }

    public static Component getZoneFlagBlockBreakSetGroupCommandGroupUpdatedFlagSaved() {
        return Component.text("Updated Block Break Interaction " + "group").color(NamedTextColor.AQUA);
    }

    public static Component getZoneFlagLeavingRemoveCommandRemovedMessage() {
        return Component.text("Removed leaving message").color(NamedTextColor.BLUE);
    }

    public static Component getEditBoundsStartCommandBeingEditedError() {
        return Component.text("Zone is already being edited").color(NamedTextColor.RED);
    }

    public static Component getEditBoundsStartCommandNotBeingInZoneError() {
        return Component.text("Must be within the zone to start editing").color(NamedTextColor.RED);
    }

    //More Custom messages to come ;)

    //Not useful if yes will create an issue
    public static Component getZoneFlagMemberGroupViewCommandUserName(@SuppressWarnings("TypeMayBeWeakened") User user) {
        return Component.text("- " + user.name()).color(NamedTextColor.AQUA);
    }

    public static Component getZoneFlagMemberZoneViewCommandMessage1(@SuppressWarnings("TypeMayBeWeakened") Zone zone) {
        return Component
                .text("----====[")
                .color(NamedTextColor.RED)
                .append(Component
                                .text(zone.getName())
                                .color(NamedTextColor.AQUA)
                                .append(Component.text("]====----").color(NamedTextColor.RED)));
    }

    public static Component getZoneArgumentReturnZonesName(@SuppressWarnings("TypeMayBeWeakened") Zone zone) {
        return Component.text(zone.getName()).color(NamedTextColor.AQUA);
    }

    public static Component getCommandLauncherrunMethodEmptyErrorError(ErrorContext error) {
        return Component.text(error.error()).color(NamedTextColor.RED);
    }

    public static Component getCommandLaunhcerrunMethodError1(String e) {
        return Component
                .text(e)
                .color(NamedTextColor.RED);
    }

    public static Component getZoneSpongeCommandError2Unknown(String message) {
        return Component.text(message).color(NamedTextColor.RED);
    }

    public static Component getZoneSpongeCommandError(ErrorContext error) {
        return Component.text(error.error()).color(NamedTextColor.RED);
    }

    public static Component getZoneSpongeCommandnotEmptyError(String e) {
        return Component.text(e).color(NamedTextColor.RED);
    }
    //Unuseful messages end or will continue
}