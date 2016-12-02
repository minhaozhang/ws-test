package com.hfi;

import com.hfi.client.MyClientApp;
import com.hfi.sys.ApplicationContextHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Class Name: ServerStartUp
 * Description: TODO
 * @author zmh
 * @date 2015年12月22日 上午11:20:20
 */
public class ServerStartUp {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerStartUp.class);

	public static void main(String[] args) {

		//ApplicationContextHost.init();


		MyClientApp client = new MyClientApp();
		client.threadStart();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			do{
				input = br.readLine();
				if(!input.equals("exit"))
					client.session.getBasicRemote().sendText(input);

			}while(!input.equals("exit"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


