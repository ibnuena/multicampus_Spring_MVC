package com.memo.model;

import java.sql.Timestamp;

import lombok.Data;
// lombok�� �Ⱥ������� getter setter ����

@Data
public class MemoVO {
	
	private int no;
	private String name;
	private String msg;
	private Timestamp wdate;
	
}
