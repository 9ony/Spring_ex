package ex10;

import java.io.IOException;

public class MessageBeanImpl implements MessageBean {
	private String name;
	private String greeting;
	
	private Output out;
	
	public MessageBeanImpl(Output out) {
		this.out=out;
	}
	
	@Override
	public void sayhello() {
		// TODO Auto-generated method stub
		try {
		out.out(greeting + " " + name);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}//-----------------

	@Override
	public void sayHi(String... names) {
		// TODO Auto-generated method stub
		try {
			out.out(greeting);
			if(names!=null) {
				for(String name:names) {
					out.out(name+",");
				}
			}
			}catch(IOException e) {
				e.printStackTrace();
			}
	}//--------------
	
	//setter
	public void setName(String name) {
		this.name = name;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	
}
