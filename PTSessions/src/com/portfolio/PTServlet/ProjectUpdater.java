package com.portfolio.PTServlet;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.*;


public class ProjectUpdater {
	private String ROOT = System.getProperty("user.dir")+"/internalDB.iDB";
	
	private String p;
	private File f=null;
	//private List<String> allPathsToFile=null;
	//private List<String> allPathsToDir=null;
	@SuppressWarnings("unused")
	private boolean checkComments = false;
	private FileWriter fw = null;
	
	public ProjectUpdater(String path){
		this.p = path;
		f = new File(p);
		//System.out.println(ROOT);
		try{
			fw = new FileWriter(ROOT, true);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		//allPathsToFile =  new ArrayList<String>();
		//allPathsToDir =  new ArrayList<String>();
	}
	
	public ProjectUpdater(String path, boolean restart){
		this.p = path;
		if(restart){
			f = new File(ROOT);
			f.delete();
			f = new File(p);
			deleteAll(f);
		}
		f = new File(p);
		//System.out.println(ROOT);
		try{
			fw = new FileWriter(ROOT, true);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public ProjectUpdater(String path, String root, boolean restart){
		this.p = path;
		ROOT = root;
		if(restart){
			f = new File(ROOT);
			f.delete();
			f = new File(p);
			deleteAll(f);
		}
		f = new File(p);
		//System.out.println(ROOT);
		try{
			fw = new FileWriter(ROOT, true);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void deleteAll(File f){
		
		if(f==null){
			return;
		}
		
		if(f.isDirectory()){
			File[] subf = f.listFiles();
			if(subf != null){
				for(File fs : subf){
					deleteAll(fs);
				}
			}
		}
		if(f.isFile()){
			String pth = f.getAbsolutePath().toLowerCase();
			if(pth.endsWith(".ptupd")){
				//System.out.println(f.getAbsolutePath() + f.delete());
				f.delete();
			}
		}
	}
	
	public void close(){
		try {
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getFile(){
		return f;
	}
	
	private void writeToDB(String file, String parent){
		try {
			File of = new File(ROOT);
			BufferedReader br = new BufferedReader(new FileReader(of));
			String toRead = null;
			while((toRead = br.readLine()) != null){
				if(toRead.contains(file)){
					br.close();
					return;
				}
			}
			br.close();
			fw.write(file);
			fw.write(System.lineSeparator());
			fw.write(parent);
			fw.write(System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeAloneToDB(String file){
		try {
			File of = new File(ROOT);
			BufferedReader br = new BufferedReader(new FileReader(of));
			String toRead = null;
			while((toRead = br.readLine()) != null){
				if(toRead.contains(file)){
					br.close();
					return;
				}
			}
			br.close();
			fw.write(file);
			fw.write(System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean hasBeenChecked(File f){
		if(f.isFile()){
			return false;
		}
		File[] i = f.listFiles();
		if(i != null){
			for(File j: i){
				if(j.getAbsolutePath().endsWith(".ptupd")){
					return true;
				}
				//System.out.println(j.getAbsolutePath());
			}
		}
		return false;
	}
	
	public void getProjectPath(File f){
		boolean test = false;
		if(f.isDirectory()){
			test = hasBeenChecked(f);
			//System.out.print(test);
			if(test){
				return;
			}
			File[] subf = f.listFiles();
			if(subf != null){
				for(File fs : subf){
					getProjectPath(fs);
				}
			}
		}
		if(f.isFile()){
			String pth = f.getAbsolutePath().toLowerCase();
			if(pth.endsWith(".ptx")){
				if(pth.contains(".bak.")){
					return;
				}
				this.checkComments = false;
				List<String> temp = checkPTX(f.getParent());
				
				if(this.checkComments = true){
					createPTUPD(f.getAbsolutePath(), checkTags(temp));
				}
				this.writeToDB(f.getAbsolutePath(), f.getParent());
				this.writeAloneToDB(f.getAbsolutePath()+" - Updater.ptupd");
				this.appendImage(f.getParentFile().listFiles());
				//allPathsToFile.add(f.getAbsolutePath());
				//allPathsToDir.add(f.getParent());
			}
		}
	}
	
	/*public void printPathToFile(){
		String[] toPrint = allPathsToFile.toArray(new String[allPathsToFile.size()]);
		for(String s: toPrint){
			System.out.println(s);
		}
	}
	
	public void printPathToDir(){
		
		String[] toPrint = allPathsToDir.toArray(new String[allPathsToFile.size()]);
		for(String s: toPrint){
			System.out.println(s);
		}
	}
	*/
	public List<String> checkPTX(String PathToPTX){
		String p = PathToPTX+"/comments.txt";
		List<String> toParse = new ArrayList<String>();
		File of = new File(p);
		if(!of.exists()){
			return null;
		}
		try{
			of = new File(p);
			BufferedReader br = new BufferedReader(new FileReader(of));
			String toRead = null;
			while((toRead = br.readLine()) != null){
				toParse.add(toRead);
			}
			br.close();
			return toParse;
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	private List<String> checkTags(List<String> ls){
		if(ls == null || ls.isEmpty()){
			ls = new ArrayList<String>();
			ls.add("No tags.");
			return null;
		}
		
		String[] soa = ls.toArray(new String[ls.size()]);
		LinkedHashSet<String> Tags = new LinkedHashSet<String>();
		List<String> TagContent = new ArrayList<String>();
		Pattern getTags = Pattern.compile("<([a-zA-Z\\s]+)>");
		Matcher m = null;
		String subS = null;
		
		boolean hasBegun = false;
		for(String s: soa){
			m = getTags.matcher(s);
			while(m.find()){
				Tags.add(s.substring(m.start()+1, m.end()-1).toLowerCase());
				s = s.substring(s.indexOf('>')+1);
				m = getTags.matcher(s);
			}
		}
		
		for(String t: Tags){
			TagContent.add(Capitalize(t)+":");
			for(String s: soa){
				String sLowerCase = s.toLowerCase();
				if(hasBegun){
					if(sLowerCase.contains("</"+t+">")){
						subS = s.substring(0, sLowerCase.indexOf("</"+t+">"));
						if(!subS.isEmpty()){
							TagContent.add(s.substring(0, sLowerCase.indexOf("</"+t+">")));
						}
						hasBegun = false;
					}
					else{
						TagContent.add(s);
					}
				}
				else{
					if(sLowerCase.contains("<"+t+">")){
						if(sLowerCase.contains("</"+t+">")){
							subS = s.substring(sLowerCase.indexOf("<"+t+">")+t.length()+2, sLowerCase.indexOf("</"+t+">")+2);
							if(!subS.isEmpty()){
								TagContent.add(subS);
							}
						}
						else{
						hasBegun = true;
						subS = s.substring(sLowerCase.indexOf("<"+t+">")+t.length()+2);
						if(!subS.isEmpty()){
							TagContent.add(subS);
						}
						}
					}
				}	
			}
			TagContent.add(System.lineSeparator());
		}
		return TagContent;
	}
	private String Capitalize(String source){
		StringBuffer res = new StringBuffer();

	    String[] strArr = source.split(" ");
	    for (String str : strArr) {
	        char[] stringArray = str.trim().toCharArray();
	        stringArray[0] = Character.toUpperCase(stringArray[0]);
	        str = new String(stringArray);

	        res.append(str).append(" ");
	    }
	    return res.toString();
	}
	
	public void appendImage(File[] f){
		if(f == null){
			return;
		}
		if(f.length == 0){
			return;
		}
		for(File p: f){
			if(p.isFile()){
				if(p.getAbsolutePath().endsWith(".jpg") ||
					p.getAbsolutePath().endsWith(".png")||
					p.getAbsolutePath().endsWith(".bmp")||
					p.getAbsolutePath().endsWith(".gif")){
					try {
					FileChannel src = new FileInputStream(p).getChannel();
					FileChannel dest;
					File dtf = new File(ROOT.replace("internalDB.iDB","/PTPhotos/"));
					if (!dtf.exists())
					    dtf.mkdirs();
						//System.out.println(dtf.getAbsolutePath()+"/"+p.getName());
						dest = new FileOutputStream(dtf.getAbsolutePath()+"/"+p.getName().replaceAll(" ", "")).getChannel();
						dest.transferFrom(src, 0, src.size());
					} catch (Exception e) {
						e.printStackTrace();
					}
					String newPath = p.getAbsolutePath();
					String ic = "";
					if(newPath.contains("/"))
						ic = newPath.substring(newPath.lastIndexOf("/",newPath.length())).replaceAll(" ", "");
					writeToDB(p.getParent() + ic,p.getParent() + ic);
				}
			}
		}
		writeAloneToDB("/=========================/");
	}
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
	
	public void createPTUPD(String q, List<String> ls){
		String p = q.substring(0,q.indexOf(".ptx"));
		p += " - Updater.ptupd";
		try {
		if(ls == null){
			FileWriter fw = new FileWriter(p);
			fw.write(" ");
			fw.flush();
			fw.close();
			return;
		}
		if(ls.isEmpty()){
			FileWriter fw = new FileWriter(p);
			fw.write(" EMPTY ");
			fw.flush();
			fw.close();
			return;
		}
			FileWriter fw = new FileWriter(p);
			BufferedWriter bw = new BufferedWriter(fw);
			String[] aos = ls.toArray(new String[ls.size()]);
			for(String i: aos){
				bw.write(i);
				bw.write(System.lineSeparator());
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
