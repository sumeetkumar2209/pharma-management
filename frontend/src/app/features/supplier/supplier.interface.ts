export interface SupplierInterface {
    supplierId: number;
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
    validTill: string; // validation for future dateD
    currency: string;
    approver: string;
    userId: string;
    initialAdditionDate: string;
    lastUpdatedBy: string;
    flowStatus: WorkflowStatus;
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
