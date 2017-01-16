/*
 * Takes instruction op code (bits 31-26) as input and sets the control lines
 * according to something
 */

public class Control {

	private boolean RegDst;
	private boolean Branch;
	private boolean MemRead;
	private boolean MemtoReg;
	private boolean ALUOp1;
	private boolean ALUOp0;
	private boolean MemWrite;
	private boolean ALUSrc;
	private boolean RegWrite;
	private boolean Jump;
	
	public Control(Instruction instr){
		setControlLines(instr);
	}
	
	private void setControlLines(Instruction instr) {
		setRegDst(false); 
		setBranch(false);  
		setMemRead(false);  
		setMemtoReg(false);  
		setALUOp1(false);  
		setALUOp0(false);  
		setMemWrite(false);  
		setALUSrc(false);  
		setRegWrite(false);  
		
		/* If R format */
		if(instr.getType() == 'r') {
			setRegDst(true);
			setRegWrite(true);
			setALUOp1(true);
		}
		/* If I format */
		else if(instr.getType() == 'i') {
			setRegWrite(true);
			setALUSrc(true);
			//ALUOp
		}
		/* If J format */
		else if(instr.getType() == 'j') {
			setJump(true);
			//ALUOp?
		}
		/* If lw */
		else if(instr.getOpcode() == 0x23) {
			setALUSrc(true);
			setMemtoReg(true);
			setRegWrite(true);
			setMemRead(true);
		}
		/* If sw */
		else if(instr.getOpcode() == 0x2b) {
			setALUSrc(true);
			setMemWrite(true);
		}
		/* If beq */
		else if(instr.getOpcode() == 0x04){
			setBranch(true);
			setALUOp0(true);
		}

	}
	
	public void update(Instruction instr) {
		setControlLines(instr);
	}

	private void setRegDst(boolean val){
		RegDst = val;
	}
	
	public Boolean getRegDst(){
		return RegDst;
	}
	
	private void setALUSrc(boolean val){
		ALUSrc = val;
	}
	
	public Boolean getALUSrc(){
		return ALUSrc;
	}
	
	private void setMemtoReg(boolean val){
		MemtoReg = val;
	}
	
	public Boolean getMemtoReg(){
		return MemtoReg;
	}
	
	private void setRegWrite(boolean val){
		RegWrite = val;
	}
	
	public Boolean getRegWrite(){
		return RegWrite;
	}
	
	private void setMemRead(boolean val){
		MemRead = val;
	}
	
	public Boolean getMemRead(){
		return MemRead;
	}
	
	private void setMemWrite(boolean val){
		MemWrite = val;
	}
	
	public Boolean getMemWrite(){
		return MemWrite;
	}
	
	private void setBranch(boolean val){
		Branch = val;
	}
	
	public Boolean getBranch(){
		return Branch;
	}
	
	private void setALUOp1(boolean val){
		ALUOp1 = val;
	}
	
	public Boolean getALUOp1(){
		return ALUOp1;
	}
	
	private void setALUOp0(boolean val){
		ALUOp0 = val;
	}
	
	public Boolean getALUOp0(){
		return ALUOp0;
	}
	
	private void setJump(boolean val){
		Jump = val;
	}
	
	public Boolean getJump(){
		return Jump;
	}
	
	
}
