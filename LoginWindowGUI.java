import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
class LoginWindowGUI extends JFrame implements ActionListener
{
private JPanel buttonPanel;
private JPanel inputPanel;
private JButton login;
private JLabel labelPassword;
private JLabel labelUsername;
private JPasswordField textPassword;
private JTextField textUsername;
private Container pane;
private static ObjectOutputStream objectOut;
public LoginWindowGUI()
{
//ADD WINDOW LISTENER FOR DETECTING WINDOW CLOSE
addWindowListener(
new WindowAdapter()
{
public void windowClosing(WindowEvent e)
{
//SHUT DOWN CLIENT CORRECTLY
EMailClient.closeDown();
}
}
);
setTitle("Login User");
pane = getContentPane();
pane.setLayout(new BorderLayout());
buttonPanel = new JPanel();
inputPanel = new JPanel();
login = new JButton("Login");
labelPassword = new JLabel("Password");
labelUsername = new JLabel("Username");
textPassword = new JPasswordField(10);
textUsername = new JTextField(10);
buttonPanel.setLayout(new GridLayout(1,1));
inputPanel.setLayout(new GridLayout(2,2));
buttonPanel.add(login, BorderLayout.CENTER);
inputPanel.add(labelUsername);
inputPanel.add(textUsername);
inputPanel.add(labelPassword);
inputPanel.add(textPassword);
pane.add(inputPanel, BorderLayout.NORTH);
pane.add(buttonPanel, BorderLayout.SOUTH);
//ADD ACTION LISTENER FOR LOGIN BUTTON
login.addActionListener(this);
}
public void actionPerformed(ActionEvent e)
{
//SPECIFY ACTION TO BE TAKEN ON BUTTON PRESS
//DECLARE VARIABLES LOCAL TO METHOD
String userName = textUsername.getText();
String password = new String(textPassword.getPassword());
//CHECK IF USER HAS ENTERED A VALID USERNAME AND PASSWORD
boolean validUser = checkUserExists(userName, password);
if(validUser == true)
{
JOptionPane.showMessageDialog(pane, "Login Accepted");
setVisible(false);
//SEND USERNAME AND PASSWORD TO CLIENT
sendUserNameToClient(userName, password);
sendUserNameToServer(userName, password);
//CREATE MAIN FUNCTION MENU GUI AND DISPLAY
MainFunctionMenuGUI mainFunctionMenuGUI = new MainFunctionMenuGUI();
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
final int HEIGHT = 350;
final int WIDTH = 500;
mainFunctionMenuGUI.setBounds(((screenSize.width / 2) - (WIDTH / 2)),
((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
mainFunctionMenuGUI.setVisible(true);
}
else
{
//INVALID USERNAME CLEAR FIELDS DISPLAY WARNING
JOptionPane.showMessageDialog(pane, "Incorrect Login");
textUsername.setText("");
textPassword.setText("");
}
}
//CHECK IF A PARTICULAR USER EXISTS
public boolean checkUserExists(String userName, String password)
{
//CHECKS WHETHER OR NOT A USER EXISTS ON SERVER
//MAKE A COPY OF USERNAMES FROM SERVER
Vector userNames = EMailClient.getUserNames();
//DECLARE VARIABLES LOCAL TO METHOD
int vectorSize = userNames.size();
int vectorPosition;
String extractedUser;
String extractedPassword;
boolean exists = false;
//SEARCH THROUGH VECTOR TO CHECK ALL USERNAMES
for(vectorPosition = 0; vectorPosition < vectorSize; vectorPosition++)
{
//RETRIEVE FIRST USER OBJECT FROM VECTOR
User temp = (User)userNames.elementAt(vectorPosition);
//EXTRACT USERNAME AND PASSWORD FROM USER OBJECT
extractedUser = temp.getUserName();
extractedPassword = temp.getPassword();
//CHECK IF EXTRACTED INFO MATCHES PASSED IN INFO
if(extractedUser.equals(userName) && extractedPassword.equals(password))
{
//IF USER EXISTS SET TO TRUE AND QUIT LOOP
exists = true;
break;
}
}
//RETURN WHETHER USER EXISTS OR NOT
return exists;
}
//SEND LOGGED IN USERS NAME TO SERVER
private void sendUserNameToServer(String userName, String Password)
{
try
{
//SET USER STATUS TO TRUE FOR EXISTING USER
Boolean userStatus = Boolean.FALSE;
//SET UP OBJECT OUTPUT STREAM
objectOut = new ObjectOutputStream(EMailClient.getLink().getOutputStream());
//CREATE NEW USER FROM ENTERED USER
User user = new User(userName, Password);
//SEND INFORMATION TO SERVER
objectOut.writeObject(user);
objectOut.writeObject(userStatus);
//FLUSH STREAM
objectOut.flush();
}
catch (IOException ioe)
{
ioe.printStackTrace();
}
}
//SEND USERS NAME TO CLIENT
private void sendUserNameToClient(String userName, String password)
{
User user = new User(userName, password);
EMailClient.setUser(user);
}
}
