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
            var vertical = this.txtMessageAreaScrollPane.getVerticalScrollBar();
            var autoScroll = vertical.getValue() == vertical.getMaximum();
            System.out.println(vertical.getValue() * 1000000 + vertical.getMaximum());
            // FIXME
            this.txtMessageArea.append(String.format("%s %s\n", e.getSenderName(), simpleDateFormat.format(new Date(System.currentTimeMillis()))));
            this.txtMessageArea.append(e.getMessage().contentToString());
            this.txtMessageArea.append("\n");
            if (autoScroll) vertical.setValue(vertical.getMaximum());
        });

        this.add(chatPanel);
    }
}
