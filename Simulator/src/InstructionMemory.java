import java.util.ArrayList;

/*
 * Contains a memory for instructions in the form of an array
 * Each element in the array is of the class Instruction, i.e. represents 32 bits (or 4 bytes)
 */
public class InstructionMemory {
	private int op;
	private Instruction[] instructions;

	/**
	 *
	 * @param instrList list of instructions
	 */
	public InstructionMemory(ArrayList<Instruction> instrList) {
		instructions = new Instruction[instrList.size()];

		for(int i = 0; i < instrList.size(); i++) {
			instructions[i] = instrList.get(i);
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
		return instructions[pc/4];
	}

}
