package com.multi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.memo.model.MemoVO;

@Mapper
public interface MemoMapper {

	int insertMemo(MemoVO vo); // C
	
	List<MemoVO> listMemo(); // R 其捞隆贸府 x
	List<MemoVO> listMemoPaging(Map<String, Object> map); // 其捞隆贸府 o
	
	MemoVO getMemo(int no); // R
	
	int deleteMemo(int no); // D
	
	int updateMemo(MemoVO vo); // U

	int getMemoTotalCount();
	
}
