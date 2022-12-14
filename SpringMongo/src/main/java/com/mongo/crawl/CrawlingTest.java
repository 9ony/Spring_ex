package com.mongo.crawl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlingTest {

	public static void main(String[] args) throws IOException{
		String url = "https://www.melon.com/chart/index.htm";
		//python=> BeautifulSoup, Java=> Jsoup
		Document doc = Jsoup.connect(url).get();
		//System.out.println(doc);
		Elements divEle=doc.select("div.service_list_song>table>tbody");
		//System.out.println(divEle);		
		Elements songEle=divEle.select("tr>td:nth-child(6) div.wrap_song_info");

		Elements imgEle=divEle.select("div.wrap img");
		for(int i=0; i<songEle.size();i++) {
			Element songInfo=songEle.get(i);
			Element imgInfo=imgEle.get(i);
			System.out.println((i+1)+"등");
			//제목추출
			String songTitle=songInfo.select("div.ellipsis.rank01>span>a").text();
			System.out.println("제목 : "+songTitle);
			//가수추출
			String songSinger=songInfo.select("div.ellipsis.rank02>a").text();
			System.out.println("가수 : "+songSinger);
			//이미지 추출
			String imgSrc=imgInfo.attr("src");
			System.out.println("이미지 : "+imgSrc);
		}
		
//		for(int i=0; i<imgEle.size();i++) {
//			Elements imgEle=divEle.select("div.wrap img");
//			String imgSrc=imgInfo.attr("src");
//			System.out.println(imgSrc);
//		}
	}

}
