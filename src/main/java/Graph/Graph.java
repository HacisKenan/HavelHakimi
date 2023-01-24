package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {
    private List<Node> nodes;
    private List<Edge> digraphEdges;
    private List<Edge> simpleEdges;
    private List<Integer> degrees;


    public Graph(List<Node> nodes, List<Edge> digraphEdges, List<Edge> simpleEdges)
    {
        this.nodes=nodes;
        this.digraphEdges=digraphEdges;
        this.simpleEdges=simpleEdges;
    }

    public Node addNewNode()
    {
        Node node = new Node();
        nodes.add(node);
        return node;
    }

    public void addNewNode(Node node)
    {
        if(!nodes.contains(node)) nodes.add(node);
    }

    public void addDigraphEdge(Edge edg)
    {
        if(digraphEdges.contains(edg)) return;
        digraphEdges.add(edg);
    }

    public void addSimpleEdge(Edge edg)
    {
        if(simpleEdges.contains(edg)) return;
        simpleEdges.add(edg);
        Edge oppDir = new Edge(edg.getTo(),edg.getFrom());
        simpleEdges.add(oppDir);
    }

    public List<Integer> createDegreeList()
    {
        degrees= new ArrayList<Integer>();
        for(Node node : nodes)
        {
            int degree = node.getDegree();
            degrees.add(degree);
        }
        return degrees;
    }

    public boolean isConnected()
    {
        List<Node> nodes = new ArrayList<Node>();
        isConnectedRec(nodes,this.nodes.get(0));
        return nodes.size()==this.nodes.size();
    }

    public void isConnectedRec(List<Node> nodeList,Node current)
    {
        if(!nodeList.contains(current))
        {
            nodeList.add(current);
        }
        else return;
        for(Node node: current.getNeighbors())
        {
            isConnectedRec(nodeList,node);
        }
    }

    public List<Integer> isRealizable() {
        if (checkCorrectness()) {
            degrees = topDownReduction(degrees);
            return degrees;
        }
        return new ArrayList<Integer>();
    }

    public boolean checkCorrectness() {
        if (degrees == null) return false;
        if (degrees.size() == 0) return false;
        for (int deg : degrees) {
            if (deg < 0) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> topDownReduction(List<Integer> degrees) {
        while (canStillCalculateReduction(degrees)) {
            Collections.sort(degrees, Collections.reverseOrder());
            int toReduce = degrees.get(0);
            for (int i = 1; i <= toReduce; i++) {
                int deg = degrees.get(i);
                deg -= 1;
                degrees.remove(i);
                degrees.add(i, deg);
            }
            degrees.remove(0);
        }
        return degrees;
    }

    public boolean canStillCalculateReduction(List<Integer> degrees) {
        boolean possible = false;
        for (int deg : degrees) {
            if (deg < 0) {
                return false;
            }
            if (deg >= 1) {
                possible = true;
            }
        }
        return possible;
    }

    public List<Node> getNodes(){return nodes;}
    public List<Integer> getDegrees(){return degrees;}
    public List<Edge> getDigraphEdges(){return digraphEdges;}
    public List<Edge> getSimpleEdges(){return simpleEdges;}


}
