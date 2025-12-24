import java.net.*;
import java.io.*;

public class RuppinServer
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            //  האזנה לפורט 4445
            serverSocket = new ServerSocket(4445);
            System.out.println("Ruppin Registration Server started on port 4445...");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4445.");
            System.exit(1);
        }

        while (listening)
        {
            new Thread(new RuppinClientHandler(serverSocket.accept())).start();
        }

        serverSocket.close();
    }
}