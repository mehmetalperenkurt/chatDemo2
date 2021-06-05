/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.net.*;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JFrame;
/**
 *
 * @author Mehmet
 */

public class client{
	
	chatRoom ClientThread;
	
	public static void main(String[] args) {
		new client();
	}
	public client()
	{	
		modal1 window = new modal1();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(676,500);
		window.setVisible(true);
	}

	public void ListenForInput()
	{
		
		@SuppressWarnings("resource")
		Scanner console=new Scanner(System.in);
		while(true)
		{
			
			while(!console.hasNextLine())
			{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			String input=console.nextLine();
			if(input.toLowerCase().equals("quit"))
			{
				break;
			}
			if(input.toLowerCase().equals("pickRoom"))
			{
				input=console.nextLine();
				ClientThread.c.SetRoom(input);
			}
			else
			{
				ClientThread.ClientOutServerIn(input);
			}
		}
		ClientThread.CloseClient();
	}
}
