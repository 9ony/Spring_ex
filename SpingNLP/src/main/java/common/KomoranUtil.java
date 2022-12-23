package common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.springframework.stereotype.Component;

import common.nlp.WordCount;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class KomoranUtil {
	
	static Komoran nlp=new Komoran(DEFAULT_MODEL.FULL);
	
	public static List<String> getWordNouns(String str){
		String txt=str.replaceAll("[^가-힣a-zA-Z0-9]", " ");
		txt=txt.trim();//앞뒤 공백 제거
		System.out.println(txt);
		System.out.println("----------------------------");
		//형태소 분석 시작
		KomoranResult res=nlp.analyze(txt);
		//형태소 분석 결과 중 명사만 추출해보자.
		List<String> nounList=res.getNouns();
		
		if(nounList==null) {
			nounList=new ArrayList<>();
		}
		log.info("nounList===="+nounList);
		//nounList.forEach((s)->System.out.println(s));
		return nounList;
	}//----------------------------------------
	
	public static Map<String, Integer> getWordCount(List<String> nounList){
		if(nounList==null) {
			nounList=new ArrayList<>();
		}
		//(단어, 빈도수) (눈, 3)
		Map<String, Integer> wMap=new HashMap<>();
		Set<String> set=new HashSet<>(nounList);
		//Set유형은 중복된 데이터가 저장되지 않음.
		
		Iterator<String> it=set.iterator();
		
		while(it.hasNext()) {
			String word=it.next();
			//중복된 단어목록에서 중복되지 않은 단어의 카운트수를 구함
			int frequency=Collections.frequency(nounList, word);
			log.info(word+": "+word+", frequency: "+frequency);
			wMap.put(word, frequency);
		}
		return wMap;
	}//--------------------------------
	
	//카운팅 된 단어 빈도수 맵에서 빈도수가 2개 이상인 단어들만 sorting해서 반화는 메서드
	public static List<WordCount> getWordCountSortProc(Map<String, Integer> map, int n){
		
		PriorityQueue<WordCount> pq=new PriorityQueue<WordCount>();
		Set<String> set=map.keySet();//key값들만 set유형으로 추출
		for(String key:set) {
			Integer val=map.get(key);
			WordCount wc=new WordCount();
			wc.setWord(key);
			wc.setCnt(val);
			pq.add(wc);
		}
		
		List<WordCount> arr=new ArrayList<>();
		while(! pq.isEmpty()) {
			WordCount wc=pq.poll();
			if(wc.getWord().length()>=1&& wc.getCnt()>n) {
				//특정 빈도수 이상인 요소들만 ArrayList에 담자
				arr.add(wc);
			}			
		}
		return arr;
	}//-----------------------------------
	
	//pos태깅 중에서 지정된 품사의 문자열만 추출해서 카운트를 세어 반환하는 메서드
	public static List<WordCount> getWordByTag(String text, int minCnt,String...tags){
		KomoranResult res=nlp.analyze(text);
		List<String> arr=res.getMorphesByTags(tags);
		if(arr==null) {
			arr=new ArrayList<>();
		}
		
		Map<String, Integer> wordCount=getWordCount(arr);
		if(wordCount==null) {
			wordCount=new HashMap<String,Integer>();
		}
		
		List<WordCount> arr2=getWordCountSortProc(wordCount, minCnt);
		return arr2;		
	}
	
	

}



