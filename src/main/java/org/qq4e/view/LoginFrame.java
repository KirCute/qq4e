package org.qq4e.view;

import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.network.LoginFailedException;
import net.mamoe.mirai.utils.BotConfiguration;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(400, 300);
        this.setResizable(false);
        this.setTitle("登录 QQ for E");

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        var panelId = new JPanel(new FlowLayout());
        var txtId = new JTextField();
        txtId.setColumns(16);
        panelId.add(new JLabel("登录"));
        panelId.add(txtId);

        var panelPwd = new JPanel(new FlowLayout());
        var txtPwd = new JPasswordField();
        txtPwd.setColumns(16);
        panelPwd.add(new JLabel("密码"));
        panelPwd.add(txtPwd);

        var panelBtn = new JPanel(new FlowLayout());
        var btnLogin = new JButton("登录");
        btnLogin.addActionListener(e -> {
            BotConfiguration.getDefault().setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PAD);
            btnLogin.setEnabled(false);
            new Thread(() -> {
                var bot = BotFactory.INSTANCE.newBot(Long.parseLong(txtId.getText()), new String(txtPwd.getPassword()));
                try {
                    bot.login();
                    new MainFrame(bot);
                    this.dispose();
                } catch (LoginFailedException ex) {
                    // TODO Display Error Message
                    btnLogin.setEnabled(true);
                }
            }).start();
        });
        panelBtn.add(btnLogin);

        this.add(panelId);
        this.add(panelPwd);
        this.add(panelBtn);
        this.setVisible(true);
    }
}
