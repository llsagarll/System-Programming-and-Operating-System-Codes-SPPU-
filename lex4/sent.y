%token STR ST
%%		
S 	:  STR { fun(); }
	|  ST	{fun1();}
	;

%%

#include "stdio.h"
#include "lex.yy.c"

int main()
{
	while(1)
	{
	yyparse();
	}
}
fun()
{
	printf("\"%s\" is a Compound Sentence\n",yytext);
}
fun1()
{
	printf("\"%s\" is a Simple Sentence\n",yytext);
}