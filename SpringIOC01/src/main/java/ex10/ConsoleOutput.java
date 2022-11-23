package ex10;

import java.io.IOException;

public class ConsoleOutput implements Output {
	private String path;
	
	public void setPath(String path) {
		this.path=path;
	}
	@Override
	public void out(String msg) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(msg);
		System.out.println(path);
	}

}
