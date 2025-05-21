/*
 * Created by JFormDesigner on Wed May 14 23:08:33 CEST 2025
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
public class BlurDialog extends JDialog {
    private boolean confirmed = false;
    private int blurValue;
    public BlurDialog(ImageForm imageForm) {
        super(imageForm, "Blur", true);
        initComponents();
        setResizable(false);
        setVisible(true);
    }

    private void calculate(ActionEvent e) {
        if (TextParser.tryParseToInt(blurTextField.getText()) )
        {
            int tempBlurValue = Integer.parseInt(blurTextField.getText());
            if (tempBlurValue >= 3 && tempBlurValue <= 99 && tempBlurValue % 2 != 0)
            {
                blurValue = tempBlurValue;
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
    public boolean isConfirmed() {
        return confirmed;
    }

    public int getBlurValue()
    {
        return blurValue;
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        blurTextField = new JTextField();
        calculateButton = new JButton();
        label1 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();

        //---- calculateButton ----
        calculateButton.setText("Calculate");
        calculateButton.addActionListener(e -> calculate(e));

        //---- label1 ----
        label1.setText("size");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(calculateButton, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                        .addComponent(blurTextField, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
                    .addGap(56, 56, 56))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(blurTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label1))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                    .addComponent(calculateButton)
                    .addGap(16, 16, 16))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTextField blurTextField;
    private JButton calculateButton;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
