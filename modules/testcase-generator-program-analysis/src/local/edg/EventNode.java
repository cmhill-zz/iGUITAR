/*	
 *  Copyright (c) 2011. The GREYBOX group at the University of Freiburg, Chair of Software Engineering.
 *  Names of owners of this group may be obtained by sending an e-mail to arlt@informatik.uni-freiburg.de
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *	conditions:
 * 
 *	The above copyright notice and this permission notice shall be included in all copies or substantial 
 *	portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *	LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *	EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *	THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package local.edg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.umd.cs.guitar.model.data.EventType;

public class EventNode {

	private static final Set<Field> EMPTY_SET = Collections.emptySet();
	private EventType event;
	private Set<Field> reads;
	private Set<Field> writes;
	private boolean empty;
	private boolean sharable;

	public EventType getEvent() {
		return event;
	}

	public Set<Field> getReads() {
		return reads;
	}

	public Set<Field> getWrites() {
		return writes;
	}

	public boolean isEmpty() {
		return empty;
	}

	public EventNode(EventType event, Set<Field> reads, Set<Field> writes) {
		this(event, reads, writes, false);
	}

	public EventNode(EventType event) {
		this(event, EMPTY_SET, EMPTY_SET, true);
	}

	private EventNode(EventType event, Set<Field> reads, Set<Field> writes,
			boolean empty) {
		this.event = event;
		this.reads = reads;
		this.writes = writes;
		this.empty = empty;
		this.sharable = true;
	}

	public boolean hasDependencies() {
		return !reads.isEmpty();
	}

	public boolean hasRead() {
		return !reads.isEmpty();
	}

	public boolean hasWrite() {
		return !writes.isEmpty();
	}

	public boolean writesTo(Set<Field> _reads) {
		Iterator<Field> i = this.writes.iterator();
		while (i.hasNext()) {
			Field f = i.next();
			if (_reads.contains(f)) {
				return true;
			}
		}
		return false;
	}

	public boolean writesTo(EventNode e) {
		return this.writesTo(e.reads);
	}

	public static int dependency(Set<Field> _reads, Set<Field> _writes) {
		int depCount = 0;
		Iterator<Field> i = _reads.iterator();
		while (i.hasNext()) {
			Field f = i.next();
			if (_writes.contains(f)) {
				depCount++;
			}
		}
		return depCount;
	}

	public static int dependency(EventNode reader, EventNode writer) {
		return dependency(reader.reads, writer.writes);
	}

	public int dependencyToWriter(EventNode writer) {
		return dependency(this.reads, writer.writes);
	}

	public int dependencyToReader(EventNode reader) {
		return dependency(reader.reads, this.writes);
	}

	public boolean isSharable() {
		return sharable;
	}

	public void setSharable(boolean sharable) {
		this.sharable = sharable;
	}

	/**
	 * This function generates essentially creates the
	 * EDG(event-dependency-graph). The edges in the EDG are determined by the
	 * fields that are read and written by the event. A event e1 depends on
	 * another event e2, if the set of all reads of e1 has a non-empty
	 * intersection with the set of all writes of e2. To allow a more
	 * comfortable handling of the events as nodes, they are wrapped into
	 * RWEntry's, which contain all the reads/writes of a event.
	 * 
	 * 
	 * @param es
	 *            All events to take into account.
	 * @param db
	 *            Class DB to resolve dependencies.
	 * @return Collection of RWEntry's.
	 */
	public static List<EventNode> createEDG(List<EventType> es,
			Map<String, Class> db) {
		ArrayList<EventNode> dep = new ArrayList<EventNode>(es.size());
		for (EventType e : es) {
			createNode(e, dep, db);
		}
		return dep;
	}

	/**
	 * Creates the EDGNode for a single event.
	 * 
	 * @param e
	 *            Event that should be wrapped into a EDGNode.
	 * @param nodes
	 *            Place where the EDGNode is stored.
	 * @param db
	 *            Class DB to extract reads/writes.
	 * @return true if a entry was created, false otherwise.
	 */
	private static void createNode(EventType e, List<EventNode> nodes,
			Map<String, Class> db) {
		List<String> listeners = e.getListeners();
		if (listeners != null) {
			boolean isEmpty = true;
			boolean sharable = true;
			HashSet<Field> reads = new HashSet<Field>();
			HashSet<Field> writes = new HashSet<Field>();
			for (String s : listeners) {
				Class c = db.get(s.replace('.', '/'));
				if (c != null && c.isDeclared()) {
					Method m = c.getMethod("actionPerformed");
					if (m != null) {
						MethodDescriptor md = m
								.getDescriptor("(Ljava/awt/event/ActionEvent;)V");
						if (md != null && !md.isEmpty()) {
							rwHelper(md, reads, writes,
									new HashSet<MethodDescriptor>());
							isEmpty = false;
							sharable &= md.isSharable();
						}
					}
				}
			}
			EventNode newnode;
			if (isEmpty) {
				newnode = new EventNode(e);

			} else {
				newnode = new EventNode(e, emptyfy(reads), emptyfy(writes));
			}
			newnode.setSharable(sharable);
			nodes.add(newnode);
		} else {
			nodes.add(new EventNode(e));
		}
	}

	/**
	 * replaces empty sets with a more efficient representation
	 * 
	 * @param set
	 * @return
	 */
	private static Set<Field> emptyfy(Set<Field> set) {
		return (set.isEmpty()) ? EMPTY_SET : set;
	}

	/**
	 * Function that recursively traverses the Class DB, taking invokes of other
	 * methods into account, to accumulate the reads and writes.
	 * 
	 * @param method
	 *            Method, to which the reads and writes should be collected.
	 * @param reads
	 *            In/Out parameter containing reads.
	 * @param writes
	 *            In/Out parameter containing writes.
	 * @param visited
	 *            Already visited methods.
	 */
	private static void rwHelper(MethodDescriptor method, Set<Field> reads,
			Set<Field> writes, Set<MethodDescriptor> visited) {
		reads.addAll(method.getRead());// maybe just add read field if they are
										// not already marked as written
		writes.addAll(method.getWrite());
		visited.add(method);// Avoid cycles and already visited methods
		for (MethodDescriptor m : method.getInvokes()) {
			if (!visited.contains(m)) {
				rwHelper(m, reads, writes, visited);
			}
		}
	}

}
