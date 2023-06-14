import java.util.*;

class KdTree {
    private NodeKD root;

    public NodeKD getRoot() {
        return root;
    }

    public void setRoot(NodeKD root) {
        this.root = root;
    }

    public void insert(Map<String, double[]> records, NodeKD rootNode) {
        Map<String, double[]> leftSubset = new HashMap<>();
        Map<String, double[]> rightSubset = new HashMap<>();
        int dimension = rootNode.getDimensionIndex();

        if (records.size() != 0) {
            for (Map.Entry<String, double[]> record : records.entrySet()) {
                if (record.getKey().equals(rootNode.getId())) {
                    continue;
                }
                if (record.getValue()[dimension] < rootNode.getCoordinates()[dimension]) {
                    String id = record.getKey();
                    double[] attributes = record.getValue();
                    leftSubset.put(id, attributes);
                } else {
                    String id = record.getKey();
                    double[] attributes = record.getValue();
                    rightSubset.put(id, attributes);
                }
            }
            double leftMedian = Ass3.calculateMedian(leftSubset, dimension);
            double rightMedian = Ass3.calculateMedian(rightSubset, dimension);
            insertRecursive(leftSubset, rightSubset, dimension, leftMedian, rightMedian, rootNode);

        }
    }


    private void insertRecursive(Map<String, double[]> recordsLeft, Map<String, double[]> recordsRight, int dimension, double medianLeft, double medianRight, NodeKD rootNode) {
        if (!recordsLeft.isEmpty()) {
            for (Map.Entry<String, double[]> record : recordsLeft.entrySet()) {
                if (record.getValue()[dimension] == medianLeft) {
                    NodeKD leftNode = new NodeKD(record.getKey(), record.getValue(), dimension + 1);
                    rootNode.setLeft(leftNode);
                    insert(recordsLeft, leftNode);
                    break;
                }
            }
        }
        if (!recordsRight.isEmpty()) {
            for (Map.Entry<String, double[]> record : recordsRight.entrySet()) {
                if (record.getValue()[dimension] == medianRight) {
                    NodeKD rightNode = new NodeKD(record.getKey(), record.getValue(), dimension + 1);
                    rootNode.setRight(rightNode);
                    insert(recordsRight, rightNode);
                    break;
                }
            }
        }
    }
    public List<String> findNearestNeighbors(double[] target, int k) {
        PriorityQueue<NodeKD> pQueue = new PriorityQueue<>(Comparator.comparingDouble(node -> distance(node.getCoordinates(), target)));
        findNearestNeighbors(root, target, k, pQueue);

        List<String> neighbors = new ArrayList<>();
        while (!pQueue.isEmpty()) {
            neighbors.add(pQueue.poll().getId());
        }
        return neighbors;
    }
    public void findNearestNeighbors(NodeKD node, double[] target, int k, PriorityQueue<NodeKD> pQueue) {
        if (node == null) {
            return;
        }

        double distance = distance(node.getCoordinates(), target);

        if (pQueue.size() < k) {
            pQueue.offer(node);
        } else if (distance < Math.sqrt(distance(pQueue.peek().getCoordinates(), target))) {
            pQueue.poll();
            pQueue.offer(node);
        }

        int dimensionAxis = node.getDimensionIndex();
        double axisDistance = target[dimensionAxis] - node.getCoordinates()[dimensionAxis];


        if (axisDistance < 0) {
            findNearestNeighbors(node.getLeft(), target, k, pQueue);
        } else {
            findNearestNeighbors(node.getRight(), target, k, pQueue);
        }
        if (Math.abs(axisDistance) < Math.sqrt(distance(pQueue.peek().getCoordinates(), target))) {
            if (axisDistance < 0) {
                NodeKD nextNode = node.getRight();
                findNearestNeighbors(nextNode, target, k, pQueue);
            } else {
                NodeKD nextNode = node.getLeft();
                findNearestNeighbors(nextNode, target, k, pQueue);
            }
        }
    }

    private double distance(double[] coords1, double[] coords2) {
        double sum = 0.0;
        for (int i = 0; i < coords1.length; i++) {
            sum += Math.pow(coords1[i] - coords2[i], 2);
        }
        return Math.sqrt(sum);
    }
}
