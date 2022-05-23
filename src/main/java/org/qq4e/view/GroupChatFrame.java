package org.qq4e.view;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public class GroupChatFrame extends ChatFrame {
    private final Group contact;

    public GroupChatFrame(Group contact, Bot bot) {
        super(contact, bot);
        this.contact = contact;
        bot.getEventChannel().subscribeAlways(GroupMessageEvent.class, e -> {
            if (e.getSubject() != this.contact) return;
            // TODO Render Message
        });
    }
}
