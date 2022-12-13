package ex02;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //기본생성자 생성
@AllArgsConstructor //인자값 받는 생성자 생성
public class BoardVO {
	private int num;
	private String title;
	private String name;
}
