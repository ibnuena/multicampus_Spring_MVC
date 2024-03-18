package com.user.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberVO {
	
	private String userid;
	private String name;
	private String pwd;
	private String hp1;
	private String hp2;
	private String hp3;
	
	private String post;
	private String addr1;
	private String addr2;
	private Date indate;
	private int mileage;
	private int mstate; // 0 : ÀÏ¹İ, -1 : Á¤Áö, -2 : Å»Åğ
	
	public String getAllHp() {
		return hp1 + "-" + hp2 + "-" + hp3;
	}
	
	public String getAddr() {
		return "[" + post + "]" + addr1 + " " + addr2;
	}
	
	public String getMstateStr() {
		if(mstate==0)
			return "ÀÏ¹İÈ¸¿ø";
		else if(mstate==-1)
			return "Á¤ÁöÈ¸¿ø";
		else if(mstate==-2)
			return "Å»ÅğÈ¸¿ø";
		else return "";
	}
}
