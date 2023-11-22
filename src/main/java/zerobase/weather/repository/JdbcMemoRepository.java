package zerobase.weather.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemoRepository {
    //Jdbc를 활용을 할 때는 JdbcTemplate을 활용을 한다
    private final JdbcTemplate jdbcTemplate;

    //JdbcTemplate초기값을 설정해주는 생성자 >>JdbcMemoRepository가 처음 생성될때
    //JdbcTemplate 초기 설정을 해준다
    @Autowired //이 어노테이션이 있어야 dataSource를 application.properties에서 알아서 가져온다
    public JdbcMemoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //memo를 저장할 수 있어야 하고 조회할 수 있어야 한다
    //Jdbc의 특징 >> query문을 직접 써야한다
    public Memo save(Memo memo){
        String sql = "insert into memo values(?,?)";
        //데이터 저장은 update함수
        jdbcTemplate.update(sql, memo.getId(), memo.getText());
        return memo;
    }

    public List<Memo> findAll(){
        String sql = "select * from memo";
        //데이터 조회는 query함수
        return jdbcTemplate.query(sql, memoRowMapper());
    }

    //만약 id=3 객체를 찾았는데 id=3인 객체가없으면 Optional객체로 wrapping해서 혹시모를
    //null값을 처리한다.
    public Optional<Memo> findById(int id){
        String sql = "select * from memo where id = ?";
        return jdbcTemplate.query(sql, memoRowMapper(), id).stream().findFirst();
    }

    //RowMapper란 Jdbc를 통해서 mysql에서 데이터베이스에서 데이터를 가져오면 그 값은
    //ResultSet이라는 Set형식이다 {id = 1, text = 'this is memo~'}
    //이러한 형식을 springboot의 Memo라는 클래스 형식에 넣어야하기 때문에
    //ResultSet형식을 Memo클래스 형식으로 Mapping해주는 과정이다
    private RowMapper<Memo> memoRowMapper(){
        return (rs, rowNum) -> new Memo(
            rs.getInt("id"),
                rs.getString("text")
        );
    }
}
