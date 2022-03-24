package org.zone.region.flag.entity.player.display;

import org.zone.region.flag.entity.player.display.bossbar.BossBarMessageDisplayType;
import org.zone.region.flag.entity.player.display.chat.ChatMessageDisplayType;
import org.zone.region.flag.entity.player.display.title.TitleMessageDisplayType;

public final class MessageDisplayTypes {

    public static final ChatMessageDisplayType CHAT = new ChatMessageDisplayType();
    public static final TitleMessageDisplayType TITLE = new TitleMessageDisplayType();
    public static final BossBarMessageDisplayType BOSS_BAR = new BossBarMessageDisplayType();

    private MessageDisplayTypes() {
        throw new RuntimeException("Should not init");
    }

}
