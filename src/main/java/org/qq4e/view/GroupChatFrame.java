package org.qq4e.view;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GroupChatFrame extends ChatFrame {
    public GroupChatFrame(Group contact, Bot bot) {
        super(contact, bot);
        this.setTitle(contact.getName());

        var simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        bot.getEventChannel().subscribeAlways(GroupMessageEvent.class, e -> {
            if (e.getSubject() != contact) return;
            this.txtMessageArea.append(String.format("%s %s\n", e.getSenderName(), simpleDateFormat.format(new Date(System.currentTimeMillis()))));
            this.txtMessageArea.append(e.getMessage().contentToString());
            this.txtMessageArea.append("\n");
        });
    }
}
