
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class NettyRunner implements Runnable{
	public static void main(String[] args) throws Exception{
		Date runTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-14 18:40:00");
		while(true){
			if(new Date().compareTo(runTime) > 0){
				break;
			}
			Thread.sleep(1000);
		}
System.out.println("begin run.");
		int socketPort = 24000;//这个参数不用改，每台测试机器有不同的端口号，用来做设备号，不能重复
		for(int i = 1;i <= 250;i++){
			String ip = "139.159.196.163";
			int port = 6666;

			//8位设备号
			String deviceNo = String.valueOf(socketPort);
			while (deviceNo.length() < 16) {
				deviceNo = "0" + deviceNo;
			}
			NettyRunner runner = new NettyRunner(ip, port, socketPort, deviceNo);
			runner.start();
			socketPort++;
			if(i%2==0){
//				Thread.sleep(10);
			}
		}

	}


	String ip;
	int port;
	int socketPort;
	String deviceNo;
	DatagramSocket socket = null;
	boolean isLogin = false;//是否已经登录
	long sendTime = 0;
	int successCount = 0;//成功的次数
	int transCount = 60;//客户端传输数据的次数
	int openDoorCount = 0;//接收开门请求次数
	StringBuilder countStr = new StringBuilder();//接收成功的次数
	StringBuilder sendcountStr = new StringBuilder();//发送成功的次数

	@Override
	public void run() {
		try {
			socket = new DatagramSocket(socketPort);
			final InetAddress address = InetAddress.getByName(ip);
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						while(true){
							byte[] buffer = new byte[124];
							DatagramPacket receiver = new DatagramPacket(buffer, buffer.length);
							socket.receive(receiver);

							String resAll = getHexStr(receiver.getData());
							Collection<String> collection = getHexStrArray(resAll);
							if(collection != null && !collection.isEmpty()){
								for(String res : collection){
									String oriCrc16 = res.substring(res.length() - 4);//服务器端的签名
									String nowCrc16 =  getCRC16_des2(res.substring(0,res.length() - 4));

									if(!oriCrc16.equals(nowCrc16)){
										System.err.println(deviceNo + "==>签名检验失败，oriCrc16：" + oriCrc16 + ";nowCrc16:" + nowCrc16);
										continue;
									}

									boolean isLoginSuccess = res.indexOf(deviceNo + "0003090100") > -1;
									boolean isHeart = res.indexOf(deviceNo + "00140902") > -1;
									boolean isOpenDoor = res.indexOf(deviceNo+ "0008" + "0A7C") > -1;
									long currentTimeMillis = System.currentTimeMillis();
									long responseTime = currentTimeMillis - sendTime;//反应时间
									if(isLoginSuccess){
										//3AA300000001522021122500000600030901003A6E
										isLogin = true;
										if(responseTime > 1500){
											System.err.println("deviceNo:" + deviceNo +"登录反应时间过长，时间：" + responseTime);
										}else{
											successCount++;
										}
									}else if(isHeart){
										//3AA300000001522021122500000600140902AD07E6000B0F0E3302081E161E081E161E01EBAF
										if(responseTime > 1000){
											System.err.println("deviceNo:" + deviceNo +"心跳反应时间过长，时间：" + responseTime);
										}else{
											successCount++;
										}
										countStr.append("," + res.substring(res.length() - 6, res.length() - 4));
									}else if(isOpenDoor){
										//3AA300000001522021122500000600080A7C215190110015
										byte[] data = getOpenDoorSuccessStr(deviceNo);
										DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
										socket.send(packet);
										openDoorCount++;
									}else{
										System.err.println("=======================出错了====================================");
									}
									//System.out.println(deviceNo + "==>" + responseTime + ";res:" + res + ";openDoorCount:" + openDoorCount + ";successCount:" + successCount);
								}
							}else{
								System.err.println(deviceNo + "==>没接收到数据");
							}
						}
					}catch (SocketException e) {
						//e.printStackTrace();
					}catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();
			//心跳60次，10分钟
			for(int i = 0;i<transCount;i++){
				byte[] data;
//			System.out.println("isLogin:" + isLogin);
				if(!isLogin){
					data = getLoginStr(deviceNo);
				}else{
					data = getHeartStr(deviceNo,i);
				}
				DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
				socket.send(packet);

				if(isLogin){
					sendcountStr.append("," + (i));
				}
				sendTime = System.currentTimeMillis();
				Thread.sleep(5* 1000);
//			Thread.sleep(300);
			}
			Thread.sleep(10 * 1000);

			String noReciveCount = "";
			for(int i=1;i<=transCount;i++){
				String str = (i < 9) ? ("0" + i) : String.valueOf(i);
				if(this.countStr.indexOf(str) < 0){
					noReciveCount += "," + str;
				}
			}
//		System.err.println(deviceNo + "====>" + sendcountStr);
			if(transCount != successCount || openDoorCount != 6){
				System.err.println(deviceNo + "====>传输次数：" + transCount + ";丢包次数：" + (transCount-successCount) + ";接收开门请求次数：" + openDoorCount + ";noReciveCount:" + noReciveCount + ";hadReciveCount:" + countStr);
			}else{
			//	System.out.println(deviceNo + "====>传输次数：" + transCount + ";丢包次数：" + (transCount-successCount) + ";接收开门请求次数：" + openDoorCount + ";noReciveCount:" + noReciveCount + ";hadReciveCount:" + countStr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			socket.close();
		}
	}

	public static byte[] getLoginStr(String deviceNo){
		String loginStr = "3AA300000001" + deviceNo + "001D0901520000010308000000015220211208092501000000000000010100";
		loginStr += getCRC16_des2(loginStr);
		return gb(loginStr);
	}
	public static byte[] getHeartStr(String deviceNo,int count){
		//最后一位用来表示数据发送次数
		String countHex = String.valueOf(count);
		if(countHex.length() == 1){
			countHex = "0" + countHex;
		}
		countHex = countHex.toUpperCase();


		String heartStr = "3AA300000001" + deviceNo + "0026090200010000AD001D15010000010000000000000000000000000000000000000000000000" + countHex;
		//变换不同事件，测试redis处理
		if(count % 7 == 0){//电量
			heartStr = "3AA300000001" + deviceNo + "0026090200010000AD001D11010000010000000000000000000000000000000000000000000000" + countHex;
		}else if(count % 8 == 0){//门事件
			heartStr = "3AA300000001" + deviceNo + "0026090200010000AD001D11010000000000000000000000000000000000000000000000000000" + countHex;
		}else if(count % 11 == 0){//电源事件
			heartStr = "3AA300000001" + deviceNo + "0026090200010000AD001D11010000010000000000000000000000000000000000001000000000" + countHex;
		}else if(count % 9 == 0){//通知开门
			heartStr = "3AA300000001" + deviceNo + "0026090200010000AD001D110100000100000000000000000000000000000000000010FFFFFFFF" + countHex;
		}
		heartStr += getCRC16_des2(heartStr);
		return gb(heartStr);
	}
	public static byte[] getOpenDoorSuccessStr(String deviceNo){
		String openDoorSuccessStr = "3AA300000001" + deviceNo + "00070A7C0000000100";
		openDoorSuccessStr += getCRC16_des2(openDoorSuccessStr);
		return gb(openDoorSuccessStr);
	}

	public static String getHexStr(byte[] bytes){
		StringBuilder sb = new StringBuilder();
		for(byte b : bytes){
			int i = b >= 0 ? b : b + 256;
			String hexString = Integer.toHexString(i);
			if(hexString.length() == 1){
				hexString = "0" + hexString;
			}
			sb.append(hexString + "");
		}
		return sb.toString().toUpperCase();
	}
	/**
	 * 防止粘包，要拆开
	 */
	public static Collection<String> getHexStrArray(String content){
		Set<String> contentSet = new HashSet<String>();//用set保存，可以去除重复的命令
		try {
			String startStr = "3AA30000000";//命令的开始
			String[] contentArray = content.split(startStr);
			for(String s : contentArray){
				if(s.length() > 10){//加上8位的设备号后，没有命令小于这个长度的
					//报错：3AA300000001 0000000000010456 0014 0902 AD 07E6000B14170B020900161E0900161E033AA3000000
					String completeContent = startStr + s;
					int dataLentgth = Integer.valueOf(completeContent.substring(28, 32), 16);
					contentSet.add(completeContent.substring(0,(16+dataLentgth + 2) * 2));
				}
			}
		} catch (Exception e) {
			/*System.err.println("处理粘包时，发生错误," + content);
			e.printStackTrace();*/
		}
		return contentSet;
	}
	/**
	 * CRC检验
	 *
	 * @param Source
	 * @return
	 */
	public static String getCRC16_des2(String source) {
		int crc = 0xA1EC; // 初始值
		int polynomial = 0x1021; // 校验公式 0001 0000 0010 0001
		byte[] bytes = gb(source);//stringToHexByte(source); // 把普通字符串转换成十六进制字符串

		for (byte b : bytes) {
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b >> (7 - i) & 1) == 1);
				boolean c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xffff;
		StringBuffer result = new StringBuffer(Integer.toHexString(crc));
		while (result.length() < 4) { // CRC检验一般为4位，不足4位补0
			result.insert(0, "0");
		}

		return result.toString().toUpperCase();
	}
	public static byte[] gb(String str){
		String[] split = str.split(" ");
		if(split.length == 1){
			split = transToArray(str);
		}


		byte[] bs = new byte[split.length];
		int i = 0 ;
		for(String s : split){
			bs[i] = (byte) Integer.parseInt(s, 16);
			i++;
		}
		return bs;
	}
	public static String[] transToArray(String str){
		int length = str.length() / 2;
		String[] split = new String[length];
		for(int i = 0;i < length;i++){
			split[i] = str.substring(i*2, (i*2)+2);
		}
		return split;
	}
	public void start(){
		new Thread(this).start();
	}
	public NettyRunner(String ip, int port, int socketPort, String deviceNo) {
		super();
		this.ip = ip;
		this.port = port;
		this.socketPort = socketPort;
		this.deviceNo = deviceNo;
	}


	public String getIp() {
		return ip;
	}
	public int getPort() {
		return port;
	}
	public int getSocketPort() {
		return socketPort;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setSocketPort(int socketPort) {
		this.socketPort = socketPort;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
}
