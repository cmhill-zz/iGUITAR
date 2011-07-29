package edu.umd.cs.guitar.testcase.plugin;

import java.util.HashMap;

import local.edg.EventNode;

public class SequenceRemove extends SequenceGenerator {

	private HashMap<EventNode, Boolean> available;

	public SequenceRemove(int evaluationMethod) {
		super(evaluationMethod);
	}

	@Override
	protected void setUpSeq() {
		//reset the availability of nodes for every sequence
		available = new HashMap<EventNode, Boolean>();
	}

	@Override
	protected boolean available(EventNode n) {
		Boolean b = available.get(n);
		return (b == null) ? true : b;
	}

	@Override
	protected void visit(EventNode n) {
		available.put(n, false);
	}
}
