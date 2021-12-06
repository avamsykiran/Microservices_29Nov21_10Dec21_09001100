package in.budgettracker.tms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.budgettracker.tms.entity.UserEntity;
@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long>{

}
