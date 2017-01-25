
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.xml.bind.SchemaOutputResolver;

class ButtonListener implements ActionListener  {

  	private Processor runMips;
  	private boolean running;
    
    public ButtonListener(Processor processor){
    	runMips = processor;
    	running = false;
    }

	@Override
    public void actionPerformed(ActionEvent e) {
    	
    	Thread thread = new Thread(new Runnable() {

	        @Override
	        public void run() {
	        	running = true;
	        	runMips.stop(false);
	        	runMips.runMIPS();
	        	running = false;
	        }});

    	if(e.getSource() == UserInterface.runButton){

   			if(!running) {
				thread.start();
			}

    	}
    	if(e.getSource() == UserInterface.stepButton){
    		runMips.stepMIPS();
    	}
    	if(e.getSource() == UserInterface.resetButton){
    		

    		runMips.stop(true);

			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

    		runMips.reset();	

    	}
    	if(e.getSource() == UserInterface.checkBox){
    		runMips.setHex();
    	}
    	
    }
    
}