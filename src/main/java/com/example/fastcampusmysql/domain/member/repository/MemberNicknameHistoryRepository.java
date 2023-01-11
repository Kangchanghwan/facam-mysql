package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberNicknameHistoryRepository {

  final private NamedParameterJdbcTemplate jdbcTemplate;
  final private String TABLE = "memberNicknameHistory";

  private static final RowMapper<MemberNicknameHistory> ROW_MAPPER =  (ResultSet rst, int rowNum) -> MemberNicknameHistory.builder()
    .id(rst.getLong("id"))
    .memberId(rst.getLong("memberId"))
    .nickname(rst.getString("nickname"))
    .createdAt(rst.getObject("createdAt",LocalDateTime.class))
    .build();


  public MemberNicknameHistory save(MemberNicknameHistory history) {

    if(history.getId() == null){
      return insert(history);
    }
    throw new UnsupportedOperationException("MemberNicknameHistory는 갱신을 지원하지 않습니다.");
  }

  private MemberNicknameHistory insert(MemberNicknameHistory history) {
    
      SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
        .withTableName(TABLE)
        .usingGeneratedKeyColumns("id");
      SqlParameterSource params = new BeanPropertySqlParameterSource(history);
      var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

    return MemberNicknameHistory.builder()
      .id(id)
      .nickname(history.getNickname())
      .memberId(history.getMemberId())
      .createdAt(history.getCreatedAt()).build();
  }



  public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {

    var sql = String.format("SELECT * FROM %s WHERE memberId = :memberId" ,TABLE);

    var param = new MapSqlParameterSource().addValue("memberId", memberId);

    return jdbcTemplate.query(sql,param, ROW_MAPPER);

  }


}
