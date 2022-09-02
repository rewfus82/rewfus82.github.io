package Objects;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

public class Button extends JButton {
        public Button(String text) {
                this.setText(text);
                this.setForeground(Color.LIGHT_GRAY);
                this.setBackground(Color.DARK_GRAY);
                this.setPreferredSize(new Dimension(100,50));
        }
}
