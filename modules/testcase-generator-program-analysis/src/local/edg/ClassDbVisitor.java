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

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassDbVisitor implements ClassVisitor, FieldVisitor,
		MethodVisitor {
	private static final String AP_NAME = "actionPerformed";
	private static final String AP_DESC = "(Ljava/awt/event/ActionEvent;)V";

	private Map<String, Class> classdb = new HashMap<String, Class>();
	private Class currentClass;
	private MethodDescriptor currentMethod;
	private boolean isActionPerformedMethod;

	public Map<String, Class> getClassDb() {
		return classdb;
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		return null;
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int parameter,
			String desc, boolean visible) {
		return null;
	}

	@Override
	public void visitCode() {
	}

	@Override
	public void visitFrame(int type, int nLocal, Object[] local, int nStack,
			Object[] stack) {
	}

	@Override
	public void visitInsn(int opcode) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		currentMethod.setEmpty(false);
		if(isActionPerformedMethod && opcode == Opcodes.ALOAD && var == 1)
			currentMethod.setSharable(false);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
		currentMethod.setEmpty(false);
		Class c;
		Field f;
		switch (opcode) {
		case Opcodes.GETFIELD:
		case Opcodes.GETSTATIC:
			c = getClass(owner);
			f = getField(c, name);
			f.setDesc(desc);
			// read after write is not a real read
			// if (!currentMethod.hasWrite(f)){
			currentMethod.addRead(f);
			// }
			break;
		case Opcodes.PUTFIELD:
		case Opcodes.PUTSTATIC:
			c = getClass(owner);
			f = getField(c, name);
			f.setDesc(desc);
			currentMethod.addWrite(f);
			break;
		}
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		currentMethod.setEmpty(false);
		switch (opcode) {
		case Opcodes.INVOKEVIRTUAL:
		case Opcodes.INVOKESTATIC:
		case Opcodes.INVOKESPECIAL:
			Class c = getClass(owner);
			MethodDescriptor m = getMethod(c, name, desc);
			currentMethod.addInvokes(m);
			break;
		}
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitLabel(Label label) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitLdcInsn(Object cst) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt,
			Label[] labels) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitMultiANewArrayInsn(String desc, int dims) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitTryCatchBlock(Label start, Label end, Label handler,
			String type) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitLocalVariable(String name, String desc, String signature,
			Label start, Label end, int index) {
		currentMethod.setEmpty(false);
	}

	@Override
	public void visitLineNumber(int line, Label start) {
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
	}

	/**
	 * 
	 * @param name
	 *            Class name.
	 * @return retrieves or creates the Class with the given name in the DB and
	 *         returns it.
	 */
	private Class getClass(String name) {
		Class c = classdb.get(name);
		if (c == null) {
			c = new Class();
			c.setName(name);
			classdb.put(name, c);
		}
		return c;
	}

	/**
	 * 
	 * @param c
	 *            Class that contains the method.
	 * @param name
	 *            Name of the method
	 * @param desc
	 *            Descriptor identifier of the method.
	 * @return retrieves or creates the MethodDescriptor with the given name and
	 *         descriptor identifier in the Class and returns it. Never null.
	 */
	private MethodDescriptor getMethod(Class c, String name, String desc) {
		Method m = c.getMethod(name);
		MethodDescriptor md = null;
		if (m == null) {
			m = new Method();
			m.setName(name);
			c.addMethod(m);
			md = new MethodDescriptor();
			md.setDesc(desc);
			m.addDescriptor(md);
		} else {
			md = m.getDescriptor(desc);
			if (md == null) {
				md = new MethodDescriptor();
				md.setDesc(desc);
				m.addDescriptor(md);
			}
		}
		return md;
	}

	/**
	 * 
	 * @param c
	 *            Class that contains the Field.
	 * @param fieldName
	 *            Name of the Field.
	 * @return retrieves or creates the Field with the given name in the Class
	 *         and returns it. Never null.
	 */
	private Field getField(Class c, String fieldName) {
		Field f = c.getField(fieldName);
		if (f == null) {
			f = new Field();
			f.setName(fieldName);
			c.addField(f);
		}
		return f;
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		currentClass = getClass(name);
		currentClass.setSuperName(superName);
		currentClass.setInterfaces(interfaces);
		currentClass.setDeclared(true);
	}

	@Override
	public void visitSource(String source, String debug) {
	}

	@Override
	public void visitOuterClass(String owner, String name, String desc) {
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return null;
	}

	@Override
	public void visitAttribute(Attribute attr) {
	}

	@Override
	public void visitInnerClass(String name, String outerName,
			String innerName, int access) {
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		Field field = getField(currentClass, name);
		field.setDesc(desc);
		return this;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		currentMethod = getMethod(currentClass, name, desc);
		currentMethod.setDesc(desc);
		currentMethod.setSignature(signature);
		currentMethod.setExceptions(exceptions);
		isActionPerformedMethod = AP_NAME.equals(name) && AP_DESC.equals(desc)
				&& (access & Opcodes.ACC_PUBLIC) != 0;
		return this;
	}

	@Override
	public void visitEnd() {
	}

}
