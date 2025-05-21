/*
 * Created by JFormDesigner on Fri May 16 02:24:42 CEST 2025
 */

package pl.edu.wsisiz.darkavenger54.dialogs;

import pl.edu.wsisiz.darkavenger54.enums.EnDepthType;
import pl.edu.wsisiz.darkavenger54.enums.EnDirectionType;
import pl.edu.wsisiz.darkavenger54.enums.EnPaddingType;
import pl.edu.wsisiz.darkavenger54.enums.EnSharpenType;
import pl.edu.wsisiz.darkavenger54.forms.ImageForm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author Zhenia
 */
public class LaplacianSharpenDialog extends JDialog {
    private EnSharpenType sharpenType;
    private EnDepthType depthType;
    private EnPaddingType paddingType;
    private boolean confirmed = false;
    public LaplacianSharpenDialog(ImageForm imageForm) {
        super(imageForm, "Laplacian Sharpen", true);
        initComponents();
        setResizable(false);
        for(EnSharpenType sharpenType : EnSharpenType.values())
        {
            filterComboBox.addItem(sharpenType);
        }
        for(EnDepthType depthType : EnDepthType.values())
        {
            desiredDepthComboBox.addItem(depthType);
        }
        for (EnPaddingType paddingType : EnPaddingType.values())
        {
            paddingComboBox.addItem(paddingType);
        }
        setVisible(true);
    }

    private void ok(ActionEvent e) {
        this.sharpenType = (EnSharpenType) filterComboBox.getSelectedItem();
        this.paddingType = (EnPaddingType) paddingComboBox.getSelectedItem();
        this.depthType = (EnDepthType) desiredDepthComboBox.getSelectedItem();
        this.confirmed = true;
        this.setVisible(false);
    }

    private void cancel(ActionEvent e) {
        this.dispose();
    }

    public EnSharpenType getSharpenType()
    {
        return sharpenType;
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        filterComboBox = new JComboBox();
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

                //---- label1 ----
                label1.setText("Filter");

                //---- label2 ----
                label2.setText("Desired Depth");

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
                            .addGap(26, 26, 26)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(desiredDepthComboBox, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                .addComponent(filterComboBox, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                .addComponent(paddingComboBox, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                            .addContainerGap(90, Short.MAX_VALUE))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1)
                                .addComponent(filterComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(desiredDepthComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label3)
                                .addComponent(paddingComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(3, Short.MAX_VALUE))
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
    private JComboBox filterComboBox;
    private JComboBox desiredDepthComboBox;
    private JComboBox paddingComboBox;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
