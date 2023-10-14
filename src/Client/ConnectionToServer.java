package Client;

import java.io.*;
import java.net.*;

public class ConnectionToServer extends Thread{
    ObjectOutputStream output = null;
    ObjectInputStream input = null;
    Socket socket;
    String address, port, login, message;
    char[] password;
    ClientWindow clientWindow;
    Client client;


    ConnectionToServer(String address, String port, String login, char[] password, Client client){
        this.client = client;
        this.address = address;
        this.port = port;
        this.login = login;
        this.password = password;
    }

    @Override
    public void run() {
        try{
            connectToServer();
            setupStreams();
            whileChatting();
        }
        catch(IOException e){
            e.getMessage();
        }
        finally{
            close();
        }
    }

    private void connectToServer() throws UnknownHostException, IOException{
        socket = new Socket(InetAddress.getByName(address), Integer.parseInt(port));
    }
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();
        input = new ObjectInputStream(socket.getInputStream());
    }
    private void whileChatting()throws IOException {
        do{
            try{
                message = (String)((ObjectInputStream) input).readObject();
                client.addTotaLog(message);
            }
            catch(ClassNotFoundException e){

            }
        }while(!message.equals("SERVER: END"));
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
            clientWindow.addTotaLog("Отключение клиента: " + socket.getInetAddress().getHostName());
            output.close();
            input.close();
            socket.close();
        }
        catch(IOException e){
            e.getMessage();
        }
    }
}
