package com.SmartPannel.userData.Service;

import com.SmartPannel.Repositories.panelRepository;
import com.SmartPannel.pannelData.Model.panelSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private panelRepository repository;

    public List<panelSpecification> listAll(){
        return repository.findAll();
    }
    public panelSpecification save (panelSpecification panel){
        repository.save(panel);
        return panel;
    }
    public panelSpecification get (Long id){
        return  repository.findById(id).get();
    }
    public  void delete(Long id){
        repository.deleteById(id);
    }
}
