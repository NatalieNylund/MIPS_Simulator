/*
 * Course: Computer Organization and Architecture, HT16
 * Author: Natalie Nylund (oi11nnd), Josefin Svensson (c14jsn)
 * Date: 2017-01-25
 * 
 * Description:
 * Contains an array that supports 1000 bytes of memory and
 * represents the data memory of a MIPS32 processor. 
 */
public class DataMemory {
	private int[] data;

	public DataMemory(){
		data = new int[1000];
		reset();
	}

	public void reset() {
	    for(int i = 0; i < data.length; i++) {
	        data[i] = 0;
        }
    }

    public int readMemory(int addr, boolean MemRead) {
		if(MemRead) {
			return get(addr);
		}
		return -1;
    }

    public void writeMemory(int addr, int data, boolean MemWrite) {
		if(MemWrite) {
			set(addr, data);
		}
    }

	private int get(int addr) {
	    return data[addr];
    }

    private void set(int addr, int val) {
	    data[addr] = val;
    }
}
