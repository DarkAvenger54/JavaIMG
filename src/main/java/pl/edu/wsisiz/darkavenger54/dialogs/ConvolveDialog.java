/*
 * Created by JFormDesigner on Thu May 15 18:40:49 CEST 2025
 */

package pl.edu.wsisiz.darkavenger54.dialogs;

import java.awt.event.*;

import pl.edu.wsisiz.darkavenger54.TextValidator;
import pl.edu.wsisiz.darkavenger54.enums.EnDepthType;
import pl.edu.wsisiz.darkavenger54.enums.EnPaddingType;
import pl.edu.wsisiz.darkavenger54.forms.ImageForm;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author Zhenia
 */
public class ConvolveDialog extends JDialog
{
    private int c1, c2, c3, c4, c5, c6, c7, c8, c9;
    private EnDepthType depthType;
    private EnPaddingType paddingType;
    private boolean confirmed = false;

    public ConvolveDialog(ImageForm imageForm)
    {
        super(imageForm, "Convolve", true);
        initComponents();
        setResizable(false);
        for (EnDepthType depthType : EnDepthType.values())
        {
            desiredDepthComboBox.addItem(depthType);
        }
        for (EnPaddingType paddingType : EnPaddingType.values())
        {
            paddingComboBox.addItem(paddingType);
        }
        setVisible(true);
    }

    private void ok(ActionEvent e)
    {
        if(TextValidator.tryParseIntAndValidateClosed(textField1.getText(), -99, 100)
                && TextValidator.tryParseIntAndValidateClosed(textField2.getText(), -99, 100)
                && TextValidator.tryParseIntAndValidateClosed(textField3.getText(), -99, 100)
                && TextValidator.tryParseIntAndValidateClosed(textField4.getText(), -99, 100)
                && TextValidator.tryParseIntAndValidateClosed(textField5.getText(), -99, 100)
                && TextValidator.tryParseIntAndValidateClosed(textField6.getText(), -99, 100)
                && TextValidator.tryParseIntAndValidateClosed(textField7.getText(), -99, 100)
                && TextValidator.tryParseIntAndValidateClosed(textField8.getText(), -99, 100)
                && TextValidator.tryParseIntAndValidateClosed(textField9.getText(), -99, 100))
        {
            this.c1 = Integer.parseInt(textField1.getText());
            this.c2 = Integer.parseInt(textField2.getText());
            this.c3 = Integer.parseInt(textField3.getText());
            this.c4 = Integer.parseInt(textField4.getText());
            this.c5 = Integer.parseInt(textField5.getText());
            this.c6 = Integer.parseInt(textField6.getText());
            this.c7 = Integer.parseInt(textField7.getText());
            this.c8 = Integer.parseInt(textField8.getText());
            this.c9 = Integer.parseInt(textField9.getText());
            this.depthType = (EnDepthType) desiredDepthComboBox.getSelectedItem();
            this.paddingType = (EnPaddingType) paddingComboBox.getSelectedItem();
            this.confirmed = true;
            this.setVisible(false);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancel(ActionEvent e)
    {
        this.dispose();
    }

    public EnDepthType getDepthType()
    {
        return depthType;
    }

    public EnPaddingType getPaddingType()
    {
        return paddingType;
    }

    public boolean isConfirmed()
    {
        return confirmed;
    }

    public int getC1()
    {
        return c1;
    }

    public int getC2()
    {
        return c2;
    }

    public int getC3()
    {
        return c3;
    }

    public int getC4()
    {
        return c4;
    }

    public int getC5()
    {
        return c5;
    }

    public int getC6()
    {
        return c6;
    }

    public int getC7()
    {
        return c7;
    }

    public int getC8()
    {
        return c8;
    }

    public int getC9()
    {
        return c9;
    }

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        textField4 = new JTextField();
        textField5 = new JTextField();
        textField6 = new JTextField();
        textField7 = new JTextField();
        textField8 = new JTextField();
        textField9 = new JTextField();
        desiredDepthComboBox = new JComboBox();
        paddingComboBox = new JComboBox();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addGap(89, 89, 89)
                                    .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(textField7, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                        .addComponent(textField4, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                        .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(textField2, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                        .addComponent(textField5, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                        .addComponent(textField8, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(textField3, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                        .addComponent(textField6, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                        .addComponent(textField9, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addGap(137, 137, 137)
                                    .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(desiredDepthComboBox, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                        .addComponent(paddingComboBox, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))))
                            .addContainerGap(89, Short.MAX_VALUE))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(textField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(desiredDepthComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(paddingComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(17, Short.MAX_VALUE))
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(e -> ok(e));
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.addActionListener(e -> cancel(e));
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JComboBox desiredDepthComboBox;
    private JComboBox paddingComboBox;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
