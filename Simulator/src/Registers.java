
public class Registers {
	private int[] registers;
	String[] names;
	
	public Registers() {
		registers = new int[32];
		names = new String[]{"$zero",
					"$at",
					"$v0",
					"$v1",
					"$a0",
					"$a1",
					"$a2",
					"$a3",
					"$t0",
					"$t1",
					"$t2",
					"$t3",
					"$t4",
					"$t5",
					"$t6",
					"$t7",
					"$s0",
					"$s1",
					"$s2",
					"$s3",
					"$s4",
					"$s5",
					"$s6",
					"$s7",
					"$t8",
					"$t9",
					"$k0",
					"$k1",
					"$gp",
					"$sp",
					"$fp",
					"$ra"};
		reset();
	}

	public void reset() {
		for(int i = 0; i < registers.length; i++) {
			registers[i] = 1;
		}
	}

	private int get(int addr) {
		return registers[addr];
	}

	private void set(int addr, int val) {
			registers[addr] = val;
	}

	public int readReg(int addr) {
		if(addr <= 31) {
			return get(addr);
		}
		return -1;
	}

	public void writeReg(int addr, int data) {
		/* Check if register is available */
		if(((addr < 26) || (addr) > 27) && (addr < 32) && (addr != 0) && (addr != 1)) {
			set(addr, data);
		}
	}

	public String getName(int i) {
		if(i <= 32) {
			return names[i];
		}
		return null;
	}

}
