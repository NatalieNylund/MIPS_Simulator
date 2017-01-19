import java.io.*;

public class Main {

	private static Instruction instruction;
	private static Control control;
	private static ALUControl ALUControl;
	private static Registers registers;
	private static ALU alu;
	private static DataMemory dataMem;
	
	public static void main(String[] args) throws Exception {
		
		boolean ALUOpCode = false;
		short ALUOp1 = 0;
		short ALUOp0 = 0;
		short ALUControlInp = 0;
		int reg1 = -1;
		int reg2 = -1;
		int ALURes = -1;
		int ALUZero = 0;
		int writeReg = 0;
		int writeData = 0;
		BufferedReader br = null;
		br = new BufferedReader(new FileReader("instructions.txt"));

		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			//Parse instruction
			instruction = new Instruction(line);
			System.out.println("INSTRUCTION: " + line);
			printStuff(instruction);
			//Set control lines
			control = new Control(instruction);
			
			//Create registers and fill them
			registers = new Registers();
			
			if(!control.getJump()){
				writeReg = mux(instruction.getRt(), instruction.getRd(), control.getRegDst());
				reg1 = registers.readReg(instruction.getRs());
				reg2 = mux(registers.readReg(instruction.getRt()), instruction.getOffset() ,control.getALUSrc());
				
			}else{
				
			}
			
	
			//Get variabels for ALU Control
			ALUOpCode = control.getALUOp0();
			if(ALUOpCode == false){
				ALUOp0 = 0;
			}else ALUOp0 = 1;
			
			ALUOpCode = control.getALUOp1();
			if(ALUOpCode == false){
				ALUOp1 = 0;
			}else ALUOp1 = 1;
			
			//Run ALU Control
			ALUControl = new ALUControl();
			ALUControlInp = ALUControl.getALUControlInput(ALUOp1, ALUOp0, instruction.getFunct());
			System.out.println("ALU Control Input = " + ALUControlInp);
			boolean memToReg = control.getMemtoReg();
			System.out.println("MemToReg: " + memToReg);
			
			//Run ALU
			alu = new ALU();
			alu.doInstruction(ALUControlInp, reg1, reg2);
			ALURes = alu.getResult();
			ALUZero = alu.getZero();
			System.out.println("ALU Output: " + ALURes);
			System.out.println("ALU Zero: " + ALUZero);
			
			dataMem = new DataMemory();

			writeData = dataMem.readMemory(ALURes, control.getMemRead());
			registers.writeReg(writeReg, writeData, control.getRegWrite());
			dataMem.writeMemory(ALURes, reg1, control.getMemWrite());
			System.out.println("Stored stuff: " + dataMem.readMemory(ALURes, control.getMemRead()));


			writeData = mux(ALURes, writeData, control.getMemtoReg());
			registers.writeReg(writeReg, writeData, control.getRegWrite());


			
			System.out.println("Write Data register: " + registers.readReg(instruction.getRd()));
			//Change line from file
		 	sb.append(line);
		    sb.append(System.lineSeparator());
		    line = br.readLine();
		    System.out.println();
		}   
		br.close();

	}
	
	public static int mux(int val1, int val2, boolean chooseVal2){
		if(chooseVal2){
			return val2;
		}else{
			return val1;
		}
	}
	
	public static void printStuff(Instruction instruction){
		short opcode = 0;
		short funct = 0;
		short offset = 0;
		short rd = 0;
		short rs = 0;
		short rt = 0;


		char type;

		boolean is_exit;
		boolean is_nop;
		
		opcode = instruction.getOpcode();
		funct = instruction.getFunct();
		offset= instruction.getOffset();
		rd = instruction.getRd();
		rs = instruction.getRs();
		rt = instruction.getRt();


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
		if(offset != 0){
			System.out.println("Offset: " + offset);
		}
		if(is_exit){
			System.out.println("Is exit!");
		}
		if(is_nop){
			System.out.println("Is nop!");
		}		

	}

}
