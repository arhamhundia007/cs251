import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class KruGraph {
    private Vertex[] vertexArr;
    private ArrayList<MyEdge> edgeArr;
    private int vertexCount;
    private int edgeCount;

    //Implement the constructor for KruGraph
    //The format of the input file is the same as the format of the input file in Dijkstra
    public KruGraph(String graph_file)throws IOException{
        File file = new File(graph_file);
        Scanner scan = new Scanner(file);
        vertexCount = scan.nextInt() + 1;
        vertexArr = new Vertex[vertexCount];
        edgeCount = scan.nextInt();
        edgeArr = new ArrayList<>();
        for (int i = 1; i < vertexCount; i++) {
            vertexArr[i] = new Vertex(i);
        }
        for (int i = 0; i < edgeCount; i++) {
            int from = scan.nextInt();
            int to = scan.nextInt();
            int weight = scan.nextInt();
            addEgde(from, to, weight);
        }


    }

    //Could be a helper function
    private void addEgde(int from, int to, int weight){
        MyEdge edgeToAdd = new MyEdge(from, to, weight);
        edgeArr.add(edgeToAdd);
    }


    //Implement Kruskal with weighted union find algorithm
    public PriorityQueue<MyEdge> kruskalMST(){
        PriorityQueue<MyEdge> queue = new PriorityQueue<>();
        PriorityQueue<MyEdge> mst = new PriorityQueue<>();
        for (int i = 0; i < edgeCount; i++) {
            queue.add(edgeArr.get(i));
        }

        MyEdge edge = null;
        while (mst.size() < vertexCount - 2){
            edge = queue.poll();
            int src = edge.getS();
            int dest = edge.getD();
            if (KruGraph.union(vertexArr[src], vertexArr[dest])){
                mst.add(edge);
            }
        }

        return mst;
    }

    //Implement the recursion trick for the leaves to update the parent efficiently
    //Set it as static as always
    public static Vertex find(Vertex x){
//        if (x.getParent() == null){
//            return x;
//        }
//
//        return find(x.getParent());


        Vertex v = x.getParent();

        if (v != x){
            x.updateParent(find(v));
            return x.getParent();
        }

        return v;
    }


    //This function should union two vertices when an edge is added to the MST
    //Return true when the edge can be picked in the MST
    //Otherwise return false
    //Set it as static as always
    public static boolean union(Vertex x, Vertex y){
        Vertex src = find(x);
        Vertex dst = find(y);
        if (src == dst){
            return false;
        }
        int size = src.getSize() + dst.getSize();
        if (src.getSize() <= dst.getSize()){
            src.updateParent(dst);
            dst.updateSize(size);
        } else {
            dst.updateParent(src);
            src.updateSize(size);
        }
        return true;
//
//        if (!(x.getParent().equals(y.getParent()))){
//            int size = x.getSize() + y.getSize();
//            if (x.getSize() < y.getSize()){
//                x.updateParent(y);
//                y.updateSize(size);
//            } else if(y.getSize() < x.getSize()){
//                y.updateParent(x);
//                x.updateSize(size);
//            }
//            return true;
//        }
//        return false;
    }

    //This is what we expect for the output format
    //The test cases will follow this format
    public static void printGraph(PriorityQueue<MyEdge> edgeList){
        int turn = edgeList.size();
        for (int i = 0; i < turn; i++) {
            MyEdge edge = edgeList.poll();
            int source = edge.getS();
            int dest = edge.getD();
            if(source > dest){
                int temp = source;
                source = dest;
                dest = temp;
            }
            System.out.println("from: " + source + " to: " + dest + " weight: " + edge.getWeight());
        }
    }

    public static void main(String[] args) throws IOException {
        KruGraph graph = new KruGraph(args[0]);
        printGraph(graph.kruskalMST());
    }

}
