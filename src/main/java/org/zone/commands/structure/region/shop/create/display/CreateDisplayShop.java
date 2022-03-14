package org.zone.commands.structure.region.shop.create.display;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.util.blockray.RayTrace;
import org.spongepowered.api.util.blockray.RayTraceResult;
import org.spongepowered.api.world.LocatableBlock;
import org.zone.ZonePlugin;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.sponge.ComponentRemainingArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.eco.shop.ShopsFlag;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKeys;
import org.zone.region.shop.Shop;
import org.zone.region.shop.type.inventory.display.DisplayCaseShop;
import org.zone.utils.Messages;

import java.util.List;
import java.util.Optional;

public class CreateDisplayShop implements ArgumentCommand {

    private static final ComponentRemainingArgument SHOP_NAME = new ComponentRemainingArgument(
            "shop_name");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return List.of(new ExactArgument("region"),
                new ExactArgument("shop"),
                new ExactArgument("create"),
                new ExactArgument("display"),
                SHOP_NAME);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Creates a display unit for selling items");
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        @NotNull Subject source = commandContext.getSource();
        if (!(source instanceof Player player)) {
            return CommandResult.error(Messages.getPlayerOnlyMessage());
        }
        Optional<RayTraceResult<LocatableBlock>> opRayTrace = RayTrace
                .block()
                .sourcePosition(player)
                .direction(player)
                .limit(8)
                .execute();
        if (opRayTrace.isEmpty()) {
            return CommandResult.error(Messages.getInvalidBlock());
        }
        LocatableBlock block = opRayTrace.get().selectedObject();
        Optional<Zone> opZone = ZonePlugin.getZonesPlugin().getZoneManager().getPriorityZone(block);
        if (opZone.isEmpty()) {
            return CommandResult.error(Messages.getMustBeWithinZoneToEditError());
        }
        Zone zone = opZone.get();
        Group group = zone.getMembers().getGroup(player.uniqueId());
        if (!group.contains(GroupKeys.CREATE_SHOP)) {
            return CommandResult.error(null);
        }


        ShopsFlag shopsFlag = zone.getFlag(FlagTypes.SHOPS).orElse(new ShopsFlag());
        Component shopName = commandContext.getArgument(this, SHOP_NAME);

        if (shopsFlag.getShop(block.blockPosition()).isPresent()) {
            return CommandResult.error(null);
        }


        Shop shop = new DisplayCaseShop(shopName, player.location());
        shopsFlag.register(shop);
        zone.setFlag(shopsFlag);
        return CommandResult.success();
    }
}
