/*
 * Course: Computer Organization and Architecture, HT16
 * Author: Natalie Nylund (oi11nnd), Josefin Svensson (c14jsn)
 * Date: 2017-01-25
 * 
 * Description:
 * Contains a memory for instructions in the form of an array
 * Each element in the array is of the class Instruction, i.e. 
 * represents 32 bits (or 4 bytes)
 */

import java.util.ArrayList;


public class InstructionMemory {
	private Instruction[] instructions;
	private int numOfInstr;

	/**
	 *
	 * @param instrList list of instructions
	 */
	public InstructionMemory(ArrayList<Instruction> instrList) {
		instructions = new Instruction[instrList.size()];
		numOfInstr = 0;

		for(int i = 0; i < instrList.size(); i++) {
			instructions[i] = instrList.get(i);
			numOfInstr++;
		}
	}

	/**
	 * Returns the Instruction from the memory based on the program counter
	 *
	 * @param pc the program counter
	 * @return the instruction
	 */
	public Instruction fetch(int pc) {
		/* Divide program counter by 4 because each element in the array represents 4 bytes */
		if((pc/4) < numOfInstr) {
			return instructions[pc/4];
		} else {
			return null;
		}
	}
	
	public void reset(){
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = null;
		}
	}

}
