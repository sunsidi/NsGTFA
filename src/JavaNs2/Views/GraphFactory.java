package JavaNs2.Views;

import JavaNs2.Models.ModelAnalysisResult;
import org.jfree.chart.ChartPanel;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphFactory {
    private ArrayList<ModelAnalysisResult> results;
    private String[] metrics;
    private int graphType;
    private ArrayList<HashMap<String, Float>> data = new ArrayList<>();
    private ArrayList<ChartPanel> graphLists = new ArrayList<>();

    public GraphFactory(ArrayList<ModelAnalysisResult> results, String[] metrics, int graphType)
    {
        this.results = results;
        this.metrics = metrics;
        this.graphType = graphType;

        initializeData();
        processResults();
    }

    private void initializeData()
    {
        for (int i = 0; i < 6; i++) {
            data.add(new HashMap<>());
        }
    }

    /**
     * Add result to a Hashmap
     * result with the same file name only appear once
     */
    private void processResults()
    {
        for (ModelAnalysisResult result : results) {
            for (int i = 0; i < metrics.length; i++) {
                if (i > 3) { // get the integer metrics
                    data.get(i).put(result.getStringMetricValue("File Name"), (float) result.getIntMetricValue(metrics[i]));
                } else { // get the float metrics
                    data.get(i).put(result.getStringMetricValue("File Name"), result.getFloatMetricValue(metrics[i]));
                }
            }
        }
    }

    /**
     * Get the abbreviation of the metrics
     * Example: "End to End Delay" should return "EtoED"
     * @param metric name
     * @return abbreviation of the metric name
     */
    private String getAbbreviation(String fullName)
    {
        String[] subStrs = fullName.split(" ");
        StringBuilder abbr = new StringBuilder();
        if (subStrs.length > 1) {
            for (int i = 0; i < subStrs.length; i++) {
                if (subStrs[i].equals(subStrs[i].toLowerCase())) { // subString has no uppercase letter, append the original subString
                    abbr.append(subStrs[i]);
                } else { // otherwise append the first letter
                    abbr.append(subStrs[i].charAt(0));
                }
            }
            return abbr.toString();
        } else {
            return fullName;
        }
    }

    /**
     * Generate a list of graph objects: barChart or pieChart
     * @return a list of selected type of graphs
     */
    public ArrayList<ChartPanel> generateGraphs()
    {
        int i = 0;

        switch (graphType) {
            case 0: // bar chart
                for (HashMap<String, Float> tmp : data) {
                    graphLists.add(new BarChart_AWT(getAbbreviation(metrics[i]), tmp).setupBarChart());
                    i++;
                }
                break;
            case 1: // pie chart
                for (HashMap<String, Float> tmp : data) {
                    graphLists.add(new PieChart_AWT(getAbbreviation(metrics[i]), tmp).setupBarChart());
                    i++;
                }
                break;
//            case 2:
//                for (HashMap<String, Float> tmp : data) {
//                    graphLists.add(new LineChart_AWT(metrics[i], tmp).setupBarChart());
//                    i++;
//                }
//                break;
        }

        return graphLists;
    }
}
