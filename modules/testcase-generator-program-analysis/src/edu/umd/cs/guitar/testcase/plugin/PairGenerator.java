package edu.umd.cs.guitar.testcase.plugin;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import local.edg.EventNode;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.testcase.plugin.ProgramAnalysis.Output;

public class PairGenerator implements TCGeneratorMethod {
	@Override
	public void generate(List<EventNode> edg, List<EventType> initialEvents,
			Hashtable<EventType, Vector<EventType>> preds,
			Hashtable<EventType, Vector<EventType>> succs, Output out,
			int maxTC, int lengthTC) {
		int tcCount = 0;
		for (EventNode n1 : edg) {

			boolean usedN1 = false;
			// does the event influence other events
			if (!n1.isEmpty() && !TCUtil.isTerminalEvent(n1.getEvent())
					&& n1.hasWrite()) {
				// create all pairs
				for (EventNode n2 : edg) {
					if (n1 != n2 && n1.writesTo(n2)) {
						if (out.createSequenceTC(makePair(n1.getEvent(),
								n2.getEvent()))) {
							usedN1 = true;
							if (!(maxTC <= 0) && ++tcCount >= maxTC)
								break;// break inner loop
						}
					}

				}
			}
			if (!usedN1) {
				// create single element TC to obtain a better comparable
				// coverage
				if (out.createSequenceTC(makeSingle(n1.getEvent())))
					tcCount++;
			}
			if (!(maxTC <= 0) && tcCount >= maxTC)
				break;// break outer loop

		}
	}

	private static LinkedList<EventType> makePair(EventType e1, EventType e2) {
		LinkedList<EventType> l = new LinkedList<EventType>();
		l.add(e1);
		l.add(e2);
		return l;
	}

	private static LinkedList<EventType> makeSingle(EventType e) {
		LinkedList<EventType> l = new LinkedList<EventType>();
		l.add(e);
		return l;
	}

}
