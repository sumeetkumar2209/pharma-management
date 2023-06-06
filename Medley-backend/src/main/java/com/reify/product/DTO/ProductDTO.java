package com.reify.product.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonIgnoreProperties(value = {"initialAdditionDate",
        "lastUpdatedBy","workFlowId","reviewStatus","action"})
public class ProductDTO {

    private String workFlowId;

    private String productId;

    private String UserId;

    private String productName;

    private String licenceNumber;

    private String productDosageForm;

    private String strength;

    private String packType;

    private int packSize;

    private String productManufactureCountry;

    private String productApprovalStatus;

    private String productApprovingAuthority;

    private double pricePerPack;


    private String currency;

    private double taxPercent;

    private String taxationType;

    private long initialAdditionDate;
    private String lastUpdatedBy;
    private long lastUpdatedTimestamp;

    private String reviewStatus;

    private String productManufacturer;

    private String action;

    private String comments;
    private String approver;

    private MultipartFile multipartFile;

    private String photoFileName;


}
