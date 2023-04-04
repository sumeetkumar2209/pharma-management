import { SelectionItem } from 'src/app/core/interfaces/interface';

export const SUPPLIER_HEADERS = [
    { header: 'Supplier Id', field: 'supplierId', type: 'default' },
    { header: 'Company Name', field: 'companyName', type: 'default'  },
    { header: 'Contact Name', field: 'contactName', type: 'default'  },
    { header: 'Contact Email', field: 'contactEmail', type: 'default'  },
    { header: 'Contact Number', field: 'contactNumber', type: 'default'  },
    { header: 'Country', field: 'country', type: 'default'  },
    { header: 'Currency', field: 'currency', type: 'default'  },
    { header: 'Valid Till', field: 'validTillDate', type: 'default'  },
    // optional
    { header: 'Address Line1', field: 'addressLine1', type: 'optional'  },
    { header: 'Town', field: 'town', type: 'optional'  },
    { header: 'Postal Code', field: 'postalCode', type: 'optional'  },
    { header: 'Reveiw Status', field: 'reviewStatus', type: 'optional'  },

];

export const SUPPLIER_WORKFLOW_HEADERS = [
    { header: 'Workflow Id', field: 'workFlowId', type: 'default' },
    { header: 'Supplier Id', field: 'supplierId', type: 'default' },
    { header: 'Company Name', field: 'companyName', type: 'default' },
    { header: 'Contact Name', field: 'contactName', type: 'default' },
    { header: 'Contact Email', field: 'contactEmail', type: 'default' },
    { header: 'Contact Number', field: 'contactNumber', type: 'default' },
    { header: 'Country', field: 'country', type: 'default' },
    { header: 'Currency', field: 'currency', type: 'default' },
    { header: 'Review Status', field: 'reviewStatus', type: 'default' },
    { header: 'Valid Till', field: 'validTillDate', type: 'default' },
];

export const CUSTOMER_HEADERS = [
    { header: 'Customer Id', field: 'customerId' },
    { header: 'Company Name', field: 'companyName' },
    { header: 'Contact Name', field: 'contactName' },
    { header: 'Contact Email', field: 'contactEmail' },
    { header: 'Contact Number', field: 'contactNumber' },
    { header: 'Country', field: 'country' },
    { header: 'Currency', field: 'currency' },
    { header: 'Status', field: 'qualificationStatus' },
    { header: 'Valid Till', field: 'validTill' },
];

export const STATUS_OPTION: SelectionItem[] = [
    { label: 'Active', value: 'AC' },
    { label: 'Inactive', value: 'IN' }];

export const QUALIFICATION_STATUS_OPTION: SelectionItem[] = [
    { label: 'QUALIFIED', value: 'QF' },
    { label: 'NOT-QUALIFIED', value: 'NQ' }];
export const APPROVAL_STATUS_OPTION: SelectionItem[] = [
    { label: 'PENDING', value: 'PE' },
    { label: 'DRAFT', value: 'DR' },
    { label: 'APPROVED', value: 'AP' },
    { label: 'REJECTED', value: 'RE' }

];
export const COUNTRY_OPTION: SelectionItem[] = [
    { label: 'INDIA', value: 'IN' },
    { label: 'UNITED KINGDOM', value: 'UK' },
    { label: 'UNITED STATES', value: 'US' },
    { label: 'AUSTRALIA', value: 'AU' },
    { label: 'CANADA', value: 'CA' }

];
export const CURRENCY_OPTION: SelectionItem[] = [
    { label: 'INR', value: 'INR' },
    { label: 'USD', value: 'USD' },
    { label: 'GBP', value: 'GBP' }

];

