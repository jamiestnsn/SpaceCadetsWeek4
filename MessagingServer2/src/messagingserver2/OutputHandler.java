package messagingserver2;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

public class OutputHandler implements Runnable {
	
	private ArrayList<PrintWriter> out;
	private Stack<String> messages;
	private Thread thread;
	private String message;
	
	public OutputHandler(ArrayList<PrintWriter> out, Stack<String> messages) {
		
		this.out = out;
		this.messages = messages;
		
	}

	public void run() {
		
		while (true) {
			if (!messages.isEmpty()) {
				message = messages.pop();
				
				for (int i = 0; i < out.size(); i++) {
					out.get(i).println(message);
				}
			}
		}
		
	}
	
	public void start() {
		
		if(thread == null) {
			thread = new Thread(this, "outputHandlerThread");
			thread.start();
		}
		
	}

}
