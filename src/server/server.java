/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
/**
 *
 * @author Mehmet
 */
public class server {

        
        private static DatagramSocket socketThis;
        private static Thread Listen;
        public HashMap<String, String> clients = new HashMap<String, String>();
	
        ServerSocket ss;
	boolean quite=false;
	ArrayList<MultiServerConnection> OurDomainsConnections=new ArrayList<MultiServerConnection>();
	
	public static void main(String[] args) {
		new server();
	}
	public server() {
		try {
			//TODO use method to take this as an input from user)
			ss=new ServerSocket(3333);
			while(!quite)
			{
				Socket s=ss.accept();//when a connection to the domain is found we accept it
				MultiServerConnection OurConnection = new MultiServerConnection(s,this);
				OurConnection.start();//Start Thread
				OurDomainsConnections.add(OurConnection);//add connection to our Domain Connection
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
