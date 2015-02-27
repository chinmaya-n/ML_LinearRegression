#!/usr/bin/Rscript

library(mvtnorm)

sin.noise <- function(x)
  {
    m = sin(2 * pi * x) + x * (x + 1) / 4;
    rmvnorm(n = 1, mean = m, sigma = 0.005 * diag(1))[1]
  }

x <- seq(0, 1, by = 1/30.0)[2:31]
y = sapply(x, sin.noise)

all = cbind(x,y)
write.table(all, file = "dataset.dat",
            row.names = FALSE, col.names = FALSE)

strain = seq(1, 30, by = 3)
train = cbind(x[strain], y[strain])
write.table(train, file = "train.dat",
            row.names = FALSE, col.names = FALSE)

stest = seq(2, 30, by = 3)
test = cbind(x[stest], y[stest])
write.table(test, file = "test.dat",
            row.names = FALSE, col.names = FALSE)

svalid = seq(3, 30, by = 3)
valid = cbind(x[svalid], y[svalid])
write.table(valid, file = "valid.dat",
            row.names = FALSE, col.names = FALSE)

