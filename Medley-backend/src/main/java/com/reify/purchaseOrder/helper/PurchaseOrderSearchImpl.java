package com.reify.purchaseOrder.helper;

import com.reify.purchaseOrder.DTO.PurchaseOrderSearchDTO;
import com.reify.purchaseOrder.model.PurchaseOrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PurchaseOrderSearchImpl implements PurchaseOrderSearch{

    @Autowired
    EntityManager em;

    @Override
    public List<PurchaseOrderDO> searchPurchaseOrderByFilter(PurchaseOrderSearchDTO purchaseOrderSearchDTO) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PurchaseOrderDO> query = cb.createQuery(PurchaseOrderDO.class);

        Root<PurchaseOrderDO> PurchaseOrders = query.from(PurchaseOrderDO.class);

        List<Predicate> predicates = new ArrayList<>();

        if(purchaseOrderSearchDTO.getFilter().getPurchaseOrderNo() != null){
            predicates.add(cb.equal(cb.lower(PurchaseOrders.get("purchaseOrderNo")),
                    purchaseOrderSearchDTO.getFilter().getPurchaseOrderNo().toLowerCase()));
        }
        if(purchaseOrderSearchDTO.getFilter().getPurchaseOrderDate() != 0L){
            predicates.add(cb.equal(cb.lower(PurchaseOrders.get("purchaseOrderDate")),
                    purchaseOrderSearchDTO.getFilter().getPurchaseOrderDate()));
        }
        if(purchaseOrderSearchDTO.getFilter().getSupplierName() != null){
            predicates.add(cb.equal(cb.lower(PurchaseOrders.get("supplierName")),
                    purchaseOrderSearchDTO.getFilter().getSupplierName().toLowerCase()));
        }
        if(purchaseOrderSearchDTO.getFilter().getValidity() != 0L){
            predicates.add(cb.equal(cb.lower(PurchaseOrders.get("validity")),
                    purchaseOrderSearchDTO.getFilter().getValidity()));
        }
        query.where(
                cb.and(predicates.toArray(new Predicate[predicates.size()]))
        );

        if(purchaseOrderSearchDTO.getOrderBy() != null){
            if (purchaseOrderSearchDTO.getOrderType() != null && purchaseOrderSearchDTO.getOrderType().equalsIgnoreCase("ASC")) {
                query.orderBy(cb.asc(PurchaseOrders.get(purchaseOrderSearchDTO.getOrderBy())));
            } else {
                query.orderBy(cb.desc(PurchaseOrders.get(purchaseOrderSearchDTO.getOrderBy())));
            }
        } else {
            query.orderBy(cb.desc(PurchaseOrders.get("lastUpdatedTimeStamp")));
        }

        int maxResult = purchaseOrderSearchDTO.getEndIndex() - purchaseOrderSearchDTO.getStartIndex();

        return em.createQuery(query).setFirstResult(purchaseOrderSearchDTO.getStartIndex()).setMaxResults(maxResult).getResultList();

    }

    @Override
    public long countPurchaseOrderByFilter(PurchaseOrderSearchDTO purchaseOrderSearchDTO) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<PurchaseOrderDO> root = query.from(PurchaseOrderDO.class);
        query.select(builder.count(root.get("purchaseOrderId")));

        List<Predicate> predicates = new ArrayList<>();

        if(purchaseOrderSearchDTO.getFilter().getPurchaseOrderNo() != null){
            predicates.add(builder.equal(builder.lower(root.get("purchaseOrderNo")),
                    purchaseOrderSearchDTO.getFilter().getPurchaseOrderNo().toLowerCase()));
        }
        if(purchaseOrderSearchDTO.getFilter().getPurchaseOrderDate() != 0L){
            predicates.add(builder.equal(builder.lower(root.get("purchaseOrderDate")),
                    purchaseOrderSearchDTO.getFilter().getPurchaseOrderDate()));
        }
        if(purchaseOrderSearchDTO.getFilter().getSupplierName() != null){
            predicates.add(builder.equal(builder.lower(root.get("supplierName")),
                    purchaseOrderSearchDTO.getFilter().getSupplierName().toLowerCase()));
        }
        if(purchaseOrderSearchDTO.getFilter().getValidity() != 0L){
            predicates.add(builder.equal(builder.lower(root.get("validity")),
                    purchaseOrderSearchDTO.getFilter().getValidity()));
        }
        query.where(
                builder.and(predicates.toArray(new Predicate[predicates.size()]))
        );
        long result = (Long) em.createQuery(query).getSingleResult();

        return  result;

    }
}
