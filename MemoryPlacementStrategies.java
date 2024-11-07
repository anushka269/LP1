import java.util.Scanner;

public class MemoryPlacementStrategies {

    static int[] jobSizes; // Array for storing job sizes
    static int[] blockSizes; // Array for storing block sizes
    static int numJobs, numBlocks;
    static int[] allocation; // To keep track of block allocation to jobs
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Input number of jobs and blocks
        System.out.println("Enter number of jobs:");
        numJobs = sc.nextInt();
        System.out.println("Enter number of blocks:");
        numBlocks = sc.nextInt();

        // Input job sizes
        jobSizes = new int[numJobs];
        blockSizes = new int[numBlocks];
        allocation = new int[numJobs];

        System.out.println("Enter sizes of jobs:");
        for (int i = 0; i < numJobs; i++) {
            System.out.print("Job " + (i + 1) + ": ");
            jobSizes[i] = sc.nextInt();
        }

        System.out.println("Enter sizes of blocks:");
        for (int i = 0; i < numBlocks; i++) {
            System.out.print("Block " + (i + 1) + ": ");
            blockSizes[i] = sc.nextInt();
        }

        while (true) {
            System.out.println("\nMemory Placement Strategies Menu:");
            System.out.println("1. First Fit");
            System.out.println("2. Best Fit");
            System.out.println("3. Worst Fit");
            System.out.println("4. Next Fit");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            resetAllocation();

            switch (choice) {
                case 1:
                    firstFit();
                    break;
                case 2:
                    bestFit();
                    break;
                case 3:
                    worstFit();
                    break;
                case 4:
                    nextFit();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please choose again.");
            }
        }
    }

    // First Fit Algorithm
    static void firstFit() {
        for (int i = 0; i < numJobs; i++) {
            for (int j = 0; j < numBlocks; j++) {
                if (blockSizes[j] >= jobSizes[i]) {
                    allocation[i] = j;
                    blockSizes[j] -= jobSizes[i]; // Reduce block size after allocation
                    break;
                }
            }
        }
        displayAllocation();
    }

    // Best Fit Algorithm
    static void bestFit() {
        for (int i = 0; i < numJobs; i++) {
            int bestIdx = -1;
            for (int j = 0; j < numBlocks; j++) {
                if (blockSizes[j] >= jobSizes[i]) {
                    if (bestIdx == -1 || blockSizes[bestIdx] > blockSizes[j]) {
                        bestIdx = j;
                    }
                }
            }
            if (bestIdx != -1) {
                allocation[i] = bestIdx;
                blockSizes[bestIdx] -= jobSizes[i]; // Reduce block size after allocation
            }
        }
        displayAllocation();
    }

    // Worst Fit Algorithm
    static void worstFit() {
        for (int i = 0; i < numJobs; i++) {
            int worstIdx = -1;
            for (int j = 0; j < numBlocks; j++) {
                if (blockSizes[j] >= jobSizes[i]) {
                    if (worstIdx == -1 || blockSizes[worstIdx] < blockSizes[j]) {
                        worstIdx = j;
                    }
                }
            }
            if (worstIdx != -1) {
                allocation[i] = worstIdx;
                blockSizes[worstIdx] -= jobSizes[i]; // Reduce block size after allocation
            }
        }
        displayAllocation();
    }

    // Next Fit Algorithm
    static void nextFit() {
        int lastAllocatedIdx = 0; // To track the last allocated block index
        for (int i = 0; i < numJobs; i++) {
            boolean allocated = false;
            for (int j = 0; j < numBlocks; j++) {
                int idx = (lastAllocatedIdx + j) % numBlocks; // Wrapping logic
                if (blockSizes[idx] >= jobSizes[i]) {
                    allocation[i] = idx;
                    blockSizes[idx] -= jobSizes[i]; // Reduce block size after allocation
                    lastAllocatedIdx = idx; // Set the last allocated index to the current one
                    allocated = true;
                    break;
                }
            }
            if (!allocated) {
                allocation[i] = -1; // No block found for this job
            }
        }
        displayAllocation();
    }

    // Display the allocation of jobs to blocks
    static void displayAllocation() {
        System.out.println("\nJob No.\tJob Size\tBlock No.");
        for (int i = 0; i < numJobs; i++) {
            System.out.print((i + 1) + "\t" + jobSizes[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.println(allocation[i] + 1);
            else
                System.out.println("Not Allocated");
        }
    }

    // Reset allocation and block sizes to their initial state
    static void resetAllocation() {
        for (int i = 0; i < numJobs; i++) {
            allocation[i] = -1;
        }
        System.out.println("\nEnter sizes of blocks (resetting):");
        for (int i = 0; i < numBlocks; i++) {
            System.out.print("Block " + (i + 1) + ": ");
            blockSizes[i] = sc.nextInt();
        }
    }
}
