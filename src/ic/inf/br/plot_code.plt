# Key means label...
set key inside bottom right
set xlabel 'Gerações'
set ylabel 'Aptidão'
set title 'Convergência'
plot  "data.txt" using 0:1 title 'AVG' with lines, "data.txt" using 0:2 title 'Best' with lines