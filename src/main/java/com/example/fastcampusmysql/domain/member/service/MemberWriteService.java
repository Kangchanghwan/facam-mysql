package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

  private final MemberRepository memberRepository;
  private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;


  public MemberDto create(RegisterMemberCommand command){
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

   return toDto( memberRepository.save(member));
  }

  public MemberDto toDto(Member member) {
    return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
  }

  public MemberDto changeNickname(Long memberId, String nickname){
    var member =memberRepository.findById(memberId).orElseThrow();
    member.changeNickname(nickname);
    Member afterMember = memberRepository.update(member);
    saveNickNameHistory(afterMember);
    return toDto(afterMember);

  }

  private void saveNickNameHistory(Member member) {
    var history = MemberNicknameHistory.builder()
      .memberId(member.getId())
      .nickname(member.getNickname())
      .build();

    memberNicknameHistoryRepository.save(history);
  }


}
