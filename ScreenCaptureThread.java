/**
 * 
 */


import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author jverderber
 *
 */
public class CaptureScreensThread extends Thread {

	/**
	 * @return the lastFile
	 */
	public synchronized String getLastFile() {
		return lastFile;
	}

	/**
	 * @param lastFile the lastFile to set
	 */
	public synchronized void setLastFile(String lastFile) {
		this.lastFile = lastFile;
	}

	/**
	 * @return the ncaptured
	 */
	public synchronized long getNcaptured() {
		return ncaptured;
	}

	/**
	 * @param ncaptured the ncaptured to set
	 */
	public synchronized void setNcaptured(long ncaptured) {
		this.ncaptured = ncaptured;
	}

	/**
	 * @return the folder
	 */
	public synchronized File getFolder() {
		return folder;
	}

	/**
	 * @param folder the folder to set
	 */
	public synchronized void setFolder(File folder) {
		this.folder = folder;
	}


	private String lastFile = "";
	private long ncaptured = 0;
	
	
	private static long CAPTURE_INTERVAL = 1100;
	private File folder = null;
	
	private JTextField ta1=null;
	JTextField ta2=null;
	
	/**
	 * @throws IOException 
	 * 
	 */
	public CaptureScreensThread() throws IOException {
		folder = File.createTempFile("tmpf", "txt");
		String folderpath = folder.getAbsolutePath();
		folder.delete();
		folder.mkdir();
				
	}

	/**
	 * @throws IOException 
	 * 
	 */
	public CaptureScreensThread(JTextField textField_1, JTextField textField_2) throws IOException {
		ta1=textField_1;ta2=textField_2;
		folder = File.createTempFile("tmpf", "txt");
		String folderpath = folder.getAbsolutePath();
		folder.delete();
		folder.mkdir();
				
	}
	private boolean running = false;
	


	/**
	 * @return the running
	 */
	public synchronized boolean isRunning() {
		return running;
	}

	/**
	 * @param running the running to set
	 */
	public synchronized void setRunning(boolean running) {
		this.running = running;
	}

	public synchronized void finishCapture()
	{
		setRunning(false);
	}

	@Override
	public void run() {
		setRunning(true);
			try {
				Robot r = new Robot();
				Rectangle bounds = GraphicsEnvironment
		           .getLocalGraphicsEnvironment()
		           .getDefaultScreenDevice()
		           .getDefaultConfiguration()
		           .getBounds(); 
				while(isRunning())
				{
					BufferedImage bi = r.createScreenCapture(bounds);
					String fpath = folder.getAbsolutePath()+File.separator+new java.util.Date().getTime()+".png";
					setLastFile(fpath);
					setNcaptured(getNcaptured()+1);
					System.out.println("Writing file: "+fpath);
					ImageIO.write(bi, "png", new File(fpath));
					if(ta1!=null){ta1.setText(ncaptured+"");}
					if(ta2!=null){ta2.setText(""+lastFile);}
					try{Thread.sleep(CAPTURE_INTERVAL);}catch(Exception e){}
				}
				//GraphicsEnvironment
		        //   .getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().
				
			} catch (AWTException e) {
				e.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
						
		
	}

	public static void main(String args[]){try {
		new CaptureScreensThread().start();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}};
}
