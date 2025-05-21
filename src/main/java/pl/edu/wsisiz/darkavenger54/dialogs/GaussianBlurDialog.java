/*
 * Created by JFormDesigner on Wed May 14 23:36:25 CEST 2025
 */

package pl.edu.wsisiz.darkavenger54.dialogs;

import java.awt.event.*;

import pl.edu.wsisiz.darkavenger54.TextValidator;
import pl.edu.wsisiz.darkavenger54.enums.EnPaddingType;
import pl.edu.wsisiz.darkavenger54.forms.ImageForm;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author Zhenia
 */
public class GaussianBlurDialog extends JDialog {
    private boolean confirmed = false;
    private int ksize;
    private double sigmaX;
    private double sigmaY;
    private EnPaddingType paddingType;

    public GaussianBlurDialog(ImageForm imageForm) {
        super(imageForm, "Gaussian Blur", true);
        initComponents();
        for (EnPaddingType paddingType : EnPaddingType.values())
        {
            paddingComboBox.addItem(paddingType);
        }
        setResizable(false);
        setVisible(true);
    }

    private void ok(ActionEvent e) {
        if (TextValidator.kernelSizeValidator(sizeTextField.getText())
                && TextValidator.tryParseDoubleAndValidateClosed(sigmaXTextField.getText(), 0, 255)
        && TextValidator.tryParseDoubleAndValidateClosed(sigmaYTextField.getText(), 0, 255))
        {
            this.ksize = Integer.parseInt(sizeTextField.getText());
            this.sigmaX = Double.parseDouble(sigmaXTextField.getText());
            this.sigmaY = Double.parseDouble(sigmaYTextField.getText());
            this.paddingType = (EnPaddingType) paddingComboBox.getSelectedItem();
            this.setVisible(false);
            this.confirmed = true;
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isConfirmed()
    {
        return confirmed;
    }

    public int getKSize()
    {
        return ksize;
    }

    public double getSigmaX()
    {
        return sigmaX;
    }

    public double getSigmaY()
    {
        return sigmaY;
    }

    public EnPaddingType getPaddingType()
    {
        return paddingType;
    }

    private void padding(ActionEvent e) {
        this.paddingType = (EnPaddingType) paddingComboBox.getSelectedItem();
    }

    private void cancel(ActionEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        sizeTextField = new JTextField();
        sigmaXTextField = new JTextField();
        sigmaYTextField = new JTextField();
        paddingComboBox = new JComboBox();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
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

                //---- paddingComboBox ----
                paddingComboBox.addActionListener(e -> padding(e));

                //---- label1 ----
                label1.setText("Size");

                //---- label2 ----
                label2.setText("qx");

                //---- label3 ----
                label3.setText("qy");

                //---- label4 ----
                label4.setText("Padding");

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addComponent(label1)
                                .addComponent(label2)
                                .addComponent(label3)
                                .addComponent(label4))
                            .addGap(36, 36, 36)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(sigmaXTextField, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addComponent(sizeTextField, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addComponent(sigmaYTextField, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addComponent(paddingComboBox, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(85, Short.MAX_VALUE))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1)
                                .addComponent(sizeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(sigmaXTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label3)
                                .addComponent(sigmaYTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label4)
                                .addComponent(paddingComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(26, Short.MAX_VALUE))
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
    private JTextField sizeTextField;
    private JTextField sigmaXTextField;
    private JTextField sigmaYTextField;
    private JComboBox paddingComboBox;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
