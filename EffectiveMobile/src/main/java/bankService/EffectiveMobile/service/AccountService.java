package bankService.EffectiveMobile.service;


import bankService.EffectiveMobile.model.DAL.AccountDTO;
import bankService.EffectiveMobile.model.DAL.AccountDtoConvertor;
import bankService.EffectiveMobile.model.entity.AccountEntity;
import com.Bank_EffectiveMobile.Bank_service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    public AccountEntity addNewAccount(AccountDTO dto){

        return repository.save(AccountDtoConvertor.convertAccountDtoToEntity(dto));
    }

}
