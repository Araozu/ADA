data2analyze <- read.csv('dataFinal.csv', TRUE)
library(ggplot2)

ggplot() +
    geom_point(aes(x = data2analyze$number_of_elements, y = data2analyze$duration), colour = 'red') +
#geom_line(aes(x = data2analyze$iter, y = data2analyze$time_2),colour = 'green') +
    ggtitle('Iteration vs Time') +
    xlab('Iteration') +
    ylab('Time (ms)') +
    theme_bw() +
    theme(plot.title = element_text(hjust = 0.5), panel.border = element_blank(), panel.grid.major = element_blank(),
    panel.grid.minor = element_blank(), axis.line = element_line(colour = "black"))
#mean(data2analyze$iter_converged)

