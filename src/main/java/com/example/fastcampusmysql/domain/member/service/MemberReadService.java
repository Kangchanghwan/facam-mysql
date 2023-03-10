package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberReadService {

  final private MemberRepository memberRepository;
  final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;


  public MemberDto getMember(Long id) {
    return toDto(memberRepository.findById(id).orElseThrow());
  }

  public MemberDto toDto(Member member) {
    return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
  }

  public List<MemberNicknameHistoryDto> getNicknameHistory(Long memberId) {
    return memberNicknameHistoryRepository.findAllByMemberId(memberId).stream().map(o -> toDto(o)).collect(Collectors.toList());
  }

  private MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
    return new MemberNicknameHistoryDto(history.getId(), history.getMemberId(), history.getNickname(), history.getCreatedAt());
  }


  public List<MemberDto> getMembers(List<Long> ids) {
    return memberRepository.findAllByIdIn(ids).stream().map(this::toDto).collect(Collectors.toList());
  }

}
