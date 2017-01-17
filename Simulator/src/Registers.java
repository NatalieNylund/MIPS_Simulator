
public class Registers {
	private byte[] registers;
	String[] names;
	
	public Registers() {
		registers = new byte[32];
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
			registers[i] = 0;
		}
	}

	private byte get(int addr) {
		return registers[addr];
	}

	private void set(int addr, byte val) {
		registers[addr] = val;
	}

	public byte readReg(int addr) {
		return get(addr);
	}

	public void writeReg(int addr, byte data) {
		/* Address 0 is $zero register */
		if(addr != 0) {
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
