package cn.lawliet.bingogame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;



/**
 * ��Ϸ�Ŀͻ���
 * 
 * @author Lawliet
 *
 */
public class BingoClient {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// ����tcp�Ŀͻ��˷���
		Socket socket = new Socket("192.168.43.64", 9090);
		// ��ȡsocket�����������
		OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream());
		// ��ȡsocket������������
		BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// ��ȡ���̵����������󣬶�ȡ����
		BufferedReader keyReader = new BufferedReader(new InputStreamReader(System.in));

		InputStream ips = socket.getInputStream();
	    ObjectInputStream ois = new ObjectInputStream(ips);
	        
		String line = null;

		// ���ϵĶ�ȡ����¼������ݣ�Ȼ�������д��
		while ((line = keyReader.readLine()) != null) {

			socketOut.write(line + "\r\n");
			// ˢ��
			socketOut.flush();
			// ��ȡ����˻��͵�����
			boolean flag = (boolean) ois.readObject();
			
			if(flag){
				System.out.println("�Բ��𣬱����һ��ʤ���������ˣ�");
				break;
			}
			line = socketReader.readLine();
			System.out.println("����˻��͵������ǣ�" + line);
			if (line.equals("��ϲ��¶��ˣ�")) {
				System.out.println("��Ϸ������");
				break;
			}
			
		}
		// �ر���Դ
		socket.close();
	}
}
