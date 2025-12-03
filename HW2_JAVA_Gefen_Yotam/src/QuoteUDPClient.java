import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class QuoteUDPClient {
	public static void main(String [] args) {
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress serverAddress = InetAddress.getByName("localhost");
			int serverPort = 8080;
			Scanner scanner = new Scanner(System.in);
			
			byte[] sendBuffer;
			byte[] receiveBuffer = new byte[1024];
			
			while(true) {
				System.out.print("Enter 'GET' for quote or 'exit' to quit: ");
				String clientans = scanner.nextLine();
				sendBuffer = clientans.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, serverPort);
				clientSocket.send(sendPacket);
				
				if(clientans.equalsIgnoreCase("exit")) {
					System.out.println("Client finished.");
					break;
				}
				
				DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
				clientSocket.receive(receivePacket);
				String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
				System.out.print("Quote received: " + serverResponse + "\n");
				System.out.println();
			}
			clientSocket.close();
			scanner.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
