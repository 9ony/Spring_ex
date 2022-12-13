package com.mongo.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

public class PostManager {
	MongoClient mclient;
	MongoDatabase mongodb;
	MongoCollection<Document> mcol;
	Scanner sc = new Scanner(System.in);
	
	public PostManager() {
		MongoClient mclient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase mongodb = mclient.getDatabase("mydb");
		mcol=mongodb.getCollection("posts");
		System.out.println("mongodb Connect!!");
	}
	
	public void close() {
		if(mclient!=null) {
			mclient.close();
		}
	}
	//posts 1개 데이터 삽입
	public void insertPosts() {
		System.out.println("작성자 입력");
		String author=sc.nextLine();
		System.out.println("제목 입력");
		String title=sc.nextLine();
		System.out.println("날짜");
		Date today=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String wdate=sdf.format(today);
		
		Document doc=new Document();
		doc.append("author", author)
			.append("title", title)
			.append("wdate", wdate);
		InsertOneResult res = mcol.insertOne(doc);
		System.out.println(res.getInsertedId()+"도큐먼트가 생성되었어요");
		
	}

	//posts 모든 목록 가져오기
	public void selectPostsAll() {
		FindIterable<Document> cursor = mcol.find();
		for(Document i:cursor) {
			//키값으로 데이터하나씩 출력
			//System.out.println(i.get("title"));
			//System.out.println(i.get("author"));
			//System.out.println(i.get("wdate"));
			//json타입으로 출력
			System.out.println(i.toJson());
		}
	}
	//posts 모든 목록 가져오기2
	public void selectPostsAll2() {
		FindIterable<Document> cursor = mcol.find();
		MongoCursor<Document> mcr=cursor.iterator();
		while(mcr.hasNext()) {
			//System.out.println(mcr.next());
			Document doc=mcr.next();
			String title=doc.getString("title");
			String author=doc.getString("author");
			String wdate=doc.getString("wdate");
			System.out.println(title+"\t"+author+"\t"+wdate);
		}
	}
	//posts 삭제
	private void deletePosts() {
		System.out.println("삭제할 작성자명 입력");
		String author=sc.nextLine();
		//com.mongodb.client.model.Filters 를임포트해서 몽고디비 필터를 이용할수 있다
		//Filters.eq , ne , or ,regex~~ 등등..
		DeleteResult dres = mcol.deleteOne(Filters.eq("author",author)); //1개만삭제
		//DeleteResult dres = mcol.deleteMany(Filters.eq("author",author));	//여러개 삭제
		long n = dres.getDeletedCount();
		System.out.println(n+"개의 도큐먼트 삭제");
	}
	//posts 수정
	private void updatePosts() {
		System.out.println("검색할 작성자명: ");
		String author=sc.nextLine();
		System.out.println("수정할 제목:");
		String title=sc.nextLine();
		Bson edittitle=Updates.combine(Updates.set("title", title));
		UpdateResult ures = mcol.updateOne(Filters.eq("author",author),edittitle );
		long n=ures.getModifiedCount();
		System.out.println(n+"개의 도큐먼트가 수정되엇어요");
	}

	public static void main(String[] args) {
		PostManager app=new PostManager();
		//app.insertPosts();
		System.out.println("----Posts 목록----");
		//app.selectPostsAll();
		app.selectPostsAll2();
		//app.deletePosts();
		app.updatePosts();
		System.out.println("----수정후 Posts 목록----");
		app.selectPostsAll();
	}

}
