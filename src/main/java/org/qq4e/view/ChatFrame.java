package org.qq4e.view;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public abstract class ChatFrame extends JFrame {
    protected final JTextArea txtMessageArea;

    public ChatFrame(Contact contact, Bot bot) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        var simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        this.setLayout(new BorderLayout());

        this.txtMessageArea = new JTextArea();
        this.txtMessageArea.setEditable(false);
        this.add(txtMessageArea, BorderLayout.NORTH);

        var txtSendingArea = new JTextArea();
        this.add(txtSendingArea, BorderLayout.CENTER);

        var btnSend = new JButton("发送");
        btnSend.setSize(800, 50);
        btnSend.addActionListener(e -> {
            try {
                contact.sendMessage(new MessageChainBuilder()
                        .append(txtSendingArea.getText())
                        .build()
                );
            } catch (IllegalArgumentException ex) {
                // TODO Message is empty, do nothing or notice user
            }
            this.txtMessageArea.append(String.format("%s %s\n", bot.getNick(), simpleDateFormat.format(new Date(System.currentTimeMillis()))));
            this.txtMessageArea.append(txtSendingArea.getText());
            this.txtMessageArea.append("\n");
            txtSendingArea.setText("");
        });
        this.add(btnSend, BorderLayout.SOUTH);

        this.setVisible(true);
    }
}
