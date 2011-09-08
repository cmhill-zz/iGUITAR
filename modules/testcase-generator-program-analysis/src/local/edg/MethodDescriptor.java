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

import java.util.HashSet;
import java.util.Set;

public class MethodDescriptor {

	private Method method;
	private String desc;
	private String signature;
	private String[] exceptions;
	private boolean empty = true;
	private boolean sharable = true;

	private Set<MethodDescriptor> invokes = new HashSet<MethodDescriptor>();
	private Set<MethodDescriptor> invokedBy = new HashSet<MethodDescriptor>();
	private Set<Field> read = new HashSet<Field>();
	private Set<Field> write = new HashSet<Field>();

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String[] getExceptions() {
		return exceptions;
	}

	public void setExceptions(String[] exception) {
		this.exceptions = exception;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public boolean isSharable() {
		return sharable;
	}

	public void setSharable(boolean sharable) {
		this.sharable = sharable;
	}

	public void addInvokes(MethodDescriptor m){
		invokes.add(m);
		m.invokedBy.add(this);
	}
	
	public void addRead(Field f){
		read.add(f);
		f.readBy(this);
	}
	
	public void addWrite(Field f){
		write.add(f);
		f.writeBy(this);
	}
	
	public Set<MethodDescriptor> getInvokes() {
		return invokes;
	}

	public Set<MethodDescriptor> getInvokedBy() {
		return invokedBy;
	}

	public Set<Field> getRead() {
		return read;
	}

	public Set<Field> getWrite() {
		return write;
	}

	@Override
	public String toString(){
		return method + desc;
	}

	public boolean hasWrite(Field f) {
		return write.contains(f);
	}

	
}
