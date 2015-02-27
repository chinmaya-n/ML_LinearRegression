## File Created from LinearRegressionWithRegularization.java - ln Lambda vs Erms values ##

x <- c(-50, -45, -40, -35, -30, -25, -20, -15, -10, -5, 0)
y1 <- c(0.05096906067803283, 0.05096906251295612, 0.050969334838985796, 0.051009735485676326, 0.056687373867272094, 0.3065337608658277, 3.6827138330924147, 44.860370916774585, 546.510847403624, 6657.865069193874, 81109.40099427225)
y2 <- c(5.4798525182723434E-5, 5.647943872355921E-5, 1.7592727480866496E-4, 0.002037393137329418, 0.024811614466722094, 0.30226661146581474, 3.6823611086240176, 44.860341962009734, 546.5108450268714, 6657.865068998778, 81109.40099425623)

plot(x, y1, type='o', pch=2, col="blue", xlab="ln Lambda", ylab="ERMS", ylim=c(0, 0.2), xlim=c(-50, 0)) 
lines(x, y2, type='o', pch=20, col="red", lty=2) 
legend("topleft", c("Test","Train"), cex=0.8, col=c("blue","red"), lty=1:2, lwd=2, bty="n")