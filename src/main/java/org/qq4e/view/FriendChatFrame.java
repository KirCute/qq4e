package org.qq4e.view;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.events.FriendMessageEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FriendChatFrame extends ChatFrame {
    public FriendChatFrame(Friend friend, Bot bot) {
        super(friend, bot);
        this.setTitle(friend.getNick());

        var simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        bot.getEventChannel().subscribeAlways(FriendMessageEvent.class, e -> {
            if (e.getSubject() != friend) return;
            this.appendMessage(e.getSenderName(), e.getMessage());
        });

        this.add(chatPanel);
    }
}
