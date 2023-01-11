package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreateFollowMemberUsecase;
import com.example.fastcampusmysql.application.usecase.GetFollowingMembersUsercase;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

  final private CreateFollowMemberUsecase createFollowMemberUsecase;
  final private GetFollowingMembersUsercase getFollowingMembersUsercase;



  @PostMapping("/{fromId}/{toId}")
  public void regster(@PathVariable Long fromId, @PathVariable Long toId){
    createFollowMemberUsecase.execute(fromId,toId);
  }

  @GetMapping("/members/{memberId}")
  public List<MemberDto> getFollowings(@PathVariable Long memberId){
    return getFollowingMembersUsercase.excute(memberId);
  }

}
