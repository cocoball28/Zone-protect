package org.zone.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.util.Nameable;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.Identifiable;
import org.zone.ZonePlugin;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.context.ErrorContext;
import org.zone.permissions.ZonePermission;
import org.zone.region.Zone;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.entity.player.move.greetings.GreetingsFlag;
import org.zone.region.flag.entity.player.move.leaving.LeavingFlag;
import org.zone.region.group.Group;
import org.zone.region.shop.transaction.price.PriceType;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

/**
 * Store house for all messages in this plugin {@link ZonePlugin} to make translation easier
 */
public final class Messages {

    /**
     * Constructor of the {@link Messages} class
     *
     * @throws RuntimeException since this class should not be constructed
     */
    private Messages() {
        throw new RuntimeException("Should not be constructed");
    }

    public static Component getNotEnough() {
        return Component.text("You do not have enough money for that").color(NamedTextColor.RED);
    }

    public static Component getInvalidBlock() {
        return Component.text("Cannot find a block to use").color(NamedTextColor.RED);
    }

    public static Component getInvalidPriceType(PriceType type) {
        return Component
                .text("Cannot accept price type of " + type.name())
                .color(NamedTextColor.RED);
    }

    public static Component getPlayerOnlyMessage() {
        return Component.text("Player only command").color(NamedTextColor.RED);
    }

    public static Component getLeftZoneMembersMessage(Identifiable identifiable) {
        return Component
                .text("You left the zone of ")
                .color(NamedTextColor.AQUA)
                .append(Component.text(identifiable.getName()).color(NamedTextColor.GOLD));
    }

    public static Component getJoinedZoneMessage(Identifiable identifiable) {
        return Component
                .text("You joined the zone of ")
                .color(NamedTextColor.AQUA)
                .append(Component.text(identifiable.getName()).color(NamedTextColor.GOLD));
    }

    public static Component getZoneSavingError(ConfigurateException ce) {
        return Component.text("Error when saving: " + ce.getMessage()).color(NamedTextColor.RED);
    }

    public static Component getZoneRegionBuilderEnabled() {
        return Component
                .text("Region builder mode enabled. Run ")
                .color(NamedTextColor.AQUA)
                .append(Component.text("'/zone create bounds end'").color(NamedTextColor.GOLD));
    }

    public static Component getDuplicateNameError() {
        return Component.text("Cannot use that name").color(NamedTextColor.RED);
    }

    public static Component getMessageTag() {
        return Component.text("Message: ").color(NamedTextColor.AQUA);
    }

    public static Component getNoMessageSet() {
        return Component.text("No message set by user").color(NamedTextColor.RED);
    }

    public static Component getFlagMessageView(GreetingsFlag greetingsFlag) {
        return getMessageTag().append(greetingsFlag.getGreetingsMessage());
    }

    public static Component getUpdatedMessage(Identifiable type) {
        return Component
                .text("Updated ")
                .color(NamedTextColor.AQUA)
                .append(Component.text(type.getName()).color(NamedTextColor.GOLD));
    }

    public static Component getNotInRegion(Identifiable parent) {
        return Component
                .text("Region must be within '" + parent.getId() + "'")
                .color(NamedTextColor.RED);
    }

    public static Component getFormattedErrorMessage(String e) {
        return Component.text(e).color(NamedTextColor.RED);
    }

    public static Component getUnknownError() {
        return Component.text("Unknown error").color(NamedTextColor.RED);
    }

    public static Component getLoadingZonesStart() {
        return Component.text("|---|Loading Zones|---|").color(NamedTextColor.AQUA);
    }

    public static Component getZonesLoadingFrom(String zonesFolderPath) {
        return Component
                .text("|- Loading from '")
                .color(NamedTextColor.DARK_AQUA)
                .append(Component.text(zonesFolderPath).color(NamedTextColor.WHITE))
                .append(Component.text("'").color(NamedTextColor.DARK_AQUA));
    }

    public static Component getZonesLoadingFail(String keyFilePath) {
        return Component
                .text("Could not load zone of '")
                .color(NamedTextColor.RED)
                .append(Component.text(keyFilePath).color(NamedTextColor.DARK_RED))
                .append(Component
                        .text("'. Below is details on why (this is not a crash)")
                        .color(NamedTextColor.RED));
    }

    public static Component getZonesLoaded(Collection<Zone> zoneCollection) {
        return Component
                .text("|---|Loaded " + zoneCollection.size() + " Zones|---|")
                .color(NamedTextColor.AQUA);
    }

    public static Component getMissingPermissionForCommand(ArgumentCommand argumentCommand) {
        return Component
                .text(" You do not have permission for that command" + ". You require '")
                .color(NamedTextColor.RED)
                .append(Component
                        .text(argumentCommand
                                .getPermissionNode()
                                .map(ZonePermission::getPermission)
                                .orElse("Unknown"))
                        .color(NamedTextColor.DARK_RED))
                .append(Component.text("'").color(NamedTextColor.RED));
    }

    public static Component getNoZoneCreatedError() {
        return Component
                .text("A region needs to be started. Use ")
                .color(NamedTextColor.RED)
                .append(Component.text("'/zone create bounds ...'").color(NamedTextColor.DARK_RED));
    }

    public static Component getFailedToFindParentZone(Zone zone) {
        return Component
                .text("Could not find parent zone of ")
                .color(NamedTextColor.RED)
                .append(Component
                        .text("'" + zone.getParentId().orElse("Unknown") + "'")
                        .color(NamedTextColor.RED));
    }

    public static Component getCreatedZoneMessage(@SuppressWarnings("TypeMayBeWeakened") Zone zone) {
        return Component
                .text("Created a new zone of ")
                .color(NamedTextColor.AQUA)
                .append(Component.text(zone.getName()).color(NamedTextColor.GOLD));
    }

    public static Component getFailedToLoadFlag(@SuppressWarnings("TypeMayBeWeakened") FlagType<?> type) {
        return Component
                .text("Failed to load flag of ")
                .color(NamedTextColor.RED)
                .append(Component
                        .text(type.getId())
                        .color(NamedTextColor.DARK_RED)
                        .decorate(TextDecoration.BOLD));
    }

    public static Component getPageTooLow() {
        return Component.text("Page needs to be 1 or more").color(NamedTextColor.RED);
    }

    public static Component getGroupInfo(@SuppressWarnings("TypeMayBeWeakened") Group group) {
        return getGroupTag().append(Component.text(group.getName()).color(NamedTextColor.GOLD));
    }

    public static Component getTotalInfo(Collection<?> memberIds) {
        return getTotalTag().append(Component.text(memberIds.size()).color(NamedTextColor.GOLD));
    }

    public static Component getPagesInfo(int page) {
        return getPageTag().append(Component.text(page).color(NamedTextColor.GOLD));
    }

    private static Component getGroupTag() {
        return Component.text("Group: ").color(NamedTextColor.AQUA);
    }

    public static Component getTotalTag() {
        return Component.text("Total: ").color(NamedTextColor.AQUA);
    }

    public static Component getPageTag() {
        return Component.text("Page: ").color(NamedTextColor.AQUA);
    }

    public static Component getMemberTag() {
        return Component.text("Members: ").color(NamedTextColor.AQUA);
    }

    public static Component getNameTag() {
        return Component.text("Name: ").color(NamedTextColor.AQUA);
    }

    public static Component getVersionTag() {
        return Component.text("Version: ").color(NamedTextColor.AQUA);
    }

    public static Component getSourceTag() {
        return Component.text("Github: ").color(NamedTextColor.AQUA);
    }

    public static Component getZonesTag() {
        return Component.text("Zones: ").color(NamedTextColor.AQUA);
    }

    public static Component getBalanceTag() {
        return Component.text("Balance: ").color(NamedTextColor.AQUA);
    }

    public static Component getOwnerTag() {
        return Component.text("Owner: ").color(NamedTextColor.AQUA);
    }

    public static Component getZoneNameInfo(Identifiable zone) {
        return Messages.getNameInfo(zone.getName());
    }

    public static Component getMembersInfo(Collection<UUID> members) {
        return getMemberTag().append(Component.text(members.size()).color(NamedTextColor.GOLD));
    }

    public static Component getNameInfo(String pluginName) {
        return getNameTag().append(Component.text(pluginName).color(NamedTextColor.GOLD));
    }

    public static Component getVersionInfo(String pluginVersion) {
        return getVersionTag().append(Component.text(pluginVersion).color(NamedTextColor.GOLD));
    }

    public static Component getSourceInfo(String pluginGithub) {
        return getSourceTag().append(Component.text(pluginGithub).color(NamedTextColor.GOLD));
    }

    public static Component getZonesCountInfo(String amount) {
        return getZonesTag().append(Component.text(amount).color(NamedTextColor.GOLD));
    }

    public static Component getEnterLeavingMessage() {
        return Component.text("Enter leaving message").color(NamedTextColor.RED);
    }

    public static Component getEnterGreetingsMessage() {
        return Component.text("Enter leaving message").color(NamedTextColor.RED);
    }

    public static Component getZoneFlagLeavingMessageSet(Component message) {
        return Component
                .text("Leaving message is now: ")
                .color(NamedTextColor.AQUA)
                .append(message);
    }

    public static Component getLeavingMessage(LeavingFlag leavingFlag) {
        return getMessageTag().append(leavingFlag.getLeavingMessage());
    }

    public static Component getBalance(BigDecimal decimal) {
        return getBalanceTag().append(Component
                .text(decimal.toString())
                .color(NamedTextColor.GOLD));

    }

    public static Component getGreetingMessageSet(Component message) {
        return Component
                .text("Zone greetings message set to: ")
                .color(NamedTextColor.AQUA)
                .append(message);
    }

    public static Component getGreetingsMessageRemoved() {
        return Component
                .text("Zone greetings message removed from this zone!")
                .color(NamedTextColor.AQUA);
    }

    public static Component getAlreadyBeingEditedError() {
        return Component.text("Zone is already being edited").color(NamedTextColor.RED);
    }

    public static Component getMustBeWithinZoneToEditError() {
        return Component.text("Must be within the zone to start editing").color(NamedTextColor.RED);
    }

    public static Component getEntryName(Nameable user) {
        return getEntry(user.name());
    }

    public static Component getEntry(String name) {
        return Component.text("- " + name).color(NamedTextColor.AQUA);

    }

    public static Component getBalanceEntry(Currency currency, BigDecimal amount) {
        return Component
                .text("- ")
                .color(NamedTextColor.AQUA)
                .append(currency.symbol())
                .append(Component.text(" " + amount.toString()).color(NamedTextColor.AQUA));
    }

    public static Component getGroupStart(Identifiable zone) {
        return Component
                .text("----====[")
                .color(NamedTextColor.RED)
                .append(Component
                        .text(zone.getName())
                        .color(NamedTextColor.AQUA)
                        .append(Component.text("]====----").color(NamedTextColor.RED)));
    }

    public static Component getName(Identifiable zone) {
        return Component.text(zone.getName()).color(NamedTextColor.AQUA);
    }

    public static Component getError(ErrorContext error) {
        return Component.text(error.error()).color(NamedTextColor.RED);
    }

    public static Component getEnabledTag() {
        return Component.text("Enabled: ").color(NamedTextColor.AQUA);
    }

    public static Component getEnabledInfo(boolean check) {
        return getEnabledTag().append(Component.text(check).color(NamedTextColor.GOLD));
    }

    public static Component getMovedGroupInfo(
            GameProfile profile, Identifiable previous, Identifiable group) {
        return Component
                .text("Moved " +
                        profile.name().orElse("Unknown name") +
                        " from " +
                        previous.getName() +
                        " to " +
                        group.getName())
                .color(NamedTextColor.AQUA);
    }

    public static Component getPlayerMovedGroupInfo(
            Identifiable zone, Identifiable previous, Identifiable group) {
        return Component
                .text("You have been moved in '" +
                        zone.getName() +
                        "' from '" +
                        previous.getName() +
                        "' to '" +
                        group.getName() +
                        "'")
                .color(NamedTextColor.AQUA);
    }

    public static Component getZoneConfigReloadedInfo() {
        return Component.text("Reloaded the Config successfully!").color(NamedTextColor.AQUA);
    }

    public static Component getZonesReloadedInfo() {
        return Component
                .text("Reloaded the config of the available zones successfully!")
                .color(NamedTextColor.AQUA);
    }

    public static Component getZoneConfigReloadFail() {
        return Component
                .text("Couldn't reload config! Below is the cause of the error")
                .color(NamedTextColor.RED);
    }

    public static Component getZoneVisibilityTag() {
        return Component.text("Visibility of zone: ").color(NamedTextColor.AQUA);
    }

    public static Component getZoneVisibility(String visibilityName) {
        return getZoneVisibilityTag().append(Component
                .text(visibilityName)
                .color(NamedTextColor.GOLD));
    }

    public static Component getInvitedPlayer() {
        return Component.text("Players have been invited to the zone!").color(NamedTextColor.AQUA);
    }

    public static Component getGotInvite(@NotNull Nameable player, @NotNull Identifiable zone) {
        return Component
                .text(player.name() + " has invited you to join " + zone.getName())
                .color(NamedTextColor.AQUA);
    }

    public static Component getZonePrivateError() {
        return Component.text("Zone is private!").color(NamedTextColor.RED);
    }

    public static Component getInvitesPlayersTag() {
        return Component.text("Current players invited to this zone:").color(NamedTextColor.AQUA);
    }

    public static Component getUnknownUserName() {
        return Component.text("Unknown Username").color(NamedTextColor.RED);
    }

    public static Component getInvitationDenied(Identifiable zone) {
        return Component
                .text("You denied the invitation from " + zone.getName())
                .color(NamedTextColor.RED);
    }

    public static Component getCreatedGroup(Identifiable group, Identifiable parentGroup) {
        return Component.text("Created group with the name " + group.getName() + " based on " + parentGroup.getName() + " group")
                .color(NamedTextColor.AQUA);
    }

    public static Component getZoneNotBeingEdited() {
        return Component.text("Zone is not being edited").color(NamedTextColor.RED);
    }

    public static Component getEditedZone() {
        return Component.text("Zone had been edited successfully!").color(NamedTextColor.AQUA);
    }

    public static Component getZoneCreateEndCommandDescription() {
        return Component.text("Ends the creation by bounds. Will use your location as ending");
    }

    public static Component getZoneCreateStartCommandDescription() {
        return Component.text("Create a zone via walking edge to edge");
    }

    public static Component getZoneCreateSubStartCommandDescription() {
        return Component.text("Creates a sub region by walking end to end");
    }

    public static Component getZoneCreateChunkStartCommandDescription() {
        return Component.text("Creates a chunk region");
    }

    public static Component getZoneCreateChunkSubStartCommandDescription() {
        return Component.text("Creates a chunk sub region");
    }

    public static Component getZonePluginInfoCommandDescription() {
        return Component.text("Command to show the plugin info");
    }

    public static Component getZoneInviteAcceptCommandDescription() {
        return Component.text("Accept the invite from a player");
    }

    public static Component getZoneInviteDenyCommandDescription() {
        return Component.text("Deny the invite from a player");
    }

    public static Component getZoneInvitePlayerCommandDescription() {
        return Component.text("Invite a player to a zone");
    }

    public static Component getZoneInvitePlayerViewCommandDescription() {
        return Component.text("View the invites of a zone");
    }

    public static Component getJoinZoneCommandDescription() {
        return Component.text("Become a member of a zone");
    }

    public static Component getLeaveZoneCommandDescription() {
        return Component.text("Leaves a zone");
    }

    public static Component getEditBoundsStartCommandDescription() {
        return Component.text("Edit the size of the region");
    }

    public static Component getEditBoundsEndCommandDescription() {
        return Component.text("End editing the size of the region");
    }

    public static Component getZoneInfoCommandDescription() {
        return Component.text("Show info about the zone");
    }

    public static Component getZoneInfoBoundsShowCommandDescription() {
        return Component.text("Show the bounds of a specified region");
    }

    public static Component getEnderManGriefEnableCommandDescription() {
        return Component.text("Enable/Disable Enderman grief");
    }

    public static Component getEnderManGriefViewCommandDescription() {
        return Component.text("View info on the Enderman Grief flag");
    }

    public static Component getEnderMiteGriefEnableCommandDescription() {
        return Component.text("Enable/Disable EnderMite grief");
    }

    public static Component getEnderMiteGriefViewCommandDescription() {
        return Component.text("View info on EnderMite Grief");
    }

    public static Component getZombieGriefEnableCommandDescription() {
        return Component.text("Enable/Disable Zombie Grief");
    }

    public static Component getZombieGriefViewCommandDescription() {
        return Component.text("View info on Zombie Grief");
    }

    public static Component getFarmLandTrampleEnableCommandDescription() {
        return Component.text("Enable/disable farmland trampling");
    }

    public static Component getFarmLandTrampleViewCommandDescription() {
        return Component.text("View info on farmland trampling");
    }

    public static Component getSkeletonGriefEnableCommandDescription() {
        return Component.text("Enable/Disable Skeleton grief");
    }

    public static Component getSkeletonGriefViewCommandDescription() {
        return Component.text("View info on Skeleton grief");
    }

    public static Component getCreeperGriefEnableCommandDescription() {
        return Component.text("Enable/disable creeper grief");
    }

    public static Component getCreeperGriefViewCommandDescription() {
        return Component.text("View info on the Creeper Grief flag");
    }

    public static Component getEnderDragonGriefEnableCommandDescription() {
        return Component.text("Enable/Disable EnderDragon Grief");
    }

    public static Component getEnderDragonGriefViewCommandDescription() {
        return Component.text("View info on EnderDragon grief");
    }

    public static Component getTntDefuseEnableCommandDescription() {
        return Component.text("Enable or disable the tnt defuse flag");
    }

    public static Component getTntDefuseViewCommandDescription() {
        return Component.text("View info about the tnt defuse flag");
    }

    public static Component getWitherGriefEnableCommandDescription() {
        return Component.text("Enable/Disable Wither grief");
    }

    public static Component getWitherGriefViewCommandDescription() {
        return Component.text("View info on Wither grief");
    }

    public static Component getDamageAttackEnableCommandDescription() {
        return Component.text("Enable/disable the Damage Flag");
    }

    public static Component getDamageAttackSetGroupCommandDescription() {
        return Component.text("Sets the group for Entity Damage Player flag");
    }

    public static Component getDamageAttackViewCommandDescription() {
        return Component.text("View the details of Fall damage flag");
    }

    public static Component getPlayerFallDamageEnableCommandDescription() {
        return Component.text("Command to enable/disable the fall damage flag");
    }

    public static Component getPlayerFallDamageSetGroupCommandDescription() {
        return Component.text("Sets the group for Entity Damage Player flag");
    }

    public static Component getPlayerFallDamageViewCommandDescription() {
        return Component.text("View the details of Entity damage player flag");
    }

    public static Component getPlayerFireDamageEnableCommandDescription() {
        return Component.text("Enable or Disable Fire Damage");
    }

    public static Component getPlayerFireDamageSetGroupCommandDescription() {
        return Component.text("Set the Group Key for Fire Damage");
    }

    public static Component getPlayerFireDamageViewCommandDescription() {
        return Component.text("View info on the Fire Damage");
    }

    public static Component getZoneViewBalanceCommandDescription() {
        return Component.text("View the balance for the zone");
    }

    public static Component getMonsterEntryEnableCommandDescription() {
        return Component.text("Command to enable/disable monster prevention flag");
    }

    public static Component getMonsterEntryViewCommandDescription() {
        return Component.text("Displays info about the Monster Prevention flag");
    }

    public static Component getPlayerEntryEnableCommandDescription() {
        return Component.text("Command to enable/disable Player Prevention");
    }

    public static Component getPlayerEntrySetGroupCommandDescription() {
        return Component.text("Sets the group for Player Prevention flag");
    }

    public static Component getPlayerEntryViewCommandDescription() {
        return Component.text("View the details of  Fall damage flag");
    }

    public static Component getCreateCustomGroupCommandDescription() {
        return Component.text("Create a custom group");
    }

    public static Component getBlockBreakEnabledCommandDescription() {
        return Component.text("Sets if the prevention to break blocks is enabled");
    }

    public static Component getBlockBreakSetGroupCommandDescription() {
        return Component.text("Sets the group of block break flag");
    }

    public static Component getBlockBreakViewCommandDescription() {
        return Component.text("View the details on block break");
    }

    public static Component getInteractDoorEnableCommandDescription() {
        return Component.text("Sets if interaction with door should be enabled");
    }

    public static Component getInteractDoorSetGroupCommandDescription() {
        return Component.text("Sets the minimum group that can interact with doors");
    }

    public static Component getInteractDoorViewCommandDescription() {
        return Component.text("View the details on door interaction");
    }

    public static Component getInteractItemFrameEnableCommandDescription() {
        return Component.text("Command to enable/disable Itemframe interaction");
    }

    public static Component getInteractItemFrameSetGroupCommandDescription() {
        return Component.text("Sets the minimum group that can interact with itemframes");
    }

    public static Component getInteractItemFrameViewCommandDescription() {
        return Component.text("View the details of Interact Itemframe");
    }

    public static Component getBlockPlaceEnableCommandDescription() {
        return Component.text("Sets if the prevention to place blocks is enabled");
    }

    public static Component getBlockPlaceSetGroupCommandDescription() {
        return Component.text("Sets the group of block place");
    }

    public static Component getBlockPlaceViewCommandDescription() {
        return Component.text("View the details on block placement");
    }

    public static Component getMemberGroupAddCommandDescription() {
        return Component.text("Add a member to a group");
    }

    public static Component getMemberGroupViewCommandDescription() {
        return Component.text("View the members in a zone by group");
    }

    public static Component getGreetingsRemoveCommandDescription() {
        return Component.text("Command for removing the greetings message");
    }

    public static Component getGreetingsSetMessageCommandDescription() {
        return Component.text("Command for setting greeting message for a zone");
    }

    public static Component getGreetingsViewCommandDescription() {
        return Component.text("Command for viewing the greeting message of a specific zone");
    }

    public static Component getLeavingRemoveCommandDescription() {
        return Component.text("Removes the leaving flag");
    }

    public static Component getLeavingSetMessageCommandDescription() {
        return Component.text("Sets the message of the leaving message");
    }

    public static Component getLeavingViewCommandDescription() {
        return Component.text("View the leaving message flag");
    }

    public static Component getZoneVisibilitySetCommandDescription() {
        return Component.text("Set the visibility of a zone");
    }

    public static Component getZoneVisibilityViewCommandDescription() {
        return Component.text("View the visibility of a zone");
    }

    public static Component getGreetingsDisplaySetChatCommandDescription() {
        return Component.text("Set the display type of the greetings message to chat");
    }

    public static Component getGreetingsDisplaySetTitleCommandDescription() {
        return Component.text("Set the display type of the greetings message to title");
    }

    public static Component getGreetingsDisplaySetBossBarCommandDescription() {
        return Component.text("Set the display type of the greetings message to boss bar");
    }

    public static Component getLeavingDisplaySetChatCommandDescription() {
        return Component.text("Set the display type of the leaving message to chat");
    }

    public static Component getLeavingDisplaySetTitleCommandDescription() {
        return Component.text("Set the display type of the leaving message to title");
    }

    public static Component getLeavingDisplaySetBossBarCommandDescription() {
        return Component.text("Set the display type of the leaving message to boss bar");
    }

    public static Component getAllZoneCommandsTag() {
        return Component.text("All Zone commands");
    }

    public static Component getAllZoneCommandsExtendedTag() {
        return Component.text("All commands for the plugin Zones");
    }

    public static Component getCommandUsage() {
        return Component.text("/zone <arg>");
    }

    public static Component getExp(int exp) {
        return Component.text(exp + "EXP").color(NamedTextColor.AQUA);
    }

    public static Component getLevel(int exp) {
        return Component.text("Level: " + exp).color(NamedTextColor.AQUA);
    }

    public static Component getPowerLevel(long price) {
        return Component.text(price + " power levels").color(NamedTextColor.AQUA);
    }

    public static Component getGreetingsFlagNotFound() {
        return Component.text("Greetings flag not found").color(NamedTextColor.RED);
    }

    public static Component getLeavingFlagNotFound() {
        return Component.text("Leaving flag not found").color(NamedTextColor.RED);
    }

    public static Component getDisplaySuccessfullyChangedStatement() {
        return Component.text(" display had been successfully changed to ");
    }

    public static Component getGreetingsDisplaySuccessfullyChangedToChat() {
        return Component
                .text("Greetings message" +
                        getDisplaySuccessfullyChangedStatement() +
                        "chat").color(NamedTextColor.AQUA);
    }

    public static Component getGreetingsDisplaySuccessfullyChangedToTitle() {
        return Component
                .text("Greetings message" +
                        getDisplaySuccessfullyChangedStatement() +
                        "title").color(NamedTextColor.AQUA);
    }

    public static Component getGreetingsDisplaySuccessfullyChangedToBossBar() {
        return Component
                .text("Greetings message" +
                        getDisplaySuccessfullyChangedStatement() +
                        "boss bar").color(NamedTextColor.AQUA);
    }

    public static Component getLeavingDisplaySuccessfullyChangedToChat() {
        return Component
                .text("Leaving message" +
                        getDisplaySuccessfullyChangedStatement() +
                        "chat").color(NamedTextColor.AQUA);
    }

    public static Component getLeavingDisplaySuccessfullyChangedToTitle() {
        return Component
                .text("Leaving message" +
                        getDisplaySuccessfullyChangedStatement() +
                        "title").color(NamedTextColor.AQUA);
    }

    public static Component getLeavingDisplaySuccessfullyChangedToBossBar() {
        return Component
                .text("Leaving message" +
                        getDisplaySuccessfullyChangedStatement() +
                        "boss bar").color(NamedTextColor.AQUA);
    }

}