package com.mongo.test;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestMongoPOJO{
	String db ="mydb";
	String table="posts";
	MongoClient mclient;
	MongoDatabase mdb;
	MongoCollection<PostVO> mcol;
	
	public TestMongoPOJO() {
		this.mappingPojo();
	}
	//Bson문서를 Java Pojo객체에 매핑하는 메서드 ==> 코덱레지스트리가 필요함!
	private void mappingPojo() {
		ConnectionString conStr=new ConnectionString("mongodb://localhost:27017");
		CodecRegistry pojoCodec=CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry=CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodec);
		//몽고디비에서 가져온 BSon데이터를 자바 POJO로 인코딩,디코딩 하는 작업
		MongoClientSettings clientSettings=MongoClientSettings.builder()
				.applyConnectionString(conStr)
				.codecRegistry(codecRegistry)
				.build();	
		mclient=MongoClients.create(clientSettings);
		mdb=mclient.getDatabase(db);
	}//-------------------
	
	public void insertOne() {
		mcol=mdb.getCollection(table,PostVO.class);
		PostVO vo=new PostVO(null,"kim","오늘도 수고 많으셨어요","2022-12-13");
		mcol.insertOne(vo);
		System.out.println(vo.getTitle()+"글을 등록했습니다.");
	}//----------------
	
	public static void main(String[] args) {
		TestMongoPOJO app=new TestMongoPOJO();
		app.insertOne();
	}
}