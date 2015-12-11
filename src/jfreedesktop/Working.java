/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfreedesktop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserPrincipal;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.collections.ObservableList;

/**
 *
 * @author herve
 */
public class Working {
    
    public static ObservableList<String> listFileDesktop(String rootDeskTop){
        //prepare temp observableList
        ObservableList<String> obp=javafx.collections.FXCollections.observableArrayList();
       //get the desktop path
       Path p=Paths.get(rootDeskTop);
       
       try{     
       Files.walkFileTree(p, new SimpleFileVisitor<Path>(){
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
            if (file.toString().endsWith("desktop")){
                obp.add(file.toFile().getName().toString().replaceAll(".desktop", ""));
            }  //add path to the list
        return FileVisitResult.CONTINUE;
        }
 });
       } catch(Exception e){}
       //return observableList
        return obp;
    }
    
    
    
    public static String readFileDeskTop(String fileName){
        try {
            
           
            String lineAttr="Permissions :\n\n";
            if (Files.isExecutable(Paths.get(fileName))==true){
                lineAttr=lineAttr+"E - Executable\n";
            }
            if (Files.isReadable(Paths.get(fileName))==true){
                lineAttr=lineAttr+"R - Readable\n";
            }
            if (Files.isWritable(Paths.get(fileName))==true){
                lineAttr=lineAttr+"W - Writable\n";
            };
             UserPrincipal owner=Files.getOwner(Paths.get(fileName), LinkOption.NOFOLLOW_LINKS);
            lineAttr=lineAttr+"\nOwner : "+owner.getName()+"\n";
            
            Working.DeskTop.setPermissions(lineAttr);
            
            
             Stream<String> lines=null;
             String currLine=null;
             BufferedReader br;
            
//read file from begin to end
            br=new BufferedReader(new FileReader(fileName));
            lines=br.lines();

            //convert into iterator
            Iterator<String> it=lines.iterator();
            if (it.hasNext()) currLine=it.next();
            while (it.hasNext()){
                currLine=currLine+"\n"+it.next();
            }
            br.close(); 
            return currLine;
        } catch (IOException  ioe) {
            Logger.getLogger(Working.class.getName()).log(Level.SEVERE, null, ioe);
        }        
        
        return "";
    }
    
    
    public static void parseFileDeskTop(String fileDesktop){
        
        Properties prop=new Properties();
        try{
        prop.load(new FileReader(fileDesktop));
        
        Working.DeskTop.setName(prop.getProperty("Name"));
        Working.DeskTop.setGenericName(prop.getProperty("GenericName"));
        Working.DeskTop.setComment(prop.getProperty("Comment"));
        Working.DeskTop.setExec(prop.getProperty("Exec"));
         Working.DeskTop.setTryExec(prop.getProperty("TryExec"));
        Working.DeskTop.setURL(prop.getProperty("URL"));
        Working.DeskTop.setPath(prop.getProperty("Path"));
        Working.DeskTop.setCategories(prop.getProperty("Categories"));
        Working.DeskTop.setType(prop.getProperty("Type"));
        
        //change if type is link or Directory
        if (Working.DeskTop.getType().compareTo("Link")==0){
            Working.DeskTop.setExec(Working.DeskTop.getURL());
        }
        if (Working.DeskTop.getType().compareTo("Directory")==0){
            Working.DeskTop.setExec(Working.DeskTop.getPath());
        }
        
        Working.DeskTop.setIcon(prop.getProperty("Icon"));
        Working.DeskTop.setTerminal(prop.getProperty("Terminal"));
        Working.DeskTop.setVersion(prop.getProperty("Version"));
        
        }
        catch(IOException ioe){}
        
    /* DeskTop.setComment("ShortCut Comment...");
     DeskTop.setName("Shortcut Name");
     DeskTop.setExec("/home/herve/prog");
     DeskTop.setType("Application");
     DeskTop.setIcon("/home/herve/.kolourpaint.png");
     DeskTop.setKeywords("kw1;kw2;kw3");*/
    }
    
    
 static class DeskTop{
    private static String GenericName;
    private static String Name;
    private static String Exec;
    private static String Comment;
    private static String URL;
    private static String Icon;
    private static String Type;
    private static String Version;
    private static String NoDisplay;
    private static String TryExec;
    private static String Hidden;
    private static String OnlyShowIn;
    private static String NotShowIn;
    private static String DBusActivatable;
    private static String Path;
    private static String Terminal;
    private static String Actions;
    private static String MimeType;
    private static String Categories;
    private static String Implements;
    private static String Keywords;
    private static String StartupNotify;
    private static String StartupWMClass;
    private static String Permissions;
    
    

        public static String getGenericName() {
            return GenericName;
        }

        public static void setGenericName(String aGenericName) {
            GenericName = aGenericName;
        }

        public static String getName() {
            return Name;
        }

        public static void setName(String aName) {
            Name = aName;
        }

        public static String getExec() {
            return Exec;
        }

        public static void setExec(String aExec) {
            Exec = aExec;
        }

        public static String getComment() {
            return Comment;
        }

        public static void setComment(String aComment) {
            Comment = aComment;
        }

        public static String getURL() {
            return URL;
        }

        public static void setURL(String aURL) {
            URL = aURL;
        }

        public static String getIcon() {
            return Icon;
        }

        public static void setIcon(String aIcon) {
            Icon = aIcon;
        }

        public static String getType() {
            return Type;
        }

        public static void setType(String aType) {
            Type = aType;
        }

        public static String getVersion() {
            return Version;
        }

        public static void setVersion(String aVersion) {
            Version = aVersion;
        }

        public static String getNoDisplay() {
            return NoDisplay;
        }

        public static void setNoDisplay(String aNoDisplay) {
            NoDisplay = aNoDisplay;
        }

        public static String getTryExec() {
            return TryExec;
        }

        public static void setTryExec(String aTryExec) {
            TryExec = aTryExec;
        }

        public static String getHidden() {
            return Hidden;
        }

        public static void setHidden(String aHidden) {
            Hidden = aHidden;
        }

        public static String getOnlyShowIn() {
            return OnlyShowIn;
        }

        public static void setOnlyShowIn(String aOnlyShowIn) {
            OnlyShowIn = aOnlyShowIn;
        }

        public static String getNotShowIn() {
            return NotShowIn;
        }

        public static void setNotShowIn(String aNotShowIn) {
            NotShowIn = aNotShowIn;
        }

        public static String getDBusActivatable() {
            return DBusActivatable;
        }

        public static void setDBusActivatable(String aDBusActivatable) {
            DBusActivatable = aDBusActivatable;
        }

        public static String getPath() {
            return Path;
        }

        public static void setPath(String aPath) {
            Path = aPath;
        }

        public static String getTerminal() {
            return Terminal;
        }

        public static void setTerminal(String aTerminal) {
            Terminal = aTerminal;
        }

        public static String getActions() {
            return Actions;
        }

        public static void setActions(String aActions) {
            Actions = aActions;
        }

        public static String getMimeType() {
            return MimeType;
        }

        public static void setMimeType(String aMimeType) {
            MimeType = aMimeType;
        }

        public static String getCategories() {
            return Categories;
        }

        public static void setCategories(String aCategories) {
            Categories = aCategories;
        }

        public static String getImplements() {
            return Implements;
        }

        public static void setImplements(String aImplements) {
            Implements = aImplements;
        }

        public static String getKeywords() {
            return Keywords;
        }

        public static void setKeywords(String aKeywords) {
            Keywords = aKeywords;
        }

        public static String getStartupNotify() {
            return StartupNotify;
        }

        public static void setStartupNotify(String aStartupNotify) {
            StartupNotify = aStartupNotify;
        }

        public static String getStartupWMClass() {
            return StartupWMClass;
        }

        public static void setStartupWMClass(String aStartupWMClass) {
            StartupWMClass = aStartupWMClass;
        }

        public static String getPermissions() {
            return Permissions;
        }

        public static void setPermissions(String aPermissions) {
            Permissions = aPermissions;
        }
     
}//end DeskTop   
 
 
} //end Working


class FilterDeskTop implements FilenameFilter{

    @Override
    public boolean accept(File dir, String name) {
       if (name.endsWith(".desktop")) return true;
        return false;
    }
    
}

