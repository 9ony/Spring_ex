package com.memo.model;

import java.util.List;
//영속성 계층 (Persistence Layer)  dao는 영속성 계층에 속한다 : @Repository 어노테이션을 붙인다

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

//@Repository를 써도 자동으로 주입되는게아니라
//servlet-context.xml안에 <context:component-scan/> 에 패키지를 설정해줘야함
@Repository
public class MemoDAOMybatis implements MemoDAO {

	//네임스페이스(NS) 설정
	private final String NS="com.memo.model.MemoMapper";
	
	//리소스 이름으로 주입
	@Resource(name="sqlSessionTemplateBean")
	private SqlSessionTemplate session;
	
	@Override
	public int insertMemo(MemoVO memo) {
		// TODO Auto-generated method stub
		return session.insert(NS+".getInsertMemo",memo);
	}

	@Override
	public List<MemoVO> listMemo() {
		return session.selectList(NS+".listMemo");
	}

	@Override
	public int deleteMemo(int idx) {
		// TODO Auto-generated method stub
		return session.delete(NS+".deleteMemo", idx);
	}

	@Override
	public int updateMemo(MemoVO memo) {
		// TODO Auto-generated method stub
		return session.update(NS+".updateMemo",memo);
	}

	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return session.selectOne(NS+".getTotalCount");
	}

	@Override
	public MemoVO selectMemo(int idx) {
		return session.selectOne(NS+".selectMemo", idx);
	}

}
