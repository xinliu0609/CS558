package shared;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;


public class ResponseInquiry implements Callable<String>{
	
	protected Socket clientSocket;
	
	public ResponseInquiry(Socket s){
		clientSocket = s;
	}

	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		
		String result = "";
		
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	
			String message = in.readLine();
			System.out.println("message from the client: "+message);
			
			OutputStream os = clientSocket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            PrintWriter pw = new PrintWriter(osw);
            
            File f = new File(message);
            if(f.exists() && !f.isDirectory()){
                pw.println("Yes");
                result = message;
            }else{
            	pw.println("No");
            }
            
            pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
