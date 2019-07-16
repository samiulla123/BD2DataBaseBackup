1.	Run The BackupDatabse.java file it will create .class file in same directory
2.	Set .class file to environment variable 
a.	Right Click On MyComputer  -> Property -> Advance System Setting -> Environment variable -> In User Variable in user -> Click New Button -> Give Variable Name -> And Select the file button and browse the .class file. Save it and come out of environment set up.
b.	Go to command prompt and type:
i.	Echo %VariableName%

3.	Open Task scheduler
a.	Open Search Task scheduler.
b.	On the right top corner select “Create Basic Task” 
c.	Give task name and description than click on next button
d.	Select “When do you want the task to start”? click next
e.	Set Daily when you want to perform task.
f.	In what action do you want the task to perform? 
i.	Select “start a program” option Click Next
g.	Program/Script browse the java.exe file 
i.	In My System its in
C:\Program Files\Java\jdk1.8.0_73\bin\java.exe
ii.	In “Add argument (optional)” give “.class” name (Without extension);
h.	Click next and finish 
4.	Test the task clicking run button.

Mail Configuration
1.	Extract file present in the same directory 
a.	File Name: mailactivation(1)
2.	Set Class Path For Both (mail.jar and activation.jar)
a.	Eq:  set CLASSPATH=%CLASSPATH%;D:\Notes\MailJarFile\mail.jar;.
(Here .dot is mendatory)
Eq:  set CLASSPATH=%CLASSPATH%;D:\Notes\MailJarFile\activation.jar;
3.	In Program Change User Email Id And Password.
4.	And Change the email Id Whom You Want To Send The Mail.
If its shows an error like mail class Not Found
1.	Set ClassPath for both Mail.jar and activation.jar file
