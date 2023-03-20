package com.reify.common.service.impl;

import com.reify.common.DTO.CountryDTO;
import com.reify.common.model.CountryDO;
import com.reify.common.repo.CountryRepo;
import com.reify.common.service.CountryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    CountryRepo countryRepo;
    @Autowired
    ApplicationContext context;

    @Override
    public List<CountryDTO> getCountry() {

        List<CountryDO> countryDOList = countryRepo.findAll();
        List<CountryDTO> countryDTOList = null;

        if (!countryDOList.isEmpty()){

            countryDTOList = countryDOList.stream().map(obj -> {

                CountryDTO countryDTO = context.getBean(CountryDTO.class);
                BeanUtils.copyProperties(obj, countryDTO);
                return countryDTO;
            }).collect(Collectors.toList());

        }

        return countryDTOList;
    }
}
