class MOT
{
	String inst;
	int opc;
	int length;
	MOT(String inst,int opc,int length)
	{
		this.inst=inst;
		this.opc=opc;
		this.length=length;

	}

}

class POT
{
	String inst;
	int opc;

	POT(String inst,int opc)
	{
		this.inst=inst;
		this.opc=opc;

	}
	
}
class SYM
{
	String sym;
	int adr;
	SYM(String sym,int adr)
	{
		this.sym=sym;
		this.adr=adr;

	}
	void print()
	{
		System.out.println(sym+"\t"+adr);
	}
	
}
class LIT
{
	String lit;
	int adr;
	LIT(String lit,int adr)
	{
		this.lit=lit;
		this.adr=adr;

	}
	void print()
	{
		System.out.println(lit+"\t"+adr);
	}
}
