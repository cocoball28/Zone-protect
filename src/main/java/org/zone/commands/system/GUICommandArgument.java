package org.zone.commands.system;

import org.spongepowered.api.item.inventory.ItemStack;
import org.zone.commands.system.context.CommandContext;

import java.util.Map;

public interface GUICommandArgument<T> extends CommandArgument<T> {

    Map<ItemStack, String> createMenuOptions(CommandContext context);
}
