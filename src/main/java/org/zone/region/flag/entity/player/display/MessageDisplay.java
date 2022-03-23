package org.zone.region.flag.entity.player.display;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;

public interface MessageDisplay {

    /**
     * Sends a message to the player either in Chat or in the form of Title or BossBar
     *
     * @param message  The message to be shown
     * @param receiver The player who will receive the message
     */
    void sendMessage(@NotNull Component message, @NotNull Player receiver);

    /**
     * Gets the type class of the display
     *
     * @return The types class from {@link MessageDisplayTypes} class
     */
    @NotNull MessageDisplayType<? extends MessageDisplay> getType();

}
