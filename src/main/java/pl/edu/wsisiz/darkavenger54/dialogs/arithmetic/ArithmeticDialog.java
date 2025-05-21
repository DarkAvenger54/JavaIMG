/*
 * Created by JFormDesigner on Sat May 17 18:35:08 CEST 2025
 */

package pl.edu.wsisiz.darkavenger54.dialogs.arithmetic;

import java.awt.event.*;

import org.opencv.core.Mat;
import pl.edu.wsisiz.darkavenger54.forms.ImageForm;
import pl.edu.wsisiz.darkavenger54.forms.MainForm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

/**
 * @author Zhenia
 */
public class ArithmeticDialog extends JDialog {
    private boolean confirmed = false;
    private MainForm mainForm;
    private ImageForm imageForm1;
    private ImageForm imageForm2;

    public ArithmeticDialog(ImageForm imageForm, MainForm mainForm, String title, String operation) {
        super(imageForm, title, true);
        this.mainForm = mainForm;
        initComponents();
        for(Map.Entry<Integer, ImageForm> entry: mainForm.getImageForms().entrySet())
        {
            imageComboBox1.addItem(entry.getKey());
            imageComboBox2.addItem(entry.getKey());
        }
        this.operationLabel.setText(operation);
        this.setVisible(true);
    }

    private void ok(ActionEvent e) {
        this.imageForm1 = mainForm.getImageFormByKey((Integer) imageComboBox1.getSelectedItem());
        this.imageForm2 = mainForm.getImageFormByKey((Integer) imageComboBox2.getSelectedItem());
        this.confirmed = true;
        this.setVisible(false);
    }

    private void cancel(ActionEvent e) {
        this.dispose();
    }

    public boolean isConfirmed()
    {
        return confirmed;
    }

    public Mat getImageForm1MatClone()
    {
        return imageForm1.getImageMat().clone();
    }
    public Mat getImageForm2MatClone()
    {
        return imageForm2.getImageMat().clone();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        panel1 = new JPanel();
        imageComboBox1 = new JComboBox();
        operationLabel = new JLabel();
        imageComboBox2 = new JComboBox();
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

                //======== panel1 ========
                {

                    //---- operationLabel ----
                    operationLabel.setText("minus");

                    GroupLayout panel1Layout = new GroupLayout(panel1);
                    panel1.setLayout(panel1Layout);
                    panel1Layout.setHorizontalGroup(
                        panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(imageComboBox1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(operationLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(imageComboBox2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(45, Short.MAX_VALUE))
                    );
                    panel1Layout.setVerticalGroup(
                        panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(imageComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(operationLabel)
                                    .addComponent(imageComboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(36, Short.MAX_VALUE))
                    );
                }

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                            .addContainerGap(19, Short.MAX_VALUE)
                            .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(14, 14, 14))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(14, Short.MAX_VALUE))
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
    private JPanel panel1;
    private JComboBox imageComboBox1;
    private JLabel operationLabel;
    private JComboBox imageComboBox2;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
