#include<stdio.h>
#include<string.h>
struct mnt{
       char macro[10];
       int ads;
       int p_ads;
       int pc;
}m1[10];

struct mdt{
       char dif[20000];
}d1[10];
struct par{
       char name[10];

}p1[100];
int main()
{
       char *s;
       int mdtc=1,mntc=1,ptc=1,i,f,m,p,l;
       FILE *fp,*fp1;
       int n,z,j;
       char words[10][10],words1[10][10];
       fp=fopen("macro.txt","r");
       if(!fp)
       exit(0);
       while(!feof(fp))
       {
              s=(char*)calloc(100,sizeof(char));
              fgets(s,100,fp);

              if(strcmp(s,"MACRO\n")==0)
              {
                     fgets(s,100,fp);
                     for(i=0;i<10;i++)
       		for(j=0;j<10;j++)
       		words[i][j]='\0';
       		i=0,z=0,n=0;
       		while(s[i]!='\0')
       		{
       			if(s[i]==' ' || s[i]=='\n')
       			{
       				z++;
       				n=0;
       				i++;
       				continue;
       			}
       			words[z][n]=s[i];
       			n++;
       			i++;
       		}

                     strcpy(m1[mntc].macro,words[0]);
                     m1[mntc].ads=mdtc;
                     m1[mntc].pc=z-1;
                     m1[mntc].p_ads=ptc;
                     for(i=1;i<z;i++)
                     {
                            strcpy(p1[ptc].name,words[i]);
                            ptc++;
                     }
                     mntc++;
                     do
                     {
                            fgets(s,500,fp);
                            strcpy(d1[mdtc].dif,s);
                            mdtc++;
                     }while(strcmp(s,"MEND\n")!=0);

              }
       }
       printf("\nMacro.txt\n" );
       system("cat macro.txt");
       printf("\nMNT\n" );
       for(i=1;i<mntc;i++)
       {
              printf("%d\t%s\t%d\t%d\n",m1[i].ads,m1[i].macro,m1[i].pc,m1[i].p_ads);
       }
       printf("\nMDT\n" );
       for(i=1;i<mdtc;i++)
       {
              printf("%d\t%s",i,d1[i].dif);
       }
       printf("\nPar_Table\n" );
       for(i=1;i<ptc;i++)
       {
              printf("%d\t%s\n",i,p1[i].name);
       }
       fclose(fp);
       fp=fopen("macro.txt","r");
       fp1=fopen("macro2.txt","w");
       if(!fp)
       exit(0);
       do
       {
              s=(char*)calloc(100,sizeof(char));
              fgets(s,100,fp);

       }while(strcmp(s,"START\n")!=0);
       fprintf(fp1, "%s", s);
       while(!feof(fp))
       {
              s=(char*)calloc(100,sizeof(char));

              fgets(s,100,fp);
              //printf("%s\n",s );
              for(i=0;i<10;i++)
              for(j=0;j<10;j++)
              words[i][j]='\0';
              i=0,z=0,n=0;
              while(s[i]!='\0')
              {
                     if(s[i]==' ' || s[i]=='\n')
                     {
                            z++;
                            n=0;
                            i++;
                            continue;
                     }
                     words[z][n]=s[i];
                     n++;
                     i++;
              }
              int flag1=0;
              for(i=1;i<mntc;i++)
              {
                     if(strcmp(words[0],m1[i].macro)==0)
                     {
                            if(z-1==m1[i].pc)
                            { int co=0;

                                   while(strcmp(d1[m1[i].ads+co].dif,"MEND\n")!=0)
                                   {
                                          //printf("%s\n",d1[m1[i].ads+co].dif );
                                          for(l=0;l<10;l++)
                                          for(m=0;m<10;m++)
                                          words1[l][m]='\0';
                                          l=0,p=0,n=0;
                                          while(d1[m1[i].ads+co].dif[l]!='\0')
                                          {
                                                 if(d1[m1[i].ads+co].dif[l]==' ' || d1[m1[i].ads+co].dif[l]=='\n')
                                                 {
                                                        p++;
                                                        n=0;
                                                        l++;
                                                        continue;
                                                 }
                                                 words1[p][n]=d1[m1[i].ads+co].dif[l];
                                                 n++;
                                                 l++;
                                          }
                                          //printf("%s",d1[m1[i].ads].dif);
                                          for(j=0;j<p;j++)
                                          {
                                                 int flag=0;
                                                 //printf("%s\n",words1[j] );
                                                 for(f=0;f<m1[i].pc;f++)
                                                 {
                                                        if(strcmp(words1[j],p1[m1[i].p_ads+f].name)==0)
                                                        {
                                                               fprintf(fp1, "%s ",words[f+1] );
                                                               flag=1;
                                                               break;
                                                        }
                                                 }
                                                 if(flag==0)
                                                 {
                                                        fprintf(fp1, "%s ",words1[j] );
                                                 }
                                          }
                                          fprintf(fp1, "\n" );
                                          co++;
                                   }
                                   flag1=1;
                                   break;
                            }

                     }
              }
              if(flag1==0)
              {
                     fprintf(fp1, "%s",s );
              }

       }
       fclose(fp);
       fclose(fp1);
       printf("\nExpandedMacro.txt\n" );
       system("cat macro2.txt");
       return 0;
}
