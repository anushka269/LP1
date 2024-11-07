import java.util.Scanner;

public class RoundRobinPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Input number of processes and quantum
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        int[] bt = new int[n];
        int[] rt = new int[n];
        int[] wt = new int[n];
        int[] tat = new int[n];
        int[] at = new int[n];
        
        System.out.print("Enter time quantum: ");
        int quantum = sc.nextInt();
        
        // Input burst time and arrival time for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for process " + (i + 1) + ": ");
            at[i] = sc.nextInt();
            bt[i] = sc.nextInt();
            rt[i] = bt[i];
        }
        
        int CT = 0;
        int c = 0;
        String ganttChart = "";

        // Round Robin scheduling
        while (c < n) {
            boolean found = false;
            for (int i = 0; i < n; i++) {
                if (rt[i] > 0 && at[i] <= CT) {
                    found = true;
                    if (rt[i] > quantum) {
                        CT += quantum;
                        rt[i] -= quantum;
                        ganttChart += "P" + (i + 1) + " ";
                    } else {
                        CT += rt[i];
                        ganttChart += "P" + (i + 1) + " ";
                        wt[i] = CT - bt[i] - at[i];
                        tat[i] = wt[i] + bt[i];
                        rt[i] = 0;
                        c++;
                    }
                }
            }
            
            // If no process is ready, increase time (CPU idle)
            if (!found) {
                CT++;
                ganttChart += "idle ";
            }
        }

        // Output Gantt Chart
        System.out.println("Gantt Chart: " + ganttChart);
        
        // Calculate average waiting time and turnaround time
        float totalWT = 0, totalTAT = 0;
        System.out.println("Process\tArrival\tBurst\tWaiting\tTurnaround");
        for (int i = 0; i < n; i++) {
            totalWT += wt[i];
            totalTAT += tat[i];
            System.out.println("P" + (i + 1) + "\t" + at[i] + "\t" + bt[i] + "\t" +
                               wt[i] + "\t" + tat[i]);
        }

        System.out.println("Average Waiting Time: " + (totalWT / n));
        System.out.println("Average Turnaround Time: " + (totalTAT / n));
        sc.close();
    }
}
