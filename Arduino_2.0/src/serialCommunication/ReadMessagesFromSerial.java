package serialCommunication;

public class ReadMessagesFromSerial extends Thread{
	
	public ReadMessagesFromSerial() {
		
	}
	
	public void run(CommChannel channel, SerialCommunication communication) throws Exception {
		
		while(true) {
			Thread.sleep(150);
			if(channel.isMsgAvailable()) {
				communication.messageArrived(channel.receiveMsg());
			}
		}
	}
}
