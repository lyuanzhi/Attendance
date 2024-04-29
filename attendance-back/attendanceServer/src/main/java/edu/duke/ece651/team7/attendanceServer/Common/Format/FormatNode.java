package edu.duke.ece651.team7.attendanceServer.Common.Format;

public abstract class FormatNode {
    public abstract String accept(FormatVisitor v);
}
