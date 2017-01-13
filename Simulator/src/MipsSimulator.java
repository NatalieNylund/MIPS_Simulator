import java.io.*;
import java.util.*;

public class MipsSimulator {
	
	/*Should not be static, change when main changes*/
	public static ArrayList<Instruction> instructions;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = null;
		instructions = new ArrayList<Instruction>();
		
		
		try {
			if(args.length < 1) {
				System.out.println("Must include file as argument");
				System.exit(1);
			} else {
				br = new BufferedReader(new FileReader(args[0]));
			}
			

		} catch (FileNotFoundException e) {
			//If empty exit
			System.exit(1);
			e.printStackTrace();
		}
		try {
		    String line = br.readLine();
		   
		    
		    while(line != null) {
		    	if(line.trim().length() != 0) {
		    		Instruction instruction = new Instruction(line);
					instructions.add(instruction);
		    	}
		        line = br.readLine();
		    }

		} finally {
		    br.close();
		}
	}

}
