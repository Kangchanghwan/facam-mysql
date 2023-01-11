package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

  final private NamedParameterJdbcTemplate jdbcTemplate;
  final private String TABLE = "member";

  public Optional<Member> findById(Long id) {

    var sql = String.format("SELECT * FROM %s WHERE id =:id", TABLE);
    var param = new MapSqlParameterSource()
      .addValue("id", id);
    RowMapper<Member> rowMapper = getMemberRowMapper();
    return Optional.ofNullable(jdbcTemplate.queryForObject(sql, param, rowMapper));
  }

  private static RowMapper<Member> getMemberRowMapper() {
    RowMapper<Member> rowMapper = (ResultSet rst, int rowNum) -> Member.builder()
      .id(rst.getLong("id"))
      .email(rst.getString("email"))
      .nickname(rst.getString("nickname"))
      .birthday(rst.getObject("birthday", LocalDate.class))
      .createdAt(rst.getObject("createdAt", LocalDateTime.class))
      .build();
    return rowMapper;
  }


  public Member save(Member member) {
    /*
     * member id를 보고 갱신또는 삽입을 정함
     * 반환값은 ID를 담아서 반환한다.
     * */
    return insert(member);
  }

  private Member insert(Member member) {

    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
      .withTableName(TABLE)
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

  public Member update(Member member) {

    var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id =:id", TABLE);

    var param = new BeanPropertySqlParameterSource(member);

    jdbcTemplate.update(sql, param);

    return member;
  }


}
