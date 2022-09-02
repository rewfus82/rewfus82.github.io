package Frames;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
        MainFrame() {
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setResizable(false);
                this.setSize(600,900);
                // this.setLayout(new GridLayout(2,1,0,0));
                this.setLayout(new FlowLayout(FlowLayout.CENTER,0,50));
                this.getContentPane().setBackground(Color.darkGray);
                // this.setUndecorated(true);
                this.getRootPane().setBorder(BorderFactory.createMatteBorder(50,50,50,50, Color.DARK_GRAY));
                this.setVisible(true);


        }

        

}
