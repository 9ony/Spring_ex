package com.mongo.crawl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;

public class CrawlingTest2 {
	String db ="mydb";
	MongoClient mclient;
	MongoDatabase mdb;
	MongoCollection<MelonVO> mcol;
	public CrawlingTest2() {
		this.mappingPojo();
	}
	public static void main(String[] args) throws IOException{
		String url = "https://www.melon.com/chart/index.htm";
		Document doc = Jsoup.connect(url).get();
		Elements divEle=doc.select("div.service_list_song>table>tbody");	
		Elements songEle=divEle.select("tr>td:nth-child(6) div.wrap_song_info");

		Elements imgEle=divEle.select("div.wrap img");
		List<MelonVO> melonArr=new ArrayList<>();
		for(int i=0; i<songEle.size();i++) {
			Element songInfo=songEle.get(i);
			Element imgInfo=imgEle.get(i);
			//제목추출
			String songTitle=songInfo.select("div.ellipsis.rank01>span>a").text();
			//가수추출
			String songSinger=songInfo.select("div.ellipsis.rank02>a").text();
			//이미지 추출
			String imgSrc=imgInfo.attr("src");
			//이미지링크로 이미지 다운
			saveAlbumImg((i+1),imgSrc,songTitle);
			
			MelonVO vo=new MelonVO((i+1),songTitle,songSinger,imgSrc);
			melonArr.add(vo);
		}
		CrawlingTest2 app = new CrawlingTest2();
		app.saveMongodb(melonArr);
	}
	public static synchronized void saveAlbumImg(int num,String imgurl,String title) {
		try {
			URL url = new URL(imgurl);
			InputStream is=url.openStream();
			BufferedInputStream bis=new BufferedInputStream(is);
			File dir=new File("C:/myjava/crawling/melon_20221214");
			if(!dir.exists()) {
				dir.mkdirs();
			}
			File target=new File(dir,num+"album_"+title+".jpg");
			BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(target));
			
			byte[] data=new byte[1024];
			int n=0;
			while((n=bis.read(data))!=-1) {
				bos.write(data,0,n);
			}
			bos.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void saveMongodb(List<MelonVO> arr) {
		String collectionName="melonChart_20221214";
		mcol=mdb.getCollection(collectionName,MelonVO.class);
		InsertManyResult res=mcol.insertMany(arr);
		int n = res.getInsertedIds().size();
		System.out.println(n+"개의 도큐먼트 삽입 완료");
		mclient.close();
	}
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

}
