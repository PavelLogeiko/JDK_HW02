package Server;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ServerWindow extends JFrame{
    private static final int WINDOW_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_POSX = 500;
    private static final int WINDOW_POSY = 550;
    private JPanel panBottom;
    private JButton btnStart, btnStop;
    private JTextArea taLog;
    private JScrollPane sp;
    private Server server;
    private ServerWindow sw;


    public ServerWindow(){
        sw = this;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(WINDOW_POSX, WINDOW_POSY, WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        add(getPanelBottom(), BorderLayout.SOUTH);
        taLog = new JTextArea("Server stopped.\n");
        taLog.setEditable(false);
        taLog.setLineWrap(true);
        taLog.setWrapStyleWord(true);
        sp = new JScrollPane(taLog, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(sp);
        addListeners();
        setVisible(true);
    }

    private void addListeners() {

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server == null) {
                    server = new Server(sw);
                }
                server.up();
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server != null) {
                    if (server.down()) server = null;
                } else {
                    addTotaLog("The server has already stopped.");
                }
            }
        });

        taLog.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

        });
    }

    private JComponent getPanelBottom() {
        panBottom = new JPanel(new GridLayout(1,2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        panBottom.add(btnStart);
        panBottom.add(btnStop);
        return panBottom;
    }

    public void addTotaLog(String text){
        taLog.append(text + "\n");
    }
}