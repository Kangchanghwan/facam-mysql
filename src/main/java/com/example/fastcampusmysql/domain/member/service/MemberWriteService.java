package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

  private final MemberRepository memberRepository;

  public Member create(RegisterMemberCommand command){
    /*
    * 목표 - 회원정보(이메일, 닉네임, 생년월일)을 등록한다.
    *
    * 파라미터- memberResisterCommand
    * var member = Member.of(memberRegisterCommand)
    * memberRepository.save()
    *
    * */
    var member = Member.builder()
      .nickname(command.nickname())
      .birthday(command.birth())
      .email(command.email())
      .build();

   return memberRepository.save(member);
  }
}
