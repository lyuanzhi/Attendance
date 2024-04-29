package edu.duke.ece651.team7.attendanceServer.Common.Format;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

import edu.duke.ece651.team7.attendanceServer.POJO.Attendance;

public class FormatVisitorImpl implements FormatVisitor {
    private JFileChooser chooser;

    public FormatVisitorImpl() {
        this.chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    }

    public FormatVisitorImpl(JFileChooser chooser) {
        this.chooser = chooser;
    }

    @Override
    public String visit(JSONNode n) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(n.getObject());
        // System.out.println(json);
        // String fileDirectory = promptForDirectory();
        // saveStringToFile(json, fileDirectory, "attendance_report.json");
        return json;
    }

    @Override
    public String visit(XMLNode n) {
        XStream xstream = new XStream();
        xstream.allowTypesByWildcard(new String[] { "**" });
        xstream.alias("Attendance", Attendance.class);
        String xml = xstream.toXML(n.getObject());
        // System.out.println(xml);
        // String fileDirectory = promptForDirectory();
        // saveStringToFile(xml, fileDirectory, "attendance_report.xml");
        return xml;
    }

    public String promptForDirectory() {
        chooser.setDialogTitle("Select a directory to save the file");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }

    public void saveStringToFile(String content, String fileDirectory, String fileName) {
        if (fileDirectory != null) {
            String filePath = fileDirectory + File.separator + fileName;

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(content);
            } catch (IOException e) {
                System.out.println("No valid directory selected, operation cancelled.");
            }
        } else {
            System.out.println("No valid directory selected, operation cancelled.");
        }
    }

}
