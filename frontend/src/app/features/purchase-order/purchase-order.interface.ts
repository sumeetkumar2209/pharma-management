export interface PurchaseOrderInterface {
    purchaseOrderNumber: string;
    supplierName: string;
    purchaseOrderDate: string;
    validity: string;
    purchaseOrderStatus: PurchaseOrderStatus; //use Green for Active, Red for inactive
    totalPOAmount:number;
    dateLastUpdated:string;
    lastUpdatedUser:string;

    approver: string;
    lastUpdatedBy: string;
    flowStatus: WorkflowStatus;
}

export interface PurchaseOrderLineItemInterface {
    id:string,
    productId:string;
    licenseNumber: string;
    productName: string;
    dosageForm: string;
    strength:string;
    packType: string;
    packSize: number;
    supplierName: string;
    purchaseOrderQuantity: string;
    pricePerPack: number;
    totalProductAmount:string;
}

export enum PurchaseOrderStatus {
    Active, Inactive
}

export enum WorkflowStatus {
    Draft, Pending, Approved, Rejected
}

export interface PurchaseOrderFilterInterface {
    purchaseOrderId: string;
    purchaseOrderName: string;
    postalCode: string;
    purchaseOrderStatus: string;
}
