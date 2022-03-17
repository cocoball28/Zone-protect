package org.zone.region.flag.entity.player.display;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.entity.player.display.bossbar.BossBarMessageDisplayType;
import org.zone.region.flag.entity.player.display.chat.ChatMessageDisplayType;
import org.zone.region.flag.entity.player.display.title.TitleMessageDisplayType;

import java.util.*;
import java.util.stream.Collectors;

public final class MessageDisplayTypes {

    public static final ChatMessageDisplayType CHAT = new ChatMessageDisplayType();
    public static final TitleMessageDisplayType TITLE = new TitleMessageDisplayType();
    public static final BossBarMessageDisplayType BOSS_BAR = new BossBarMessageDisplayType();

    private MessageDisplayTypes() {
        throw new RuntimeException("Should not init");
    }

    public static @NotNull Collection<MessageDisplayType<?>> getVanillaDisplayTypes() {
        return Arrays
                .stream(MessageDisplayTypes.class.getDeclaredFields())
                .parallel()
                .filter(field -> MessageDisplayType.class.isAssignableFrom(field.getType()))
                .map(field -> {
                    try {
                        return (MessageDisplayType<?>) field.get(null);
                    } catch (IllegalAccessException iae) {
                        iae.printStackTrace();
                        //noinspection ReturnOfNull
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

}
