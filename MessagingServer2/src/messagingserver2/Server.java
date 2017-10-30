package messagingserver2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Stack;

public class Server {
	
	private ServerSocket serverSocket;
	
	public static void main(String[] args) {
		
		Server server = new Server();
		
		server.go();
		
	}
	
	public void go() {
		
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		OutputHandler outputHandler = null;
		Stack<String> messages = new Stack<String>();
		Socket clientSocket = null;
		ArrayList<Socket> clientSockets = new ArrayList<Socket>();
		ArrayList<PrintWriter> out = new ArrayList<PrintWriter>();
		BufferedReader in = null;
		ClientHandler clientHandler = null;
		ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
		ConsoleHandler consoleHandler;
		int portNumber = 0;
		
		//Set up terminal:
		
		System.out.print("\u001b[40m"); //Black background
		System.out.print("\u001b[37m"); //White text
		System.out.print("\u001b[1m"); //Set to bright
		System.out.print("\u001b[2J"); //Clear terminal
		System.out.print("\u001b[H"); //Move cursor to home
		
		System.out.println("SERVER SETUP");
		System.out.println();
		
		//Get port number from user:
		
		System.out.print("Port number: ");
		
		try {
			portNumber = Integer.parseInt(console.readLine());
		} catch (IOException e) {
			System.out.println();
			System.out.println("ERROR: READ FROM CONSOLE FAILED");
			System.out.println();
			e.printStackTrace();
		}
		
		System.out.println();
		
		//Create server socket:
		
		System.out.print("Creating server socket... ");
		
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println();
			System.out.println("ERROR: CREATE SERVER SOCKET FAILED");
			System.out.println();
			e.printStackTrace();
		}
		
		System.out.println(" complete.");
		System.out.println();
		
		//Create output handler:
		
		outputHandler = new OutputHandler(out, messages);
		consoleHandler = new ConsoleHandler(console, this);
		
		outputHandler.start();
		consoleHandler.start();
		
		System.out.println("Server setup complete.");
		System.out.println();
		
		//Accept new clients:
		
		while (true) {
			
			try {
				clientSocket = serverSocket.accept();
				clientSockets.add(clientSocket);
				out.add(new PrintWriter(clientSocket.getOutputStream(), true));
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				clientHandler = new ClientHandler(in, messages);
				clientHandlers.add(clientHandler);
				clientHandler.start();
			} catch (IOException e) {
				System.out.println();
				System.out.println("ERROR: CREATE CLIENT SOCKET FAILED");
				System.out.println();
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void exit() {
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println();
			System.out.println("ERROR: CLOSE SERVER SOCKET FAILED");
			System.out.println();
			e.printStackTrace();
		}
		
		System.exit(0);
		
	}

}
