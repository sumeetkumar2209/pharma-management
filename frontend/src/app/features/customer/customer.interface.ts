export interface CustomerInterface {
    customerId: number;
    customerStatus: CustomerStatus; //use Green for Active, Red for inactive
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
    customerQualificationStatus: CustomerQualificationStatus;
    validTill: string; // validation for future dateD
    currency: string;
    approver: string;
    userId: string;
    initialAdditionDate: string;
    lastUpdatedBy: string;
    flowStatus: WorkflowStatus;
}

export enum CustomerStatus {
    Active, Inactive
}
export enum CustomerQualificationStatus {
    Qualified, Non_Qualified
}
export enum WorkflowStatus {
    Draft, Pending, Approved, Rejected
}

export interface CustomerFilterInterface {
    customerId: string;
    customerName: string;
    postalCode: string;
    customerStatus: string;
}
