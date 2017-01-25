/*
 * Course: Computer Organization and Architecture, HT16
 * Author: Natalie Nylund (oi11nnd), Josefin Svensson (c14jsn)
 * Date: 2017-01-25
 * 
 * Description:
 * This class specifies what operation ALU performs.
 * It takes 3 input signals and generates a 4-bit ALU control input
 */

public class ALUControl {
	private short ADD = 0x20;
	private short SUB = 0x22;
	private short AND = 0x24;
	private short OR = 0x25;
	private short NOR = 0x27;
	private short SLT = 0x2a;
	private short SRL = 0x02;
	private short SRA = 0x03;
	
	private short ALU_AND = 0x0;
	private short ALU_OR = 0x1;
	private short ALU_ADD = 0x2;
	private short ALU_SUB = 0x6;
	private short ALU_SLT = 0x7;
	private short ALU_NOR = 0xc;
	private short ALU_SRL = 0x3;
	private short ALU_SRA = 0x4;
			
	
	public ALUControl(){

	}
	
	public short getALUControlInput(short OpCode1, short OpCode0, short funct){
		/*
		 * For load word and store word instructions we use the ALU to
		 * compute the memory address by addition. This depends on ALUop
		 */
		if(OpCode1 == 0 && OpCode0 == 0){
			return ALU_ADD;
		}
		//For beq
		else if(OpCode1 == 0 && OpCode0 == 1){
			return ALU_SUB;
		}else if(OpCode1 == 1 && OpCode0 == 0){
			
			if(funct == ADD){
				return ALU_ADD;
			}else if(funct == SUB){
				return ALU_SUB;
			}else if(funct == AND){
				return ALU_AND;
			}else if(funct == OR) {
				return ALU_OR;
			}else if(funct == NOR){
				return ALU_NOR;
			}else if(funct == SLT) {
				return ALU_SLT;
			} else if(funct == SRL) {
				return ALU_SRL;
			} else if (funct == SRA) {
				return ALU_SRA;
			}else return -1;
		//For ori
		}else if(OpCode0 == 1 && OpCode1 == 1) {
			return ALU_OR;
		}
		else return -1;
	}

}
