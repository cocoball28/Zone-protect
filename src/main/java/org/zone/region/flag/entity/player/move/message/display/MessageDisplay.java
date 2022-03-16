package org.zone.region.flag.entity.player.move.message.display;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;

public interface MessageDisplay {

    void sendMessage(@NotNull Component message, @NotNull Player receiver);

    @NotNull MessageDisplayType<? extends MessageDisplay> getType();

}
