import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Ass3 {
    public static double calculateMedian(Map<String, double[]> records, int index) {
        if(records.size()!=0) {
            List<Double> keyList = new ArrayList<>();
            for (double[] attributes : records.values()) {
                keyList.add(attributes[index]);
            }
            Collections.sort(keyList);
            int size = keyList.size();
            int middle = size / 2;
            return keyList.get(middle);
        }else{
            return 0;
        }
    }
    public static double calculatePercentRight(Map<String, double[]> testRecords, Map<String, double[]> trainRecords, Map<String, String[]> idPTR, int k, double attribute1Median) {
        KdTree kdTree = new KdTree();
        if (kdTree.getRoot() == null) {
            for (Map.Entry<String, double[]> rootNode : trainRecords.entrySet()) {
                if (rootNode.getValue()[0] == attribute1Median) {
                    kdTree.setRoot(new NodeKD(rootNode.getKey(), rootNode.getValue(), 0));
                    break;
                }
            }
        }
        if (kdTree.getRoot() != null) {
            kdTree.insert(trainRecords, kdTree.getRoot());
        } else {
            System.out.println("Root node is null. Unable to build the KdTree.");
        }

        Map<String, List<String>> nearestNeighbors = new HashMap<>();
        for (Map.Entry<String, double[]> entry : testRecords.entrySet()) {
            String id = entry.getKey();
            double[] attributes = entry.getValue();
            List<String> neighbors = kdTree.findNearestNeighbors(attributes, k);
            nearestNeighbors.put(id, neighbors);
        }

        int correctPredictions = 0;
        for (Map.Entry<String, List<String>> entry : nearestNeighbors.entrySet()) {
            int countOne = 0;
            int countZero = 0;
            String prediction;
            String id = entry.getKey();
            List<String> neighbors = entry.getValue();
            for (String neighbor : neighbors) {
                String[] key = idPTR.get(neighbor);
                if (key[0].equals("M")) {
                    countOne++;
                }else{
                    countZero++;
                }
            }
            if (countOne>countZero) {
                prediction = "M";
            } else {
                prediction = "B";
            }
            String[] idValues = idPTR.get(id);
            String diagnosis = idValues[0];
            if (diagnosis.equals(prediction)) {
                correctPredictions++;
            }
        }

        return ((double) correctPredictions / testRecords.size()) *100;
    }

    public static void main(String[] args){
        Map<String, double[]> trainRecords = new HashMap<>();
        Map<String, double[]> testRecords = new HashMap<>();
        Map<String, String[]> idPTR = new HashMap<>();

        int N = 450;
        int T = N / 4;
        int testCount = 0;
        int trainCount = 0;

        String path = "/Users/justinsciortino/Desktop/data.csv";
        String line = "";
        try {
            BufferedReader BR = new BufferedReader(new FileReader(path));
            int lineCount = 0;
            while ((line = BR.readLine()) != null) {
                if (lineCount == 0) {
                    lineCount++;
                    continue;
                }
                String[] values = line.split(",");
                if (values.length > 0) {
                    String id = values[0];
                    String[] attributes = Arrays.copyOfRange(values, 1, values.length);
                    idPTR.put(id, attributes);
                    lineCount++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> idList = new ArrayList<>(idPTR.keySet());
        Collections.shuffle(idList);

        for (String id : idList) {
            String[] attributes = idPTR.get(id);
            double[] newAttributes = new double[attributes.length-1];
            for (int i = 1; i < attributes.length; i++) {
                newAttributes[i-1] = Double.parseDouble(attributes[i]);
            }
            trainRecords.put(id, newAttributes);
            trainCount++;
            if (trainCount >= N) {
                break;
            }
        }

        for (String id : idList) {
            if (!trainRecords.containsKey(id)) {
                String[] attributes = idPTR.get(id);
                double[] newAttributes = new double[attributes.length-1];
                for (int i = 1; i < attributes.length; i++) {
                    newAttributes[i-1] = Double.parseDouble(attributes[i]);
                }
                testRecords.put(id, newAttributes);
                testCount++;
                if (testCount >= T) {
                    break;
                }
            }
        }
        double attribute1MedianValue = calculateMedian(trainRecords,0);
        long timer3 = System.currentTimeMillis();
        double percentRight3 = calculatePercentRight(testRecords, trainRecords, idPTR, 3, attribute1MedianValue);
        long endTime3 = System.currentTimeMillis();
        long runTime3 = endTime3-timer3;

        long timer5 = System.currentTimeMillis();
        double percentRight5 = calculatePercentRight(testRecords, trainRecords, idPTR, 5, attribute1MedianValue);
        long endTime5 = System.currentTimeMillis();
        long runTime5 = endTime5 - timer5;

        long timer7 = System.currentTimeMillis();
        double percentRight7 = calculatePercentRight(testRecords, trainRecords, idPTR, 7, attribute1MedianValue);
        long endTime7 = System.currentTimeMillis();
        long runTime7 = endTime7 - timer7;

        System.out.println("Value of N: " + N);
        System.out.println('\n'+"Testing accuracy for k = 3:   "+ percentRight3);
        System.out.println("Running time for k = 3:   " + runTime3);
        System.out.println('\n'+"Testing accuracy for k = 5:   "+ percentRight5);
        System.out.println("Running time for k = 3:   " + runTime5);
        System.out.println('\n'+"Testing accuracy for k = 7:   "+ percentRight7);
        System.out.println("Running time for k = 3:   " + runTime7);
    }
}
