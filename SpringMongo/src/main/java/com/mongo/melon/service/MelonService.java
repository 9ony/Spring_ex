package com.mongo.melon.service;

import java.util.List;

import com.mongo.melon.domain.MelonVO;
import com.mongo.melon.domain.SumVO;

public interface MelonService {

	int crawlingMelon() throws Exception;

	List<MelonVO> getMelonList() throws Exception;

	List<SumVO> getCntBySinger() throws Exception;

	List<MelonVO> getMelonListBySinger(String colName, String singer) throws Exception;
	
}
