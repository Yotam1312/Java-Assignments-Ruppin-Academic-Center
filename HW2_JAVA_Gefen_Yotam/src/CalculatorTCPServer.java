import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class CalculatorTCPServer {
	public static void main(String [] args) throws IOException{
		try(ServerSocket serverSocket = new ServerSocket(9090)) {
			System.out.println("Server is listening on port 9090...");
			Socket socket = serverSocket.accept();
			System.out.println("Client is connected");
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine;
			while((inputLine = in.readLine()) != null) {
				System.out.println("Received Expression: " + inputLine);
				if(inputLine.equals("exit")) {
					System.out.println("Client requested to close connection.");
					break;
				}
				String result = calculator(inputLine);
                out.println(result);
			}
			System.out.println("Client disconnected.");
			in.close();
			out.close();
			socket.close();
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static String calculator(String inputLine) {
		String parts[] = inputLine.trim().split(" ");
		if(parts.length != 3)
			return "Error: Invalid expression";
		
		double num1 = Double.parseDouble(parts[0]);
		double num2 = Double.parseDouble(parts[2]);
		String op = parts[1];
		double result = 0;
		
		switch(op) {
		case "+":
			result = num1 + num2;
			break;
		case "-":
			result = num1 - num2;
			break;
		case "*":
			result = num1 * num2;
			break;
		case "/":
			if(num2 == 0) {
				return "Error: Division by zero";
			}
			result = num1 / num2;
			break;
		default:
	        return "Error: Invalid expression";
		}
		return String.valueOf(result);
	}
}
