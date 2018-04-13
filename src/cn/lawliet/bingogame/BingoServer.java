package cn.lawliet.bingogame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;

/**
 * ��Ϸ�ķ����
 * 
 * @author Lawliet
 *
 */
public class BingoServer extends Thread {

	Socket socket;
	static int res = 1;
	//static int res = (int) (Math.random() * 10);
	static boolean flag = false;
	
	static HashSet<String> ips = new HashSet<String>();
	public BingoServer(Socket socket){
		this.socket = socket;
	}
	
	@Override
		public void run() {
			try {
				//��ȡ��Socket������������
				BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// ��ȡ��Socket���������
				OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream());
				// ��ȡ���̵�����������
				BufferedReader keyReader = new BufferedReader(new InputStreamReader(System.in));
				// ��ȡ�ͻ��˵�����
				OutputStream ops = socket.getOutputStream();
		        ObjectOutputStream oos = new ObjectOutputStream(ops);
				String line = null;
				while ((line = socketReader.readLine()) != null) {
					String ip = socket.getInetAddress().getHostAddress();
		            if(ips.add(ip)) {
		                System.out.println("��ϲ" + ip + "ͬѧ�ɹ����ӷ�������");
		            }
					System.out.println(ip + "ͬѧ������������  ��" + line);
					String bingo = Integer.toString(res);
					if (line.equals(bingo)) {
						line = "��ϲ��¶��ˣ�";
						flag = true;
						oos.writeObject(flag);
						socketOut.write(line + "\r\n");
						socketOut.flush();
					} else {
						line = "���ź���û�в¶ԣ�����һ�Σ�";
						oos.writeObject(flag);
						socketOut.write(line + "\r\n");
						socketOut.flush();
					}
					if(flag){
						line = "��ϲ" + ip + "ͬѧ���ʤ����";
						socketOut.write(line + "\r\n");
					}
					
				}
				// �ر���Դ
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
				//System.out.println("�������쳣��");
			}
		}	
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(9090);
		while(true){
			Socket socket = serverSocket.accept();
			new BingoServer(socket).start();
		}
	}
	
}