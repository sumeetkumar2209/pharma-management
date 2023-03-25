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
            predicates.add(cb.equal(cb.lower(suppliers.get("supplierId")),
                    supplierSearchDTO.getFilter().getSupplierId().toLowerCase()));
        }
        if(supplierSearchDTO.getFilter().getSupplierStatus() != null){

            Join<SupplierDO, StatusDO> supplierStatusDOJoin =
                    suppliers.join("supplierStatus", JoinType.INNER);


            predicates.add(cb.equal(cb.lower(supplierStatusDOJoin.get("statusCode")),
                    supplierSearchDTO.getFilter().getSupplierStatus().toLowerCase()));
        }
        if(supplierSearchDTO.getFilter().getCompanyName()!= null){

            predicates.add(cb.equal(cb.lower(suppliers.get("companyName")),
                    supplierSearchDTO.getFilter().getCompanyName().toLowerCase()));
        }
        if(supplierSearchDTO.getFilter().getReviewStatus() != null){

            Join<SupplierDO, ReviewStatusDO> reviewStatusDOJoin =
                    suppliers.join("reviewStatus", JoinType.INNER);


            predicates.add(cb.equal(cb.lower(reviewStatusDOJoin.get("reviewCode")),
                    supplierSearchDTO.getFilter().getReviewStatus().toLowerCase()));
        }
        if(supplierSearchDTO.getFilter().getPostalCode() != null){

            predicates.add(cb.equal(cb.lower(suppliers.get("postalCode")),
                    supplierSearchDTO.getFilter().getPostalCode().toLowerCase()));
        }

        query.where(
                cb.and(predicates.toArray(new Predicate[predicates.size()]))
        );


        if(supplierSearchDTO.getOrderBy() != null){
            if (supplierSearchDTO.getOrderType().equalsIgnoreCase("ASC")) {
                query.orderBy(cb.asc(suppliers.get(supplierSearchDTO.getOrderBy())));
            } else {
                query.orderBy(cb.desc(suppliers.get(supplierSearchDTO.getOrderBy())));
            }
        } else {
            query.orderBy(cb.desc(suppliers.get("lastUpdatedTimeStamp")));
        }

        int maxResult = supplierSearchDTO.getEndIndex() - supplierSearchDTO.getStartIndex();

        return em.createQuery(query).setFirstResult(supplierSearchDTO.getStartIndex()).setMaxResults(maxResult).getResultList();

    }

    @Override
    public long countSupplierByFilter(SupplierSearchDTO supplierSearchDTO) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<SupplierDO> root = query.from(SupplierDO.class);
        query.select(builder.count(root.get("supplierId")));

        List<Predicate> predicates = new ArrayList<>();

        if(supplierSearchDTO.getFilter().getSupplierId() != null){
            predicates.add(builder.equal(builder.lower(root.get("supplierId")),
                    supplierSearchDTO.getFilter().getSupplierId().toLowerCase()));
        }
        if(supplierSearchDTO.getFilter().getSupplierStatus() != null){

            Join<SupplierDO, StatusDO> supplierStatusDOJoin =
                    root.join("supplierStatus", JoinType.INNER);


            predicates.add(builder.equal(builder.lower(supplierStatusDOJoin.get("statusCode")),
                    supplierSearchDTO.getFilter().getSupplierStatus().toLowerCase()));
        }
        if(supplierSearchDTO.getFilter().getCompanyName()!= null){

            predicates.add(builder.equal(builder.lower(root.get("companyName")),
                    supplierSearchDTO.getFilter().getCompanyName().toLowerCase()));
        }
        if(supplierSearchDTO.getFilter().getReviewStatus() != null){

            Join<SupplierDO, ReviewStatusDO> reviewStatusDOJoin =
                    root.join("reviewStatus", JoinType.INNER);


            predicates.add(builder.equal(builder.lower(reviewStatusDOJoin.get("reviewCode")),
                    supplierSearchDTO.getFilter().getReviewStatus().toLowerCase()));
        }
        if(supplierSearchDTO.getFilter().getPostalCode() != null){

            predicates.add(builder.equal(builder.lower(root.get("postalCode")),
                    supplierSearchDTO.getFilter().getPostalCode().toLowerCase()));
        }

        query.where(
                builder.and(predicates.toArray(new Predicate[predicates.size()]))
        );
        long result = (Long) em.createQuery(query).getSingleResult();

        return  result;
    }
}
