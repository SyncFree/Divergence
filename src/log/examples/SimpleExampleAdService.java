package log.examples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

import log.formats.AdServiceOperation;
import log.formats.Operation;
import log.formats.model.AD_OP;
import log.readers.SimpleLog;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Queues;

public class SimpleExampleAdService {

	private static final int MINUTE_IN_SECONDS = 60;
	private static final int ONE_SECOND = 1;

	public static void main(String[] args) throws ParseException, IOException, InstantiationException,
	IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
	SecurityException, ClassNotFoundException {

		File keysPerOpFreqAllFile = new File(args[1] + "_" + "keys_per_op_freq_all");
		File keysPerOpFreqAcumAllFile = new File(args[1] + "_" + "keys_per_op_freq_acum_all");
		File opsOverTimeAllFile = new File(args[1] + "_" + "ops_over_time_all");
		File opsPerKeyAllFile = new File(args[1] + "_" + "ops_per_key_all");
		File opsPerKeyAllOrderedFile = new File(args[1] + "_" + "ops_per_key_all_ordered");

		File keysPerOpFreqReadsFile = new File(args[1] + "_" + "keys_per_op_freq_reads");
		File keysPerOpFreqAcumReadsFile = new File(args[1] + "_" + "keys_per_op_freq_acum_reads");
		File opsOverTimeReadsFile = new File(args[1] + "_" + "ops_over_time_reads");
		File opsPerKeyReadsFile = new File(args[1] + "_" + "ops_per_key_reads");
		File opsPerKeyReadsOrderedFile = new File(args[1] + "_" + "ops_per_key_reads_ordered");

		File keysPerOpFreqWritesFile = new File(args[1] + "_" + "keys_per_op_freq_writes");
		File keysPerOpFreqAcumWritesFile = new File(args[1] + "_" + "keys_per_op_freq_acum_writes");
		File opsOverTimeWritesFile = new File(args[1] + "_" + "ops_over_time_writes");
		File opsPerKeyWritesFile = new File(args[1] + "_" + "ops_per_key_writes");
		File opsPerKeyWritesOrderedFile = new File(args[1] + "_" + "ops_per_key_writes_ordered");

		SimpleLog<AD_OP> log = new SimpleLog<AD_OP>(args[0], AdServiceOperation.getFactory());
		ImmutableSet<String> all = ImmutableSet.copyOf(new String[] {});
		ImmutableSet<String> reads = ImmutableSet.copyOf(new String[] { "read" });
		ImmutableSet<String> writes = ImmutableSet.copyOf(new String[] { "write" });

		Map<String, Integer> countAllOpsPerKey = computeOpsPerKey(log, all);
		log.resetState();
		Map<Long, Integer> allOpsOverTime = computeOpsOverTime(log, all, MINUTE_IN_SECONDS);
		log.resetState();
		Map<String, Integer> countReadOpsPerKey = computeOpsPerKey(log, reads);
		log.resetState();
		Map<Long, Integer> readOpsOverTime = computeOpsOverTime(log, reads, MINUTE_IN_SECONDS);
		log.resetState();
		Map<String, Integer> countWriteOpsPerKey = computeOpsPerKey(log, writes);
		log.resetState();
		Map<Long, Integer> writeOpsOverTime = computeOpsOverTime(log, writes, MINUTE_IN_SECONDS);
		log.resetState();

		printKeysPerOpFreq(countAllOpsPerKey, all, 50, new FileOutputStream(keysPerOpFreqAllFile));
		printKeysPerOpFreqAcum(countAllOpsPerKey, all, 50, new FileOutputStream(keysPerOpFreqAcumAllFile));
		printOpsPerKey(countReadOpsPerKey, all, new FileOutputStream(opsPerKeyAllFile));
		printOpsPerKeyOrdered(countAllOpsPerKey, all, new FileOutputStream(opsPerKeyAllOrderedFile));
		printOpsOverTime(allOpsOverTime, log, all, new FileOutputStream(opsOverTimeAllFile));

		printKeysPerOpFreq(countReadOpsPerKey, reads, 50, new FileOutputStream(keysPerOpFreqReadsFile));
		printKeysPerOpFreqAcum(countReadOpsPerKey, reads, 50, new FileOutputStream(keysPerOpFreqAcumReadsFile));
		printOpsPerKey(countReadOpsPerKey, reads, new FileOutputStream(opsPerKeyReadsFile));
		printOpsPerKeyOrdered(countReadOpsPerKey, reads, new FileOutputStream(opsPerKeyReadsOrderedFile));
		printOpsOverTime(readOpsOverTime, log, reads, new FileOutputStream(opsOverTimeReadsFile));

		printKeysPerOpFreq(countWriteOpsPerKey, writes, 50, new FileOutputStream(keysPerOpFreqWritesFile));
		printKeysPerOpFreqAcum(countWriteOpsPerKey, writes, 50, new FileOutputStream(keysPerOpFreqAcumWritesFile));
		printOpsPerKey(countWriteOpsPerKey, writes, new FileOutputStream(opsPerKeyWritesFile));
		printOpsPerKeyOrdered(countWriteOpsPerKey, writes, new FileOutputStream(opsPerKeyWritesOrderedFile));
		printOpsOverTime(writeOpsOverTime, log, writes, new FileOutputStream(opsOverTimeWritesFile));

	}

	static void printKeysPerOpFreq(Map<String, Integer> countOpsPerKey, Set<String> opTypes, int aggregateInterval,
			OutputStream resultsStream) throws IOException {
		resultsStream.write(String.format("Filter ops: %s\n", opTypes).getBytes());
		resultsStream.write(String.format("# Keys %d\n", countOpsPerKey.size()).getBytes());
		PriorityQueue<Pair<String, Integer>> orderedOutput = Queues.newPriorityQueue();
		for (Entry<String, Integer> keyFreq : countOpsPerKey.entrySet()) {
			orderedOutput.add(new Pair<String, Integer>(keyFreq.getKey(), keyFreq.getValue()));
		}
		resultsStream.write(String.format("Freq #Keys\n").getBytes());
		int range = 0;
		int count = 0;
		while (orderedOutput.size() > 0) {
			Pair<String, Integer> next = orderedOutput.remove();
			while (next.second > range) {
				resultsStream.write(String.format("%d %d\n", range, count).getBytes());
				range += aggregateInterval;
				count = 0;
			}
			count += 1;
		}
		resultsStream.write(String.format("%d %d\n", range, count).getBytes());
	}

	static void printKeysPerOpFreqAcum(Map<String, Integer> countOpsPerKey, Set<String> opTypes, int aggregateInterval,
			OutputStream resultsStream) throws IOException {
		resultsStream.write(String.format("Filter ops: %s\n", opTypes).getBytes());
		resultsStream.write(String.format("# Keys %d\n", countOpsPerKey.size()).getBytes());
		PriorityQueue<Pair<String, Integer>> orderedOutput = Queues.newPriorityQueue();
		for (Entry<String, Integer> keyFreq : countOpsPerKey.entrySet()) {
			orderedOutput.add(new Pair<String, Integer>(keyFreq.getKey(), keyFreq.getValue()));
		}
		resultsStream.write(String.format("Freq #Keys\n").getBytes());
		int range = 0;
		int count = 0;
		while (orderedOutput.size() > 0) {
			Pair<String, Integer> next = orderedOutput.remove();
			while (next.second > range) {
				resultsStream.write(String.format("%d %d\n", range, count).getBytes());
				range += aggregateInterval;
			}
			count += 1;
		}
		resultsStream.write(String.format("%d %d\n", range, count).getBytes());
	}

	static void printOpsPerKey(Map<String, Integer> countOpsPerKey, Set<String> opTypes, OutputStream resultsStream)
			throws IOException {
		resultsStream.write(String.format("Filter ops: %s\n", opTypes).getBytes());
		resultsStream.write(String.format("# Keys %d\n", countOpsPerKey.size()).getBytes());
		PriorityQueue<Pair<String, Integer>> orderedOutput = Queues.newPriorityQueue();
		for (Entry<String, Integer> keyFreq : countOpsPerKey.entrySet()) {
			orderedOutput.add(new Pair<String, Integer>(keyFreq.getKey(), keyFreq.getValue()));
		}
		resultsStream.write(String.format("KEY FREQ\n").getBytes());
		while (orderedOutput.size() > 0) {
			Pair<String, Integer> next = orderedOutput.remove();
			resultsStream.write(String.format("%s %d\n", next.first, next.second).getBytes());
		}
	}

	static void printOpsPerKeyOrdered(Map<String, Integer> countOpsPerKey, Set<String> opTypes,
			OutputStream resultsStream) throws IOException {
		resultsStream.write(String.format("Filter ops: %s\n", opTypes).getBytes());
		resultsStream.write(String.format("# Keys %d\n", countOpsPerKey.size()).getBytes());
		PriorityQueue<Pair<String, Integer>> orderedOutput = Queues.newPriorityQueue();
		for (Entry<String, Integer> keyFreq : countOpsPerKey.entrySet()) {
			orderedOutput.add(new Pair<String, Integer>(keyFreq.getKey(), keyFreq.getValue(), true));
		}
		resultsStream.write(String.format("KEY FREQ\n").getBytes());
		int key = 0;
		while (orderedOutput.size() > 0) {
			Pair<String, Integer> next = orderedOutput.remove();
			resultsStream.write(String.format("%s %d\n", key++, next.second).getBytes());
		}
	}

	static Map<String, Integer> computeOpsPerKey(SimpleLog<AD_OP> log, Set<String> opTypes) {
		Map<String, Integer> countOpsPerKey = new HashMap<String, Integer>();
		while (log.hasNext()) {
			Operation<AD_OP> op = log.next();
			if (filterOp(op, opTypes)) {
				String key = op.getAttributeByName(AD_OP.KEY);
				int count = countOpsPerKey.getOrDefault(key, 0);
				countOpsPerKey.put(key, count + 1);
			}
		}
		return countOpsPerKey;
	}

	static void printOpsOverTime(Map<Long, Integer> opsOverTime, SimpleLog<AD_OP> log, Set<String> opTypes,
			OutputStream resultsStream) throws IOException {
		// int totalOps = 0;
		long startTime = log.getStartTime();

		resultsStream.write(String.format("Start time in seconds %d\n", startTime / 1000).getBytes());
		resultsStream.write(String.format("TIME VALUE\n").getBytes());
		for (Entry<Long, Integer> op : opsOverTime.entrySet()) {
			// totalOps += op.getValue();
			resultsStream.write((String.format("%d %d\n", (op.getKey() - startTime) / 1000, op.getValue())).getBytes());
		}

		// resultsStream.write((String.format("Total ops: %d",
		// totalOps)).getBytes());
	}

	private static boolean filterOp(Operation<AD_OP> op, Set<String> opTypes) {
		return opTypes.isEmpty() || opTypes.contains(op.getAttributeByName(AD_OP.OPERATION));
	}

	static Map<Long, Integer> computeOpsOverTime(SimpleLog<AD_OP> log, Set<String> opTypes, int timeIntervalInSeconds) {
		Map<Long, Integer> opsPerPeriod = new TreeMap<Long, Integer>();

		long startTime = log.getStartTime();
		long lastTs = startTime;
		long timeIntervalInMilli = timeIntervalInSeconds * 1000;

		while (log.hasNext()) {
			Operation<AD_OP> op = log.next();
			if (filterOp(op, opTypes)) {
				long millis = op.getTimestampInMillis();
				int count = opsPerPeriod.getOrDefault(lastTs, 0);
				while (millis - lastTs >= timeIntervalInMilli) {
					lastTs += timeIntervalInMilli;
					count = opsPerPeriod.getOrDefault(lastTs, 0);
				}
				opsPerPeriod.put(lastTs, count + 1);
			}
		}
		return opsPerPeriod;
	}
}
