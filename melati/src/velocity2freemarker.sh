#!/bin/bash

FILE_IN=$1
FILE_TMP=$1.tmp
cp $FILE_IN $FILE_TMP
FILE_OUT=$2 

perl -i -pe 's/(\$\{[a-zA-Z]+)(\.)([A-Z])([a-zA-Z]+)(\})/$1$2\l$3$4$5/g' $FILE_TMP 
perl -i -pe 's/(\$\{[a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\})/$1$2\l$3$4$5\l$6$7$8/g' $FILE_TMP 
perl -i -pe 's/(\$\{[a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\})/$1$2\l$3$4$5\l$6$7$8\l$9$10$11/g' $FILE_TMP 
perl -i -pe 's/(\$\{[a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\})/$1$2\l$3$4$5\l$6$7$8\l$9$10$11\l$12$13$14/g' $FILE_TMP 
perl -i -pe 's/(\$\{[a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\})/$1$2\l$3$4$5\l$6$7$8\l$9$10$11\l$12$13$14\l$15$16$17/g' $FILE_TMP 


perl -i -pe 's/([^\\]|^)(\$[a-zA-Z]+)(\.)([A-Z])([a-zA-Z]+)([^a-zA-Z0-9\.]|$)/$1$2$3\l$4\l$5$6/g' $FILE_TMP 
perl -i -pe 's/([^\\]|^)(\$[a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)([^a-zA-Z0-9\.]|$)/$1$2$3\l$4$5$6\l$7$8$9/g' $FILE_TMP 
perl -i -pe 's/([^\\]|^)(\$[a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)([^a-zA-Z0-9\.]|$)/$1$2$3\l$4$5$6\l$7$8$9\l$10$11$12/g' $FILE_TMP 
perl -i -pe 's/([^\\]|^)(\$[a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)(\.)([a-zA-Z])([a-zA-Z]+)([^a-zA-Z0-9\.]|$)/$1$2$3\l$4$5$6\l$7$8$9\l$10$11$12\l$13$14$15/g' $FILE_TMP


./vm2fm.jar $FILE_TMP $FILE_OUT 

perl -i -pe 's/\"\<\#call ([abcdef0-9ABCDEF][abcdef0-9ABCDEF][abcdef0-9ABCDEF][abcdef0-9ABCDEF][abcdef0-9ABCDEF][abcdef0-9ABCDEF])>\"/"#$1"/g' $FILE_OUT
