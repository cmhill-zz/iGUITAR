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
