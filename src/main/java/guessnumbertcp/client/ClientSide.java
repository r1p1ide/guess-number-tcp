package guessnumbertcp.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ClientSide {

    public void client() throws IOException {
        System.out.println("Requesting connection from the server...");
        Socket client = null;
        Scanner getKey = null;
        Scanner getServer = null;
        PrintWriter out = null;
        try {
            client = new Socket("127.0.0.1", 6666);
            getServer = new Scanner(client.getInputStream());
            System.out.println(getServer.nextLine());
            out = new PrintWriter(client.getOutputStream());

            System.out.print("GUESS:");
            getKey = new Scanner(System.in);
            while (getKey.hasNextLine()) {
                out.println(getKey.nextLine());
                out.flush();
                try {
                    String result = getServer.nextLine();
                    System.out.println(result);
                    if ("EQUAL".equals(result)) {
                        break;
                    }
                    System.out.print("GUESS:");
                } catch (Exception e) {
                    System.out.print("Game over");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(getKey).close();
            getServer.close();
            out.close();
            client.close();
        }

    }

    public static void main(String[] args) throws IOException {
        new ClientSide().client();
    }
}