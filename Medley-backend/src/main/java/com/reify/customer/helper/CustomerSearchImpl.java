package com.reify.customer.helper;

import com.reify.common.model.ReviewStatusDO;
import com.reify.common.model.StatusDO;
import com.reify.customer.DTO.CustomerSearchDTO;
import com.reify.customer.model.CustomerDO;
import com.reify.supplier.model.SupplierDO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerSearchImpl implements CustomerSearch{

    @Autowired
    EntityManager em;
    @Override
    public List<CustomerDO> searchCustomerByCriteria(CustomerSearchDTO customerSearchDTO) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CustomerDO> query = cb.createQuery(CustomerDO.class);

        Root<CustomerDO> customers = query.from(CustomerDO.class);

        List<Predicate> predicates = new ArrayList<>();

        if(customerSearchDTO.getCustomerFilter().getCustomerId() != null){
            predicates.add(cb.equal(customers.get("customerId"),
                    customerSearchDTO.getCustomerFilter().getCustomerId()));
        }
        if(customerSearchDTO.getCustomerFilter().getCustomerStatus() != null){

            Join<CustomerDO, StatusDO> customerStatusDOJoin =
                    customers.join("customerStatus", JoinType.INNER);


            predicates.add(cb.equal(customerStatusDOJoin.get("statusCode"),
                    customerSearchDTO.getCustomerFilter().getCustomerStatus()));
        }
        if(customerSearchDTO.getCustomerFilter().getCompanyName()!= null){

            predicates.add(cb.equal(customers.get("companyName"),
                    customerSearchDTO.getCustomerFilter().getCompanyName()));
        }
        if(customerSearchDTO.getCustomerFilter().getReviewStatus() != null){

            Join<SupplierDO, ReviewStatusDO> reviewStatusDOJoin =
                    customers.join("reviewStatus", JoinType.INNER);


            predicates.add(cb.equal(reviewStatusDOJoin.get("reviewCode"),
                    customerSearchDTO.getCustomerFilter().getReviewStatus()));
        }
        if(customerSearchDTO.getCustomerFilter().getPostalCode() != null){

            predicates.add(cb.equal(customers.get("postalCode"),
                    customerSearchDTO.getCustomerFilter().getPostalCode()));
        }
        query.where(
                cb.and(predicates.toArray(new Predicate[predicates.size()]))
        );

        query.orderBy(cb.desc(customers.get("lastUpdatedTimeStamp")));

        int maxResult = customerSearchDTO.getEndIndex() - customerSearchDTO.getStartIndex();

        return em.createQuery(query).setFirstResult(customerSearchDTO.getStartIndex()).setMaxResults(maxResult).getResultList();

    }
}
