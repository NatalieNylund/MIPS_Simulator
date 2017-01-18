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
	
	public ALU(){
		
	}
	/*
	 * This method  checks the ALU control input to decide
	 * what instruction to perform, then uses the given register
	 * values to perform that instruction upon.
	 */
	public void doInstruction(short input, int reg1, int reg2){
		
		if(input == AND){
			ALUResult = reg1 & reg2;

		}else if(input == OR){
			ALUResult = reg1 | reg2;

		}else if(input == ADD){
			ALUResult = reg1 + reg2;

		}else if(input == SUB){
			ALUResult = reg1 - reg2;

		}else if(input == SLT){
			ALUResult = reg1 < reg2 ? 1 : 0;

		}else if(input == NOR){
			ALUResult = ~(reg1 | reg2);
		}else ALUResult = -1;
		
	}

	public int getResult(){
		return ALUResult;
	}
	
	public int getZero(){
		
		if(ALUResult == 0){
			return 1;
		}
		else return 0;
	}
}
