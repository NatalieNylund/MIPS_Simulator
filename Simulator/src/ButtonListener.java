
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class ButtonListener implements ActionListener  {

  	private Processor runMips;
    
    public ButtonListener(Processor processor){
    	runMips = processor;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == UserInterface.runButton){
			runMips.runMIPS();
    	}
    	if(e.getSource() == UserInterface.stepButton){
    		runMips.stepMIPS();
    	}
    	if(e.getSource() == UserInterface.resetButton){
    		runMips.reset();
    		
    	}
    	if(e.getSource() == UserInterface.checkBox){
    		runMips.setHex();
    	}

    }
}