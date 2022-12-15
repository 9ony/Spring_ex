package com.mongo.test;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class PostVO {
	private ObjectId id;
	
	@BsonProperty(value="author")
	private String author;
	@BsonProperty(value="title")
	private String title;
	@BsonProperty(value="wdate")
	private String wdate;
	
}
