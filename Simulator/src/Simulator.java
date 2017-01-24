import javax.swing.SwingUtilities;

public class Simulator {

	public static void main(String[] args) throws Exception {
		Processor processor = new Processor();
		/*Undviker att anropa swing metoder fran main-traden*/
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                UserInterface userInterface = new UserInterface("MIPS Simulator", processor);
                userInterface.showGui();
            }});

        
	}

}
