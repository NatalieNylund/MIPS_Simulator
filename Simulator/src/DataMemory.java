
public class DataMemory {
	private byte[] data;

	public DataMemory(){
		data = new byte[1000];
		reset();
	}

	public void reset() {
	    for(int i = 0; i < data.length; i++) {
	        data[i] = 0;
        }
    }

    public byte readMemory(int addr) {
	    return get(addr);
    }

    public void writeMemory(int addr, byte data) {
	    set(addr, data);
    }

	private byte get(int addr) {
	    return data[addr];
    }

    private void set(int addr, byte val) {
	    data[addr] = val;
    }
}
