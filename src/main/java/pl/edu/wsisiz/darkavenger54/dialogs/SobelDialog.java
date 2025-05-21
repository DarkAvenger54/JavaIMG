/*
 * Created by JFormDesigner on Thu May 15 00:27:05 CEST 2025
 */

package pl.edu.wsisiz.darkavenger54.dialogs;

import pl.edu.wsisiz.darkavenger54.TextValidator;
import pl.edu.wsisiz.darkavenger54.enums.EnDepthType;
import pl.edu.wsisiz.darkavenger54.enums.EnPaddingType;
import pl.edu.wsisiz.darkavenger54.forms.ImageForm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Yevhenii Manuilov
 */
public class SobelDialog extends JDialog {
    private int ksize;
    private boolean confirmed = false;
    private EnPaddingType paddingType;
    private EnDepthType depthType;
    public SobelDialog(ImageForm imageForm) {
        super(imageForm, "Sobel", true);
        initComponents();
        for(EnDepthType depthType : EnDepthType.values())
        {
            desiredDepthcomboBox.addItem(depthType);
        }
        for (EnPaddingType paddingType : EnPaddingType.values())
        {
            paddingComboBox.addItem(paddingType);
        }
        setResizable(false);
        setVisible(true);
    }

    private void ok(ActionEvent e) {
        if (TextValidator.kernelSizeValidator(ksizeTextField.getText()))
        {
            this.ksize = Integer.parseInt(ksizeTextField.getText());
            this.paddingType = (EnPaddingType) paddingComboBox.getSelectedItem();
            this.depthType = (EnDepthType) desiredDepthcomboBox.getSelectedItem();
            this.confirmed = true;
            this.setVisible(false);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getKsize()
    {
        return ksize;
    }

    public boolean isConfirmed()
    {
        return confirmed;
    }

    public EnPaddingType getPaddingType()
    {
        return paddingType;
    }

    public EnDepthType getDepthType()
    {
        return depthType;
    }

    private void cancel(ActionEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        ksizeTextField = new JTextField();
        desiredDepthcomboBox = new JComboBox();
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

                //---- label1 ----
                label1.setText("Size");

                //---- label2 ----
                label2.setText("DesiredDepth");

                //---- label3 ----
                label3.setText("Padding");

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addComponent(label1)
                                .addComponent(label2)
                                .addComponent(label3))
                            .addGap(77, 77, 77)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(ksizeTextField, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                .addComponent(desiredDepthcomboBox, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                .addComponent(paddingComboBox, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                            .addContainerGap(126, Short.MAX_VALUE))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1)
                                .addComponent(ksizeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(desiredDepthcomboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label3)
                                .addComponent(paddingComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(8, Short.MAX_VALUE))
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
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField ksizeTextField;
    private JComboBox desiredDepthcomboBox;
    private JComboBox paddingComboBox;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
