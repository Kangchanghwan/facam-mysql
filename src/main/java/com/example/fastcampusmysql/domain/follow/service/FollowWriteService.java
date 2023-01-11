package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class FollowWriteService {


  final private FollowRepository followRepository;

  public void create(MemberDto fromMember,MemberDto toMember){

    /*
    * from , to 회원 정보를 받아서 저장할텐데 무슨 정보를 받을 것인가
    * */

    Assert.isTrue(!Objects.equals(fromMember.id(), toMember.id()),"From, To 회원이 동일합니다.");

    Follow follow = Follow.builder()
      .fromMemberId(fromMember.id())
      .toMemberId(toMember.id())
      .build();

    followRepository.save(follow);

  }

}
