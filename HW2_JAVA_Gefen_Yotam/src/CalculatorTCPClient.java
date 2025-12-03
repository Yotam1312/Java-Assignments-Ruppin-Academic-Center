import java.io.IOException;
import java.net.Socket;
import java.io.*;

public class CalculatorTCPClient {
	public static void main(String [] args) throws IOException{
		try (Socket socket = new Socket("localhost", 9090)){
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("connected to the server.");
			
			String userInput;
			while(true) {
				System.out.print("Enter expression (num op num) or 'close' to exit: ");
				userInput = stdIn.readLine();
				
				if(userInput.equalsIgnoreCase("close")){
					System.out.println("Client closed.");
					break;
				}
				out.println(userInput);
				String serverReuslt = in.readLine();
				System.out.println(userInput + " = " + serverReuslt);
			}
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
