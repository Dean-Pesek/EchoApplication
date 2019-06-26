package pesek.caruso.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	public static void main(String[] args) {
		Socket socket = null;
		String host = "";
		String portString = "";

		if (!args[0].equals(null) || !args[0].isEmpty())
			host = args[0];
		if (!args[1].equals(null) || !args[1].isEmpty())
			portString = args[1];

		socket = createSocket(host, portString);
		String clientString = "";

		boolean cont = true;
		while (cont == true) {
			try {
				clientString = getClientInput();
				socket.getOutputStream().write(String.format("%50s", clientString).getBytes());
				System.out.println(getServerResponse(socket));
				cont = getClientCont();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static String getServerResponse(Socket socket) {
		byte[] messageBuffer = new byte[50];
		String message = "";
		try {
			socket.getInputStream().read(messageBuffer);
			message = new String(messageBuffer).trim();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	private static boolean getClientCont() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Would you like to go again? (y/n)");
		String clientCont = reader.readLine();
		if (clientCont == "y" || clientCont == "Y")
			return true;
		return false;
	}

	private static String getClientInput() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("What do you want to tell the server?: ");
		String clientInput = reader.readLine();
		return clientInput;
	}

	private static Socket createSocket(String host, String portString) {
		Socket socket = new Socket();
		try {
			socket = new Socket(host, Integer.parseInt(portString));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}

}
