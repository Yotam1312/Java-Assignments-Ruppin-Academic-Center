import java.net.*;
import java.io.*;

public class RuppinClientHandler implements Runnable
{
    private Socket clientSocket;

    public RuppinClientHandler(Socket socket)
    {
        this.clientSocket = socket;
    }

    @Override
    public void run()
    {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String inputLine, outputLine;
            RuppinRegistrationProtocol rrp = new RuppinRegistrationProtocol();
            outputLine = rrp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null)
            {
                outputLine = rrp.processInput(inputLine);
                out.println(outputLine);

                if (outputLine.equals("Registration complete.") || outputLine.equals("Bye."))
                {
                    break;
                }
            }

            // סגירת החיבור בסיום
            out.close();
            in.close();
            clientSocket.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}