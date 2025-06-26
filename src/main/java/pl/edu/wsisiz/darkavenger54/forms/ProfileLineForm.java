/*
 * Created by JFormDesigner on Wed Jun 11 20:42:32 CEST 2025
 */

package pl.edu.wsisiz.darkavenger54.forms;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.opencv.core.Mat;
import pl.edu.wsisiz.darkavenger54.MatAlgorithms;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa odpowiedzialna za wyświetlanie profilu jasności pikseli pomiędzy
 * dwoma wybranymi punktami na obrazie w skali szarości.
 *
 * Tworzy tabelę danych (współrzędne X, Y i jasność) oraz
 * wizualizację wykresu profilu.
 *
 * @author Yevhenii Manuilov
 */
public class ProfileLineForm extends JFrame {
    private List<int[]> data;
    private DefaultTableModel model;
    private XYSeries series;
    private XYSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private Mat mat;
    private org.opencv.core.Point p1;
    private org.opencv.core.Point p2;

    /**
     * Konstruktor inicjalizujący i ustawiający wygląd oraz dane profilu jasności.
     *
     * @param mat obraz typu Mat
     * @param p1  punkt początkowy profilu
     * @param p2  punkt końcowy profilu
     */
    public ProfileLineForm(Mat mat, org.opencv.core.Point p1, org.opencv.core.Point p2)
    {
        initComponents();
        data = new ArrayList<int[]>();
        this.mat = mat;
        this.p1 = p1;
        this.p2 = p2;
        // Inicjalizujemy tabelę
        model = new DefaultTableModel();
        model.addColumn("X");
        model.addColumn("Y");
        model.addColumn("Luminosity");
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        for (int i = 0; i < table.getColumnCount(); i++)
        {
            table.getColumnModel().getColumn(i).setResizable(false);
        }
        // Inicjalizujemy wykres
        series = new XYSeries("");
        dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        chart = ChartFactory.createXYLineChart("", "px", "Brightness", dataset, PlotOrientation.VERTICAL, false, true, false);
        chartPanel = new ChartPanel(chart);
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        initProfileLine();
        setTitle("Profile Line");
        this.setVisible(true);
    }
    /**
     * Inicjalizuje dane profilu jasności poprzez
     * wyznaczenie jasności pikseli pomiędzy punktami p1 i p2.
     */
    private void initProfileLine()
    {
        data = MatAlgorithms.getProfileLine(mat, p1, p2);
        int i = 0;
        for (int[] entry : data)
        {
            series.add(i, entry[2]);
            model.addRow(new Object[]{entry[0], entry[1], entry[2]});
            ++i;
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        table = new JTable();
        panel = new JPanel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();

        //======== panel1 ========
        {

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(table);
            }

            //======== panel ========
            {

                GroupLayout panelLayout = new GroupLayout(panel);
                panel.setLayout(panelLayout);
                panelLayout.setHorizontalGroup(
                    panelLayout.createParallelGroup()
                        .addGap(0, 578, Short.MAX_VALUE)
                );
                panelLayout.setVerticalGroup(
                    panelLayout.createParallelGroup()
                        .addGap(0, 229, Short.MAX_VALUE)
                );
            }

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
                        .addContainerGap())
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
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
    private JScrollPane scrollPane1;
    private JTable table;
    private JPanel panel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
