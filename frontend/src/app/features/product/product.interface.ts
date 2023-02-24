export interface ProductInterface {
    productId: string;
    productName: string;
    productManufacturer: string;
    productManufacturerCountry: string;
    licenseNumber: number;
    productStatus: ProductStatus; 
    productApprovingAuthority:string;
    dosageForm: string;
    packType: string;
    packSize: number;
    pricePerPack: number;
    taxPercentage:number;
    taxationType:string;
    strength:string;
    currency: string;
    productPhoto:string;
    approver: string;
    initialAdditionDate: string;
    lastUpdatedBy: string;
    flowStatus: WorkflowStatus;
}

export enum ProductStatus {
    Active, Inactive
}

export enum WorkflowStatus {
    Draft, Pending, Approved, Rejected
}

export interface ProductFilterInterface {
    productId: string;
    productName: string;
    packType: string;
    productStatus: string;
}
