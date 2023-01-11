package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetFollowingMembersUsercase {

  final private MemberReadService memberReadService;
  final private FollowReadService followReadService;



  public List<MemberDto> excute(Long memberId) {

    /*
    * 1. fromMemberId = memberId -> follow List
    * 2. 1번을 순회하면서 찾을 수 있다.
    * */

    var followings = followReadService.getFollowings(memberId);
    var followingsMemberIds = followings.stream().map(Follow::getToMemberId).collect(Collectors.toList());

    return memberReadService.getMembers(followingsMemberIds);

  }

}
