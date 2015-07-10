package peersim.core.dcdatastore.transport;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Node;
import peersim.core.dcdatastore.ServerNode;

public class DCTransportWithPartitionsLayer extends DCTransportLayer implements Cloneable {

	public static final String PAR_PART_CONFIG_FILE = "part-config";
	public static final String PAR_RELIABLE_CHANNELS = "reliable-channels";

	private HashMap<String,ArrayList<Interval>> partitions;
	private boolean reliableChannels;

	public DCTransportWithPartitionsLayer(String prefix) {
		super(prefix);
		this.reliableChannels = Configuration.contains(prefix + "." + PAR_RELIABLE_CHANNELS);
		this.partitions = new HashMap<String, ArrayList<Interval>>();
		Scanner input = null;
		try {
			input = new Scanner(new File(Configuration.getString(prefix + "." + PAR_PART_CONFIG_FILE)));
			while(input.hasNext()) {
				String[] tokens = input.nextLine().split(" ");
				String key = tokens[0] + ":" + tokens[1];
				long b = Long.parseLong(tokens[2]);
				long e = Long.parseLong(tokens[3]);
				Interval i = new Interval(b, e);
				if(!partitions.containsKey(key))
					partitions.put(key, new ArrayList<DCTransportWithPartitionsLayer.Interval>());
				partitions.get(key).add(i);
			}	
		} catch (FileNotFoundException e) {
			System.err.println("DCTransportWithPartitionsLayer: cannot open configuration file: '" + Configuration.getString(PAR_PART_CONFIG_FILE) + "' -- obtained from parameter: '"+ PAR_PART_CONFIG_FILE + "'");
			e.printStackTrace();
			System.exit(101);
		} catch (Exception e) {
			System.err.println("DCTransportWithPartitionsLayer: parse error on config file.");
			e.printStackTrace();
			System.exit(102);
		} finally {
			if(input != null) 
				input.close();
		}
	}

	public Object clone() {
		return this;
	}

	public void send(Node src, Node dest, Object msg, int pid) {
		boolean found = false;
		Interval interval = null;

		if(src instanceof ServerNode && dest instanceof ServerNode) {
			short dc_src = ((ServerNode) src).getDC();
			short dc_dest = ((ServerNode) dest).getDC();
			String map_identifier = null;
			if(dc_src != dc_dest) {
				map_identifier = (dc_src < dc_dest) ? dc_src + ":" + dc_dest : dc_dest + ":" + dc_src;
				ArrayList<Interval> ints = partitions.get(map_identifier);
				for(int i = 0; !found && i < ints.size(); i++) {
					found = ints.get(i).isContained(CommonState.getTime());
					if(found) interval = ints.get(i);
				}
			}
		}


		if(!found)
			super.send(src, dest, msg, pid);
		else if(this.reliableChannels)
			super.send(src, dest, msg, pid, interval.end - CommonState.getTime());
	}

	protected class Interval {

		private long start;
		private long end;

		protected Interval(long start, long end) {
			this.start = start;
			this.end = end;
		}

		protected boolean isContained(long now) {
			return now >= start && now <= end;
		}

	}

}
