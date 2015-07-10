package peersim.core.dcdatastore.observers.divergence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Node;
import peersim.core.dcdatastore.DCCommonState;
import peersim.core.dcdatastore.GeoReplicatedDatastoreNetwork;

public class DivergenceObserver implements Control {

	private static final String PAR_FILENAME = "filename";
	private static final String PAR_PROTOCOL = "protocol";
	
	private PrintStream out;
	private short protocolID;
	
	public DivergenceObserver(String prefix) {
		try {
			this.out = new PrintStream(new FileOutputStream(new File(Configuration.getString(prefix+"."+PAR_FILENAME))));
		} catch (FileNotFoundException e) {
			this.out = System.out;
		}
		this.protocolID = (short) Configuration.lookupPid(Configuration.getString(prefix+"."+PAR_PROTOCOL));
	}

	public boolean execute() {
		//System.err.println(DCCommonState.getTime() + ": Triggering " + this.getClass().getCanonicalName());
		String line = DCCommonState.getTime() + " ";
		for(int i = 1 ; i <= GeoReplicatedDatastoreNetwork.numberDCs(); i++) {
			Node[] servers = GeoReplicatedDatastoreNetwork.getServersForDC(i);
			for(int j = 0; j < servers.length; j++) {
				DivergenceObservable prot = (DivergenceObservable) servers[j].getProtocol(this.protocolID);
				double min = prot.getMinDivergence();
				double max = prot.getMaxDivergence();
				double average = prot.getAverageDivergence();
				int measures = prot.getDivergenceMeasuresCount();
				line += "DC" + i + ":" + j + " min " + min + " max " + max + " avg " + average + " count " + measures + " ";
				prot.resetDivergenceMetrics();
			}
		}
		this.out.println(line);
		return false;
	}
}
