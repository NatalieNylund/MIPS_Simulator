import java.util.*;

/*
 * This class takes a line of an instruction and parses it 
 * to parameters depending of the instruction.
 * Input: String line, a line with an instruction
 * Output: short, boolean, parameters set based on instruction
 */

public class Instruction {
	
	//R-format rd, rs, rt
	public static final short FUNCT_ADD = 0x20;
	public static final short FUNCT_SUB = 0x22;
	public static final short FUNCT_AND = 0x24;
	public static final short FUNCT_OR = 0x25;
	public static final short FUNCT_NOR = 0x27;
	public static final short FUNCT_SLT = 0x2a;

	// I-format rt, adress(rs)
	public static final short OPCODE_LW = 0x23;
	public static final short OPCODE_SW = 0x2b;
	// I-format rs, rt, label
	public static final short OPCODE_BEQ = 4;
	
	// I-format rt, rs, imm
	public static final short OPCODE_ADDI = 0x08;
	public static final short OPCODE_ORI = 0x0d;
	// I-format rd, rt, shamt
	public static final short FUNCT_SRL = 0x02;
	public static final short FUNCT_SRA = 0x03;
	// J-format target
	public static final short OPCODE_J = 0x02;
	// J-format rs
	public static final short FUNCT_JR = 0x08;

	private short opcode = 0;
	private short funct = 0;
	private short rd = 0;
	private short rs = 0;
	private short rt = 0;
	private short offset = 0;

	private boolean isRd = false;
	private boolean isRt = false;
	private boolean isRs = false;
	
	private boolean r_type = false;
	private boolean j_type = false;
	private boolean i_type = false;
	private boolean is_exit;
	private boolean is_nop;

	
	public Instruction(String line) throws Exception {
			
			line = line.replaceAll(",", "");
			StringTokenizer tokens = new StringTokenizer(line, " ");
			String op = "", t1 = "", t2 = "", t3 = "";

			op = tokens.nextToken();

			try {
				t1 = tokens.nextToken();
				t2 = tokens.nextToken();
				t3 = tokens.nextToken();
			} catch(NoSuchElementException e) {}

			if(op.equalsIgnoreCase("add")) {
				funct = FUNCT_ADD;
				r_type = true;
			} else if(op.equalsIgnoreCase("sub")) {
				funct = FUNCT_SUB;
				r_type = true;
			} else if(op.equalsIgnoreCase("and")) {
				funct = FUNCT_AND;
				r_type = true;
			} else if(op.equalsIgnoreCase("or")) {
				funct = FUNCT_OR;
				r_type = true;
			} else if(op.equalsIgnoreCase("nor")) {
				funct = FUNCT_NOR;
				r_type = true;
			} else if(op.equalsIgnoreCase("slt")) {
				funct = FUNCT_SLT;
				r_type = true;
			}
			else if(op.equalsIgnoreCase("srl")){
				funct = FUNCT_SRL;
				i_type = true;
			}
			else if(op.equalsIgnoreCase("sra")){
				funct = FUNCT_SRA;
				i_type = true;
			}
			else if(op.equalsIgnoreCase("jr")){
				funct = FUNCT_JR;
				j_type = true;
			}
			
			else if(op.equalsIgnoreCase("ori")){
				opcode = OPCODE_ORI;
				i_type = true;
			}
			else if(op.equalsIgnoreCase("addi")){
				opcode = OPCODE_ADDI;
				i_type = true;
			}
			else if(op.equalsIgnoreCase("lw")) {
				opcode = OPCODE_LW;
				i_type = true;
			} 
			else if(op.equalsIgnoreCase("sw")) {
				opcode = OPCODE_SW;
				i_type = true;
			}
			else if(op.equalsIgnoreCase("j")){
				opcode = OPCODE_J;
				j_type = true;
			}
			else if(op.equalsIgnoreCase("beq")) {
				opcode = OPCODE_BEQ;
				i_type = true;
			}
			
			else if(op.equalsIgnoreCase("nop")) {
				is_nop = true;
			}

			else if(op.equalsIgnoreCase("exit")) {
				is_exit = true;
			}

			
			//Parse additional parameters
			if(r_type){
				rd = parseReg(t1);
				rs = parseReg(t2);
				rt = parseReg(t3);
				isRd = true;
				isRt = true;
				isRs = true;
			}
			else if(i_type){
				if(opcode == OPCODE_LW || opcode == OPCODE_SW){
					//Here t3 should be empty
					rt = parseReg(t1);
					isRt = true;
					if((t2.indexOf('(')!= -1)){
						rs = parseReg(t2.substring(t2.indexOf('(')+1, t2.indexOf(')')));
						isRs = true;
						offset = parseAddr(t2.substring(0, t2.indexOf('(')));
					}
					else{
						rs = parseReg(t2);
					}	
				}
				else if(opcode == OPCODE_ADDI || opcode == OPCODE_ORI){
					rt = parseReg(t1);
					rs = parseReg(t2);
					isRt = true;
					isRs = true;
					offset = parseAddr(t3);
				}
				else if(funct == FUNCT_SRL || funct == FUNCT_SRA){
					rd = parseReg(t1);
					rt = parseReg(t2);
					isRd = true;
					isRt = true;
					offset = parseAddr(t3);
					
				}
				else if(opcode == OPCODE_BEQ){
					rs = parseReg(t1);
					rt = parseReg(t2);
					isRs = true;
					isRt = true;
					offset = parseAddr(t3);
				}
			}
			else if(j_type){
				if(funct == FUNCT_JR){
					rs = parseReg(t1);
					isRs = true;
				}
				else if(opcode == OPCODE_J){
					offset = parseReg(t1);
				}
			}
	}


	private short parseReg(String register) throws Exception {
		if(register.charAt(0) == '$') {
			if(register.equalsIgnoreCase("$zero")) {
				return 0;
			} else if(register.equalsIgnoreCase("$gp")) {
				return 28;
			} else if(register.equalsIgnoreCase("$sp")) {
				return 29;
			} else if(register.equalsIgnoreCase("$fp")) {
				return 30;
			} else if(register.equalsIgnoreCase("$ra")) {
				return 31;
			}

			char prefix = register.charAt(1);
			short number = Short.parseShort(register.substring(2));
			switch(prefix) {
			case 'v':
				number += 2;
				break;
			case 'a':
				number += 4;
				break;
			case 't':
				number += 8;
				if(number >= 16) {
					number += 8;
				}
				break;
			case 's':
				number += 16;
				break;

			default:
				throw new Exception("Invalid register " + register);
			}
			return number;
		}

		return parseAddr(register);
	}
	
	private short parseAddr(String address) {
		/*
		 * If address is in hexadecimal
		 */
		if(address.contains("x")) {
			return Short.parseShort(
					address.substring(address.indexOf('x')+1), 16);
		}
		return Short.parseShort(address);
	}
	
	//Get-methods for each parameter
	public short getOpcode(){
		return opcode;
	}
	public short getFunct(){
		return funct;
	}
	public short getOffset(){
		return offset;
	}
	public short getRd(){
		return rd;
	}
	public short getRs(){
		return rs;
	}
	public short getRt(){
		return rt;
	}
	public char getType(){
		if(r_type){
			return 'r';
		}
		else if(i_type){
			return 'i';
		}
		else if(j_type){
			return 'j';
			
		}
		else{
			return 'e';
		}
	}
	public boolean getExit(){
		return is_exit;
	}
	public boolean getNop(){
		return is_nop;
	}

	public boolean getIsRd(){
		return isRd;
	}
	
	public boolean getIsRt(){
		return isRt;
	}
	public boolean getIsRs(){
		return isRs;
	}
}
