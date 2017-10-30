package messagingclient2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Stack;

public class Client {
	
	private Socket socket;
	private ServerHandler serverHandler;
	
	public static void main(String[] args) {
		
		Client client = new Client();
		
		client.go();
		
	}
	
	public void go() {
		
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		String hostName = "localhost";
		int portNumber = 4444;
		PrintWriter out = null;
		BufferedReader in = null;
		String clientUserName = null;
		Stack<String> messages = new Stack<String>();
		ConsoleHandler consoleHandler;
		String message;
		
		//Set up terminal:
		
		System.out.print("\u001b[40m"); //Black background
		System.out.print("\u001b[37m"); //White text
		System.out.print("\u001b[1m"); //Set to bright
		System.out.print("\u001b[2J"); //Clear terminal
		System.out.print("\u001b[H"); //Move cursor to home
		
		//Enter host name:
		
		System.out.println("Welcome to OldTerraceChat. Please enter the host name.");
		System.out.print("> ");
		
		try {
			hostName = console.readLine();
		} catch (IOException e) {
			System.out.println();
			System.out.println("ERROR: READ CONSOLE INPUT FAILED");
			System.out.println();
			e.printStackTrace();
		}
		
		System.out.print("\u001b[2J"); //Clear terminal
		System.out.print("\u001b[H"); //Move cursor to home
		
		//Enter port number:
		
		System.out.println("Thank you. Please enter the port number.");
		System.out.print("> ");
		
		try {
			portNumber = Integer.parseInt(console.readLine());
		} catch (IOException e) {
			System.out.println();
			System.out.println("ERROR: READ CONSOLE INPUT FAILED");
			System.out.println();
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println("Waiting for server...");
		
		//Create socket:
		
		try {
			socket = new Socket(hostName, portNumber);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.out.println();
			System.out.println("ERROR: UNKNOWN HOST");
			System.out.println();
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println();
			System.out.println("ERROR: CREATE SOCKET FAILED");
			System.out.println();
			e.printStackTrace();
		}
		
		System.out.print("\u001b[2J"); //Clear terminal
		System.out.print("\u001b[H"); //Move cursor to home
		
		//Get client user name:
		
		System.out.println("Thank you. Please enter your name.");
		System.out.print("> ");
		
		try {
			clientUserName = console.readLine();
		} catch (IOException e) {
			System.out.println();
			System.out.println("ERROR: READ CONSOLE INPUT FAILED");
			System.out.println();
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println("Waiting for server...");
		
		//Start console and server handling threads:
		
		consoleHandler = new ConsoleHandler(out, console, clientUserName, this);
		serverHandler = new ServerHandler(in, messages);
		
		consoleHandler.start();
		serverHandler.start();
		
		System.out.print("\u001b[2J"); //Clear terminal
		System.out.print("\u001b[H"); //Move cursor to home
		
		System.out.println("Messaging session successfully joined.");
		System.out.println();
		
		//Alert the other users that this client has joined the messaging session:
		
		out.println("[" + clientUserName + " joined]");
		
		System.out.print("\u001b[36m"); //Cyan text
		
		//Handle messages
		
		while(true) {
			if(!messages.isEmpty()) {
				message = messages.pop();
				
				if(message.startsWith(clientUserName)) {
					System.out.print("\u001b[36m"); //Cyan text
					System.out.print("\u001b[1A"); //Move cursor up one line
					System.out.println(message);
				} else {
					System.out.print("\u001b[37m"); //White text
					System.out.println(message);
					System.out.print("\u001b[36m"); //Cyan text
				}
			}
		}
		
	}
	
	public void exit(PrintWriter out, String clientUserName) {
		
		out.println("[" + clientUserName + " left]");
		
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println();
			System.out.println("ERROR: CLOSE SOCKET FAILED");
			System.out.println();
			e.printStackTrace();
		}
		
		System.exit(0);
		
	}
	
}
