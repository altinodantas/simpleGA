# Key means label...
set key inside bottom right
set xlabel 'Gera��es'
set ylabel 'Aptid�o'
set title 'Converg�ncia'
plot  "data.txt" using 0:1 title 'AVG' with lines, "data.txt" using 0:2 title 'Best' with lines