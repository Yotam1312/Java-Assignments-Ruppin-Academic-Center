import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class QuoteUDPServer {
	public static void main(String[] args) {
		try {
			int port = 8080;
			DatagramSocket serverSocket = new DatagramSocket(port);
			System.out.println("UDP Quote Server listening on port " + port + "...");

			String[] quotes = { "Dolphins sleep with one eye open", "Octopuses have three hearts",
					"You blink around 15,000 times a day", "The first email was sent in 1971",
					"Most dreams last only a few seconds" };

			byte[] receiveBuffer = new byte[1024];
			byte[] sendBuffer;

			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
				serverSocket.receive(receivePacket);
				String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
				System.out.println("Request received: " + clientMessage);
				
				if (clientMessage.equalsIgnoreCase("exit")) {
					System.out.println("Server shutting down.");
					break;
				}

				Random rand = new Random();
				if(clientMessage.equals("GET")) {
					sendBuffer = quotes[rand.nextInt(quotes.length)].getBytes();
				}
				else {
					sendBuffer = "Success is not final, failure is not fatal.".getBytes();
				}
				
				InetAddress clientAddress = receivePacket.getAddress();
				int clientPort = receivePacket.getPort();
				DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
				serverSocket.send(sendPacket);
			}
			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}