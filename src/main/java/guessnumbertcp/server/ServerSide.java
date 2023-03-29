package guessnumbertcp.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class ServerSide {

    private int flag;

    public ServerSide() {
        System.out.println("Server started...");
    }

    public void server() throws IOException {
        PrintWriter out = null;
        Scanner getClient = null;
        ServerSocket server = null;
        Socket client = null;
        try {
            server = new ServerSocket(6666);
            client = server.accept();
            this.setFlag();
            System.out.println(client.getInetAddress() + ":" + client.getPort() +
                    " Successfully connected to this server. (flag = " + getFlag() + ")");
            out = new PrintWriter(client.getOutputStream());
            out.println("Welcome to the guessing number game (1-100)!");
            out.flush();

            getClient = new Scanner(client.getInputStream());
            while (getClient.hasNextLine()) {
                String returnMsg;
                String tmp = getClient.nextLine();

                int clientInput = Integer.parseInt(tmp);

                if (clientInput > flag) {
                    returnMsg = "LESS";
                } else if (clientInput < flag) {
                    returnMsg = "MORE";
                } else {
                    returnMsg = "EQUAL";
                    out.println(returnMsg);
                    out.flush();
                    break;
                }
                out.println(returnMsg);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(out).close();
            Objects.requireNonNull(getClient).close();
            System.out.println(client.getInetAddress() + ":" + client.getPort()
                    + " Successfully disconnected to this server.");
            server.close();
            server();
        }
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag() {
        Random r = new Random();
        flag = r.nextInt(100);
    }

    public static void main(String[] args) throws IOException {
        new ServerSide().server();
    }
}