package com.reify.common.constant;

public enum JsonSchemaEnum {

    SUPPLIER_SCHEMA("supplierSchema.json"),
    CUSTOMER_SCHEMA("customerSchema.json"),
    PRODUCT_SCHEMA("productSchema.json"),
    APPROVE_SCHEMA("approveSchema.json");

    private String name;

    private JsonSchemaEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}
