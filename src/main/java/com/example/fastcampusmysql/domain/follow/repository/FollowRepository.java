package com.example.fastcampusmysql.domain.follow.repository;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
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
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

  final private NamedParameterJdbcTemplate jdbcTemplate;
  final private String TABLE = "follow";

  final static private RowMapper<Follow> ROW_MAPPER = (ResultSet rst, int rowNum) -> Follow.builder()
    .id(rst.getLong("id"))
    .fromMemberId(rst.getLong("fromMemberId"))
    .toMemberId(rst.getLong("toMemberId"))
    .createdAt(rst.getObject("createdAt",LocalDateTime.class))
    .build();

  public List<Follow> findAllByMemberId(Long fromMemberId){
    var sql = String.format("SELECT * FROM %s WHERE fromMemberId = :fromMemberId" ,TABLE);

    var param = new MapSqlParameterSource()
      .addValue("fromMemberId", fromMemberId);
    return jdbcTemplate.query(sql,param,ROW_MAPPER);

  }

  public Follow save(Follow follow) {

    if(follow.getId() ==null) {
      return insert(follow);
    }
    throw new UnsupportedOperationException("Follow는 갱신을 지원하지 않습니다.");
  }

  private Follow insert(Follow follow) {

    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
      .withTableName(TABLE)
      .usingGeneratedKeyColumns("id");
    SqlParameterSource params = new BeanPropertySqlParameterSource(follow);
    var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
    return Follow.builder()
      .id(id)
      .fromMemberId(follow.getFromMemberId())
      .toMemberId(follow.getToMemberId())
      .createdAt(follow.getCreatedAt())
      .build();
  }




}
