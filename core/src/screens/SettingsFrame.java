package screens;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.BreakoutBlitz;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;

public class SettingsFrame extends JDialog implements ActionListener {
    public int choice;
    private JComboBox<String> comboBox;
    private JButton btn;
    private JLabel lbl;
    private String[] sizes = {"360 x 240", "720 x 480", "1,080 x 720", "1,440 x 960", "1,800 x 1,200"};

    //constructor for Frame
    SettingsFrame() {
        // Settings for Frame
        setModalityType(ModalityType.DOCUMENT_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        pack();
        setSize(300, 125);
        setLocationRelativeTo(null);
        // adding text to the window
        lbl = new JLabel("Select one of the possible choices and click OK");
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lbl);
        // adding a dropdown
        comboBox = new JComboBox<>(sizes);
        add(comboBox);
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        // adding a button
        btn = new JButton("OK");
        add(btn);
        btn.addActionListener(this); // ActionListener to tell when button is pressed
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // saving selected option and disposes of current Frame
        if (e.getSource() == btn) {
            choice = comboBox.getSelectedIndex();
            choice = (choice + 1) * 360;

            BreakoutBlitz.width = choice;
            BreakoutBlitz.height = (int) ((double) BreakoutBlitz.width * 2/3);
            dispose();
        }
    }
}

