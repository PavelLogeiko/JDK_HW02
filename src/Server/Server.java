package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread {
    private ServerWindow serverWindow;
    private ServerSocket serverSocket = null;
    private Socket connection = null;
    private final int PORT = 9999;
    private boolean isServerWorking;
    private LinkedList<ConnectionToClient> listClientConnection = new LinkedList<>();

    Server(ServerWindow serverWindow){
        this.isServerWorking = false;
        this.serverWindow = serverWindow;
    }

    @Override
    public void run() {
        try{
            System.out.println("RUN");
            serverSocket = new ServerSocket(PORT);
            isServerWorking = true;
            while(true){
                try{
                    connection = serverSocket.accept();
                    listClientConnection.add(new ConnectionToClient(connection, serverWindow, this));
                }catch(IOException ex){
                    if (connection!=null) connection.close();
                }
            }
        }catch(IOException e){
            e.getMessage();
        }
        finally{
            close();
        }
    }

    public void closeConnection(){
        for(ConnectionToClient ss : listClientConnection){
            ss.close();
        }
    }
    public void close(){
        try{
            isServerWorking = false;
            serverSocket.close();
        }
        catch(IOException e){
            e.getMessage();
        }
    }
    public void showMessage(String message){
        serverWindow.addTotaLog(message);
    }

    public boolean getServerStatus(){
        return isServerWorking;
    }

    public void up(){
        if (!getServerStatus()) {
            try {
                System.out.println("UP");
                start();
                System.out.println("UP after start");
                serverWindow.addTotaLog("Server started");//(String.format("Server started. Address: %s, port: %s", serverSocket.getLocalSocketAddress(), PORT));
                setServerStatus(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                serverWindow.addTotaLog("The server could not start.");
            }
        } else {
            serverWindow.addTotaLog("The server has already started.");
        }
    }

    public boolean down(){
        if (getServerStatus()) {
            try {
                System.out.println("DOWN");
                closeConnection();
                close();
                setServerStatus(false);
                serverWindow.addTotaLog("Server stopped.");
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                serverWindow.addTotaLog("The server couldn't stop.");
                return false;
            }
        } else {
            return false;
        }
    }

    private void setServerStatus(boolean status){
        isServerWorking = status;
    }

    public void sendMessageAll(String message){
        for(ConnectionToClient ss : listClientConnection){
            ss.sendMessage(message);
        }
    }
    public void sendMessageToClient(String message){
    }
}