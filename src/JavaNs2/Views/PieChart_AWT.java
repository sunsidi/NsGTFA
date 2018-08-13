package JavaNs2.Views;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.util.HashMap;

public class PieChart_AWT {
    private int height = 400;
    private int width = 200;
    private ChartPanel chartPanel;

    public PieChart_AWT(String chartTitle, HashMap<String, Float> data)
    {
        JFreeChart pieChart = ChartFactory.createPieChart(
                chartTitle, // chart title
                createDataset(data), // data
                true, // include legend
                true,
                false);
        chartPanel = new ChartPanel(pieChart,
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

    private static PieDataset createDataset(HashMap<String, Float> data)
    {
        DefaultPieDataset dataset = new DefaultPieDataset();
        data.forEach(dataset::setValue);
        return dataset;
    }

    public ChartPanel setupBarChart()
    {
        return chartPanel;
    }
}