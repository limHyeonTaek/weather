package zerobase.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

//데이터베이스와 연동을 하는 코드의 경우 Repository패키지 안에 작성을 많이 한다

@Repository
public interface JpaMemoRepository extends JpaRepository<Memo, Integer> {

}
