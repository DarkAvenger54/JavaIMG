/*
 * Created by JFormDesigner on Wed May 14 21:16:04 CEST 2025
 */

package pl.edu.wsisiz.darkavenger54.dialogs;

import java.awt.event.*;

import pl.edu.wsisiz.darkavenger54.TextParser;
import pl.edu.wsisiz.darkavenger54.forms.ImageForm;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Zhenia
 */
public class PosterizeDialog extends JDialog {
    private int posterizeValue;
    private boolean confirmed = false;
    public PosterizeDialog(ImageForm imageForm) {
        super(imageForm, "Posterize", true);
        initComponents();
        setResizable(false);
        setVisible(true);
    }



    public boolean isConfirmed() {
        return confirmed;
    }

    public int getPosterizeValue()
    {
        return posterizeValue;
    }

    private void calculate(ActionEvent e) {
        if (TextParser.tryParseToInt(posterizeTextField.getText()))
        {
            int tempPosterizeValue = Integer.parseInt(posterizeTextField.getText());
            if (tempPosterizeValue >= 3 && tempPosterizeValue <= 255)
            {
                posterizeValue = tempPosterizeValue;
                confirmed = true;
                setVisible(false);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        posterizeTextField = new JTextField();
        calculateButton = new JButton();
        label1 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();

        //---- calculateButton ----
        calculateButton.setText("Calculate");
        calculateButton.addActionListener(e -> calculate(e));

        //---- label1 ----
        label1.setText("levels");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(calculateButton)
                        .addComponent(posterizeTextField, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(51, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap(50, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(posterizeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(calculateButton)
                    .addGap(23, 23, 23))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTextField posterizeTextField;
    private JButton calculateButton;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
