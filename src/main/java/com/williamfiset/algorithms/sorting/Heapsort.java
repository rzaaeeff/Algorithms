/**
 * Implementation of heapsort
 *
 * <p>Run with:
 *
 * <p>$ ./gradlew run -Palgorithm=sorting.Heapsort
 *
 * @author Heydar Rzayev, rzaaeeff@gmail.com
 */
package com.williamfiset.algorithms.sorting;

public class Heapsort implements InplaceSort {

    @Override
    public void sort(int[] values) {
        Heapsort.heapsort(values);
    }

    private static void heapsort(int[] ar) {
        int size = ar.length;
        if (size <= 1) return;

        // heapify
        // sink each element starting from the level before last level
        for (int i = Math.max(0, (size - 1) / 2); i >= 0; i--) {
            sink(ar, i, size);
        }

        // get max from root
        // replace it with last
        // then sink new root
        for (int i = size - 1; i > 0; i--) {
            swap(ar, i, 0);
            sink(ar, 0, i);
        }
    }

    // if element is less than any of its descendants,
    // replace with a greater one of them
    //      (4)                  (9)
    //      / \        ->        / \
    //    (6) (9)              (6) (4)
    private static void sink(int[] ar, int index, int size) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * (index + 1);
            int largest = index;

            if (left < size && ar[left] > ar[largest]) largest = left;
            if (right < size && ar[right] > ar[largest]) largest = right;

            if (largest == index) break;

            swap(ar, index, largest);
            sink(ar, largest, size);
        }
    }

    private static void swap(int[] ar, int i, int j) {
        int tmp = ar[i];
        ar[i] = ar[j];
        ar[j] = tmp;
    }

    /* TESTING */

    public static void main(String[] args) {
        Heapsort sorter = new Heapsort();
        int[] array = {10, 4, 6, 4, 8, -13, 2, 3};
        sorter.sort(array);
        // Prints:
        // [-13, 2, 3, 4, 4, 6, 8, 10]
        System.out.println(java.util.Arrays.toString(array));
    }
}
