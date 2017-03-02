package com.portfolio.PTServlet;

import java.io.File;

import javax.swing.filechooser.*;
import org.apache.commons.io.*;

public class DAWFilter extends FileFilter {
	@Override
	public boolean accept(File f) {
		if(f.isDirectory()){
			return true;
		}
		String extension = FilenameUtils.getExtension(f.getName()).toLowerCase();
		System.out.println(extension);
		if(extension != null){
			if(extension.equals("ptx") || extension.equals(".ptx")){
				return true;
			}
		}
		return false;
	}

	public String getDescription() {
		return null;
	}
}
