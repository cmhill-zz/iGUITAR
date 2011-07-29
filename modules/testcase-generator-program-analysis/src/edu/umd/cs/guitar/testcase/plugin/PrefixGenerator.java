package edu.umd.cs.guitar.testcase.plugin;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import local.edg.EventNode;
import local.edg.Field;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.testcase.plugin.ProgramAnalysis.Output;

public class PrefixGenerator implements TCGeneratorMethod {

	private List<EventNode> eventNodes;
	private int countTC;
	private int maxTC;
	private Output out;

	@Override
	public void generate(List<EventNode> edg, List<EventType> initialEvents,
			Hashtable<EventType, Vector<EventType>> preds,
			Hashtable<EventType, Vector<EventType>> succs, Output out,
			int maxTC, int lengthTC) {
		if (lengthTC <= 0)
			return;// to short to genereta a prefix

		this.eventNodes = edg;
		this.countTC = 0;
		this.maxTC = maxTC;
		this.out = out;

		for (EventNode n : edg) {
			try {
				generatePrefix(n, lengthTC);
			} catch (MaxTCReachedException e) {
				break;
			}
		}

	}

	private void generatePrefix(EventNode n, int prefixLength)
			throws MaxTCReachedException {
		LinkedList<EventType> seq = new LinkedList<EventType>();
		seq.push(n.getEvent());
		prefixHelper(seq, n.getReads(), prefixLength);

	}

	private void prefixHelper(LinkedList<EventType> seq, Set<Field> reads,
			int prefixLength) throws MaxTCReachedException {
		int newLength = prefixLength - 1;
		for (EventNode n : eventNodes) {
			if (!TCUtil.isTerminalEvent(n.getEvent()) && n.writesTo(reads)) {
				seq.push(n.getEvent());
				if (newLength <= 0) {
					// write the testcase
					if (out.createSequenceTC(seq)) {
						// if testcase was created test if maxTC is reached
						countTC++;
						if (!(maxTC <= 0) && countTC >= maxTC)
							throw new MaxTCReachedException();
					}
				} else {
					// extend the sequence
					// maintain unfullfilled reads
					HashSet<Field> newReads = new HashSet<Field>(reads);
					newReads.removeAll(n.getWrites());
					newReads.addAll(n.getReads());
					prefixHelper(seq, newReads, newLength);
				}
				seq.pop();
			}
		}
	}

}
