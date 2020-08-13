import java.io.File;
import java.io.IOException;
import java.util.*;

public class DijGraph {
    static int MAXWEIGHT = 10000000;//The weight of edge will not exceed this number
    private Node[] nodeArr;//The vertices set in the graph
    private int nodeCount;//number of total vertices
    private int edgeCount;//number of total edges

    //Two option for the DijGraph constructor
    //Option 0 is used to build graph with for part 1: implementation for Dijkstra
    //Option 1 is used to build graph with for part 2: simple application of Dijkstra
    public DijGraph(String graph_file, int option)throws IOException{
        if (option == 0){
            File file = new File(graph_file);
            Scanner sc = new Scanner(file);
            nodeCount = sc.nextInt();
            edgeCount = sc.nextInt();
            nodeArr = new Node[nodeCount + 1];
            for(int i =0; i < nodeCount + 1; i ++){
                if(i != 0) {
                    nodeArr[i] = new Node(i);
                }
            }
            for(int i = 0;i < edgeCount; i ++){
                int begin = sc.nextInt();
                int end = sc.nextInt();
                int weight = sc.nextInt();
                nodeArr[begin].addEdge(end, weight);
                nodeArr[end].addEdge(begin,weight);
            }
        }
        else if (option == 1){
            File file = new File(graph_file);
            Scanner sc = new Scanner(file);
            nodeCount = sc.nextInt();
            edgeCount = sc.nextInt();
            nodeArr = new Node[nodeCount + 1];
            for(int i =0; i < nodeCount + 1; i ++){
                if(i != 0){
                    nodeArr[i]= new Node(i, sc.next());
                }
            }
            for(int i = 0;i < edgeCount; i ++){
                String begin = sc.next();
                String end = sc.next();
                int weight = sc.nextInt();
                Node beginNode = findByName(begin);
                Node endNode = findByName(end);
                beginNode.addEdge(endNode.getNodeNumber(), weight);
                endNode.addEdge(beginNode.getNodeNumber(),weight);
            }
        }

    }

    //Finding the single source shortest distances by implementing dijkstra.
    //Using min heap to find the next smallest target
    public  Dist[] dijkstra(int source){
        Dist[] result = new Dist[nodeCount +1];
        Dist[] minHeap = new Dist[nodeCount + 1];
        result[source] = new Dist(source, 0);
        int size = 0;
        for (int i = 0; i < result.length ; i++) {
            if (i != source) {
                result[i] = new Dist(i, MAXWEIGHT);
            }
            insert(minHeap, result[i], i);
            size++;
        }
        HashSet<Integer> visitedNodes = new HashSet<>();
        while (size > 0){
            Dist curr = extractMin(minHeap, size);
            visitedNodes.add(curr.getNodeNumber());
            size--;
            int vertexNum = curr.getNodeNumber();
            if (vertexNum == 0){
                continue;
            }

            int currDist = curr.getDist();
            HashMap<Integer, Integer> adjVertices = nodeArr[vertexNum].getEdges();

            for (Map.Entry<Integer, Integer> ent : adjVertices.entrySet()){

                int vertexToAdd = ent.getKey(); //number of the vertex connected to curr node
                //checking if the node is already visited
                if (visitedNodes.contains(vertexToAdd)){
                    continue;
                }
                int temp = ent.getValue(); //distance from source to that vertex
                int newDist = currDist + temp;
                if (newDist < result[vertexToAdd].getDist()){
                    result[vertexToAdd].updateDist(newDist);
                    for (int i = 0; i < minHeap.length; i++) {
                        if (minHeap[i].getNodeNumber() == vertexToAdd){
                            minHeap[i].updateDist(newDist);
                            while (i > 0){
                                if (minHeap[i].getDist() < minHeap[i-1].getDist()){
                                    swap(minHeap, i, i-1);
                                }
                                i--;
                            }
                            break;
                        }


                    }
                }
            }
        }
        return result;
    }



    //Find the vertex by the location name
    public Node findByName(String name){
        for (int x =1; x < nodeCount + 1; x++){
            if(nodeArr[x].getLocation().equals(name)){
                return nodeArr[x];
            }
        }
        return null;
    }

    //Implement insertion in min heap
    //first insert the element to the end of the heap
    //then swim up the element if necessary
    //Set it as static as always
    public static void insert(Dist [] arr, Dist value, int index){
        if (index >= arr.length){
            return;
        }

        arr[index] = value;
        if (index != 0){
            boolean noSwap = false;
            int currNode = index;
            while(!noSwap && currNode != 0){
                int parent;

                if (currNode % 2 == 0){
                    parent = (currNode/2)-1;
                } else{
                    parent = (currNode/2);
                }

                if ((arr[parent].compareTo(arr[currNode])) == 1){
                    swap(arr, currNode, parent);
                    currNode = parent;
                }else{
                    noSwap = true;
                }
            }
        }

    }

    public static void swap(Dist []arr, int index1, int index2){
        Dist temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    //Extract the minimum element in the min heap
    //replace the last element with the root
    //then do minheapify
    //Set it as static as always
    public static Dist extractMin (Dist[] arr, int size){
        Dist min = arr[0];
        arr[0] = arr[--size];
        boolean noSwaps = false;
        int parent = 0;
        int left = (2 * parent) + 1;
        int right = (2 * parent) + 2;
        while(left < size && !noSwaps){

            if (right >= size){
                if (arr[parent].compareTo(arr[left]) == 1){
                    swap(arr, parent, left);
                    parent = left;
                    left = (2 * parent) + 1;
                    right = (2 * parent) + 2;
                } else {
                    noSwaps = true;
                }
            } else if(arr[parent].compareTo(arr[left]) != 1 && arr[parent].compareTo(arr[right]) != 1){
                noSwaps = true;
            } else {
                int comparison;
                if (arr[left].compareTo(arr[right]) == 1){
                    comparison = right;
                } else{
                    comparison = left;
                }

                if (arr[parent].compareTo(arr[comparison]) == 1){
                    swap(arr, parent, comparison);
                    parent = comparison;
                    left = (2 * parent) + 1;
                    right = (2 * parent) + 2;
                } else{
                    noSwaps = true;
                }
            }
        }

        return min;
    }




    //This will print the shortest distance result
    //The output format will be what we expect to pass the test cases
    public static void printResult(Dist[] result, int source){
        for(int x = 1;  x < result.length; x++){
            if(x != source){
                System.out.println(result[x].getNodeNumber() + " " +result[x].getDist());
            }
        }
    }

    public static void main(String[] args)throws IOException {
       /* DijGraph graph = new DijGraph("localmap.txt", 0);
        Dist[] result  = graph.dijkstra();
        printResult(result, 7);*/
    }
}
