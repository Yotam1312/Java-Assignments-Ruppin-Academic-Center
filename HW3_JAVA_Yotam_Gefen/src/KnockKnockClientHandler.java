import java.net.*;
import java.io.*;

public class KnockKnockClientHandler implements Runnable {

    private Socket clientSocket;

    public KnockKnockClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine, outputLine;

            KnockKnockProtocol kkp = new KnockKnockProtocol();

            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("q")) {
                    break;
                }
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);

                if (outputLine.equals("Bye."))
                    break;
            }

            out.close();
            in.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}