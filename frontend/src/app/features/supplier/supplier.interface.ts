export interface SupplierInterface {
    workFlowId?: string;
    supplierId: number|null;
    supplierStatus: SupplierStatus; //use Green for Active, Red for inactive
    companyName: string;
    contactName: string;
    contactNumber: number;
    contactEmail: string;//Generic email Id validation
    addressLine1: string;
    addressLine2?: string;
    addressLine3?: string;
    town: string;
    country: string;
    postalCode: string;
    supplierQualificationStatus: SupplierQualificationStatus;
    validTillDate: string; // validation for future dateD
    currency: string;
    approver?: string;
    userId: string;
    initialAdditionDate: string|null;
    lastUpdatedBy?: string;
    lastUpdatedTimeStamp?:string;
    reviewStatus?: WorkflowStatus;
    comments?:string;
}

export enum SupplierStatus {
    Active, Inactive
}
export enum SupplierQualificationStatus {
    Qualified, Non_Qualified
}
export enum WorkflowStatus {
    Draft, Pending, Approved, Rejected
}

export interface SupplierFilterInterface {
    supplierId: string;
    supplierName: string;
    postalCode: string;
    supplierStatus: string;
}

