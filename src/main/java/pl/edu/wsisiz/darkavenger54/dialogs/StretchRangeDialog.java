/*
 * Created by JFormDesigner on Tue May 13 23:25:31 CEST 2025
 */

package pl.edu.wsisiz.darkavenger54.dialogs;

import pl.edu.wsisiz.darkavenger54.TextParser;
import pl.edu.wsisiz.darkavenger54.forms.ImageForm;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Zhenia
 */
public class StretchRangeDialog extends JDialog {
    private int p1;
    private int p2;
    private int q3;
    private int q4;
    private boolean confirmed = false;
    public StretchRangeDialog(ImageForm imageForm) {
        super(imageForm, "StretchRange" , true);
        initComponents();
        setResizable(false);
        setVisible(true);
    }

    public int getP1()
    {
        return p1;
    }

    public int getP2()
    {
        return p2;
    }

    public int getQ3()
    {
        return q3;
    }

    public int getQ4()
    {
        return q4;
    }
    public boolean isConfirmed() {
        return confirmed;
    }

    private void calculate(ActionEvent e) {
        if(TextParser.tryParseToInt(p1TextField.getText()) &&
                TextParser.tryParseToInt(p2TextField.getText()) &&
                TextParser.tryParseToInt(q3TextField.getText()) &&
                TextParser.tryParseToInt(q4TextField.getText()))
        {
            int pf1 = Integer.parseInt(p1TextField.getText());
            int pf2 = Integer.parseInt(p2TextField.getText());
            int qf3 = Integer.parseInt(q3TextField.getText());
            int qf4 = Integer.parseInt(q4TextField.getText());
            if (pf1 <= 255 && pf1 >= 0
            && pf2 <= 255 && pf2 >= 0
            && qf3 <= 255 && qf3 >= 0
            && qf4 <= 255 && qf4 >= 0)
            {
                this.p1 = pf1;
                this.p2 = pf2;
                this.q3 = qf3;
                this.q4 = qf4;
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
        p1Label = new JLabel();
        p1TextField = new JTextField();
        p2Label = new JLabel();
        p2TextField = new JTextField();
        q3Label = new JLabel();
        q4Label = new JLabel();
        q3TextField = new JTextField();
        q4TextField = new JTextField();
        calculateButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();

        //---- p1Label ----
        p1Label.setText("p1");

        //---- p2Label ----
        p2Label.setText("p2");

        //---- q3Label ----
        q3Label.setText("q3");

        //---- q4Label ----
        q4Label.setText("q4");

        //---- calculateButton ----
        calculateButton.setText("Calculate");
        calculateButton.addActionListener(e -> calculate(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(p1Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(p2Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(p2TextField, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                                .addComponent(p1TextField, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(q3Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(q4Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(q4TextField, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                .addComponent(q3TextField, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(44, 44, 44)
                            .addComponent(calculateButton)))
                    .addContainerGap(25, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(p1Label)
                        .addComponent(p1TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(q3Label)
                        .addComponent(q3TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(p2Label)
                        .addComponent(p2TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(q4Label)
                        .addComponent(q4TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(calculateButton)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel p1Label;
    private JTextField p1TextField;
    private JLabel p2Label;
    private JTextField p2TextField;
    private JLabel q3Label;
    private JLabel q4Label;
    private JTextField q3TextField;
    private JTextField q4TextField;
    private JButton calculateButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
