package com.reify.supplier.helper;

import com.reify.supplier.DTO.SupplierSearchDTO;
import com.reify.supplier.model.SupplierDO;
import com.reify.supplier.model.SupplierStatusDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SearchSupplierImpl implements SearchSupplier{

    @Autowired
    EntityManager em;

    @Override
    public List<SupplierDO> searchSupplierByFilter(SupplierSearchDTO supplierSearchDTO) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SupplierDO> criteriaQuery = cb.createQuery(SupplierDO.class);

        Root<SupplierDO> suppliers = criteriaQuery.from(SupplierDO.class);

        suppliers.fetch("supplierStatusCode", JoinType.LEFT);
        //Join<SupplierDO, SupplierStatusDO> supplierStatusJoin = suppliers.join("supplierStatusCode", JoinType.LEFT);
        //Fetch<SupplierDO, SupplierStatusDO> supplierStatusFetch = suppliers.fetch("supplierStatusCode", JoinType.LEFT);


        List<Predicate> predicates = new ArrayList<>();

        if(supplierSearchDTO.getFilter().getSupplierId() != null){
            predicates.add(cb.equal(suppliers.get("supplierId"),
                    supplierSearchDTO.getFilter().getSupplierId()));
        }
        if(supplierSearchDTO.getFilter().getSupplierStatus() != null){

            predicates.add(cb.equal(suppliers.get("supplierStatusCode"),
                    supplierSearchDTO.getFilter().getSupplierStatus()));
        }
        if(supplierSearchDTO.getFilter().getCompanyName()!= null){

            predicates.add(cb.equal(suppliers.get("companyName"),
                    supplierSearchDTO.getFilter().getCompanyName()));
        }
       /* if(supplierSearchDTO.getFilter().getReviewStatus() != null){

            predicates.add(cb.equal(suppliers.get("reviewStatus.reviewCode"),
                    supplierSearchDTO.getFilter().getReviewStatus()));
        }*/
        if(supplierSearchDTO.getFilter().getPostalCode() != null){

            predicates.add(cb.equal(suppliers.get("postalCode"),
                    supplierSearchDTO.getFilter().getPostalCode()));
        }

        criteriaQuery.where(
                cb.and(predicates.toArray(new Predicate[predicates.size()]))
        );

        return em.createQuery(criteriaQuery).getResultList();

    }
}
