import java.util.*;
import java.io.*;
public class Pass1 
{	
		static ArrayList<MOT> mot;
		static ArrayList<POT> pot;
		static ArrayList<SYM> sym;
		static ArrayList<LIT> lit;
	public static void main(String args[])
	{
		int i;
		try
		{	 mot= new ArrayList<MOT>();
			 pot= new ArrayList<POT>();
			 sym= new ArrayList<SYM>();
			 lit= new ArrayList<LIT>();
			Scanner motfile = new Scanner(new FileInputStream("mot.txt"));
			Scanner potfile = new Scanner(new FileInputStream("pot.txt"));
			BufferedWriter intcode = new BufferedWriter(new FileWriter("intcode.txt"));
			BufferedWriter symtab = new BufferedWriter(new FileWriter("symtab.txt"));
			BufferedWriter littab =new BufferedWriter(new FileWriter("littab.txt"));

		 	i=0;
			while(motfile.hasNext())
			{
				String inst=motfile.next();
				int opcode=Integer.parseInt(motfile.next());
				int length=Integer.parseInt(motfile.next());
				mot.add(new MOT(inst,opcode,length));
			}

			i=0;
			while(potfile.hasNext())
			{
				String inst=potfile.next();
				int opcode=Integer.parseInt(potfile.next());
				pot.add(new POT(inst,opcode));
			}			
			int loc=0;
			int pos;
			int line=0;
			int stflag=0;
			int pool=0;
			Scanner infile =new Scanner(new FileInputStream("input.asm"));
			System.out.println("\tinput.asm");
			while(infile.hasNextLine())
			{	
				String s=infile.nextLine();
				String words[]=s.split("[ \t]+");
				if(words.length==0 || words[0].equals(""))
					continue;
				if(words.length<3)
					System.out.print("\t");
				System.out.println(String.join("\t",(words)));
				//System.out.println(s);
				line++;
				int p=0;
				if(words.length>3)
					linerr("119",line);
				else if(words.length==3)
				{	p=1;
					pos=issym(words[0]);
					if(pos==-1)
					{
						sym.add(new SYM(words[0],loc));
					}
					else
					{
						if(sym.get(pos).adr==-1)
							(sym.get(pos)).adr=loc;
						else
							linerr("132",line);
					}

				}
				pos=ispot(words[p]);
				//System.out.println(words[p]+""+pos);
				if(pos==-1)
				{
					pos=ismot(words[p]);
					if(pos==-1)
					{
						linerr("142",line);
					}

					int iloc=mot.get(pos).length;
					intcode.write(loc+"   IS,"+mot.get(pos).opc);
					String operands[]=(words[p+1]).split(",");
					switch(mot.get(pos).opc)
					{
						case 8:
								if(operands[0].equals("EQ"))
									intcode.write("   1   ");
								else if(operands[0].equals("LT"))
									intcode.write("   2   ");
								else if(operands[0].equals("LE"))
									intcode.write("   3   ");
								else if(operands[0].equals("GT"))
									intcode.write("   4   ");
								else if(operands[0].equals("GE"))
									intcode.write("   5   ");
								else if(operands[0].equals("ANY"))
									intcode.write("   6   ");
								else 
									linerr("164",line);
								break;
							case 9:
							case 10:
								if(ispot(operands[0])==-1 && ismot(operands[0])==-1 && isreg(operands[0])==-1 && operands[0].charAt(0)!='=' )
								{
									pos=issym(operands[0]);
									if(pos==-1)
									{
										sym.add(new SYM(operands[0],-1));
									}
									intcode.write("   S,"+(issym(operands[0])+1));
								}
								else
								{
									linerr("179",line);
								}
								break;
							default:
								pos=isreg(operands[0]);
								//System.out.println(operands[0]);
								if(pos!=-1)
								{
									intcode.write("   "+(pos)+"   ");
								}
								else
								{
									linerr("190",line);
								}
					}
					for(int k=1;k<operands.length;k++)
					{
						if(operands[k].charAt(0)=='=')
						{
							pos=islit(operands[k],pool);
							if(pos==-1)
							{
								lit.add(new LIT(operands[k],-1));
							}
							intcode.write("L,"+(islit(operands[k],pool)+1)+" ");
						}
						else if(isconst(operands[k]))
						{
							intcode.write("C,"+ operands[k]+ " ");
						}
						else
						{
							pos=issym(operands[k]);
							if(pos==-1)
							{
								sym.add(new SYM(operands[k],-1));
							}
							intcode.write("S,"+(issym(operands[k])+1)+" ");
						}
					}
					intcode.write("\n");
					loc+=iloc;
				}
				else
				{
					switch(pot.get(pos).opc)
					{
						case 1:
							if(stflag==0)
							{
								if(words.length==p+2)
								{
									loc=Integer.parseInt(words[p+1]);
								}
								else
								{
									loc=0;
								}
								stflag=1;
								//intcode.write("AD,1 C")
							}
							else
							{
								linerr("240",line);
							}
							break;
						case 2:
						case 3:
							
							for(i=pool;i<lit.size();i++)
							{
								if(lit.get(i).adr==-1)
									lit.get(i).adr=loc++;
							}
							pool=lit.size();
							if(words.length>1)
							{
								linerr("254",line);
							}
							break;
						case 4:
							if(words.length==3)
							{
								pos=issym(words[2]);
								if(pos!=-1)
								{
									sym.get(issym(words[0])).adr=sym.get(pos).adr;
								}
							}
							else
							{
								linerr("269",line);
							}
							break;
						case 5:
							if(words.length==2)
							{
								if(isconst(words[1]))
								{
									loc=Integer.parseInt(words[1]);
								}
								else
								{
									linerr("281",line);
								}
							}
							break;
						case 6:
							if(words.length==3)
							{
								pos=issym(words[0]);

								intcode.write(loc+"   DL,1   S,"+(pos+1)+" C,"+words[2]+"\n");
								loc++;
							}
							else
							{
								linerr("294",line);
							}
							break;
						case 7:
							if(words.length==3)
							{
								pos=issym(words[0]);
								System.out.println("hello");
								intcode.write(loc+"   DL,2   S,"+(pos+1)+"\n");
								loc+=Integer.parseInt(words[2]);
							}
							else
							{
								linerr("294",line);
							}
							break;
						case 8:
								break;
					

					}
				}
				//intcode.write("\n");
			}
			intcode.close();
			Scanner printcode= new Scanner(new FileInputStream("intcode.txt"));
			System.out.println("\nintcode.txt");
			while(printcode.hasNextLine())
			{
				System.out.println(printcode.nextLine());
			}
			System.out.println("\nsymtab.txt");
			for (i=0;i<sym.size() ;i++ ) {
				System.out.println(sym.get(i).sym+"\t"+sym.get(i).adr);
				symtab.write(sym.get(i).sym+"\t"+sym.get(i).adr+"\n");
			}
			System.out.println("\nlittab.txt");
			for (i=0;i<lit.size() ;i++ ) {
				System.out.println(lit.get(i).lit+"\t"+lit.get(i).adr);
				littab.write(lit.get(i).lit+"\t"+lit.get(i).adr+"\n");
			}
			printcode.close();
			symtab.close();
			littab.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.exit(0);
	}
	static int ismot(String s)
	{
		for(int i=0;i<mot.size();i++)
		{
			if(mot.get(i).inst.equals(s))
				return i;
		}
		return -1;
	}
	static int ispot(String s)
	{
		////System.out.println(s);
		for(int i=0;i<pot.size();i++)
		{
			if(s.equals(pot.get(i).inst))
				return i;
		}
		return -1;
	}
	static int issym(String s)
	{
		for(int i=0;i<sym.size();i++)
		{
			if(sym.get(i).sym.equals(s))
				return i;
		}
		return -1;
	}
	static int islit(String s,int i)
	{
		for(;i<lit.size();i++)
		{
			if(lit.get(i).lit.equals(s))
				return i;
		}
		return -1;
	}
	static void linerr(String s,int line)
	{
		System.out.println("Error at line "+line);
		System.exit(0);
	}
	static boolean isconst(String s)
	{
		for(int i=0;i<s.length();i++)
		{
			if(!(s.charAt(i)>='0' && s.charAt(i)<='9'))
				return false;
		}
		return true;
	}

	static int isreg(String s)
	{
		if(s.equals("AREG"))
			return 1;
		if(s.equals("BREG"))
			return 2;
		if(s.equals("CREG"))
			return 3;
		if(s.equals("DREG"))
			return 4;
		return -1;
	}
}
