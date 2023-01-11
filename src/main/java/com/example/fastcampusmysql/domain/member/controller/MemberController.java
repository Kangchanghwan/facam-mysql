package com.example.fastcampusmysql.domain.member.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberWriteService memberWriteService;

  private final MemberReadService memberReadService;


  @PostMapping("/members")
  public MemberDto register(@RequestBody RegisterMemberCommand command){
    return memberWriteService.create(command);
  }

  @GetMapping("/members/{id}")
  public MemberDto getMember(@PathVariable Long id){
    return memberReadService.getMember(id);
  }

  @PutMapping("/members/{id}/nickname")
  public MemberDto changeNickname(@PathVariable Long id, @RequestBody String nickname){

    return  memberWriteService.changeNickname(id,nickname);
  }

  @GetMapping("/{id}/nickname-history")
  public List<MemberNicknameHistoryDto> getNicknameHistories(@PathVariable Long id) {
    return memberReadService.getNicknameHistory(id);
  }
}
