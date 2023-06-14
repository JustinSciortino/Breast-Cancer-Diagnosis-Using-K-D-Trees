public class NodeKD {
    private String id;
    private double[] coordinates;
    private int dimensionIndex;
    private NodeKD left;
    private NodeKD right;

    public NodeKD(String id, double[] coordinates, int dimensionIndex) {
        this.id = id;
        this.coordinates = coordinates;
        this.dimensionIndex = dimensionIndex;
    }

    public String getId() {
        return id;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public int getDimensionIndex() {
        if(dimensionIndex == 10){
            dimensionIndex = 0;
            return dimensionIndex;
        }else {
            return dimensionIndex;
        }
    }
    public NodeKD getLeft() {
        return left;
    }

    public void setLeft(NodeKD left) {
        this.left = left;
    }

    public NodeKD getRight() {
        return right;
    }

    public void setRight(NodeKD right) {
        this.right = right;
    }
}
