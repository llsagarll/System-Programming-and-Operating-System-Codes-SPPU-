import java.util.*;
 
class Process {
    int at, bt, pri, pno;
    Process(int pno, int at, int bt, int pri)
    {
        this.pno = pno;
        this.pri = pri;
        this.at = at;
        this.bt = bt;
    }
}
 
class GChart {
    int pno, stime, ctime, wtime, ttime;
}
 
class MyComparator implements Comparator {
 
    public int compare(Object o1, Object o2)
    {
 
        Process p1 = (Process)o1;
        Process p2 = (Process)o2;
        if (p1.at < p2.at)
            return (-1);
 
        else if (p1.at == p2.at && p1.pri > p2.pri)
            return (-1);
 
        else
            return (1);
    }
}

class FindGantChart {
    void findGc(LinkedList queue)
    {
        int time = 0;
        TreeSet prique = new TreeSet(new MyComparator());
        LinkedList result = new LinkedList();
        while (queue.size() > 0)
            prique.add((Process)queue.removeFirst());
 
        Iterator it = prique.iterator();
        time = ((Process)prique.first()).at;
        while (it.hasNext()) {
            Process obj = (Process)it.next();
 
            GChart gc1 = new GChart();
            gc1.pno = obj.pno;
            gc1.stime = time;
            time += obj.bt;
            gc1.ctime = time;
            gc1.ttime = gc1.ctime - obj.at;
            gc1.wtime = gc1.ttime - obj.bt;
            result.add(gc1);
        }
        new ResultOutput(result);
    }
}
 
class ResultOutput {
 
    ResultOutput(LinkedList result)
    {
 
        double wavg = 0, tavg = 0;
        int totalprocess = result.size();
        System.out.println("Process_no\tStart_time\t"+ "Complete_time\tTrun_Around_Time\tWating_Time");
        while (result.size() > 0) {
 
            GChart obj = (GChart)result.poll();
            wavg += obj.wtime;
            tavg += obj.ttime;
            System.out.println(obj.pno + "\t\t" + obj.stime + "\t\t" + obj.ctime + "\t\t" + obj.ttime + "\t\t\t" + obj.wtime);
        }
        System.out.println("Average Wating Time is : "
        + (wavg / totalprocess));
 
        System.out.println("Average Trun Around time is : "
         + (tavg / totalprocess));
    }
}

class PriorityPreemption {
 
    public static void main(String args[])
    {
        LinkedList queue = new LinkedList();
 
        int arrivaltime[] = { 1, 2, 3, 4, 5 };
        int bursttime[] = { 3, 5, 1, 7, 4 };
        int priority[] = { 3, 4, 1, 7, 8 };
 
        for (int i = 0; i < 5; i++)
            queue.addLast(new Process(i + 1, arrivaltime[i],bursttime[i], priority[i]));
 
        FindGantChart obj = new FindGantChart();
        obj.findGc(queue);
    }
}

/* OUTPUT
Process_no	Start_time	Complete_time	Trun_Around_Time	Wating_Time
1		1		4		3			0
2		4		9		7			2
3		9		10		7			6
4		10		17		13			6
5		17		21		16			12
Average Wating Time is : 5.2
Average Trun Around time is : 9.2
*/
