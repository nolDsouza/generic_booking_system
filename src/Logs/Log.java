package Logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Log {
	public Logger logger; 
	FileHandler fh;
	
	public Log() throws IOException{
		 
		String file_name = "src/Logs/log.txt"; // fix this to make it point to the package txt!!!!!
		
		//checks if the file exits, if so just appends to it, otherwise creates it 
		File f = new File(file_name);
		if(!f.exists()){
			f.createNewFile();
		}
		fh = new FileHandler(file_name, true);
		logger = Logger.getLogger("Test");
		logger.addHandler(fh);
		
		//displays all the levels from finest to severe
		logger.setLevel(Level.ALL);
		
		//formats the log.txt
		SimpleFormatter format = new SimpleFormatter();
		fh.setFormatter(format);
		
		//fh.close();
		
	}
	public void closeHandler()
	{
		fh.close();
	}
	
	public void clearLog(String file_name){
		PrintWriter pw;
		try {
			pw = new PrintWriter(file_name);
			pw.print("");
			pw.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
