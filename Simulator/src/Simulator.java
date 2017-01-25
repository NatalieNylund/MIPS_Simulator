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
