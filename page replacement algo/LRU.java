
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.io.* ;
 
class LRU
{
    static int pageFaults(int pages[], int n, int capacity)
    {
        HashSet<Integer> s = new HashSet<>(capacity);
        HashMap<Integer, Integer> indexes = new HashMap<>();
      
        int page_faults = 0;
        for (int i=0; i<n; i++)
        {
	    System.out.println("Before " + (i+1) + " : ");
	    for(int k : s)
	    {
              System.out.println(" | " + k + " | ");  
              System.out.println(" -----");  
 
	    }
 	    //System.out.println("Before " + (i+1) + " iteration : " + s);
            if (s.size() < capacity)
            {
                if (!s.contains(pages[i]))
                {
                    s.add(pages[i]);
                    page_faults++;
                }
                indexes.put(pages[i], i);
            }
      
            else
            {
                if (!s.contains(pages[i]))
                {
                    int lru = Integer.MAX_VALUE, val=Integer.MIN_VALUE;
                    Iterator<Integer> itr = s.iterator();
                     
                    while (itr.hasNext()) {
                        int temp = itr.next();
                        if (indexes.get(temp) < lru)
                        {
                            lru = indexes.get(temp);
                            val = temp;
                        }
                    }
                 
                    s.remove(val);
                    s.add(pages[i]);
                    page_faults++;
                }
                indexes.put(pages[i], i);
            }
           System.out.println("After " + (i+1) + " : ");
	    for(int k : s)
	    {
              System.out.println(" | " + k + " | ");   
	      System.out.println(" -----"); 
	    }
          System.out.println("**********************************************");
          System.out.println("**********************************************");
  
       }
      
        return page_faults;
    }
     
    public static void main(String args[]) throws IOException
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("Enter number of pages to be scheduled : ");
        int pageCnt = Integer.parseInt(br.readLine());
        
        int[] pages = new int[pageCnt];
        System.out.print("Enter page indices : ");
        String[] indices = (br.readLine()).split(" ");
        for(int i=0;i<pageCnt;i++)
            pages[i] = Integer.parseInt(indices[i]);
            
        System.out.print("Enter buffer capacity : ");
        int capacity = Integer.parseInt(br.readLine());
        System.out.println("Number of page Faults : " + pageFaults(pages, pages.length, capacity));
    }
}
