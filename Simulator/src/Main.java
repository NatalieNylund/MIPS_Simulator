import java.io.*;

public class Main {

	private static Instruction instruction;
	private static Control control;
	private static ALUControl ALUControl;
	
	public static void main(String[] args) throws Exception {
		
		boolean ALUOpCode = false;
		short ALUOp1 = 0;
		short ALUOp0 = 0;
		short ALUControlInp = 0;
		BufferedReader br = null;
		br = new BufferedReader(new FileReader("instructions.txt"));

		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			instruction = new Instruction(line);
			System.out.println("INSTRUCTION: " + line);
			printStuff(instruction);
			control = new Control(instruction);
			
			ALUOpCode = control.getALUOp0();
			if(ALUOpCode == false){
				ALUOp0 = 0;
			}else ALUOp0 = 1;
			
			ALUOpCode = control.getALUOp1();
			if(ALUOpCode == false){
				ALUOp1 = 0;
			}else ALUOp1 = 1;
			
			ALUControl = new ALUControl();
			ALUControlInp = ALUControl.getALUControlInput(ALUOp1, ALUOp0, instruction.getFunct());
			System.out.println("ALU Control Input = " + ALUControlInp);
			boolean memToReg = control.getMemtoReg();
			System.out.println("MemToReg: " + memToReg);
					
		 	sb.append(line);
		    sb.append(System.lineSeparator());
		    line = br.readLine();
		    System.out.println();
		}   
		br.close();

	}
	
	public static void printStuff(Instruction instruction){
		short opcode = 0;
		short funct = 0;
		short shamt = 0;
		short label = 0;
		short adress = 0;
		short rd = 0;
		short rs = 0;
		short rt = 0;
		short imm = 0;

		char type;

		boolean is_exit;
		boolean is_nop;
		
		opcode = instruction.getOpcode();
		funct = instruction.getFunct();
		shamt = instruction.getShamt();
		label = instruction.getLabel();
		adress = instruction.getAddress();
		rd = instruction.getRd();
		rs = instruction.getRs();
		rt = instruction.getRt();
		imm = instruction.getImm();

		type = instruction.getType();

		is_exit = instruction.getExit();
		is_nop = instruction.getNop();
		
		System.out.println("Op: " + opcode);
		System.out.println("Type: " + type);
		
		if(rs != 0){
			System.out.println("Rs: " + rs);
		}
		if(rt != 0){
			System.out.println("Rt: " + rt);
		}
		if(rd != 0){
			System.out.println("Rd: " + rd);
		}
		if(funct != 0){
			System.out.println("Funct: " + funct);
		}
		if(shamt != 0){
			System.out.println("Shamt: " + shamt);
		}
		if(label != 0){
			System.out.println("Label: " + label);
		}
		if(adress != 0){
			System.out.println("Address: " + adress);
		}
		if(imm != 0){
			System.out.println("Imm: " + imm);
		}
		if(is_exit){
			System.out.println("Is exit!");
		}
		if(is_nop){
			System.out.println("Is nop!");
		}		

	}

}
