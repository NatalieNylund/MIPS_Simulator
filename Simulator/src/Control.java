/*
 * Takes instruction op code (bits 31-26) as input and sets the control lines
 * according to something
 */

public class Control {

	private boolean RegDist;
	private boolean Branch;
	private boolean MemRead;
	private boolean MemtoReg;
	private boolean ALUOp1;
	private boolean ALUOp0;
	private boolean MemWrite;
	private boolean ALUsrc;
	private boolean RegWrite;
	
	public Control(){
		
	}

	private void setRegDst(){
		
	}
	
	public Boolean getRegDst(){
		
		return true;
	}
	
	private void setALUSrc(){
		
	}
	
	public Boolean getALUSrc(){
		
		return true;
	}
	
	private void setMemtoReg(){
		
	}
	
	public Boolean getMemtoReg(){
		
		return true;
	}
	
	private void setRegWrite(){
		
	}
	
	public Boolean getRegWrite(){
		
		return true;
	}
	
	private void setMemRead(){
		
	}
	
	public Boolean getMemRead(){
		
		return true;
	}
	
	private void setMemWrite(){
		
	}
	
	public Boolean getMemWrite(){
		
		return true;
	}
	
	private void setBranch(){
		
	}
	
	public Boolean getBranch(){
		
		return true;
	}
	
	private void setALUop(){
		
	}
	
	public Boolean getALUop(){
		
		return true;
	}
	
	
}
