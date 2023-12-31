import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//INCLUDE LIBRARIEWS NEEDED FOR FILE INPUT
import java.io.*;
class ViewGraphicsAttachment extends JFrame
{
//DECLARE COMPONENTS TO BE PLACED ON GUI
private static JLabel picLabel;
private static ImageIcon pictureAttachment;
private static Container pane;
//DECLARE ATTACHMENT VARIABLES
private static byte[] graphicsAttachment;
//CONSTRUCTOR FOR ATTACHMENT GUI
public ViewGraphicsAttachment(byte[] graphicsAttachment)
{
//GET BYTE ARRAY FROM CLIENT
this.graphicsAttachment = graphicsAttachment;
try
{
getGraphicsAttachmentContents();
}
catch(IOException e)
{
e.printStackTrace();
}
//SET WINDOW TITLE
setTitle("View Graphics Attachment");
//ADD COMPONENTS TO CONTENT PANEL
pane = getContentPane();
pane.add(picLabel, BorderLayout.CENTER);
}
//GET IMAGE FROM BYTE ARRAY ATTACHMENT
public void getGraphicsAttachmentContents() throws IOException
{
pictureAttachment = new ImageIcon(graphicsAttachment);
picLabel = new JLabel(pictureAttachment);
}
}