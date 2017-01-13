import java.lang.Long;
import java.math.BigInteger;

/**
 * Class that translates 32-bits hexadecimal strings to MIPS instructions
 * 
 * @author c14llk, c14jsn, oi11nnd
 *
 */

public class Translator {
	
	/**
	 * Translates selected string (instruction on 32-bit hexadecimal form) 
	 * 
	 * @param str String to translate
	 * @return array with translated string on form: 
	 * 			{original string, format, decimal form, hexadecimal form, mnemonic form}
	 * 			if selected string is not supported:
	 *  		{original string, error message}
	 */
	public String[] translate(String str){
		String[] resultData = new String[5];
		resultData[0] = str;
		
		long number;
		String binary;
		
		if(str.substring(0, 2).equals("0x")) {
			number = Long.parseLong(str.substring(2), 16);
			binary = convertNumber(str.substring(2), 16, 2);
		} else {
			number = Long.parseLong(str); 
			binary = convertNumber(str, 10, 2);
		}

		if (binary.equals("0")){		
			for(int i = 1; i < resultData.length-1; i++) {
				resultData[i] = "0";
			}
			resultData[resultData.length-1] = "nop";
			return resultData;
		}
		
		while (binary.length() < 32){
			binary = "0" + binary; 
		}
		
		int op = (int)(number/(int)Math.pow(2, 26));
		
		Format formatInfo = new Format();
		char format = formatInfo.getFormat(op);
		
		if(format == 0) {
			String[] notSupported = {str, "Instruction not supported"};
			return notSupported;
		}
		
		String[] info;
		if(format == 'R'){
			resultData[1] = "R";
			int indices[] = {5, 5, 5, 5, 6};
			info = new String[indices.length];
			info = reformatString(op, indices, binary.substring(6));
		} else if (format == 'I') {
			resultData[1] = "I";
			int indices[] = {5, 5, 16};
			info = new String[indices.length];
			info = reformatString(op, indices, binary.substring(6));
		} else if (format == 'J') {
			resultData[1] = "J";
			int indices[] = {26};
			info = new String[indices.length];
			info = reformatString(op, indices, binary.substring(6));
		} else {
			info = null;
		}
		
		Instruction instrInfo = new Instruction();
		resultData[4] = instrInfo.getInstruction(info);
		
		String decForm = combineString(info);
		resultData[2] = decForm;
		
		convertToHex(info);
		String hexForm = combineString(info);
		resultData[3] = hexForm;
		
		return resultData;
	}
	
	/**
	 * Reformats a selected string (instruction on 32-bit hexadecimal form)
	 * 
	 * @param op the operation code of the instruction
	 * @param indices the indices format for the instruction on form:
	 * 			R format: {5, 5, 5, 6} 
	 * 			I format: {5, 5, 16}
	 * 			J format: {26}
	 * @param instruction the instruction to reformat (26 bits, without op code)
	 * @return the reformatted string (size of array indices + 1)
	 * 			
	 */
	private String[] reformatString(int op, int indices[], String instruction) {
		long field = 0;
		int length = 26;
		int index =  0;
		String[] result = new String[indices.length+1];
		result[0] = Integer.toString(op);
		
		for(int i = 0; i < indices.length; i++) {
			
			if (i != 0) {
				index += indices[i-1];
				length -= indices[i-1];
			}
			field = Long.parseLong(instruction.substring(index), 2);
			
			if (i != indices.length-1) {
				field = field/(long)Math.pow(2, length-indices[i]);
			}

			result[i+1] = Long.toString(field);
		}

		return result;
	}
	
	/**
	 * Combines elements of an array to a single string
	 * 
	 * @param info the array to combine
	 * @return the combined string
	 */
	private String combineString(String info[]) {
		String res = info[0];
		for(int i = 1; i < info.length; i++) {
			 res = res + " " + info[i];
		}
		
		return res;
	}
	
	/**
	 * Converts an array with numbers to hexadecimal form
	 *  
	 * @param info array to convert
	 */
	private void convertToHex(String info[]) {
		for(int i = 0; i < info.length; i++) {
			info[i] = convertNumber(info[i], 10, 16);
		}
	}
	
	/**
	 * Converts numbers between different formats
	 * 
	 * @param s number to convert (on string form)
	 * @param from format to convert from (e.g. 16 for hexadecimal)
	 * @param to format to convert to (e.g. 2 for binary)
	 * @return
	 */
	public String convertNumber(String s, int from, int to) {
		  return new BigInteger(s, from).toString(to);
	}
}
