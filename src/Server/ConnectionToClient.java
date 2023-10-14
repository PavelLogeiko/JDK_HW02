package Server;

import java.io.*;
import java.net.*;

public class ConnectionToClient extends Thread {
    ObjectOutputStream output = null;
    ObjectInputStream input = null;
    Repository repository = new Repository();
    Socket socket;
    ServerWindow serverWindow;
    Server server;

    ConnectionToClient(Socket socket, ServerWindow serverWindow, Server server) throws IOException{
        this.socket = socket;
        this.serverWindow = serverWindow;
        this.server = server;
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        serverWindow.addTotaLog("Новое соединение " + socket.getInetAddress().getHostAddress());
        getHistory();
        start();
    }

    @Override
    public void run(){
        String message;
        try{
            while(true){
                message = (String) input.readObject();
                repository.writeToFile(message);
                serverWindow.addTotaLog(message);
                server.sendMessageAll(message);
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.getMessage();
        }
    }
    private void getHistory() {
        try{
            String histMessage = repository.readFile();
            if(histMessage != null){
                output.writeObject(histMessage);
                output.flush();
            }
        }
        catch(IOException ignored){}
    }
    public void sendMessage(String message) {
        System.out.println(message);
        try{
            output.writeObject(message);
            output.flush();
        }
        catch(IOException ignored){}
    }

    public void close() {
        try{
            serverWindow.addTotaLog("Отключение клиента: " + socket.getInetAddress().getHostName());
            output.close();
            input.close();
            socket.close();
        }
        catch(IOException e){
            e.getMessage();
        }
    }
}