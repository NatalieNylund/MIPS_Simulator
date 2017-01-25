/*
 * Course: Computer Organization and Architecture, HT16
 * Author: Natalie Nylund (oi11nnd), Josefin Svensson (c14jsn)
 * Date: 2017-01-25
 * 
 * Description:
 * This class redirects the output stream to a text area
 * source given as input.
 */
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class RedirectText extends OutputStream {
    private JTextArea textarea;

    public RedirectText(JTextArea textarea) {
	this.textarea = textarea;
    }

    public void write(int b) throws IOException {
	textarea.append(String.valueOf((char) b));

    }

}
