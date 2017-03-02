package com.portfolio.PTServlet;

import java.math.BigInteger;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.*;

import org.apache.commons.io.FileUtils;

import java.io.*;

@WebServlet(description = "This servlet allows classification of PT files.", urlPatterns = { "/PTServlet" })
public class PTServlet extends HttpServlet {
	private static final char SPACER = ';';
	private static final long serialVersionUID = 1L;
	
	private static final String setPath = "/Volumes/LuisToshiba/ProTools";  
    private static final String ROOT = "/Users/Dovahart 1/Desktop/ProToolsFolder" +  "/internalDB.iDB";
	private static final String ip="localhost";
	private static final String user="root";
	private static final String pass="420414imi2017";
	private static final String port="3306";
	private static final boolean RESETDATABASE=true;
	private static final int DOWNLOADMN = 3;
	private static final int GETALLMN = 2;
	private static final int GETONEMN = 1;
    
    /**
     * 
     * SET THE PATH 
     * TO THE PRO TOOLS FILES
     * IN private static final String setPath = "HERE";
     * Similarly, pass (from password), port, and ip MUST match the pc's
     * 
     * RESETDATABASE is a constant with which the user can delete the
     * interior database, mysql database and PTUPD files. This is to scan
     * again for missing stuff and reorganize from scratch.
     * 
     * Multiple directories are possible by creating a new ProjectUpdater
     * with the ProjectUpdater(String) instantiator, them using
     * pu.getProjectPath(pu.getFile());
     * pu.close();
     * One per each directory (it, however, includes all subdirectories.
     * I. E. /Desktop includes /Desktop and /Desktop/ProTools)
     * 
     * 
     */
	
	private Statement stmt = null;
    private Connection con = null;
    
    public PTServlet() {
        super();
        ProjectUpdater pu = new ProjectUpdater(setPath, ROOT, RESETDATABASE);
		pu.getProjectPath(pu.getFile());
		pu.close();
		//System.out.println(ROOT);
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.con = DriverManager.getConnection(
					"jdbc:mysql://"+ip+":"+port+"/", user, pass);
			stmt = con.createStatement();
			if(RESETDATABASE)
				stmt.executeUpdate("DROP SCHEMA IF EXISTS `ProTools`");
			stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS `ProTools`");
			String sql = "CREATE TABLE IF NOT EXISTS `ProTools`.`PTMaster` ("
					+ "`ID` int NOT NULL AUTO_INCREMENT, "
					+ "`session_name` varchar(255) NOT NULL, "
					+ "`session_path` varchar(500) NOT NULL, "
					+ "`comments` varchar(500) DEFAULT NULL, "
					+ "`images` text DEFAULT NULL, "
					+ "`session_size` text DEFAULT NULL, "
					+ "PRIMARY KEY (ID)) ENGINE=InnoDB;";
			stmt.executeUpdate(sql);
			appendToSQL();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void init(ServletConfig config) throws ServletException {
	}
	
	private void appendToSQL(){
		try {
			File of = new File(ROOT);
			BufferedReader br = new BufferedReader(new FileReader(of));
			String toRead = null;
			List<String> ls = new ArrayList<String>();
			while((toRead = br.readLine()) != null){
				ls.add(toRead);
			}
			String[] str = ls.toArray(new String[ls.size()]);
			String parent = null;
			String SessionName = null;
			String SessionPath = null;
			String Comments = null;
			String Images = "";
			String FileSize = "";
			
			String temp = null;
			for(String i: str){
				if(! i.equals("/=========================/")){
					if(SessionPath == null){
						SessionPath = getParent(i);
					}
					File f = new File(i);
					if(f.isDirectory()){
						FileSize = Long.toString(size(f.toPath()));
						if(FileSize.length()>9){
							FileSize = FileSize.substring(0, FileSize.length()-9) + "." 
						+ FileSize.substring(FileSize.length()-9, FileSize.length()) + " GB";
						}
						else if(FileSize.length()>6){
							FileSize = FileSize.substring(0, FileSize.length()-6) + "." 
						+ FileSize.substring(FileSize.length()-6, FileSize.length()) + " MB";
						}
						else if(FileSize.length()>3){
							FileSize = FileSize.substring(0, FileSize.length()-3) + "." 
						+ FileSize.substring(FileSize.length()-3, FileSize.length()) + " KB";
						}
						else{
							FileSize += " B";
						}
					}
					else{
						temp = i.substring(SessionPath.length());
						if(temp.endsWith(".ptx")){
							SessionName = temp;
						}
						else if(temp.endsWith(".ptupd")){
							Comments=temp;
						}
						else{
							if(temp != null && (!temp.isEmpty())){
								if(!Images.contains(temp)){
									Images+=SPACER+temp;
								}
							}
						}
					}
					/*
						
							String Statement =  "REPLACE INTO `ProTools`.`PTMaster` "
									+"(session_name, session_path, comments, images, session_size) VALUES "
									+ "('"+SessionName+"','"+SessionPath+"','"+Comments+"','"+Images+"','"+FileSize+"')";
							System.out.println(Statement);
							stmt.executeUpdate(Statement);
						
					*/
					}else{
						if(SessionPath != null){
							String Statement =  "REPLACE INTO `ProTools`.`PTMaster` "
									+"(session_name, session_path, comments, images, session_size) VALUES "
									+ "('"+SessionName+"','"+SessionPath+"','"+Comments+"','"+Images+"','"+FileSize+"')";
							//System.out.println(Statement);
							stmt.executeUpdate(Statement);
						}
						SessionName = null;
						SessionPath = null;
						Comments = null;
						Images = "";
					}
				}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getParent(String s){
		String t = null;
		if(s == null || s.isEmpty()){
			return t;
		}
		if(s.lastIndexOf("/") == -1)
			return null;
		t = s.substring(0,s.lastIndexOf("/"));
		return t;
	}
	
	public long size(Path path) {

	    final AtomicLong size = new AtomicLong(0);

	    try {
	        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
	            @Override
	            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

	                size.addAndGet(attrs.size());
	                return FileVisitResult.CONTINUE;
	            }

	            @Override
	            public FileVisitResult visitFileFailed(Path file, IOException exc) {

	                System.out.println("skipped: " + file + " (" + exc + ")");
	                // Skip folders that can't be traversed
	                return FileVisitResult.CONTINUE;
	            }

	            @Override
	            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

	                if (exc != null)
	                    System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
	                // Ignore errors traversing a folder
	                return FileVisitResult.CONTINUE;
	            }
	        });
	    } catch (IOException e) {
	        throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
	    }

	    return size.get();
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
	}

	private String getComments(String sParentPath, String cPath ){
		if(sParentPath == null || cPath == null){
			return "No comments";
		}
		File p = null;
		if(cPath.contains(".ptx")){
			String sub = cPath.substring(0, cPath.lastIndexOf(".ptx"));
			if(sub.length() + 4 < cPath.length()){
				sub += cPath.substring(cPath.lastIndexOf(".ptx")+4, cPath.length());
			}
			p = new File(sParentPath + sub);
			
		}
		else{
			p = new File(sParentPath + cPath);	
		}
		
		String comments = "";
		if(!p.exists() || !p.isFile()){
			comments = "No comments";
		}
		else{
			try {
				BufferedReader br = new BufferedReader(new FileReader(p));
				String toRead = null;
				while((toRead = br.readLine()) != null){
					comments += "<br />" + toRead.replace('\\',' ');
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		if(comments == null || comments.equals("")){
			return "No comments";
		}
		return comments;
		}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("MN") != null){
		int MN = Integer.parseInt(request.getParameter("MN").replace("/", ""));
		if(MN == DOWNLOADMN){
			String removeSlash = request.getParameter("ID").replace("/", "");
			System.out.println("Download #" + removeSlash + " triggered.");
		}else if(MN == GETALLMN){
			String allNames = "";
			ResultSet rs;
			try {
				rs = stmt.executeQuery("SELECT * FROM `ProTools`.`PTMaster`");
				while (rs.next()) {
					String comments = "<details><summary>Click for comments</summary>";
					comments += getComments(rs.getString("session_path"),rs.getString("comments"));
					comments += "</details>";
					allNames += "<tr><td>" + rs.getString("ID") + "</td> <td>"
							+ rs.getString("session_name").replaceAll(".ptx", "").replace('/', ' ') + "</td> <td>" 
							+ comments + "</td> <td>"
							+ rs.getString("session_size") + "</td> <td>"+ "<a href=\"PTServlet?ID="+rs.getString("ID")+"&MN="+GETONEMN+"\"> Check details </a></td> </tr>";
				}
				request.setAttribute("allNames", allNames);
				request.getRequestDispatcher("/getAll.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(MN == GETONEMN){
			try {
				ResultSet rs = stmt.executeQuery("SELECT * FROM `ProTools`.`PTMaster` WHERE ID='"+request.getParameter("ID").replace("/","")+"'");
				if(rs.next()){
					
					request.setAttribute("ID", rs.getString("ID"));
					request.setAttribute("Name", rs.getString("session_name").replaceAll("/", "").replaceAll(".ptx", ""));
					
					//System.out.print(comments);
					request.setAttribute("Comments", getComments(rs.getString("session_path"),rs.getString("comments")));
					request.setAttribute("Size", rs.getString("session_size"));
					request.setAttribute("Videos", "LULMARKER");
					/**
					 * 
					 *MODIFY VIDEOS
					 *
					 */
					String images = rs.getString("images");
					String[] imgArr=images.split(";");
					
					for(String s:imgArr){
						System.out.println(s);
						if(s == null||s.isEmpty()){
						}
						else{
							images += "<span class=\"image featured\"><img src=PTPhotos"+s+" alt=\"\" /></span>";
						}
					}
				request.setAttribute("Images", images);
				}
				  request.getRequestDispatcher("/NewFile.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
