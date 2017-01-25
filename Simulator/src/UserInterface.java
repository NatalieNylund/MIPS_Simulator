/*
 * Course: Computer Organization and Architecture, HT16
 * Author: Natalie Nylund (oi11nnd), Josefin Svensson (c14jsn)
 * Date: 2017-01-25
 * 
 * Description:
 * This class represents the user interface of the program.
 * All the text areas and the buttons are created here.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class UserInterface {

	private JFrame frame;
	public JTextField textField;
	public static JTextArea instructionArea;
	public static JTextArea memoryArea;
	public static JTextArea registerArea;
	public static JTextArea pcArea;
	private JScrollPane scrollPane;
	public static JButton runButton;
	public static JButton stepButton;
	public static JButton resetButton;
	public static JCheckBox checkBox;
	private Processor processor;

	public UserInterface(String title, Processor processor){
		this.processor = processor;
		
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        // Build panels
        JPanel instructionPanel = buildMiddlePanel(1);
        JPanel memoryPanel = buildMiddlePanel(2);
        JPanel registerPanel = buildMiddlePanel(3);
        JPanel buttonPanel = buildLowerPanel();
        

        //Add panels to the frame  
        frame.add(TitlePanel(), BorderLayout.NORTH);
        frame.add(instructionPanel, BorderLayout.WEST);
        frame.add(registerPanel, BorderLayout.CENTER);
        frame.add(memoryPanel, BorderLayout.EAST);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.pack();
        //showGui();

	}
	public void showGui() {
		frame.setVisible(true);
	    }

	public JPanel TitlePanel(){           
              
		JPanel reg = new JPanel();
		JLabel labelreg = new JLabel("REGISTERS");
        labelreg.setFont(labelreg.getFont().deriveFont(Font.BOLD));
        reg.add(labelreg);
        
		JPanel inst = new JPanel();
		JLabel labelinst = new JLabel("INSTRUCTIONS");
        labelinst.setFont(labelinst.getFont().deriveFont(Font.BOLD));
        inst.add(labelinst);
        
		JPanel mem = new JPanel();
		JLabel labelmem = new JLabel("MEMORY");
        labelmem.setFont(labelmem.getFont().deriveFont(Font.BOLD));
        mem.add(labelmem);
		
		JPanel titlePanel = new JPanel(new BorderLayout());
		
		titlePanel.add(inst, BorderLayout.WEST);
		titlePanel.add(reg, BorderLayout.CENTER);
		titlePanel.add(mem, BorderLayout.EAST);
		
		return titlePanel;
		
		
	}
	public JPanel buildLowerPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 300));
		
		
		checkBox = new JCheckBox("Hexadecimal");
		runButton = new JButton("Run");
		stepButton = new JButton("Step");
		resetButton = new JButton("Reset");
		
		checkBox.addActionListener(new ButtonListener(processor));
		runButton.addActionListener(new ButtonListener(processor));
		stepButton.addActionListener(new ButtonListener(processor));
		resetButton.addActionListener(new ButtonListener(processor));
		
		panel.add(runButton);
		panel.add(stepButton);
		panel.add(resetButton);
		panel.add(checkBox);

		return panel;
	}
	

	public JPanel buildMiddlePanel(int i){
		JPanel panel = new JPanel(new BorderLayout());

		/* Wraps the text so that the horizontal scroll is not needed. */
		if(i == 1){
			instructionArea = new JTextArea(50, 50);
			instructionArea.setLineWrap(true);
			instructionArea.setEditable(false);

			scrollPane = new JScrollPane(instructionArea);
		}else if(i == 2){
			memoryArea = new JTextArea(50, 50);
			memoryArea.setLineWrap(true);
			memoryArea.setEditable(false);
			
			pcArea = new JTextArea(2, 1);
			pcArea.setLineWrap(true);
			pcArea.setEditable(false);
	
			panel.add(new JScrollPane(memoryArea), BorderLayout.NORTH);
			panel.add(new JScrollPane(pcArea), BorderLayout.CENTER);
			return panel;
			
		}else if(i == 3){
			registerArea = new JTextArea(50, 50);
			registerArea.setLineWrap(true);
			registerArea.setEditable(false);

			scrollPane = new JScrollPane(registerArea);
		}

		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.
		                                    VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.
		                                      HORIZONTAL_SCROLLBAR_NEVER);

		panel.add(scrollPane);

		return panel;
	}  
}