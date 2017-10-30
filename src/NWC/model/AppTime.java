package NWC.model;

public class AppTime {
	private int head = 0;
	private int tail = 0;
	public int[] buffer = {0,0,0,0,0,0,0,0};
	
	public AppTime(int[] buffer) {
		this.buffer = buffer;
		for (int i=0;i<buffer.length;i++) {
			if (buffer[i] == 1) {
				head = i+10;
				break;
			}
		}
		for (int i=buffer.length-1; i>=0 ;i--) {
			if (buffer[i] == 1) {
				tail = i+11;
				break;
			}
		}
	}
	
	public AppTime(int head, int tail) {
		this.head = head;
		this.tail = tail;
		
		for (int i=(head-10); i<(tail-10); i++) {
			buffer[i] = 1;
		}
	}
	
	public void setBuffer(int[] buffer) {
		this.buffer = buffer;
	}
	
	public void flip(int[] buffer) {
		for (int i=0; i<8; i++) {
			if (buffer[i] == 1) {
				this.buffer[i] = -1;
			}
		}
	}
	
	public void add(AppTime a) {
		for (int i=0;i<8;i++) {
			if (this.buffer[i] != 1 && a.buffer[i] == 1) {
				this.buffer[i] = 1;
			}
		}
	}
	
	public String bufferTS() {
		String tempString = "[";
		for (int i=0; i<buffer.length; i++) {
			tempString += buffer[i];
		}
		return tempString += "]";
	}
	
	public String toString() {
		return String.format("%d:00-%d:00", head, tail);
	}
	
	public boolean matches(int[] buffer) {
		for (int i=0;i<buffer.length; i++) {
			if (this.buffer[i] == 1) {
				if (buffer[i] != 1) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean isEmpty(int[] buffer) {
		for (int i=0;i<buffer.length;i++) {
			if (buffer[i] == 1) 
				return false;
		}
		return true;
	}
	
	public boolean isFull() {
		for (int i=0;i<buffer.length;i++) {
			if (buffer[i] == 0) 
				return false;
		}
		return true;
	}
	
}
