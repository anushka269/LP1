import java.util.Scanner;

public class SJFpreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        int[] bt = new int[n]; // Burst time
        int[] at = new int[n]; // Arrival time
        int[] rt = new int[n]; // Remaining time
        int[] wt = new int[n]; // Waiting time
        int[] tat = new int[n]; // Turn Around time
        boolean[] C = new boolean[n]; // Tracks if a process is completed

        System.out.println("Enter Arrival Time and Burst Time of the processes:");
        for (int i = 0; i < n; i++) {
            System.out.print("Arrival time: P" + (i + 1) + ": ");
            at[i] = sc.nextInt();
            System.out.print("Burst time: P" + (i + 1) + ": ");
            bt[i] = sc.nextInt();
            rt[i] = bt[i]; // Remaining time initialized to burst time
        }

        int P = 0, CT = 0, shortest = 0; // CT=current time; P=processes
        boolean found;
        String ganttChart = "";

        while (P < n) {
            found = false;

            // Find the shortest job that has arrived
            for (int i = 0; i < n; i++) {
                if (!C[i] && at[i] <= CT && (found == false || rt[i] < rt[shortest])) {
                    shortest = i;
                    found = true;
                }
            }

            if (found) // if(found == true)
            {
                rt[shortest]--; // Decrement the remaining time
                ganttChart += "P" + (shortest + 1) + " ";
                CT++;

                // If the process is completed
                if (rt[shortest] == 0) {
                    C[shortest] = true;
                    P++;
                    tat[shortest] = CT - at[shortest]; // Turnaround time
                    wt[shortest] = tat[shortest] - bt[shortest]; // Waiting time
                }
            } else {
                CT++; // Idle time if no process is ready
                ganttChart += "idle ";
            }
        }

        // Output Gantt Chart
        System.out.println("\nGantt Chart: " + ganttChart);

        // Calculate and output Average WT and TAT
        float avgWT = 0, avgTAT = 0;
        System.out.println("\nProcess\tArrival\tBurst\tWaiting\tTurnaround");
        for (int i = 0; i < n; i++) {
            avgWT += wt[i]; 
            avgTAT += tat[i];
            System.out.println("P" + (i + 1) + "\t" + at[i] + "\t" + bt[i] + "\t" + wt[i] + "\t" + tat[i]);
        }
        avgWT /= n;
        avgTAT /= n;

        System.out.println("\nAverage Waiting Time: " + avgWT);
        System.out.println("Average Turnaround Time: " + avgTAT);

        // Close scanner
        sc.close();
    }
}
