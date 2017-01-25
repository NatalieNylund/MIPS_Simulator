/*
 * Course: Computer Organization and Architecture, HT16
 * Author: Natalie Nylund (oi11nnd), Josefin Svensson (c14jsn)
 * Date: 2017-01-25
 * 
 * Description:
 * This class contains a main that starts the gui and
 * makes a call to the processor class that starts the
 * simulation depending on the users choice.
 */
import javax.swing.SwingUtilities;

public class Simulator {

	public static void main(String[] args) throws Exception {

			if(args.length < 1) {
				System.out.println("Must include file as argument");
				System.exit(1);
			}

			String fileName = args[0];
			Processor processor = new Processor(fileName);
			
		
		
		/*Undviker att anropa swing metoder fran main-traden*/
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                UserInterface userInterface = new UserInterface("MIPS Simulator", processor);
                userInterface.showGui();
            }});

        
	}

}
