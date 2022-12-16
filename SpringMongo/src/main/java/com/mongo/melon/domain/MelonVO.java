package com.mongo.melon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class MelonVO {
	
	@Id
	private String id;
	private int ranking;// 순위
	private String songTitle;// 노래제목
	private String singer;// 가수
	private String ctime;// 
	private String albumImage;// 이미지명
	
}
