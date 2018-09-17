import java.util.*;
import java.io.*;
public class Pass2
{	
		static ArrayList<SYM> sym;
		static ArrayList<LIT> lit;
	public static void main(String args[])
	{
		sym = new ArrayList<SYM>();
		lit = new ArrayList<LIT>();
		try
		{
			Scanner symtab = new Scanner(new FileInputStream("symtab.txt"));
			Scanner littab = new Scanner(new FileInputStream("littab.txt"));
			Scanner intcode = new Scanner(new FileInputStream("intcode.txt"));
			BufferedWriter maccode= new BufferedWriter(new FileWriter("maccode.txt"));
			while(symtab.hasNext())
			{
				sym.add(new SYM(symtab.next(),symtab.nextInt()));
				//sym.get(i++).print();
			}
			
			while(littab.hasNext())
			{
				lit.add(new LIT(littab.next(),littab.nextInt()));
				//lit.get(i++).print();
			}
			symtab.close();
			littab.close();
			String s;
			while(intcode.hasNextLine())
			{
				s=intcode.nextLine();
				String words[]= s.split("( |'t)+");
				maccode.write(words[0]+"\t+\t");
				for (int i=1; i<words.length;i++ ) {
					switch(words[i].charAt(0))
					{
						case 'I':
							maccode.write(words[i].substring(3)+"\t");
							break;
						case 'S':
							maccode.write(sym.get(Integer.parseInt(words[i].substring(2))-1).adr+"\t");
							break;
						case 'L':
							maccode.write(lit.get(Integer.parseInt(words[i].substring(2))-1).adr+"\t");
							break;
						case 'D':
							if(words[i].charAt(3)=='2')
							{
								i++;
								break;
							}
							maccode.write("0\t0\t");
							i++;
							break;
						case 'C':
							maccode.write(words[i].substring(2)+"\t");
							break;
						default:
							maccode.write(words[i]+"\t");

					}
				}
				maccode.write("\n");
			}
			intcode.close();
			maccode.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}
