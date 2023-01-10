package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

  final private NamedParameterJdbcTemplate jdbcTemplate;

  public Member save(Member member) {
    /*
     * member id를 보고 갱신또는 삽입을 정함
     * 반환값은 ID를 담아서 반환한다.
     * */
    return insert(member);
  }

  private Member insert(Member member) {

    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
      .withTableName("Member")
      .usingGeneratedKeyColumns("id");
    SqlParameterSource params = new BeanPropertySqlParameterSource(member);
    var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
    return Member.builder()
      .id(id)
      .nickname(member.getNickname())
      .email(member.getEmail())
      .birthday(member.getBirthday())
      .createdAt(member.getCreatedAt()).build();
  }

  private Member update(Member member) {
    return member;
  }

}
