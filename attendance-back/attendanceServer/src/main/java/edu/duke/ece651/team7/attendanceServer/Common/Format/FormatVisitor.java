package edu.duke.ece651.team7.attendanceServer.Common.Format;

public interface FormatVisitor {

    public String visit(JSONNode n);

    public String visit(XMLNode n);

}
