package com.reify.customer.helper;

import com.reify.common.model.ReviewStatusDO;
import com.reify.common.model.StatusDO;
import com.reify.customer.DTO.CustomerSearchDTO;
import com.reify.customer.model.CustomerDO;
import com.reify.supplier.DTO.SupplierSearchDTO;
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
            predicates.add(cb.equal(cb.lower(customers.get("customerId")),
                    customerSearchDTO.getCustomerFilter().getCustomerId().toLowerCase()));
        }
        if(customerSearchDTO.getCustomerFilter().getCustomerStatus() != null){

            Join<CustomerDO, StatusDO> customerStatusDOJoin =
                    customers.join("customerStatus", JoinType.INNER);


            predicates.add(cb.equal(cb.lower(customerStatusDOJoin.get("statusCode")),
                    customerSearchDTO.getCustomerFilter().getCustomerStatus().toLowerCase()));
        }
        if(customerSearchDTO.getCustomerFilter().getCustomerName()!= null){

            predicates.add(cb.equal(cb.lower(customers.get("customerName")),
                    customerSearchDTO.getCustomerFilter().getCustomerName().toLowerCase()));
        }
        if(customerSearchDTO.getCustomerFilter().getReviewStatus() != null){

            Join<SupplierDO, ReviewStatusDO> reviewStatusDOJoin =
                    customers.join("reviewStatus", JoinType.INNER);


            predicates.add(cb.equal(cb.lower(reviewStatusDOJoin.get("reviewCode")),
                    customerSearchDTO.getCustomerFilter().getReviewStatus().toLowerCase()));
        }
        if(customerSearchDTO.getCustomerFilter().getPostalCode() != null){

            predicates.add(cb.equal(cb.lower(customers.get("postalCode")),
                    customerSearchDTO.getCustomerFilter().getPostalCode().toLowerCase()));
        }
        query.where(
                cb.and(predicates.toArray(new Predicate[predicates.size()]))
        );



        if(customerSearchDTO.getOrderBy() != null){

            query.orderBy(cb.desc(customers.get(customerSearchDTO.getOrderBy())));
        } else {
            query.orderBy(cb.desc(customers.get("lastUpdatedTimeStamp")));
        }


        int maxResult = customerSearchDTO.getEndIndex() - customerSearchDTO.getStartIndex();

        return em.createQuery(query).setFirstResult(customerSearchDTO.getStartIndex()).setMaxResults(maxResult).getResultList();

    }

    @Override
    public long countCustomerByFilter(CustomerSearchDTO customerSearchDTO) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<CustomerDO> customers = query.from(CustomerDO.class);
        query.select(cb.count(customers.get("customerId")));


        List<Predicate> predicates = new ArrayList<>();

        if(customerSearchDTO.getCustomerFilter().getCustomerId() != null){
            predicates.add(cb.equal(cb.lower(customers.get("customerId")),
                    customerSearchDTO.getCustomerFilter().getCustomerId().toLowerCase()));
        }
        if(customerSearchDTO.getCustomerFilter().getCustomerStatus() != null){

            Join<CustomerDO, StatusDO> customerStatusDOJoin =
                    customers.join("customerStatus", JoinType.INNER);


            predicates.add(cb.equal(cb.lower(customerStatusDOJoin.get("statusCode")),
                    customerSearchDTO.getCustomerFilter().getCustomerStatus().toLowerCase()));
        }
        if(customerSearchDTO.getCustomerFilter().getCustomerName()!= null){

            predicates.add(cb.equal(cb.lower(customers.get("customerName")),
                    customerSearchDTO.getCustomerFilter().getCustomerName().toLowerCase()));
        }
        if(customerSearchDTO.getCustomerFilter().getReviewStatus() != null){

            Join<SupplierDO, ReviewStatusDO> reviewStatusDOJoin =
                    customers.join("reviewStatus", JoinType.INNER);


            predicates.add(cb.equal(cb.lower(reviewStatusDOJoin.get("reviewCode")),
                    customerSearchDTO.getCustomerFilter().getReviewStatus().toLowerCase()));
        }
        if(customerSearchDTO.getCustomerFilter().getPostalCode() != null){

            predicates.add(cb.equal(cb.lower(customers.get("postalCode")),
                    customerSearchDTO.getCustomerFilter().getPostalCode().toLowerCase()));
        }
        query.where(
                cb.and(predicates.toArray(new Predicate[predicates.size()]))
        );


        long result = (Long) em.createQuery(query).getSingleResult();

        return  result;

    }
}
