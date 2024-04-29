package edu.duke.ece651.team7.attendanceServer.Common.Format;

//import jakarta.xml.bind.annotation.XmlElementWrapper;
//import jakarta.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "")
public class XMLNode extends FormatNode {

    // private List<Attendance> attendances;
    private Object object;

    public XMLNode() {
    }

    public XMLNode(Object object) {
        this.object = object;
    }

    // @XmlElementWrapper(name = "element")
    public Object getObject() {
        return object;
    }

    @Override
    public String  accept(FormatVisitor v) {
        return v.visit(this);
    }

}
