package cloud7c.speedrunrecorder.gui;

import cloud7c.speedrunrecorder.SpeedRunRecorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SpeedRunRecorderGUI extends JFrame{

    public SpeedRunRecorderGUI() {
        setTitle("SpeedRunRecorder");

        setLayout(null);

        setSize(300, 150);

        setResizable(false);

        JLabel testLabel = new JLabel("Recording...");
        testLabel.setBounds(55, 25, 200, 50);
        testLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(testLabel);

        ImageIcon imageIcon = new ImageIcon("templates/img/enterNether.png");

        this.setIconImage(imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                SpeedRunRecorder.getInstance().stop();
                System.exit(0);
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
