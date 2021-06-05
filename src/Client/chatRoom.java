/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import server.server;
/**
 *
 * @author Mehmet
 */
public class chatRoom extends Thread {
	
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	boolean quite=false;
	public ClientData c;
	public modal1 GUI;
        HashMap<String, String> clients=new server().clients;
	
	public chatRoom(Socket OurMultiSocket, modal1 gui)
	{
		s=OurMultiSocket;
		c=new ClientData();
		GUI=gui;
	}
	public void ClientOutServerIn(String Text)
	{
		
		try {
			if(Text.equals("pickRoom"))
			{
				dout.writeUTF(Text);
				dout.flush();
			}
			else if(Text.equals("userJoin"))
			{
				//System.out.print("sending new user: "+ Text+"\n");
				dout.writeUTF(Text+":"+c.GetName()+"="+c.GetRoom());
				dout.flush();
			}
			else
			{
				dout.writeUTF(c.GetRoom()+"="+this.getName()+": "+Text);
				dout.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void SetClient(String room,String Name)
	{
		c.SetName(Name);
		c.SetRoom(room);
	}
	public void run()
	{
		try {
			din=new DataInputStream(s.getInputStream());
			dout=new DataOutputStream(s.getOutputStream());
			while(!quite)
			{
                            
                           /* int i=clients.size();
                            String k=String.valueOf(i);
                            clients.put(k,c.GetName());
                            if(clients.size()>0){
                                for (int j = 0; j < clients.size(); j++) {
                                    GUI.setUserInChannel(clients.get(j));
                                }
                            }*/
				try {
					while(din.available()==0)
					{
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					String reply=din.readUTF();
					String room=ExtractChannel(reply);
					String name=ExtractName(reply);
					
					if(name.equals("userJoin"))
					{
						//System.out.print("new user in body: "+reply+"\n");
						setRoom(reply);
					}
					else
					{
						PrintReply(room,reply);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						din.close();
						dout.close();
						s.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				din.close();
				dout.close();
				s.close();
			} catch (IOException x) {
				// TODO Auto-generated catch block
				x.printStackTrace();
			}
		}
                
               
	}
	public void CloseClient()
	{
		try {
			din.close();
			dout.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String ExtractName(String x)
	{
		String[]Y=x.split(":");
		return Y[0];
	}
	public String ExtractChannel(String X)
	{
		String[]Y=X.split("=");
		return Y[0];
	}
	public void PrintReply(String room,String Rep)
	{
		if(c.GetRoom().equals(room))
		{
			String []Y=Rep.split("=");
			GUI.setDisplay(Y[1]);
		}
		
	}
	public void setRoom(String x)
	{
		String []Y=x.split(":");
		String []Z=Y[1].split("=");
		//System.out.print("setting "+Z[0]+" channel to "+Z[1]+"\n");
		GUI.setUserInChannel(Z[0]);
	}
	public void setChangedChannel()
	{
		GUI.setUserInChannel(c.GetName()+": "+c.GetRoom());
	}
	class ClientData
	{
		public String ClientName;
		public String room;
		
		public void SetRoom(String Chan)
		{
			room=Chan;
		}
		public void SetName(String name)
		{
			ClientName=name;
		}
		public String GetRoom()
		{
			return room;
		}
		public String GetName()
		{
			return ClientName;
		}
	}
	
}
