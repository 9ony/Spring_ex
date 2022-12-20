package ex01;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
//commons.io 라이브러리 필요
public class TextFileReadApache {

	public static void main(String[] args) throws IOException {
		File file=new File("src/main/java/ex01/readme.txt");
		//try {}catch{} or throws
		String str = FileUtils.readFileToString(file, "utf-8"); //(file,charset)
		//commons.io.FileUtils.readFileToString를 사용하면 Stream객체를 이용해 반복해서 읽어드리는것까지 해준다
		System.out.println(str);
	}
	
}
