package com.example.javascheduler.repository;

import com.example.javascheduler.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");


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

    @Override
    public List<Schedule> findAllByCondition(LocalDate date, Long userId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule");
        List<Object> params = new ArrayList<>();

        // WHERE 절 동적 구성
        if (date != null || userId != null) {
            sql.append(" WHERE");

            List<String> conditions = new ArrayList<>();

            if (date != null) {
                conditions.add(" DATE(updated_at) = ?");
                params.add(date);
            }

            if (userId != null) {
                conditions.add(" user_id = ?");
                params.add(userId);
            }

            sql.append(String.join(" AND", conditions));
        }

        sql.append(" ORDER BY updated_at DESC");

        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), params.toArray());
    }

    @Override
    public Schedule findScheduleById(Long id) {
        List<Schedule> query = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", scheduleRowMapper(), id);
        return query.stream().findAny().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public int updateScheduleById(Long id, String content, LocalDateTime now) {
        return jdbcTemplate.update("update schedule set content = ? , updated_at = ? WHERE id = ?", content, now, id);
    }

    @Override
    public int deleteScheduleById(Long id) {
        return jdbcTemplate.update("delete from schedule where id = ?", id);
    }

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
}
