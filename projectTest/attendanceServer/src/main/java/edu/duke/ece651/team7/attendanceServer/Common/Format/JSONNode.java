package edu.duke.ece651.team7.attendanceServer.Common.Format;

public class JSONNode extends FormatNode {

    private Object object;

    public JSONNode(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String accept(FormatVisitor v) {
        return v.visit(this);
    }

}
