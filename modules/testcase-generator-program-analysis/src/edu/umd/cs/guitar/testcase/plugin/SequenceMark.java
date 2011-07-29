package edu.umd.cs.guitar.testcase.plugin;

import java.util.Comparator;
import java.util.HashMap;

import local.edg.EventNode;

public class SequenceMark extends SequenceGenerator {

	private HashMap<EventNode, Integer> visits;
	
	private class VisitsComparator implements Comparator<EventNode> {
		private Comparator<EventNode> _comp;

		public VisitsComparator(Comparator<EventNode> c) {
			_comp = c;
		}

		@Override
		public int compare(EventNode e1, EventNode e2) {
			// the less visits the more it should be used
			if (getVisits(e1) < getVisits(e2)) {
				return 1;
			}
			if (getVisits(e1) > getVisits(e2)) {
				return -1;
			}
			return _comp.compare(e1, e2);
		}

	}

	public SequenceMark(int evaluationMethod) {
		super(evaluationMethod);
		comp = new VisitsComparator(comp);

	}



	@Override
	protected void setUp() {
		visits = new HashMap<EventNode, Integer>();
	}

	@Override
	protected void visit(EventNode n) {
		Integer i = visits.get(n);
		if (i == null)
			i = 0;
		visits.put(n, i + 1);
	}

	private int getVisits(EventNode n) {
		Integer i = visits.get(n);
		return (i == null) ? 0 : i;
	}

	@Override
	protected boolean available(EventNode n) {
		return true;
	}

}
