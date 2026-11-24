package ProcessoSeletivo_FAE.demo.services;

import java.util.Optional;
import ProcessoSeletivo_FAE.demo.entities.Address;
import ProcessoSeletivo_FAE.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AddressService {
    @Autowired 
    private AddressRepository repository;

    public Address create(Address obj){
        return repository.save(obj);
    }

    public void delete(Long id_person){
        repository.deleteById(id_person);
    }
    
    public Address selectById(Long id_person){
        Optional<Address> obj = repository.findById(id_person);
        return obj.get();
    }

    public Address update(Address obj){
        Optional<Address> newObj = repository.findById(obj.getId());
        updateAddress(newObj, obj);
        return repository.save(newObj.get());
    }

    private void updateAddress(Optional<Address> newObj, Address obj){
        newObj.get().setName(obj.getName());
    }
}
