package com.memo.model;

import java.sql.Timestamp;

import lombok.Data;
// lombok이 안보이지만 getter setter 만듦

@Data
public class MemoVO {
	
	private int no;
	private String name;
	private String msg;
	private Timestamp wdate;
	
}
