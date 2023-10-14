package Client;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

public class ClientWindow extends JFrame{
    private static final int WINDOW_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_POSX = 500;
    private static final int WINDOW_POSY = 550;

    private JPanel panBottom, panTop;
    private JButton btnLogin, btnSend;
    private JTextArea taLog;
    private JTextField tfAddress, tfPort, tfLogin, tfMessage;
    private JLabel lblNothing;
    private JPasswordField pfPassword;
    private JScrollPane sp;
    private Client client;

    public ClientWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(WINDOW_POSX, WINDOW_POSY, WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        setAlwaysOnTop(true);
        panTop = getPanelTop();
        add(panTop, BorderLayout.NORTH);
        add(getPanelBottom(), BorderLayout.SOUTH);
        taLog = new JTextArea("Введите данные и подключитесь к серверу.\n");
        taLog.setEditable(false);
        taLog.setLineWrap(true);
        taLog.setWrapStyleWord(true);
        sp = new JScrollPane(taLog, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(sp);
        addListeners();
        client = new Client(this);
        setVisible(true);


    }
    private void addListeners() {
        ActionListener alSend = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage(tfMessage.getText());
            }
        };

        btnSend.addActionListener(alSend);
        tfMessage.addActionListener(alSend);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.connectToServer(tfAddress.getText(), tfPort.getText(), tfLogin.getText(), pfPassword.getPassword());
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

    private JPanel getPanelTop() {
        JPanel panel = new JPanel(new GridLayout(2,3));
        tfAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("9999");
        lblNothing = new JLabel();
        tfLogin = new JTextField("login");
        pfPassword = new JPasswordField();
        btnLogin = new JButton("login");
        panel.add(tfAddress);
        panel.add(tfPort);
        panel.add(lblNothing);
        panel.add(tfLogin);
        panel.add(pfPassword);
        panel.add(btnLogin);
        return panel;
    }

    private JComponent getPanelBottom() {
        panBottom = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        btnSend = new JButton("Send");
        panBottom.add(btnSend, BorderLayout.EAST);
        panBottom.add(tfMessage);
        return panBottom;
    }

    public void addTotaLog(String text){
        taLog.append(text + "\n");
    }
    public void setTextTotaLog(String text){
        taLog.setText(text);
    }
    public void setTextTotfMessage(String text){
        tfMessage.setText(text);
    }
    public void setVisiblePanTop(boolean visible){
        panTop.setVisible(visible);
    }
}
