public class PTSessionServer{
  private static String ip = 127.0.0.1;
  private static String port = 80;
  private static int SUCCESS = 0;
  private static int FILENAMENOTSET=1;
  private static String mysqlPath="";
  private String mysqlusername="";
  private String mysqlpass="";
  private String currentURI="";

  public String getIP(){
      return this.ip;
  }

  public String getPort(){
        return this.port;
    }

  public String getMySqlUser(){
        return this.mysqlusername;
   }

  public String getMySqlPass(){
        return this.mysqlpass;
    }

  private int setMysql(File f){
    if(this.f == null){
      return FILENAMENOTSET;
    }
  }

  public static void main(String[] args){
    File mysqlfile = new File(mysqlPath);
    try(){
      setMysql(mysqlfile);
    } 
    catch(IOException e1){
      System.out.print("Error setting file.");
      exit(1);
    }
      

  }


}