package com.example.javascheduler.repository;

import com.example.javascheduler.entity.Schedule;
import com.example.javascheduler.exception.ScheduleNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // 일정 저장
    @Override
    public Schedule saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id"); // 자동 생성 ID 사용


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("content", schedule.getContent());
        parameters.put("userId", schedule.getUserId());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", schedule.getCreated_at());
        parameters.put("updated_at", schedule.getUpdated_at());

        // 저장 후 생성된 key값 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        schedule.setId(key.longValue());

        return schedule;
    }

    // 조건별 일정 조회
    @Override
    public List<Schedule> findAllByCondition(LocalDate date, Long userId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule");
        MapSqlParameterSource params = new MapSqlParameterSource();

        // WHERE 절 동적 구성
        if (date != null || userId != null) {
            sql.append(" WHERE");

            List<String> conditions = new ArrayList<>();

            if (date != null) {
                conditions.add(" DATE(updated_at) = :date");
                params.addValue("date", date);
            }

            if (userId != null) {
                conditions.add(" user_id = :userId");
                params.addValue("userId", userId);
            }

            sql.append(String.join(" AND", conditions));
        }
        sql.append(" ORDER BY updated_at DESC"); // 최신순으로 정렬

        return namedParameterJdbcTemplate.query(sql.toString(), params, scheduleRowMapper());
    }

    // 페이징 조회
    @Override
    public List<Schedule> findPageByCondition(LocalDate date, Long userId, int offset, int limit) {

        String sql = """
            SELECT s.id, s.content, s.user_id, s.created_at, s.updated_at, u.email
            FROM schedule s
            JOIN user u ON s.user_id = u.id
            WHERE (:date IS NULL OR DATE(s.updated_at) = :date)
            AND (:userId IS NULL OR s.user_id = :userId)
            ORDER BY s.updated_at DESC
            LIMIT :limit OFFSET :offset
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("date", date)
                .addValue("userId", userId)
                .addValue("limit", limit)
                .addValue("offset", offset);

        return namedParameterJdbcTemplate.query(sql, params, pageRowMapper());
    }

    // 단일 일정 조회
    @Override
    public Schedule findScheduleById(Long id) {
        List<Schedule> query = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", scheduleRowMapper(), id);
        return query.stream().findAny().orElseThrow(()-> new ScheduleNotFoundException(id));
    }

    // 일정 수정
    @Override
    public int updateScheduleById(Long id, String content, LocalDateTime now) {
        return jdbcTemplate.update("update schedule set content = ? , updated_at = ? WHERE id = ?", content, now, id);
    }

    // 일정 삭제
    @Override
    public int deleteScheduleById(Long id) {
        return jdbcTemplate.update("delete from schedule where id = ?", id);
    }

    // 총 일정 수 카운트
    @Override
    public long countAll(LocalDate date, Long userId) {
        List<Schedule> schedules = findAllByCondition(date, userId);
        return schedules.size();
    }

    // 기본 RowMapper(비밀번호 포함)
    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("content"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    // 페이징용 RowMapper(비밀번호 X)
    private RowMapper<Schedule> pageRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("content"),
                null,
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
