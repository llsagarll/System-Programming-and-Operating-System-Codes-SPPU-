%%
int|char|bool|double|float   {return TYPE;}
[a-zA-Z_][A-Za-z_0-9]*          { return ID;}
,                               {return yytext[0];}
;                               {return yytext[0];}
\n                              {return 0;}
[ \t]+                          ;
.                               {return 0;}
%%
