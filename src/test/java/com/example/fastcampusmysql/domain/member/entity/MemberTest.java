package com.example.fastcampusmysql.domain.member.entity;

import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {


  @DisplayName("회원은 닉네임을 변경할 수 있다.")
  @Test
  public void testChangeName() throws Exception {
    //given
    Member member = MemberFixtureFactory.create();
    var expected = "pnu";
    //when
    member.changeNickname(expected);
    //then
    Assertions.assertEquals(expected, member.getNickname());
  }

  @DisplayName("회원 닉네임은 10자 이상 초과할 수 없다.")
  @Test
  public void testNicknameMaxLength() throws Exception {
    //given
    Member member = MemberFixtureFactory.create();
    var expected = "pnu1234567879";
    //when
    //then
    Assertions.assertThrows(IllegalArgumentException.class,
      () ->
        member.changeNickname(expected));
  }


}