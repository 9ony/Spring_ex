package com.mongo.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.UpdateResult;

public class TestMongoPOJO{
	String db ="mydb";
	String table="posts";
	MongoClient mclient;
	MongoDatabase mdb;
	MongoCollection<PostVO> mcol;
	Scanner sc = new Scanner(System.in);
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
	public void insertMany() {
		mcol = mdb.getCollection(table,PostVO.class);
		List<PostVO> postsArr=Arrays.asList(
				new PostVO(null,"Scott","추운 날씨입니다","2022-12-14"),
				new PostVO(null,"James","감기 조심하세요","2022-12-14"),
				new PostVO(null,"King","알겠습니다","2022-12-14")
				);
		InsertManyResult res=mcol.insertMany(postsArr);
		int n = res.getInsertedIds().size();
		System.out.println(n+"개의 도큐먼트 삽입 완료");
	}//----------
	public void findAll() {
		mcol=mdb.getCollection(table,PostVO.class);
		FindIterable<PostVO> cursor=mcol.find();
		MongoCursor<PostVO> mcr=cursor.iterator();
		List<PostVO> arr=new ArrayList<>();
		System.out.println("------------------------");
 		while(mcr.hasNext()) {
			PostVO vo=mcr.next();
			arr.add(vo);
			System.out.println(vo.toString());
		}
 		//System.out.println(arr);
 		System.out.println("-----------------------");
	}
	public void findByAuthor() {
		System.out.println("검색할 글의 작성자를 입력하세요");
		String S_author = sc.nextLine();
		mcol=mdb.getCollection(table,PostVO.class);
		//특정조건검색은 필터객체 필요
		FindIterable<PostVO> cursor =mcol.find(Filters.eq("author", S_author));
		//PostVO vo1=cursor.first();
		System.out.println("---검색어 : "+S_author+"---");
		for(PostVO vo:cursor) {
			System.out.println(vo.getAuthor()+"\t"+vo.getTitle());
		}
		System.out.println("-------------------------");
	}//---------------
	public void delete() {
		System.out.println("삭제할 글의 작성자를 입력하세요");
		String D_author = sc.nextLine();
		//DeleteResult res = mcol.deleteOne(Filters.eq("author",D_author));
		DeleteResult res = mcol.deleteMany(Filters.eq("author",D_author));
		System.out.println(res.getDeletedCount()+"개의 도큐먼트가 삭제됨");
	}//-----------------
	public void updateOne() {
		System.out.println("수정할 글의 작성자를 입력하세요");
		String author=sc.nextLine();
		System.out.println("수정할 글 제목 : ");
		String title = sc.nextLine();
		System.out.println("수정할 글의 작성일(YYYY-MM-DD)");
		String wdate=sc.nextLine();
		mcol=mdb.getCollection(table,PostVO.class);
		
		//import static 사용해서 줄여도됨
		UpdateResult res = mcol.updateOne(Filters.eq("author",author), 
				Updates.combine(Updates.set("title", title),Updates.set("wdate", wdate)));
	
		long n = res.getModifiedCount();
		System.out.println(n+"개의 도큐먼트가 수정되었습니다");
	}//-------------
	public void updateMany() {
		System.out.println("수정하지 않을 작성자를 입력하세요");
		String author=sc.nextLine();
		System.out.println("수정할 글 제목 : ");
		String title = sc.nextLine();
		System.out.println("수정할 글의 작성일(YYYY-MM-DD)");
		String wdate=sc.nextLine();
		mcol=mdb.getCollection(table,PostVO.class);
		
		//Filters.ne = not equals
		UpdateResult res = mcol.updateMany(Filters.ne("author",author), 
				Updates.combine(Updates.set("title", title),Updates.set("wdate", wdate)));
	
		long n = res.getModifiedCount();
		System.out.println(n+"개의 도큐먼트가 수정되었습니다");
	}//-------------
	public void replaceOne() {
		mcol=mdb.getCollection(table,PostVO.class);
		PostVO vo=new PostVO(null,"kim","replaceOne으로 Posts내용을 변경합니다","2022-12-14");
		UpdateResult res=mcol.replaceOne(Filters.eq("author",vo.getAuthor()), vo);
		System.out.println(res.getModifiedCount()+"개의 도큐먼트가 변경되었습니다");
	
	}
	public static void main(String[] args) {
		TestMongoPOJO app=new TestMongoPOJO();
		//app.insertOne();
		//app.insertMany();
		app.findAll();
		//app.findByAuthor();
		//app.delete();
		//app.updateMany();
		//app.replaceOne();
		
	}
}