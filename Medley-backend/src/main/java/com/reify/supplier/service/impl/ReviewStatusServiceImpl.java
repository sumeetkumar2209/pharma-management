package com.reify.supplier.service.impl;

import com.reify.common.DTO.ReviewStatusDTO;
import com.reify.common.model.ReviewStatusDO;
import com.reify.supplier.repo.ReviewStatusRepo;
import com.reify.supplier.service.ReviewStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewStatusServiceImpl implements ReviewStatusService
{

    @Autowired
    ApplicationContext context;

    @Autowired
    ReviewStatusRepo reviewStatusRepo;

    @Override
    public List<ReviewStatusDTO> getAllRoleStatus() {

        List<ReviewStatusDO> reviewStatusDOList = reviewStatusRepo.findAll();

        List<ReviewStatusDTO> reviewStatusDTOList =  new ArrayList<>();

        if(!reviewStatusDOList.isEmpty()){

            for ( ReviewStatusDO reviewStatusDO: reviewStatusDOList) {

                ReviewStatusDTO reviewStatusDTO = context.getBean(ReviewStatusDTO.class);

                reviewStatusDTO.setReviewCode(reviewStatusDO.getReviewCode());
                reviewStatusDTO.setReviewName(reviewStatusDO.getReviewName());

                reviewStatusDTOList.add(reviewStatusDTO);

            }

        }

        return reviewStatusDTOList;
    }
}
