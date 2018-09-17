%%
[a-zA-Z0-9 \t]+([aA][nN][dD]|[bB][uU][tT]|[oO][rR]|,)[a-zA-Z0-9 \t]+		{return STR;}
.+																			{return ST;}
\n 										{return 0;}
%%