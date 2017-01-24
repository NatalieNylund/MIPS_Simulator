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
