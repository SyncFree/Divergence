#!/bin/bash
ts=$(date +%s)


f_sim_pre(){
	if [ $# -lt 3 ]; then 
		f_sim_help
		exit 1
	fi

	# lets move to a nicer folder
	## The space is important so that we only take the proper one.
	cm=$(grep "simulation.clientmanager " $2 | awk -F"." '{print $NF}')
	cf=$(grep "simulation.clientopsfile " $2 | awk -F"/" '{print $NF}')
	rep=$(grep "server.protocol.replication " $2 | awk -F"." '{print $NF}')
	dc=$(grep "DC " $2 | awk '{print $NF}' | head -n 1)
	cli=$(grep "CLIENTS " $2 | awk '{print $NF}' | head -n 1)
	ser=$(grep "SERVERS " $2 | awk '{print $NF}' | head -n 1)
	
	wd="${cm}_${cf}_${rep}_${dc}_${ser}_${cli}_${ts}"
	# Not robust. names with / or " " might fuck it up
	mkdir "$wd"
	cd "$wd"	
}

f_sim(){

	java -jar "$1" "$2"  2>> "$3" 1>> "$3"
}

f_sim_post(){
	cd ../
}

f_sim_help() {
	echo -e "\t $0 sim <jar> <conf> <output>"
}

f_plot(){
	Rscript "$1" "$2"
}

f_plot_help(){
	echo -e "\t $0 plot <scripts.r> <divergence file>"
}

f_help(){
	echo "Usage: $0 { sim | plot | help }"
	f_sim_help
	f_plot_help
}

case "$1" in
	"sim")
		shift
		f_sim_pre $*
		f_sim $*
		f_sim_post

	;;
	"plot")
		shift
		if [ $# -lt 2 ]; then 
			f_plot_help
			exit 2
		fi

		f_plot $*
	;;
	"both")
		if [ $# -lt 5 ]; then 
			f_plot_help
			f_sim_help
			exit 3
		fi
		shift
		f_sim_pre $*
		f_sim $*
		shift
		shift
		shift
		f_plot $*
		f_sim_post		
	;;
	*)
		f_help
esac
