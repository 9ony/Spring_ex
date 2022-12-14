package com.mongo.crawl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MelonVO {
	
	private int ranking;
	private String songTitle;
	private String songSinger;
	private String songUrl;
	
}
