package Client;

public class Client {
    private ClientWindow clientWindow;
    private ConnectionToServer connectionToServer;
    private boolean isConnectToServer;

    Client(ClientWindow clientWindow){
        this.clientWindow = clientWindow;
        isConnectToServer = false;
    }

    public void sendMessage(String message){
        if (isConnectToServer) {
            if (message.isEmpty()) {
                clientWindow.addTotaLog("! Нет данных для отправки !");
            } else {
                clientWindow.addTotaLog(message);
                clientWindow.setTextTotfMessage("");
            }
        } else {
            clientWindow.addTotaLog("! Нет подключения к серверу, отправить сообщение невозможно !");
        }
    }
    public void connectToServer(String address, String port, String login, char[] password) {
        if (isConnectToServer) {
            clientWindow.addTotaLog("Вы уже подключены к серверу.");
        } else {
            try {
                connectionToServer = new ConnectionToServer(address, port, login, password, this);
                connectionToServer.start();
                isConnectToServer = true;
                clientWindow.addTotaLog("Вы подключены к серверу.");
            } catch (Exception e) {
                clientWindow.addTotaLog("Не удалось подключиться.");
            }
        }
    }
    public void addTotaLog(String text){
        clientWindow.addTotaLog(text);
    }
}