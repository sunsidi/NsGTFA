package JavaNs2.Views;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.HashMap;

public class BarChart_AWT {
    private int height = 400;
    private int width = 200;
    private ChartPanel chartPanel;

    public BarChart_AWT(String chartTitle, HashMap<String, Float> dataset)
    {
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "", // x-axis title
                "", // y-axis title
                createDataset(dataset),
                PlotOrientation.VERTICAL, // vertical y-axis
                true, true, false);

        chartPanel = new ChartPanel(barChart,
                width,
                height,
                width,
                height,
                width,
                height,
                false,
                true,
                true,
                true,
                true,
                true);

//        this.add(chartPanel);
    }

    private CategoryDataset createDataset(HashMap<String, Float> data)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        data.forEach((String key, Float value) -> dataset.addValue(value, key, ""));
        return dataset;
    }

    public ChartPanel setupBarChart()
    {
        return chartPanel;
    }

//    public static void main(String[] args)
//    {
//        JFrame f = new JFrame("Demo");
//        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        HashMap<String, Float> data1 = new HashMap<>();
//        data1.put("File1", (double) 80);
//        data1.put("File2", (double) 50);
//        data1.put("File3", (double) 100);
//
//        BarChart_AWT chart1 = new BarChart_AWT("PDF", data1);
//
//        f.add(chart1.setupBarChart());
//        f.pack();
//        f.setVisible(true);
//    }
}