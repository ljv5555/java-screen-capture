/**
 * 
 */
package com.kana.util.screencapture;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;

import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.FlowLayout;

import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.Action;

/**
 * @author jverderber
 *
 */
public class ScreenCaptureApplet extends Frame {
	private JTextField textField_1;
	private JTextField textField_2;
	private final Action actionstop = new SwingActionStop();
	private final Action actionstart = new SwingActionStart();

	
	private CaptureScreensThread s = null;
	/**
	 * @return the s
	 */
	public synchronized CaptureScreensThread getS() {
		return s;
	}
	/**
	 * @param s the s to set
	 */
	public synchronized void setS(CaptureScreensThread s) {
		this.s = s;
	}
	/**
	 * @throws HeadlessException
	 */
	public ScreenCaptureApplet() throws HeadlessException {
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane_1);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane_1.setLeftComponent(splitPane);
		
		JButton btnStop = new JButton("stop");
		btnStop.setAction(actionstop);
		splitPane.setLeftComponent(btnStop);
		
		JButton btnStart = new JButton("Start");
		btnStart.setAction(actionstart);
		splitPane.setRightComponent(btnStart);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_2);
		
		textField_1 = new JTextField();
		splitPane_2.setLeftComponent(textField_1);
		textField_1.setColumns(50);
		
		textField_2 = new JTextField();
		splitPane_2.setRightComponent(textField_2);
		textField_2.setColumns(50);
		
		btnStart.setText("Start");
		btnStop.setText("Stop");
		setSize(new Dimension(600,300));
		setVisible(true);
		JButton exit = new JButton();
		exit.setText("Exit");
		exit.setAction(new SwingActionExit());
		add(exit);
		exit.setText("Exit");
		repaint();
	}


	private class SwingActionExit extends AbstractAction {
		public SwingActionExit() {
			
		}
		public void actionPerformed(ActionEvent e) {
			try {
				if(getS()!=null){getS().finishCapture();}
				setVisible(false);
				
				Thread.sleep(5000);
				System.exit(0);
				
			} catch (Exception ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
		}
	}
	
	private class SwingActionStop extends AbstractAction {
		public SwingActionStop() {
			
		}
		public void actionPerformed(ActionEvent e) {
			try {
				if(getS()!=null){getS().finishCapture();}
			} catch (Exception ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
		}
	}
	private class SwingActionStart extends AbstractAction {
		public SwingActionStart() {
			
		}
		public void actionPerformed(ActionEvent e) {
			try {
				setS(new CaptureScreensThread(textField_1,textField_2));
				getS().start();
			} catch (IOException eee) {
				// TODO Auto-generated catch block
				eee.printStackTrace();
			}
		}
	}
	public static void main(String args[]){new ScreenCaptureApplet();}
}
