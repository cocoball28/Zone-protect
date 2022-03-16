package org.zone.region.shop.type.inventory;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.item.inventory.*;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.item.inventory.menu.ClickType;
import org.spongepowered.api.item.inventory.menu.ClickTypes;
import org.spongepowered.api.item.inventory.menu.InventoryMenu;
import org.spongepowered.api.item.inventory.menu.handler.SlotClickHandler;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.item.inventory.type.ViewableInventory;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.service.economy.transaction.TransactionTypes;
import org.zone.ZonePlugin;
import org.zone.region.shop.Shop;
import org.zone.region.shop.selling.Selling;
import org.zone.region.shop.selling.SellingItemStack;
import org.zone.region.shop.utils.TransactionResultBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public abstract class InventoryShop implements Shop.Modifiable<SellingItemStack> {

    public class ShopSellClickHandler implements SlotClickHandler {

        @Override
        public boolean handle(
                Cause cause,
                Container container,
                Slot slot,
                int slotIndex,
                ClickType<?> clickType) {
            if (clickType.equals(ClickTypes.CLICK_LEFT.get())) {
                return false;
            }
            ItemStack stack = slot.peek();
            Optional<SellingItemStack> opSellingItem = InventoryShop.this
                    .getSellingItems()
                    .stream()
                    .filter(selling -> ItemStackComparators.ALL
                            .get()
                            .compare(selling.getItem().createStack(), stack) == 0)
                    .findAny();
            if (opSellingItem.isEmpty()) {
                return false;
            }

            ServerPlayer player = container.viewer();
            InventoryShop.this.buy(player, opSellingItem.get());
            return false;
        }
    }


    private final List<SellingItemStack> sellingItems = new ArrayList<>();
    private final Component name;

    public InventoryShop(Component component) {
        this.name = component;
    }

    public Component getName() {
        return this.name;
    }

    public @NotNull List<SellingItemStack> getSellingItems() {
        return Collections.unmodifiableList(this.sellingItems);
    }

    @Override
    public void register(SellingItemStack selling) {
        this.sellingItems.add(selling);
    }

    public void register(Collection<? extends SellingItemStack> selling) {
        this.sellingItems.addAll(selling);
    }

    public void unregister(SellingItemStack selling) {
        this.sellingItems.remove(selling);
    }

    private ViewableInventory createInventory(UUID invId) {
        Set<SellingItemStack> items = this.sellingItems
                .parallelStream()
                .filter(selling -> selling.getAmount() != 0)
                .collect(Collectors.toSet());
        int slots = ((int) (items.size() / 9.0)) * 9;
        Inventory inv = Inventory
                .builder()
                .slots(slots)
                .completeStructure()
                .plugin(ZonePlugin.getZonesPlugin().getPluginContainer())
                .identity(invId)
                .build();
        items.forEach(selling -> inv.offer(selling.getItem().createStack()));
        return inv
                .asViewable()
                .orElseThrow(() -> new IllegalStateException(
                        "Could not create the inventory as a ViewableInventory"));
    }

    public InventoryMenu createSellingInventory(UUID invId) {
        ViewableInventory viewInv = this.createInventory(invId);
        InventoryMenu menu = viewInv.asMenu();
        menu.setTitle(this.name);
        menu.setReadOnly(true);
        menu.registerSlotClick(new ShopSellClickHandler());
        return menu;
    }

    @Override
    @Deprecated
    public @NotNull Collection<Selling<?, ?>> getSelling() {
        return Collections.unmodifiableCollection(this.sellingItems);
    }

    @Override
    public @NotNull TransactionResult buy(
            @NotNull Player account, @NotNull Selling<?, ?> item, int amount) {
        if (!(item instanceof SellingItemStack stack)) {
            throw new IllegalArgumentException("Selling is not of a SellingItemStack");
        }
        BigDecimal price = stack.getPrice().getAmount().multiply(BigDecimal.valueOf(amount));
        TransactionResultBuilder transactionBuilder = new TransactionResultBuilder()
                .setAccount(account)
                .setTransactionType(TransactionTypes.DEPOSIT.get())
                .setCurrency(stack.getPrice().getCurrency())
                .setAmount(price)
                .addContext(new Context(Context.USER_KEY, account.name()));
        if (stack.getAmount() < amount) {
            return transactionBuilder.setResultType(ResultType.FAILED).build();
        }
        BigDecimal balance = transactionBuilder
                .getAccount()
                .balance(stack.getPrice().getCurrency());
        if (balance.compareTo(price) < 0) {
            return transactionBuilder.setResultType(ResultType.ACCOUNT_NO_FUNDS).build();
        }
        PlayerInventory playerInv = account.inventory();
        Account playersAccount = transactionBuilder.getAccount();

        ItemStack[] items = new ItemStack[amount];
        for (int a = 0; a < amount; a++) {
            ItemStack itemStack = stack.getItem().createStack();
            items[a] = itemStack;
        }

        TransactionResult withdrawResult = playersAccount.withdraw(stack.getPrice().getCurrency(),
                price);
        if (withdrawResult.result() != ResultType.SUCCESS) {
            return withdrawResult;
        }

        InventoryTransactionResult offer = playerInv.offer(items);
        if (offer.type() == InventoryTransactionResult.Type.SUCCESS) {
            return withdrawResult;
        }
        playersAccount.deposit(stack.getPrice().getCurrency(), price);
        return transactionBuilder.setResultType(ResultType.ACCOUNT_NO_SPACE).build();
    }
}
