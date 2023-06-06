export interface SelectionItem{
    label:string;
    value:string;
}

export interface reviewStatusMeta{
    reviewCode:string;
    reviewName:string;
}
export interface statusMeta{
    statusCode:string;
    statusName:string;
}
export interface currencyMeta{
    currencyCode:string;
    currencyName:string;
}
export interface qualificationStatusMeta{
    qualificationCode:string;
    qualificationName:string;
}

export interface countryMeta{
    countryCode:string;
    countryName:string;
}

export interface MetadataInterface{
    reviewStatusList:reviewStatusMeta[];
    statusList:statusMeta[];
    currencyList:currencyMeta[];
    qualificationStatusList:qualificationStatusMeta[];
    countryList:countryMeta[];
}

export interface OptionsInterface{
    reviewStatusList:SelectionItem[];
    statusList:SelectionItem[];
    currencyList:SelectionItem[];
    qualificationStatusList:SelectionItem[];
    countryList:SelectionItem[];
}