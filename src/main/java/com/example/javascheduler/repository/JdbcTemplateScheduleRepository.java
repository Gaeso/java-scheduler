package com.example.javascheduler.repository;

import com.example.javascheduler.dto.ScheduleResponseDto;
import com.example.javascheduler.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        LocalDateTime now = LocalDateTime.now();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("content", schedule.getContent());
        parameters.put("author", schedule.getAuthor());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", now);
        parameters.put("updated_at", now);

        // 저장 후 생성된 key값 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(), schedule.getAuthor(), schedule.getContent(), schedule.getPassword(), now, now);
    }

    @Override
    public List<Schedule> findAllByCondition(LocalDate date, String author) {

        if(date == null)
        {
            return jdbcTemplate.query("SELECT author, content, updated_at FROM schedule WHERE author = ? ORDER BY updated_at DESC", scheduleRowMapper(), author);
        }
        else if(author == null)
        {
            return jdbcTemplate.query("SELECT author, content, updated_at FROM schedule WHERE Date(updated_at) = ? ORDER BY updated_at DESC", scheduleRowMapper(), date);
        }
        else
        {
            return jdbcTemplate.query("SELECT author, content, updated_at FROM schedule WHERE Date(updated_at) = ? AND author = ? ORDER BY updated_at DESC", scheduleRowMapper(), date, author);
        }
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getString("author"),
                rs.getString("content"),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
