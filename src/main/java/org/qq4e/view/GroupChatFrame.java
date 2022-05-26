package org.qq4e.view;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class GroupChatFrame extends ChatFrame {
    public GroupChatFrame(Group contact, Bot bot) {
        super(contact, bot);
        this.setTitle(contact.getName());

        var simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        var functionTabs = new JTabbedPane();
        functionTabs.add("聊天", this.chatPanel);

        var remoteFilesPanel = new JPanel();
        remoteFilesPanel.setLayout(new BoxLayout(remoteFilesPanel, BoxLayout.PAGE_AXIS));
        for (var folder : contact.getFiles().getRoot().foldersStream().collect(Collectors.toList())) {
            remoteFilesPanel.add(new JLabel(folder.getName()));
        }
        for (var file : contact.getFiles().getRoot().filesStream().collect(Collectors.toList())) {
            var filePanel = new JPanel();
            var download = new JButton("下载");
            download.addActionListener(e -> {
                System.out.println(file.getUrl());
                // TODO Download the file
            });
            filePanel.add(download);
            filePanel.add(new JLabel(file.getName()));
            remoteFilesPanel.add(filePanel);
        }
        functionTabs.add("文件", new JScrollPane(remoteFilesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        bot.getEventChannel().subscribeAlways(GroupMessageEvent.class, e -> {
            if (e.getSubject() != contact) return;
            var vertical = this.txtMessageAreaScrollPane.getVerticalScrollBar();
            var autoScroll = vertical.getValue() == vertical.getMaximum();
            this.txtMessageArea.append(String.format("%s %s\n", e.getSenderName(), simpleDateFormat.format(new Date(System.currentTimeMillis()))));
            this.txtMessageArea.append(e.getMessage().contentToString());
            this.txtMessageArea.append("\n");
            if (autoScroll) vertical.setValue(vertical.getMaximum());
        });

        this.add(functionTabs);
    }
}
