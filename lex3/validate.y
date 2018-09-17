%token TYPE
%token ID
%left ','
%%
S :  TYPE  {fun1();} E ';'
  ;
E :  E ',' E
  |  ID         {fun2();}
  ;
%%
#include "stdio.h"
#include "lex.yy.c"
int main()
{
  printf("Enter the statement to validate: ");
  yyparse();
}
fun1()
{
  printf("%s is a Type of Variable\n",yytext);
}
fun2()
{
printf("%s is a name of variable\n",yytext);
}
yyerror()
{
printf("\nInvalid declaration\n");
}
