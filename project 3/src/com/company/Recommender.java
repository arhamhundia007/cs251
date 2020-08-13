package com.company;

public class Recommender{

    /********************************
     * Do not change below code
     ********************************/
    int swaps, compares;
    int[] inversionCounts;
    String[] products;

    public Recommender(){
        swaps = 0;
        compares = 0;
    }

    public int getComapares() {
        return compares;
    }

    public int getSwaps() {
        return swaps;
    }

    /**************
     * This function is for the quick sort.
     **************/
    private boolean compare(int a ,int b){
        compares++;
        return a <= b;
    }

    /***************
     * This functions is for the quick sort.
     * By using this function, swap the similarity and the products at the same time.
     *****************/
    private void swap(int[] arr, int index1, int index2){
        swaps++;
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;

        String tempS = products[index1];
        products[index1] = products[index2];
        products[index2] = tempS;
    }

    /********************************
     * Do not change above code
     ********************************/

    /**
     * This function is for the calculate inversion counts of each option's.
     * @param dataset is file name of all data for hash table
     * @param options is the list of product name which we want to getting the inversion counts
     * @return it is integer array of each option's inversion counts. The order of return should be matched with options.
     */
    public int[] inversionCounts(String dataset, String[] options) {
        HashTable fromFile = new HashTable();
        try {
            fromFile.load(dataset);
        }catch (Exception e){
            System.out.println(e);
        }
        inversionCounts = new int[options.length];
        for (int i = 0; i < options.length; i++) {
            swaps = 0;
            HashTable.Pair temp = fromFile.get(options[i]);
            int inv = mergeSort(temp.value.depRating);
            inversionCounts[i] = inv;
        }

        return inversionCounts;
    }

    public int mergeSort(int [] arrayToSort){
        int [] temp = new int[arrayToSort.length];
        return mergeSortRecursively(arrayToSort,temp, 0, arrayToSort.length - 1);
    }



    public int mergeSortRecursively(int [] arrayToSort, int [] temp, int leftIndex, int rightIndex){
        int numberOfSwaps = 0;
        if (leftIndex < rightIndex){
            int mid = (leftIndex + rightIndex) / 2;
            numberOfSwaps += mergeSortRecursively(arrayToSort,temp, leftIndex, mid);
            numberOfSwaps += mergeSortRecursively(arrayToSort, temp, mid + 1, rightIndex);
            numberOfSwaps += mergeArrays(arrayToSort, temp, leftIndex, mid + 1, rightIndex);
        }
        return numberOfSwaps;
    }

    public int mergeArrays(int [] arrayToMerge, int[] temp, int leftIndex, int midIndex, int rightIndex){
            swaps = 0;
            // Initial indices of first and second subarrays
            int l = leftIndex;
            int r = midIndex;

            // index of the position of where ot will be merged from.
            int indexToPut = leftIndex;

            while (l < midIndex && r <= rightIndex){
                if (arrayToMerge[l] >= arrayToMerge[r]) {
                    temp[indexToPut] = arrayToMerge[r];
                    r++;
                    swaps = swaps +  midIndex - l;         //To calculate number of swaps when we do mid minus the leftCurrentIndex of the array tells us how many swaps are done.
                } else {
                    temp[indexToPut] = arrayToMerge[l];
                    l++;
                }
                indexToPut++;
            }

            /* Copy remaining elements of leftArray[] if any */
            while (l <= midIndex - 1) {
                temp[indexToPut] = arrayToMerge[l];
                l++;
                indexToPut++;
            }

            /* Copy remaining elements of rightArray[] if any */
            while (r <= rightIndex) {
                temp[indexToPut] = arrayToMerge[r];
                r++;
                indexToPut++;
            }

            for (int i = leftIndex; i <= rightIndex; i++) {
                arrayToMerge[i] = temp[i];
            }

            return swaps;
    }



    /**
     * Get the sequence of recommendation from the dataset by sorting the inverse count.
     * Compare the similarity of depRating between RecentPurchase's and each option's.
     * Use inverse count to get the similarity of two array.
     */
    public String[] recommend(String dataset, String recentPurchase, String[] options) {
        products = options.clone();
        HashTable file = new HashTable();
        try{
            file.load(dataset);
        }catch (Exception e){
            System.out.println(e);
        }
        int [] diff = new int[options.length];
        inversionCounts = inversionCounts(dataset, options);
        swaps = 0;
        int subtrahend = mergeSort(file.get(recentPurchase).value.depRating);

        swaps = 0;

        for (int i = 0; i < options.length; i++) {
            swaps = 0;
            diff[i] = Math.abs(inversionCounts[i] - subtrahend);
        }

        quickSort(diff, 0, options.length - 1);
        return products;
    }

    /**
     * partitioning the array in two halves by putting the pivot in its
     * actual position in the array.
     * @param arrayToSort - the array to sort.
     * @param highIndex - last element of the array
     * @param lowIndex - first element of the array
     */
    public int divideArray(int [] arrayToSort, int lowIndex, int highIndex){

        int pivot = arrayToSort[highIndex];
        int index = lowIndex - 1; //index of lower element

        for (int i = lowIndex; i < highIndex ; i++) {
            if (!compare(arrayToSort[i], pivot)) {
                continue;
            }
            index++;
            swap(arrayToSort, index, i);
        }

        swap(arrayToSort, index + 1, highIndex); //putting the pivot in its position

        return index + 1;
    }

    /**
     *
     * @param arrayToSort
     * @param lowIndex
     * @param highIndex
     */
    public void quickSort(int [] arrayToSort, int lowIndex, int highIndex){
        if (compare(lowIndex, highIndex)){
            int pivot = divideArray(arrayToSort, lowIndex, highIndex);
            quickSort(arrayToSort, lowIndex, pivot-1);
            quickSort(arrayToSort, pivot+1, highIndex);
        }
    }
}
