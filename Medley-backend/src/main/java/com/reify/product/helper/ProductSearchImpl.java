package com.reify.product.helper;

import com.reify.common.model.ReviewStatusDO;
import com.reify.product.DTO.ProductSearchDTO;
import com.reify.product.model.PackTypeDO;
import com.reify.product.model.ProductApprovalStatusDO;
import com.reify.product.model.ProductDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductSearchImpl implements ProductSearch{

    @Autowired
    EntityManager em;
    @Override
    public List<ProductDO> searchProductByFilter(ProductSearchDTO productSearchDTO) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProductDO> query = cb.createQuery(ProductDO.class);

        Root<ProductDO> products = query.from(ProductDO.class);

        List<Predicate> predicates = new ArrayList<>();

        if(productSearchDTO.getProductFilter().getProductId() != null){
            predicates.add(cb.equal(cb.lower(products.get("productId")),
                    productSearchDTO.getProductFilter().getProductId().toLowerCase()));
        }
        if(productSearchDTO.getProductFilter().getProductName() != null){
            predicates.add(cb.equal(cb.lower(products.get("productName")),
                    productSearchDTO.getProductFilter().getProductName().toLowerCase()));
        }

        if(productSearchDTO.getProductFilter().getReviewStatus() != null){

            Join<ProductDO, ReviewStatusDO> reviewStatusDOJoin =
                    products.join("reviewStatus", JoinType.INNER);


            predicates.add(cb.equal(cb.lower(reviewStatusDOJoin.get("reviewCode")),
                    productSearchDTO.getProductFilter().getReviewStatus().toLowerCase()));
        }
        if(productSearchDTO.getProductFilter().getPackType() != null){

            Join<ProductDO, PackTypeDO> packTypeDOJoin =
                    products.join("packType", JoinType.INNER);


            predicates.add(cb.equal(cb.lower(packTypeDOJoin.get("packTypeCode")),
                    productSearchDTO.getProductFilter().getPackType().toLowerCase()));
        }
        if(productSearchDTO.getProductFilter().getProductApprovalStatus() != null){

            Join<ProductDO, ProductApprovalStatusDO> productApprovalStatusDOJoin =
                    products.join("productApprovalStatus", JoinType.INNER);


            predicates.add(cb.equal(cb.lower(productApprovalStatusDOJoin.get("productApprovalStatusCode")),
                    productSearchDTO.getProductFilter().getProductApprovalStatus().toLowerCase()));
        }

        query.where(
                cb.and(predicates.toArray(new Predicate[predicates.size()]))
        );


        if(productSearchDTO.getOrderBy() != null){
            if (productSearchDTO.getOrderType() != null && productSearchDTO.getOrderType().equalsIgnoreCase("ASC")) {
                query.orderBy(cb.asc(products.get(productSearchDTO.getOrderBy())));
            } else {
                query.orderBy(cb.desc(products.get(productSearchDTO.getOrderBy())));
            }
        } else {
            query.orderBy(cb.desc(products.get("lastUpdatedTimeStamp")));
        }

        int maxResult = productSearchDTO.getEndIndex() - productSearchDTO.getStartIndex();

        return em.createQuery(query).setFirstResult(productSearchDTO.getStartIndex()).setMaxResults(maxResult).getResultList();

    }

    @Override
    public long countProductByFilter(ProductSearchDTO productSearchDTO) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<ProductDO> root = query.from(ProductDO.class);
        query.select(builder.count(root.get("productId")));

        List<Predicate> predicates = new ArrayList<>();

        if(productSearchDTO.getProductFilter().getProductId() != null){
            predicates.add(builder.equal(builder.lower(root.get("productId")),
                    productSearchDTO.getProductFilter().getProductId().toLowerCase()));
        }
        if(productSearchDTO.getProductFilter().getPackType() != null){

            Join<ProductDO, PackTypeDO> packTypeDOJoin =
                    root.join("packType", JoinType.INNER);


            predicates.add(builder.equal(builder.lower(packTypeDOJoin.get("packTypeCode")),
                    productSearchDTO.getProductFilter().getPackType().toLowerCase()));
        }
        if(productSearchDTO.getProductFilter().getProductName()!= null){

            predicates.add(builder.equal(builder.lower(root.get("productName")),
                    productSearchDTO.getProductFilter().getProductName().toLowerCase()));
        }
        if(productSearchDTO.getProductFilter().getReviewStatus() != null){

            Join<ProductDO, ReviewStatusDO> reviewStatusDOJoin =
                    root.join("reviewStatus", JoinType.INNER);


            predicates.add(builder.equal(builder.lower(reviewStatusDOJoin.get("reviewCode")),
                    productSearchDTO.getProductFilter().getReviewStatus().toLowerCase()));
        }
        if(productSearchDTO.getProductFilter().getProductApprovalStatus() != null){

            Join<ProductDO, ProductApprovalStatusDO> productApprovalDOJoin =
                    root.join("productApprovalStatus", JoinType.INNER);


            predicates.add(builder.equal(builder.lower(productApprovalDOJoin.get("productApprovalStatusCode")),
                    productSearchDTO.getProductFilter().getProductApprovalStatus().toLowerCase()));
        }


        query.where(
                builder.and(predicates.toArray(new Predicate[predicates.size()]))
        );
        long result = (Long) em.createQuery(query).getSingleResult();

        return  result;
    }
}
