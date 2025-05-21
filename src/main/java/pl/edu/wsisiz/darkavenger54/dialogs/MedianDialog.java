/*
 * Created by JFormDesigner on Thu May 15 18:41:03 CEST 2025
 */

package pl.edu.wsisiz.darkavenger54.dialogs;

import pl.edu.wsisiz.darkavenger54.enums.EnPaddingType;
import pl.edu.wsisiz.darkavenger54.forms.ImageForm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author Zhenia
 */
public class MedianDialog extends JDialog {
    private int ksize;
    private EnPaddingType paddingType;
    private boolean confirmed = false;
    public MedianDialog(ImageForm imageForm) {
        super(imageForm, "Median", true);
        initComponents();
        setResizable(false);
        sizeComboBox.addItem(3);
        sizeComboBox.addItem(5);
        sizeComboBox.addItem(7);
        for (EnPaddingType enPaddingType : EnPaddingType.values())
        {
            paddingComboBox.addItem(enPaddingType);
        }
        setVisible(true);
    }

    private void ok(ActionEvent e) {
        this.ksize = Integer.parseInt(sizeComboBox.getSelectedItem().toString());
        this.paddingType = (EnPaddingType) paddingComboBox.getSelectedItem();
        confirmed = true;
        this.setVisible(false);
    }

    private void cancel(ActionEvent e) {
        this.dispose();
    }

    public int getKsize()
    {
        return ksize;
    }

    public EnPaddingType getPaddingType()
    {
        return paddingType;
    }

    public boolean isConfirmed()
    {
        return confirmed;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        paddingComboBox = new JComboBox();
        sizeComboBox = new JComboBox();
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
                label2.setText("Padding");

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addComponent(label1)
                                .addComponent(label2))
                            .addGap(47, 47, 47)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(paddingComboBox, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addComponent(sizeComboBox, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                            .addContainerGap(108, Short.MAX_VALUE))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1)
                                .addComponent(sizeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(paddingComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(21, Short.MAX_VALUE))
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
    private JComboBox paddingComboBox;
    private JComboBox sizeComboBox;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
