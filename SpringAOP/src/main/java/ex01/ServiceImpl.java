package ex01;

public class ServiceImpl implements Service {

	@Override
	public void sayHello(String... names) {
		// TODO Auto-generated method stub
		for(int i=0;i<names.length;i++) {
			System.out.println("Hello~~"+names[i]);
		}
	}

}
