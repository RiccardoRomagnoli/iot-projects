package serialCommunication;

public class ReadMessagesFromSerial extends Thread{
	
	public ReadMessagesFromSerial() {
		
	}
	
	public void run(CommChannel channel, SerialCommunication communication) throws Exception {
		while(true) {
			if(channel.isMsgAvailable()) {
				communication.messageArrived(channel.receiveMsg());
			}
		}
	}
	
	public void start(CommChannel channel, SerialCommunication communication) throws Exception {
		while(true) {
			if(channel.isMsgAvailable()) {
				communication.messageArrived(channel.receiveMsg());
			}
		}
	}
}
