package com.portfolio.PTServlet;


public class DAWTESTER {
	public static void main(String[] args) {
		ProjectUpdater pu = new ProjectUpdater("/Users/Dovahart 1/Desktop", true);
		pu.getProjectPath(pu.getFile());
		pu.close();
	}
}
