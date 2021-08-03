package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate; // Injection을 받을 수 있는 것은 아니다.
                                            // DataSource를 Injection 받는다.

    @Autowired // 생성자가 하나일 땐 Autowired를 생략해줘도 상관없다.
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("name", member.getName());
        // 여기까지 insert문이 만들어짐
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        member.setId(key.longValue());
        return member;
    } // 따로 쿼리를 짤 필요가 없다.

    @Override
    public Optional<Member> findById(Long id) {
       List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
       return result.stream().findAny(); // Optional로 바꿔서 return
    } // jdbcTemplate에서 select * from member where id = ? 쿼리를 날려 memberRowMapper()를 통해서 매핑을하고 Member을 List로 받아서 Optional로 바꿔서 반환

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny(); //Optional로 return
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper()); // List로 반환
    }

    private RowMapper<Member> memberRowMapper() { // 멤버 생성
        return (rs, rowNum) -> {

            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getNString("name"));
            return member;
        };
    }
}
