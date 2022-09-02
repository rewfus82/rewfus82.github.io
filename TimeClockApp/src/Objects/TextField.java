package Objects;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;

public class TextField extends JTextField {
    public TextField(String str) {
        this.setColumns(10);
        this.setFont(new Font("Consolas", Font.PLAIN, 15));
        this.setBackground(Color.WHITE);
        this.setCaretColor(Color.BLACK);
        this.setText(str);
        // this.setPreferredSize(new Dimension(80,40));
    }   
}
