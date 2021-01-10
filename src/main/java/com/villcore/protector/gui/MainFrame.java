package com.villcore.protector.gui;

import com.villcore.protector.client.ClientIdentity;
import com.villcore.protector.client.ClientManager;
import com.villcore.protector.client.ClientStateCode;
import com.villcore.protector.client.command.CommandBroadcaster;
import com.villcore.protector.command.CommandCode;
import com.villcore.protector.utils.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainFrame {

    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    private JList host_list;
    private JButton hidden_bt;
    private JButton show_bt;
    private JButton exit_bt;
    private JPanel main;

    private ScheduledExecutorService scheduler;
    private ClientManager clientManager;
    private CommandBroadcaster commandBroadcaster;

    public MainFrame(ClientManager clientManager, CommandBroadcaster commandBroadcaster) {
        this.clientManager = clientManager;
        this.commandBroadcaster = commandBroadcaster;
        init();
    }

    private void init() {
        host_list.setListData(new String[]{"hello"});
        hidden_bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandBroadcaster.broadcastCommand(CommandCode.HIDDEN_DISK_COMMAND);
                log.info("Broadcast command hidden disk command ");
            }
        });

        this.scheduler = Executors.newScheduledThreadPool(1, new NamedThreadFactory("update-host-list"));
        this.scheduler.scheduleAtFixedRate(() -> {
            host_list.clearSelection();
            Object[] array = clientManager.listAllClientState().stream()
                    .map(clientState -> {
                        ClientIdentity clientIdentity = clientState.getClientIdentity();
                        String host = clientIdentity.getHost();
                        String address = clientIdentity.getAddress();
                        long lastTouchTimeMillis = clientState.getLastUpdateTimestamp();
                        ClientStateCode stateCode = clientState.getStateCode();
                        return host + " (" + address + ")"
                                + (stateCode == ClientStateCode.ONLINE ? "在线" : "离线") + " - 最后通信: "
                                + ((System.currentTimeMillis() - lastTouchTimeMillis) / 1000) + "秒前";
                    }).toArray();
            host_list.setListData(array);
        }, 5, 3, TimeUnit.SECONDS);

        JFrame frame = new JFrame("MainFrame");
        frame.setContentPane(this.main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
