package com.user.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.common.exception.NotUserException;
import com.multi.mapper.MemberMapper;
import com.user.domain.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service("memberService")
@RequiredArgsConstructor // 생성자 주입 -> 주입될 의존성을 갖는 객체는 final 필드여야 한다
@Log4j
public class MemberServiceImpl implements MemberService {

//	@Inject
//	private MemberMapper mMapper; // Field Injection
	private final MemberMapper mMapper; // final field -> 생성자 주입 @RequiredArgsConstructor 
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	// 사용자가 입력한 비밀번호에 솔트를 덧붙이고 이를 해시하여 암호화된 비밀번호를 만들어준다
	@Override
	public int insertMember(MemberVO vo) {
//		log.info("passwordEncoder : " + passwordEncoder);
		// 비밀번호 암호화 처리
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
//		log.info("암호화된 비밀번호: " + vo.getPwd());
		return mMapper.insertMember(vo);
	}

	@Override
	public boolean idCheck(String userid) {
		int n = mMapper.idCheck(userid);
		return (n>0) ? false : true;
	}

	@Override
	public List<MemberVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO selectByUserid(String userid) {
		return mMapper.selectByUserid(userid);
	}

	@Override
	public MemberVO loginCheck(MemberVO tmpUser) throws NotUserException {
		MemberVO dbUser = this.selectByUserid(tmpUser.getUserid());
		
		if(dbUser==null) {
			// id가 없는 경우 -> 사용자 정의 예외 발생
			throw new NotUserException("id 또는 비밀번호가 일치하지 않습니다");
		}
		
		// 비밀번호 일치 확인
		boolean isMatch = passwordEncoder.matches(tmpUser.getPwd(), dbUser.getPwd());
		
		log.info("tmpUser.getPwd() " + tmpUser.getPwd());
		log.info("dbUser.getPwd() " + dbUser.getPwd());
		
		if(!isMatch) throw new NotUserException("id 또는 비밀번호가 일치하지 않아요");
		
		return dbUser; // 회원이 맞다면, 회원정보 반환
	}

}
