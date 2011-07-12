package edu.umd.cs.guitar.guitestrunner;

/**
 * 
 * @author Scott McMaster, Si Huang
 * 
 */
public class WindowName {
    private String name;

    private String number;

    public WindowName(String rawName) {
        int lastDelim = rawName.lastIndexOf('_');
        name = rawName.substring(0, lastDelim);
        number = rawName.substring(lastDelim + 1);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WindowName) {
            WindowName another = (WindowName) obj;
            boolean result = (name == null && another.name == null) || (name != null && name.equals(another.name));
            result = result && ((number == null && another.number == null) || (number != null && number.equals(another.number)));
            return result;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int nameHash = (name == null ? 0 : name.hashCode());
        int numberHash = (number == null ? 0 : number.hashCode());
        return nameHash + numberHash;
    }

    @Override
    public String toString() {
        return name + '_' + number;
    }
}
