/*
 * 
 */
public class ALU {
	
	private short AND = 0;
	private short OR = 1;
	private short ADD = 2;
	private short SUB = 6;
	private short SLT = 7;
	private short NOR = 12;
	
	private int ALUResult;
	private int zero = 0;
	
	public ALU(){
		
	}
	/*
	 * This method  checks the ALU control input to decide
	 * what instruction to perform, then uses the given register
	 * values to perform that instruction upon.
	 */
	public int doInstruction(short input, int read1, int read2){
		
		if(input == AND){
			ALUResult = read1 & read2;
			return ALUResult;
		}else if(input == OR){
			ALUResult = read1 | read2;
			return ALUResult;
		}else if(input == ADD){
			ALUResult = read1 + read2;
			return ALUResult;
		}else if(input == SUB){
			ALUResult = read1 - read2;
			return ALUResult;
		}else if(input == SLT){
			ALUResult = read1 < read2 ? 1 : 0;
			return ALUResult;
		}else if(input == NOR){
			ALUResult = ~(read1 | read2);
			return ALUResult;
		}else return -1;
		
	}
	
	public int getZero(){
		
		if(ALUResult == 0){
			return zero = 0;
		}
		else return zero;
	}
}
