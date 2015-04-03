package com.dongfeng.function;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Transmission {

	private static Transmission trans = new Transmission();
	private DatagramSocket aSocket;
	private int port;
	private InetAddress host;
	public String getDistrict() {
		return district;
	}

	private String district;

	private Transmission() {
		// ------set the time out of the socket to 1000 ms
		try {
			aSocket = new DatagramSocket();
			aSocket.setSoTimeout(1000);
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

	public static Transmission getInstance() {
		return trans;
	}

	/*
	 * send the data to the server (data), before that, you need to do the
	 * initialization operation
	 */
	public String sendData(String data) {

		return sendData(data, port, host, 1);

	}

	/*
	 * initialize the transmission, set up the port and the host for the
	 * transmission
	 */
	public boolean initialization(int port, String host) {

		this.port = port;
		InetAddress aHost = null;
		try {
			aHost = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.host = aHost;

		// get the district name from the server
		String district_replydata = sendData("7:", this.port, this.host, 1);
		if (district_replydata.compareTo("null") == 0) {

			//fail to connect to the server
			return false;
			
		} else {
			String[] district_array = district_replydata.split(":");
			this.district = district_array[1];
			return true;
		}

	}

	private String sendData(String data, int port, InetAddress host, int count) {

		try {

			byte[] m = new String(data).getBytes("UTF-8");

			// ---------get the checksum value for 'm'
			Checksum checksum = new CRC32();
			checksum.update(m, 0, m.length);
			long checksumValue = checksum.getValue();
			byte[] CVByte = ByteBuffer.allocate(9).putLong(checksumValue)
					.array();

			// ---------combine the checksum value to the datagram packet
			byte[] combineValue = new byte[m.length + CVByte.length];
			System.arraycopy(CVByte, 0, combineValue, 0, CVByte.length);
			System.arraycopy(m, 0, combineValue, CVByte.length, m.length);

			// ------send the packet
			DatagramPacket request = new DatagramPacket(combineValue,
					combineValue.length, host, port);
			aSocket.send(request);

			// ------receive the data
			byte[] buffer = new byte[10000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);

			// ------check the reply message
			byte[] dataByte = Arrays.copyOfRange(reply.getData(), 0,
					reply.getLength());
			String validResult = new String(dataByte);
			if (validResult.compareTo("resend") != 0) {

				return validResult;

			} else {

				Thread.sleep(100);
				// ------send the data again, because the data is invalid
				System.out.println("Broken data, data being resent!");
				return sendData(data);

			}

		} catch (SocketTimeoutException e) {

			// Resends the data
			if (count <= 2) {

				count++;
				System.out.println("Timeout, data being resent!");
				return sendData(data, port, host, count);

			} else {

				return "null";

			}

		} catch (SocketException e) {

			System.out.println("Socket: " + e.getMessage());

		} catch (IOException e) {

			System.out.println("IO: " + e.getMessage());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "null";

	}
}
