
package pl.edu.wsisiz.darkavenger54.forms;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.opencv.core.Mat;
import pl.edu.wsisiz.darkavenger54.MatAlgorithms;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @author Yevhenii Manuilov
 */

public class HistogramForm extends JFrame {
    private int[] data;
    DefaultTableModel model;
    DefaultCategoryDataset dataset;
    JFreeChart chart;
    ChartPanel chartPanel;
    public HistogramForm(String imagePath) {
        initComponents();
        this.setTitle("JavaIMG "+imagePath+" Histogram");

        //table
        model = new DefaultTableModel();
        model.addColumn("Brightness");
        model.addColumn("Count");
        model.addColumn("Normalized");
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setResizable(false);
        }

        //histogram
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart("Histogram", "Brightness", "Count", dataset);
        chartPanel = new ChartPanel(chart);
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    public void updateHistogram(Mat mat)
    {
       data = MatAlgorithms.calculateGrayscaleHistogram(mat);
       model.setRowCount(0);
       dataset.clear();
       int max = 0;
       for (int value : data) {
            if (value > max) max = value;
       }
       for (int i = 0; i < data.length; ++i)
       {
           dataset.addValue(data[i],"Brightness", Integer.toString(i));
           double normalized = ((double) data[i]) / max;
           model.addRow(new Object[]{i,data[i], normalized});
       }
       this.revalidate();
       this.repaint();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        panel = new JPanel();
        scrollPane1 = new JScrollPane();
        table = new JTable();

        //======== this ========
        var contentPane = getContentPane();

        //======== panel1 ========
        {

            //======== panel ========
            {

                GroupLayout panelLayout = new GroupLayout(panel);
                panel.setLayout(panelLayout);
                panelLayout.setHorizontalGroup(
                    panelLayout.createParallelGroup()
                        .addGap(0, 536, Short.MAX_VALUE)
                );
                panelLayout.setVerticalGroup(
                    panelLayout.createParallelGroup()
                        .addGap(0, 166, Short.MAX_VALUE)
                );
            }

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(table);
            }

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE))
                        .addContainerGap())
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JPanel panel;
    private JScrollPane scrollPane1;
    private JTable table;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
