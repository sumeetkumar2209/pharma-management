package com.reify.supplier.helper;

import com.reify.common.model.ReviewStatusDO;
import com.reify.common.model.StatusDO;
import com.reify.supplier.DTO.SupplierSearchDTO;
import com.reify.supplier.model.SupplierDO;
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
    public List<SupplierDO>  searchSupplierByFilter(SupplierSearchDTO supplierSearchDTO) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SupplierDO> query = cb.createQuery(SupplierDO.class);

        Root<SupplierDO> suppliers = query.from(SupplierDO.class);

        List<Predicate> predicates = new ArrayList<>();

        if(supplierSearchDTO.getFilter().getSupplierId() != null){
            predicates.add(cb.equal(suppliers.get("supplierId"),
                    supplierSearchDTO.getFilter().getSupplierId()));
        }
        if(supplierSearchDTO.getFilter().getSupplierStatus() != null){

            Join<SupplierDO, StatusDO> supplierStatusDOJoin =
                    suppliers.join("supplierStatus", JoinType.INNER);


            predicates.add(cb.equal(supplierStatusDOJoin.get("statusCode"),
                    supplierSearchDTO.getFilter().getSupplierStatus()));
        }
        if(supplierSearchDTO.getFilter().getCompanyName()!= null){

            predicates.add(cb.equal(suppliers.get("companyName"),
                    supplierSearchDTO.getFilter().getCompanyName()));
        }
        if(supplierSearchDTO.getFilter().getReviewStatus() != null){

            Join<SupplierDO, ReviewStatusDO> reviewStatusDOJoin =
                    suppliers.join("reviewStatus", JoinType.INNER);


            predicates.add(cb.equal(reviewStatusDOJoin.get("reviewCode"),
                    supplierSearchDTO.getFilter().getReviewStatus()));
        }
        if(supplierSearchDTO.getFilter().getPostalCode() != null){

            predicates.add(cb.equal(suppliers.get("postalCode"),
                    supplierSearchDTO.getFilter().getPostalCode()));
        }

        query.where(
                cb.and(predicates.toArray(new Predicate[predicates.size()]))
        );

        if(supplierSearchDTO.getOrderBy() != null){

            query.orderBy(cb.desc(suppliers.get(supplierSearchDTO.getOrderBy())));
        } else {
            query.orderBy(cb.desc(suppliers.get("lastUpdatedTimeStamp")));
        }

        int maxResult = supplierSearchDTO.getEndIndex() - supplierSearchDTO.getStartIndex();

        return em.createQuery(query).setFirstResult(supplierSearchDTO.getStartIndex()).setMaxResults(maxResult).getResultList();

    }
}
