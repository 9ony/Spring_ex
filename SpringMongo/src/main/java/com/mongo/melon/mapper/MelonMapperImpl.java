package com.mongo.melon.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongo.melon.domain.MelonVO;
import com.mongo.melon.domain.SumVO;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

@Repository
public class MelonMapperImpl implements MelonMapper {

	@Inject
	private MongoTemplate mTemplate;

	@Override
	public boolean createCollection(String colName) throws Exception {
		
		return false;
	}

	@Override
	public int insertMelon(List<MelonVO> mList, String colName) throws Exception {
		System.out.println(colName+">>>>>");
		Collection<MelonVO> arr=mTemplate.insert(mList, colName);
		return arr.size();		
	}

	@Override
	public List<MelonVO> getMelonList(String colName) throws Exception {
		// TODO Auto-generated method stub
		return this.mTemplate.findAll(MelonVO.class,colName);
	}
	/*
		{db.getCollection("Melon_20221219").aggregate(
	    [
	        { 
	            "$group" : { 
	                "_id" : { 
	                    "singer" : "$singer"
	                }, 
	                "count" : { 
	                    "$sum" : 1.0
	                }
	            }
	        }, 
	        { 
	            "$project" : { 
	                "singer" : "$_id.singer", 
	                "cntBySinger" : "$count"
	            }
	        },
	        {
	        	"$sort" : {
	        		"cntBySinger":-1
	        	}
	        }
	    ], 
	    { 
	        "allowDiskUse" : false
	    }
		)
	*/
	@Override
	public List<SumVO> getCntBySinger(String colName) throws Exception {
		MongoCollection<Document> col=mTemplate.getCollection(colName);
		
		List<? extends Bson> pipeline=Arrays.asList(
			new Document().append("$group", new Document().append("_id", 
					new Document().append("singer", "$singer")).append("count", new Document().append("$sum", 1))),	 //해당가수의 차트인 곡개수당 +1증가
			new Document().append("$project", new Document().append("singer", "$_id.singer").append("cntBySinger", "$count")), //위에서 구해진 가수의 카운트개수
			new Document().append("$match", new Document().append("cntBySinger", new Document().append("$gt", 1))), //1개이상인것
			new Document().append("$sort", new Document().append("cntBySinger", -1)) //
				);
		
		
		AggregateIterable<Document>  cr= col.aggregate(pipeline);
		List<SumVO> arr=new ArrayList<>();
		for(Document doc:cr) {
			if(doc==null) {
				doc=new Document();
			}
			String singer=doc.getString("singer");
			int cntBySinger=doc.getInteger("cntBySinger", 0);//key값이 없으면 디폴트값은 0
			SumVO svo=new SumVO();
			svo.setSinger(singer);
			svo.setCntBySinger(cntBySinger);
			arr.add(svo);
		}//for-------------
		 
		return arr;
	}

	@Override
	public List<MelonVO> getMelonListBySinger(String colName, String singer) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
