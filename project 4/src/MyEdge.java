
public class MyEdge implements Comparable<MyEdge>{
    private int source;
    private int destination;
    private int weight;

    public MyEdge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public int getS(){
        return source;
    }
    public int getD(){
        return  destination;
    }

    public int getWeight(){
        return  weight;
    }


    @Override
    public int compareTo(MyEdge edge){
        if (weight > edge.weight){
            return 1;
        } else if(weight < edge.weight){
            return -1;
        } else{
            return 0;
        }
    }


    //Implement the comparable interface for MyEdge class
    //This will provide interface for PrioritQueue to compare the instances of MyEdge class

}
