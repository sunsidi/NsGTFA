package JavaNs2.Models;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ModelAnalysisResult {

    private LinkedHashMap<String, String> stringMap;
    private HashMap<String, Integer> intMap;
    private HashMap<String, Float> floatMap;

    public ModelAnalysisResult()
    {
        this.stringMap = new LinkedHashMap<>();
        this.intMap = new HashMap<>();
        this.floatMap = new HashMap<>();
    }

    public String getStringMetricValue(String metric)
    {
        return stringMap.get(metric) == null ? "" : stringMap.get(metric);
    }

    public Integer getIntMetricValue(String metric)
    {
        return intMap.get(metric) == null ? 0 : intMap.get(metric);
    }

    public Float getFloatMetricValue(String metric)
    {
        return floatMap.get(metric) == null ? (float) 0 : floatMap.get(metric);
    }

    public void setMetricValue(String metric, int value)
    {
        intMap.put(metric, value);
    }

    public void setMetricValue(String metric, float value)
    {
        floatMap.put(metric, value);
    }

    public void setMetricValue(String metric, String value)
    {
        stringMap.put(metric, value);
    }

    public void resetMap()
    {
        intMap.clear();
        floatMap.clear();
    }

    public LinkedHashMap<String, String> getStringMap()
    {
        return stringMap;
    }

    public HashMap<String, Integer> getIntMap()
    {
        return intMap;
    }

    public HashMap<String, Float> getFloatMap()
    {
        return floatMap;
    }

    public void print()
    {
        stringMap.forEach((String metric, String value) -> System.out.println(metric + ": " + value));
        intMap.forEach((String metric, Integer value) -> System.out.println(metric + ": " + value));
        floatMap.forEach((String metric, Float value) -> System.out.println(metric + ": " + value));
    }
}
