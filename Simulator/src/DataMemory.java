
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

    public int readMemory(int addr) {
	    return get(addr);
    }

    public void writeMemory(int addr, int data) {
	    set(addr, data);
    }

	private int get(int addr) {
	    return data[addr];
    }

    private void set(int addr, int val) {
	    data[addr] = val;
    }
}
