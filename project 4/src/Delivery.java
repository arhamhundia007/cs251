public class Delivery {
    private DijGraph westLafayette;//The graph
    private Node restaurant;//The vertex that the driver start
    private Node[] customer;//The vertices that the driver need to pass through
    private double slope;//Tip percentage function slope
    private double intercept;//Tip percentage function intercept
    private double [] order;//The order amount from each customer
    public Delivery (DijGraph graph,Node restaurant, Node[] customer, double slope, double intercept, double[] order){
        this.westLafayette = graph;
        this.restaurant = restaurant;
        this.customer = customer;
        this.slope = slope;
        this.intercept  = intercept;
        this.order = order;
    }

    //Finding the best path that the driver can earn most tips
    //Each time the driver only picks up three orders
    //Picking up N orders and find the maximum tips will be NP-hard
    public double bestPath(){
        double max = 0.0;

        Dist[] res = westLafayette.dijkstra(restaurant.getNodeNumber());
        Dist [][] temp = new Dist[4][];
        for (int i = 0; i < 3; i++) {
            temp[i] = westLafayette.dijkstra(customer[i].getNodeNumber());
        }
//        /*Dist[] cus1 = westLafayette.dijkstra(customer[0].getNodeNumber());
//        Dist[] cus2 = westLafayette.dijkstra(customer[1].getNodeNumber());*/
//        int [] dist = new int[3];
//        dist[0] = res[customer[0].getNodeNumber()].getDist();
//        dist[1] = cus1[customer[1].getNodeNumber()].getDist();
//        dist[2] = cus2[customer[2].getNodeNumber()].getDist();
        //double curr;

//        double tip1;
//        double tip2;
//        double tip3;


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j){
                    continue;
                }
                for (int k = 0; k < 3; k++) {
                    if (i == k || j == k){
                        continue;
                    }
                    double totTip = 0;
                    double currDist = res[customer[i].getNodeNumber()].getDist();
                    totTip += ((slope * currDist) + intercept) * order[i];
                    currDist += temp[i][customer[j].getNodeNumber()].getDist();
                    totTip += ((slope * currDist) + intercept) * order[j];
                    currDist += temp[j][customer[k].getNodeNumber()].getDist();
                    totTip += ((slope * currDist) + intercept) * order[k];
                    totTip = totTip/100;
//                    int d1 = dist[i];
//                    int d2 = dist[j];
//                    int d3 = dist[k];
//                    tip1 = (((slope * d1) + intercept)) * order[i];
//                    tip2 = (((slope * (d1 + d2)) + intercept)) * order[j];
//                    tip3 = (((slope * (d1 + d2 + d3)) + intercept)) * order[k];
//                    totTip = tip1 + tip2 + tip3;
//                    totTip = totTip/100;
                    if (totTip > max){
                        max = totTip;
                    }

                }
            }
        }



//        for (int i = 0; i < 3; i++) {
//            curr = 0;
//            int d1 = tp[customer[i].getNodeNumber()].getDist();
//            totDist = d1;
//            curr = (((slope * totDist) + intercept)/100) * order[i];
//            for (int j = 0; j < 3; j++) {
//                if (i == j){
//                    continue;
//                }
//                int d2 = tp[customer[j].getNodeNumber()].getDist();
//                totDist = totDist + d2;
//                temp1 = (((slope * totDist) + intercept)/100) * order[j];
//                curr = curr + (((slope * totDist) + intercept)/100) * order[j];
//                for (int k = 0; k < 3; k++) {
//                    if (i == k || j == k){
//                        continue;
//                    }
//                    int d3 = tp[customer[k].getNodeNumber()].getDist();
//                    totDist = totDist + d3;
//                    curr = curr + (((slope * totDist) + intercept)/100) * order[k];
//                }
//
//                if (curr > max){
//                    max = curr;
//                }
//
//            }
//
//        }
        return max;
    }

}
