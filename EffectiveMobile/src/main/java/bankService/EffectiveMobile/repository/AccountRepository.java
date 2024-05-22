package bankService.EffectiveMobile.repository;


import bankService.EffectiveMobile.model.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

}
