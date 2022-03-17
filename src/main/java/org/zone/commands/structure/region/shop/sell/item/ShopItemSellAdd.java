package org.zone.commands.structure.region.shop.sell.item;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.util.blockray.RayTrace;
import org.spongepowered.api.util.blockray.RayTraceResult;
import org.spongepowered.api.world.LocatableBlock;
import org.zone.ZonePlugin;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.number.DoubleArgument;
import org.zone.commands.system.arguments.sponge.CurrencyArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.shop.Shop;
import org.zone.region.shop.selling.SellingItemStack;
import org.zone.region.shop.transaction.price.PriceBuilder;
import org.zone.region.shop.transaction.price.PriceType;
import org.zone.region.shop.transaction.price.player.PlayerEcoPrice;
import org.zone.utils.Messages;

import java.util.List;
import java.util.Optional;

public class ShopItemSellAdd implements ArgumentCommand {

    public static final DoubleArgument PRICE = new DoubleArgument("price");
    public static final CurrencyArgument CURRENCY = new CurrencyArgument("currency");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return List.of(new ExactArgument("region"),
                new ExactArgument("shop"),
                new ExactArgument("sell"),
                new ExactArgument("item"),
                new ExactArgument("add"),
                CURRENCY,
                PRICE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Add a item to a shop");
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        if (!(commandContext.getSource() instanceof Player player)) {
            return CommandResult.error(Messages.getPlayerOnlyMessage());
        }

        Optional<RayTraceResult<LocatableBlock>> opRay = RayTrace
                .block()
                .sourcePosition(player)
                .direction(player)
                .limit(10)
                .execute();
        if (opRay.isEmpty()) {
            return CommandResult.error(null);
        }
        LocatableBlock block = opRay.get().selectedObject();
        Optional<Zone> opZone = ZonePlugin.getZonesPlugin().getZoneManager().getPriorityZone(block);
        if (opZone.isEmpty()) {
            return CommandResult.error(null);
        }
        Optional<Shop> opShop = opZone
                .get()
                .getFlag(FlagTypes.SHOPS)
                .flatMap(sf -> sf.getShop(block.blockPosition()));
        if (opShop.isEmpty()) {
            return CommandResult.error(null);
        }
        Shop shop = opShop.get();
        if (!(shop instanceof Shop.Modifiable)) {
            return CommandResult.error(null);
        }
        Shop.Modifiable<SellingItemStack> modifiable = (Shop.Modifiable<SellingItemStack>) shop;

        Hotbar hotBar = player.inventory().hotbar();
        Slot slot = hotBar
                .slot(hotBar.selectedSlotIndex())
                .orElseThrow(() -> new RuntimeException("Cannot get slot of selected"));

        ItemStack stack = slot.peek();

        PlayerEcoPrice price = (PlayerEcoPrice) new PriceBuilder()
                .setType(PriceType.ECO)
                .setAmount(commandContext.getArgument(this, PRICE))
                .setCurrency(commandContext.getArgument(this, CURRENCY))
                .buildPlayer();
        SellingItemStack selling = new SellingItemStack(price, stack.createSnapshot());

        modifiable.register(selling);

        return CommandResult.success();
    }
}
