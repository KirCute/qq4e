package org.qq4e.view;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.events.FriendMessageEvent;

public class FriendChatFrame extends ChatFrame {
    private final Friend contact;

    public FriendChatFrame(Friend friend, Bot bot) {
        super(friend, bot);
        this.contact = friend;
        bot.getEventChannel().subscribeAlways(FriendMessageEvent.class, e -> {
            if (e.getSubject() != this.contact) return;
            // TODO Render Message
        });
    }
}
