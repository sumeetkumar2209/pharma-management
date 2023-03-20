package com.reify.common.service.impl;

import com.reify.common.DTO.CurrencyDTO;
import com.reify.common.model.CurrencyDO;
import com.reify.common.repo.CurrencyRepo;
import com.reify.common.service.CurrencyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    CurrencyRepo currencyRepo;
    @Autowired
    ApplicationContext context;

    @Override
    public List<CurrencyDTO> getCurrency() {

       List<CurrencyDO> currencyDOList = currencyRepo.findAll();

        List<CurrencyDTO> currencyDTOList = null;

        if(!currencyDOList.isEmpty()){

          currencyDTOList =  currencyDOList.stream().map( obj -> {

               CurrencyDTO currencyDTO = context.getBean(CurrencyDTO.class);
               BeanUtils.copyProperties(obj, currencyDTO);
               return currencyDTO;

           }).collect(Collectors.toList());

       }

        return currencyDTOList;
    }
}
