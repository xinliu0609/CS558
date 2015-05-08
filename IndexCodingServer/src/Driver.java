import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Driver {
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException{

		int portNumber = 8888;
		ServerSocket serverSocket = null;
		Socket client = null;
		
	    ExecutorService executor = Executors.newFixedThreadPool(10);
	    List<Future<String>> futureList = new ArrayList<Future<String>>(); 
	    List<String> requestVideoList = new ArrayList<String>();
		
		int numberOfClient = 0;
		
		try{
			serverSocket = new ServerSocket(portNumber);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		while(numberOfClient < 2){
			
			client = serverSocket.accept();
			System.out.println("Received a connection from client");	
			Future<String> future = executor.submit(new ResponseInquiry(client));
			futureList.add(future);
			numberOfClient++;
		}
		
		for(Future<String> f : futureList){
			if(!f.get().equals("")){
				requestVideoList.add(f.get());
			}
		}
		
		// use the video list to generate an INCOMPLETE matrix
		// send to the matrix completion module
		// get each row of the COMPLETED matrix
		// send these information to the following function
		
		try {
			new BroadCastVideo(requestVideoList).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
