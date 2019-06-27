package pesek.caruso.echoserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void main(String[] args) {
		String portString = "";
		if (!args[0].equals(null) || !args[0].isEmpty())
			portString = args[0];
		Socket socket = createServerSocket(portString);
		while (true) {
			String clientMessage = getClientInput(socket);
			sendClientEcho(clientMessage, socket);
		}
	}

	private static void sendClientEcho(String clientMessage, Socket socket) {
		if (!clientMessage.isEmpty() || clientMessage != null) {
			try {
				socket.getOutputStream().write(clientMessage.getBytes());
				System.out.println("Sending to client: " + clientMessage);
			} catch (IOException e) {
			}
		}
	}

	private static String getClientInput(Socket socket) {
		byte[] messageBuffer = new byte[50];
		String message = null;
		try {
			socket.getInputStream().read(messageBuffer);
			message = new String(messageBuffer).trim();
			System.out.println("Received from client: " + message);
		} catch (IOException e) {

		}
		return message;
	}

	private static Socket createServerSocket(String portString) {
		ServerSocket socket = null;
		Socket returnSocket = null;
		try {
			socket = new ServerSocket(Integer.parseInt(portString));
			returnSocket = socket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnSocket;
	}

}
