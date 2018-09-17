%{
#include<stdio.h>
int ch=0, ln=0, wr=0;
%}

%%
[\n]		{ln++;}
[^\n\t ]+ 	{wr++;ch+=yyleng;}
%%

int yywrap(void)
{
    return 1;
}

int main()
{
    FILE *fp=fopen("pvg.txt","r"); 
    char file[10];
    yyin=fp;
    yylex();
    yywrap();
    printf("\nTotal No. of Characters = %d\nTotal No.of Lines = %d\nTotal No. of Words = %d\n",ch,ln,wr);
    return 0;
}
