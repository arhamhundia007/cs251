public class Main {

    public static void main(String[] args) {
        int n = 50;
        MyPatientQueue patient = new MyPatientQueue();
        //testing enqueue
        for (int i = 0; i < n; i++){
            Patient p = new Patient( "a" + i, i, (i % 4 + 1) );
            patient.enqueue(p);
        }

        System.out.println();
        System.out.println();
        System.out.println("test enqueue");
        System.out.println();
        System.out.println();

        for (int i = 0; i < patient.array.length; i ++){
            if (patient.array[i] == null){
                System.out.printf("%d  null \n",i);
            } else {
                System.out.println(patient.array[i].toString());
            }
        }
        //testing dequeue
        for (int i = 0; i < 5; i ++ ){
            patient.dequeue();
            patient.dequeue();
        }

        System.out.println();
        System.out.println();
        System.out.println("test dequeue");
        System.out.println();
        System.out.println();

        for (int i = 0; i < patient.array.length; i ++){
            if (patient.array[i] == null){
                System.out.printf("%d  null \n",i);
            } else {
                System.out.println(patient.array[i].toString());
            }
        }

        for (int i = 0; i < 15; i++){
            Patient p = new Patient( "a" + i + 100, i + 100, (i % 4 + 1) );
            patient.enqueue(p);

        }

        System.out.println();
        System.out.println();
        System.out.println("test Mix");
        System.out.println();
        System.out.println();

        for (int i = 0; i < patient.array.length; i ++){
            if (patient.array[i] == null){
                System.out.printf("%d  null \n",i);
            } else {
                System.out.println(patient.array[i].toString());
            }
        }


        for (int i = 0; i < 20; i ++ ){
            patient.dequeue();
            patient.dequeue();
        }
        System.out.println();
        System.out.println();
        System.out.println("test Mix2");
        System.out.println();
        System.out.println();

        for (int i = 0; i < patient.array.length; i ++){
            if (patient.array[i] == null){
                System.out.printf("%d  null \n",i);
            } else {
                System.out.println(patient.array[i].toString());
            }
        }



    }
}





