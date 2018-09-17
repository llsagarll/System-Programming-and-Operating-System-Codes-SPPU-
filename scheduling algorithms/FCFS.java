import java.io.*;

class FCFS { 
	 
	public static void main(String args[]) throws Exception 
	{ 
		int n,at[],bt[],wt[],tat[]; 
		float awt = 0; 
		InputStreamReader isr = new InputStreamReader(System.in); 
		BufferedReader br = new BufferedReader(isr); 
		System.out.println("Enter no of process"); 
		n = Integer.parseInt(br.readLine()); 
		  
		wt = new int[n]; 
		tat = new int[n]; 
		bt = new int[n];
		at = new int[n];
		  
		System.out.println("Enter Burst time for each process\n"); 

		for(int i=0;i<n;i++) { 
			System.out.print("Process["+(i+1)+"]"); 
			bt[i]=Integer.parseInt(br.readLine()); 
		} 
		System.out.println("\n\nEnter Around Time"); 

		for(int i=0;i<n;i++) 		{ 
			System.out.print("Process["+i+"]"); 
			at[i]=Integer.parseInt(br.readLine()); 
		} 
		System.out.println("\n"); 
		wt[0]=0; 

		for(int i=1;i<n;i++) 		{ 
			wt[i]=wt[i-1]+bt[i-1]; 
			wt[i]=wt[i]-at[i]; 
		} 

		for(int i=0;i<n;i++) { 
			tat[i]=wt[i]+bt[i]; 
			awt=awt+wt[i]; 
		} 
		System.out.println("  PROCESS\t\tBURST-TIME\tWAITING-TIME\tTURN AROUND-TIME\n"); 
		for(int i=0;i<n;i++) {
			System.out.println("    "+ i + "\t\t"+bt[i]+"\t"+wt[i]+"\t"+tat[i]);
		} 
		awt=awt/n; 
		System.out.println("\n"); 
		System.out.println("Avg waiting time="+awt+"\n"); 
	} 
} 
/*OUTPUT
Enter no of process 5
Enter Burst time for each process

Process[1] 2
Process[2] 3
Process[3] 1
Process[4] 5
Process[5] 7


Enter Around Time
Process[0] 3
Process[1] 4
Process[2] 1
Process[3] 1
Process[4] 5


  PROCESS		BURST-TIME	WAITING-TIME	TURN AROUND-TIME

    0		2	0	2
    1		3	-2	1
    2		1	0	1
    3		5	0	5
    4		7	0	7


Avg waiting time=-0.4
*/
