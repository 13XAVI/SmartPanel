package com.SmartPannel.pannelData.panelService;

import com.SmartPannel.Repositories.panelRepository;
import com.SmartPannel.pannelData.Model.panelSpecification;
import com.SmartPannel.userData.Model.Users;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private panelRepository repository;

    public List<panelSpecification> listAll(){
        return repository.findAll();
    }
    public panelSpecification save (@Valid panelSpecification panel){
        repository.save(panel);
        return panel;
    }
    public panelSpecification get (Long id){
        return  repository.findById(id).get();
    }
    public  void delete(Long id){
        repository.deleteById(id);
    }

    public List<panelSpecification> search(String keyword) {
         List<panelSpecification> searchResults = repository.findByKeyword(keyword);
        return searchResults;
    }

}
