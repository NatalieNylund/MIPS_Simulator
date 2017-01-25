/*
 * Course: Computer Organization and Architecture, HT16
 * Author: Natalie Nylund (oi11nnd), Josefin Svensson (c14jsn)
 * Date: 2017-01-25
 * 
 * Description:
 * This class listens to buttons in the interface and 
 * calls to methods corresponding to the action of a button.
 * The run button causes the program to create a new thread
 * and runs all the instructions from the instruction file.
 * The step button steps through the instruction file and 
 * simulates one instruction per click.
 * The reset button resets the list that have been filled
 * and clears the screen.
 */



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

class ButtonListener implements ActionListener  {

  	private Processor runMips;
    
    public ButtonListener(Processor processor){
    	runMips = processor;
    }

	@Override
    public void actionPerformed(ActionEvent e) {
    	
    	Thread thread = new Thread(new Runnable() {

	        @Override
	        public void run() {
	        	runMips.stop(false);
	        	runMips.runMIPS();

	        	System.out.println(SwingUtilities.isEventDispatchThread());
	        	System.out.println(Thread.currentThread().getName());
	           
	        }});
    	if(e.getSource() == UserInterface.runButton){
    		
    		thread.start();

    	}
    	if(e.getSource() == UserInterface.stepButton){
    		runMips.stepMIPS();
    	}
    	if(e.getSource() == UserInterface.resetButton){
    		

    		runMips.stop(true);	
    		try {
				thread.join();

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		runMips.reset();	

    		

    	}
    	if(e.getSource() == UserInterface.checkBox){
    		runMips.setHex();
    	}
    	
    }
    
}