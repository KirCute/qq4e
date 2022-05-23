package org.qq4e.view;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public abstract class ChatFrame extends JFrame {
    private final Contact contact;
    private final JTextArea txtSendingArea;
    private final JButton btnSend;

    public ChatFrame(Contact contact, Bot bot) {
        this.contact = contact;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.txtSendingArea = new JTextArea();
        this.btnSend = new JButton("发送");
        this.btnSend.addActionListener(e -> {
            this.contact.sendMessage(new MessageChainBuilder()
                    .append(txtSendingArea.getText())
                    .build()
            );
        });

        this.setVisible(true);
    }

    public static String convertGBKToUTF8(String gbk) {
        try {
            var unicode = new String(gbk.getBytes(), "GBK");
            return new String(unicode.getBytes(StandardCharsets.UTF_8));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String convertUTF8ToGBK(String utf8) {
        try {
            var unicode = new String(utf8.getBytes(), StandardCharsets.UTF_8);
            return new String(unicode.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
