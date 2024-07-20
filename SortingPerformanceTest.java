import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class SortingPerformanceTest {

    public static void main(String[] args) {
        int[] smallDataset = generateRandomArray(1000);
        int[] mediumDataset = generateRandomArray(100000);
        int[] largeDataset = generateRandomArray(1000000);

        try (FileWriter writer = new FileWriter("performance_results.csv")) {
            writer.write("Algorithm,Dataset Size,Time (seconds)\n");
            runPerformanceTest("Quick Sort", smallDataset, mediumDataset, largeDataset, SortingPerformanceTest::quickSort, writer);
            runPerformanceTest("Merge Sort", smallDataset, mediumDataset, largeDataset, SortingPerformanceTest::mergeSort, writer);
            runPerformanceTest("Heap Sort", smallDataset, mediumDataset, largeDataset, SortingPerformanceTest::heapSort, writer);
            // Limit Bubble Sort to small dataset only due to its inefficiency on large datasets
            runPerformanceTest("Bubble Sort", smallDataset, null, null, SortingPerformanceTest::bubbleSort, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runPerformanceTest(String algorithmName, int[] smallDataset, int[] mediumDataset, int[] largeDataset, SortingAlgorithm algorithm, FileWriter writer) throws IOException {
        System.out.println("Testing " + algorithmName);
        if (smallDataset != null) {
            double time = measureTime(algorithm, smallDataset);
            System.out.println("Small dataset: " + time + " seconds");
            writer.write(algorithmName + ",1000," + time + "\n");
        }
        if (mediumDataset != null) {
            double time = measureTime(algorithm, mediumDataset);
            System.out.println("Medium dataset: " + time + " seconds");
            writer.write(algorithmName + ",100000," + time + "\n");
        }
        if (largeDataset != null) {
            double time = measureTime(algorithm, largeDataset);
            System.out.println("Large dataset: " + time + " seconds");
            writer.write(algorithmName + ",1000000," + time + "\n");
        }
        System.out.println();
    }

    private static double measureTime(SortingAlgorithm algorithm, int[] data) {
        int[] copy = Arrays.copyOf(data, data.length);
        long startTime = System.nanoTime();
        algorithm.sort(copy);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1e9;
    }

    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }
        return array;
    }

    @FunctionalInterface
    interface SortingAlgorithm {
        void sort(int[] array);
    }

    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

    public static void mergeSort(int[] array) {
        if (array.length > 1) {
            int mid = array.length / 2;
            int[] left = Arrays.copyOfRange(array, 0, mid);
            int[] right = Arrays.copyOfRange(array, mid, array.length);
            mergeSort(left);
            mergeSort(right);
            merge(array, left, right);
        }
    }

    private static void merge(int[] array, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        while (i < left.length) {
            array[k++] = left[i++];
        }
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    public static void heapSort(int[] array) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapify(array, i, 0);
        }
    }

    private static void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && array[left] > array[largest]) {
            largest = left;
        }
        if (right < n && array[right] > array[largest]) {
            largest = right;
        }
        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            heapify(array, n, largest);
        }
    }

    public static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}
