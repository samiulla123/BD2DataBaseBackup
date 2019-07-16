import java.lang.*;
import java.sql.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
class backup
{
	 private static String USER_NAME = "abrarkhanpathan74@gmail.com";  
	 private static String PASSWORD = "9738544546"; 
	 private static String RECIPIENT = "samipat@sgibgm.org";
	 private static String REC = "samiullapathan77@gmail.com";
	public static void main(String args[])
	{
		System.out.println("Databse Backup");
		LocalDate dt=LocalDate.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		System.out.println("Mail Service");
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT, REC }; 
        String subject = "Database Backup";
        String body = "";
		
		//Date Details
		String formtDate = dt.format(myFormatObj); 
		String month[]={"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sup", "Oct", "Nov", "Dec"};
		String m=formtDate.substring(4, 5);
		String y=formtDate.substring(6, 10);
		int mm=Integer.parseInt(m);
		String date=(String)formtDate;
		String fileName=date.replace("-", "_");
		
		//DataBase Details
		String dbPath="jdbc:db2://localhost:50000/MGTPEST";
		String password="db2admin";
		String user="db2admin";
		Connection con = null;
		CallableStatement callStmt = null;
		ResultSet rs = null;
		
		//Direcotry Creation
		String Path="D:\\Notes\\db2\\backup\\"+y;
		String mon=month[mm-1];
		File FilePath=null;
		boolean flag=false;
		File p=new File(Path);
		if(!p.exists())
		{
			p.mkdir();
			FilePath=new File("D:\\Notes\\db2\\backup\\"+y+"\\"+mon);
			System.out.println("File "+FilePath);
			boolean f=FilePath.mkdir();
			System.out.println("File Direcotry Created "+f);
		}
		else if(p.exists())
		{
			FilePath=new File("D:\\Notes\\db2\\backup\\"+y+"\\"+mon);
			if(!FilePath.exists())
			{	
				boolean f1=FilePath.mkdir();
				System.out.println("File Exists "+f1);
			}
			else
			{
				System.out.println("File Already Exists ");
			}
			System.out.println("File Directory Exists");
		}
		System.out.println("Created Path Is "+FilePath);
		try
		{
			// initialize DB2Driver and establish database connection.
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			con=DriverManager.getConnection(dbPath,user,password);
			
			// prepare the CALL statement for ADMIN_CMD
			String sql = "CALL SYSPROC.ADMIN_CMD(?)";
			callStmt = con.prepareCall(sql);
			
			 // execute database backup to the specified path
			String param = "BACKUP DB MGTPEST TO " + FilePath;
			
			// set the input parameter  
			callStmt.setString(1, param);

			System.out.println("\nCALL ADMIN_CMD('" + param + "')");
			// call the stored procedure
			callStmt.execute();
		  
			// get the result set       
			rs = callStmt.getResultSet();
			
			if (rs.next())
			{ 
			  // getting the time taken for the database backup 
			  String backupTime = rs.getString(1); 
			  System.out.print("\nTimestamp for the backup image is = ");
			  System.out.println(backupTime);
			}
 
			System.out.println("\nOnline backup completed successfully"); 
			body = "Good Evening, Suraj Sir. Database backup is taken sucessfully";
			flag=true;
		}
		catch(Exception e)
		 {
			System.out.println("Exception "+e);
		 }
      finally
      {
        try
        {
          // close the resultset and callStmt
          rs.close();
          callStmt.close();

          // roll back any changes to the database made by this MGTPEST
          con.rollback();                                   

          // closing the connection
          con.close();
        }
        catch (Exception x)
        { 
          System.out.print("\n Unable to Rollback/Disconnect ");
          System.out.println("from 'MGTPEST' database"); 
          body = "Unable To Take Backup Please Check The Server";
          flag=false;
		  System.out.println(x);		  
        }
      }
      sendFromGMail(from, pass, to, subject, body);
	}
	private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) 
	{
		System.out.println("Send Mail Data");
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
			System.out.println("In Try Statment");
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
			System.out.println("Address Exception");
            ae.printStackTrace();
        }
        catch (MessagingException me) {
			System.out.println("Messaging "+me);
            me.printStackTrace();
        }
    }
}