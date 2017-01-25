/*
 * Course: Computer Organization and Architecture, HT16
 * Author: Natalie Nylund (oi11nnd), Josefin Svensson (c14jsn)
 * Date: 2017-01-25
 * 
 * Description:
 * It is a register that holds the address of the current instruction
 */


public class PC {
	
	private int value;

	public PC(){
		value = 0;
	}
	public void set(int value) {
		assert value%4 == 0;
		this.value = value;
	}

	public int get() {
		return value;
	}

	public void reset() {
		value = 0;
	}
}

