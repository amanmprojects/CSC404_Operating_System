public class BankersAlgorithm{
    public static void main(String[] args) {
        int processes = 5, resources = 3;
        int[][] allocation= {
            {0, 1, 0},
            {2, 0, 0},
            {3, 0, 2},
            {2, 1, 1},
            {0, 0, 2}
        };
        int[][] max = {
            {7, 5, 3},
            {3, 2, 2},
            {9, 0, 2},
            {2, 2, 2},
            {4, 3, 3}
        };
        int[][] need = new int[processes][resources];
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
            need[i][j] = max[i][j] - allocation[i][j];
            }
        }
        
        int[] available = {
            3, 3, 2
        };

        // Print matrices
        System.out.println("Allocation Matrix:");
        printMatrix(allocation);
        System.out.println("Max Matrix:");
        printMatrix(max);
        System.out.println("Need Matrix:");
        printMatrix(need);
        System.out.println("Available Resources:"); 
        for (int i = 0; i < available.length; i++) {
            System.out.print(available[i] + " ");
        }
        System.out.println();
        
        // Check system state
        if (isSafe(processes, resources, allocation, max, available)) {
            System.out.println("System is in a safe state.");
        } else {
            System.out.println("System is not in a safe state.");
        }

        int[] request = {0, 2, 0};
        requestResources(1, request, allocation, max, available);
    }

    public static boolean isSafe(int processes, int resources, int[][] allocation, int[][] max, int[] available) {
        int[][] need = new int[processes][resources];
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        boolean[] finish = new boolean[processes];
        int[] safeSequence = new int[processes];
        int count = 0;

        while (count < processes) {
            boolean found = false;
            for (int p = 0; p < processes; p++) {
                if (!finish[p]) {
                    int j;
                    for (j = 0; j < resources; j++) {
                        if (need[p][j] > available[j]) {
                            break;
                        }
                    }
                    if (j == resources) {
                        for (int k = 0; k < resources; k++) {
                            available[k] += allocation[p][k];
                        }
                        safeSequence[count++] = p;
                        finish[p] = true;
                        found = true;
                        break;
                    }
                }  
            }
            if (!found) {
                return false;
            }
        }
        System.out.print("Safe sequence is: ");
        for (int i = 0; i < processes ; i++) {
            System.out.print(safeSequence[i] + " ");
        }
        System.out.println();
        return true;
    }

    public static void requestResources(int process, int[] request, int[][] allocation, int[][] max, int[] available) {
        for (int i = 0; i < available.length; i++) {
            if (request[i] > available[i]) {
                System.out.println("Request cannot be granted.");
                return;
            }
        }

        for (int i = 0; i < available.length; i++) {
            available[i] -= request[i];
            allocation[process][i] += request[i];
            max[process][i] -= request[i];
        }

        if (isSafe(allocation.length, allocation[0].length, allocation, max, available)) {
            System.out.println("Request granted.");
        } else {
            for (int i = 0; i < available.length; i++) {
                available[i] += request[i];
                allocation[process][i] -= request[i];
                max[process][i] += request[i];
            }
            System.out.println("Request cannot be granted.");
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}