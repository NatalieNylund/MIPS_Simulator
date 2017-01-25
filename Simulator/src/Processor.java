import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Processor {

	private InstructionMemory instrMem;
	private Instruction instruction;
	private Control control;
	private ALUControl ALUControl;
	private Registers registers;
	private ALU alu;
	private DataMemory dataMem;
	private PC pc;
	private ADD addUnit;
	private List<Integer> registerList;
	private List<Integer> dataMemoryList;
	private ArrayList<Instruction> instrList;
	private boolean isHex = false;
	private boolean isStop = false;
	
	public Processor(String fileName) throws Exception{
		
		registerList = new ArrayList<Integer>();
		dataMemoryList = new ArrayList<Integer>();
		instrList = new ArrayList<Instruction>();

		BufferedReader br = null;
		br = new BufferedReader(new FileReader(fileName));

		//Create registers and fill them with zeros
		registers = new Registers();
		alu = new ALU();
		ALUControl = new ALUControl();
		dataMem = new DataMemory();
		pc = new PC();
		addUnit = new ADD();

		//Get instructions from file
		String line = br.readLine();

		while(line != null) {
			line = line.trim();

			if((line.length() != 0) && (line.length() > 1)) {
				instrList.add(new Instruction(line));
			}

			//Change line from file
			line = br.readLine();
		}

		//Save instructions in instruction memory
		instrMem = new InstructionMemory(instrList);

		//Fetch and parse first instruction
		getNextInstruction();
   
		br.close();
		

	}
	private void printRegisters(){
		UserInterface.registerArea.setText("");
		PrintStream toGui = new PrintStream(new RedirectText(UserInterface.registerArea));
		System.setOut(toGui);
		for(int i = 0; i < registerList.size(); i++){
			String val = Integer.toString(registers.readReg(registerList.get(i)));
			if(isHex){
				val = "0x" + convertNumber(val, 10, 16);
			}
			System.out.println(registers.getName(registerList.get(i)) + " " + val);
		}
	}
	private void printDataMemory(DataMemory dataMem, List<Integer> dataMemoryList){
		UserInterface.memoryArea.setText("");
		PrintStream toGui = new PrintStream(new RedirectText(UserInterface.memoryArea));
		System.setOut(toGui);
		for(int i = 0; i < dataMemoryList.size(); i++){
			String val = Integer.toString(dataMem.readMemory(dataMemoryList.get(i), true));
			if(isHex){
				val = "0x" + convertNumber(val, 10, 16);
			}
			System.out.println(dataMemoryList.get(i) + " " + val);
		}
	}
	private void printInstructions(){
		UserInterface.instructionArea.setText("");
		
		PrintStream toGui = new PrintStream(new RedirectText(UserInterface.instructionArea));
		System.setOut(toGui);
		for(int i = 0; i < instrList.size(); i++){
			if(instrList.get(i) == instruction){
				System.out.print("-> ");			

			}
			System.out.print(instrList.get(i).getInstrStr());
			String rs, rt, rd, op, funct, adress;
			op = Integer.toString(instrList.get(i).getOpcode());
			rs = Integer.toString(instrList.get(i).getRs());
			rt = Integer.toString(instrList.get(i).getRt());
			rd = Integer.toString(instrList.get(i).getRd());
			funct = Integer.toString(instrList.get(i).getFunct());
			adress = Integer.toString(instrList.get(i).getOffset());
			
			if(isHex){
				op = "0x" + convertNumber(op, 10, 16);
				rs = "0x" + convertNumber(rs, 10, 16);
				rt = "0x" + convertNumber(rt, 10, 16);
				rd = "0x" + convertNumber(rd, 10, 16);
				funct = "0x" + convertNumber(funct, 10, 16);
				adress = "0x" + convertNumber(adress, 10, 16);
				
			}
			if(instrList.get(i).getType()== 'r'){
				System.out.print("          OP: " + op + " RD: " + rd + " RS: " + rs + " RT: " + rt + " FUNCT: " + funct);
			}else if(instrList.get(i).getName().equals("sw") || instrList.get(i).getName().equals("lw")){
				System.out.print("          OP: " + op + " RT: " + rt + " ADDRESS: " + adress + " (RS): " + rs);
			}else if(instrList.get(i).getName().equals("addi") || instrList.get(i).getName().equals("ori")){
				System.out.print("          OP: " + op + " RT: " + rt + " RS: " + rs + " IMM: " + adress);
			}else if(instrList.get(i).getName().equals("sra") || instrList.get(i).getName().equals("srl")){
				System.out.print("          OP: " + op + " RD: " + rd + " RT: " + rt + " SHAMT: " + adress + " FUNCT: " + funct);
			}else if(instrList.get(i).getName().equals("beq")){
				System.out.print("          OP: " + op + " RS: " + rs + " RT: " + rt + " OFFSET: " + adress);
			}else if(instrList.get(i).getName().equals("j")){
				System.out.print("          OP: " + op + " TARGET: " + adress);
			}else if(instrList.get(i).getName().equals("jr")){
				System.out.print("          OP: " + op + " RS: " + rs + " FUNCT: " + funct);
			}
				
			System.out.println("");
		}
		
		UserInterface.pcArea.setText("");
		toGui = new PrintStream(new RedirectText(UserInterface.pcArea));
		System.setOut(toGui);
		System.out.println("PC COUNTER " + pc.get());
	}
	public String convertNumber(String s, int from, int to) {
		return new BigInteger(s, from).toString(to);	   
	}
	public void setHex(){
		if(isHex){
			isHex = false;
		}else{
			isHex = true;
		}
			
	}
	public void stop(boolean stop){
		isStop = stop;
	}
	public void runMIPS(){

		while ((instruction != null && !isStop)) {

			stepMIPS();
			if(instruction != null && instruction.getExit()){
				stepMIPS();
				break;
			}
		}	
		
	}
	
	public void stepMIPS(){
		if(instruction.getExit()){
			printInstructions();

		}
		else if((instruction != null)){
			
			printInstructions();
			short ALUControlInp = 0;
			int reg1 = -1;
			int reg2 = -1;
			int ALURes = -1;
			int ALUZero = 0;
			int writeReg = 0;
			int writeData = 0;
	
			//Compute new value for program counter
			int pcCount = addUnit.addition(pc.get(), 4);
	
			if(!instruction.getNop()){
				//Check what register to write to
				writeReg = mux(instruction.getRt(), instruction.getRd(), control.getRegDst());
	
				//Read register RS
				reg1 = registers.readReg(instruction.getRs());
	
				//Check what register to read from
				reg2 = mux(registers.readReg(instruction.getRt()), instruction.getOffset() ,control.getALUSrc());
	
				//Run the ALU control
				ALUControlInp = runALUControl();
				
				//Run ALU
				alu.doInstruction(ALUControlInp, reg1, reg2);
				ALURes = alu.getResult();
				ALUZero = alu.getZero();
	
				//Compute branch address
				int branchAddr = addUnit.addition(pcCount, (instruction.getOffset() << 2));
	
				//Check if branch
				pcCount = mux(pcCount, branchAddr, (control.getBranch() && (ALUZero == 1)));
	
				//Compute jump address
				int jmpAddr = instruction.getOffset() << 2;
				int upperBits = (pcCount >> 28);
				jmpAddr = (upperBits << 28) | jmpAddr;
	
				jmpAddr = mux(jmpAddr, reg1, control.getJumpReg());
	
				//Check if jump
				pcCount = mux(pcCount, jmpAddr, control.getJump());
	
				//Read data memory
				writeData = dataMem.readMemory(ALURes, control.getMemRead());
	
				//Write to data memory
				dataMem.writeMemory(ALURes, registers.readReg(instruction.getRt()), control.getMemWrite());
				//Set which address at data memory that has been changed
				if(control.getMemWrite() && !dataMemoryList.contains(ALURes)){
					dataMemoryList.add(ALURes);
					Collections.sort(dataMemoryList);
				}
				
				//Check which data to use
				writeData = mux(ALURes, writeData, control.getMemtoReg());
	
				//Write to register
				registers.writeReg(writeReg, writeData, control.getRegWrite());
				//Set what register has been changed
				if(control.getRegWrite() && !registerList.contains(writeReg)){
					registerList.add(writeReg);
					Collections.sort(registerList);
				}
			}
			
			//Update program counter
			pc.set(pcCount);
		
			printRegisters();
			printDataMemory(dataMem, dataMemoryList);
	
			//Fetch and parse next instruction
			getNextInstruction();
		}
	}
	
	private int mux(int val1, int val2, boolean chooseVal2){
		if(chooseVal2){
			return val2;
		}else{
			return val1;
		}
	}
	

	private void getNextInstruction(){
		//Fetch instruction
		instruction = instrMem.fetch(pc.get());

		if (instruction != null) {
			//Set control lines
			control = new Control(instruction);
		}
	}

	private short runALUControl() {
		boolean ALUOpCode = false;
		short ALUOp1 = 0;
		short ALUOp0 = 0;
		short ALUControlInp = 0;

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
		ALUControlInp = ALUControl.getALUControlInput(ALUOp1, ALUOp0, instruction.getFunct());

		return ALUControlInp;
	}
	
	private void resetRegisters(){
		registerList.clear();
		registers.reset();

	}
	
	private void resetDatamemory(){
		dataMemoryList.clear();
		dataMem.reset();
	
	}
	private void resetPc(){
		pc.reset();
	}
	//Empty the list and arrays
	public void reset(){

		UserInterface.registerArea.setText("");
		UserInterface.memoryArea.setText("");
		UserInterface.instructionArea.setText("");
		UserInterface.pcArea.setText("");
		resetRegisters();
		resetDatamemory();
		resetPc();
		getNextInstruction();
		
	}
 }


