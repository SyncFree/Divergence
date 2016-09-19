## WP3 - Create plots from the logsimulator divergence logs.

load.moodle_log <- function (fn){
  # Load the logs to a table
  tbl <- read.table(sep=" ", file = fn)
  
  #Get the number of DC
  n_repl <- (ncol(tbl) - 2) / 9
  
  # Get the names of each DC
  col_names <- tbl[1, (0:(n_repl-1))*9 + 2]
  
  # Get the column of count
  dat <- tbl[, (0:(n_repl-1))*9 + 10]
  
  # Cumsum
  cum_dat <- cumsum(dat)
  
  #Diff
  diff_dat <- dat
  
  return (list(cum=cum_dat, diff=diff_dat))
}

if (interactive() == FALSE){
  args <- commandArgs(trailingOnly=TRUE)
  tbl <- load.moodle_log(args[1])
  
  matplot(tbl$cum, type="l", lty=1, xlab="Received Ops", ylab="Cummulative Count")
  matplot(tbl$diff, type="l", lty=1, xlab="Received Ops", ylab="Count")
}

