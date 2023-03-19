import { SelectionItem } from 'src/app/core/interfaces/interface';


export const STATUS_OPTION: SelectionItem[] = [
    {label:'Active', value:'AC'},
    {label:'Inactive', value:'IN'}];

export const QUALIFICATION_STATUS_OPTION: SelectionItem[] =  [
    {label:'QUALIFIED', value:'QF'},
    {label:'NOT-QUALIFIED', value:'NQ'}];
export const APPROVAL_STATUS_OPTION: SelectionItem[] =  [
    {label:'PENDING', value:'PE'},
    {label:'DRAFT', value:'DR'},
    {label:'APPROVED', value:'AP'},
    {label:'REJECTED', value:'RE'}

];
export const COUNTRY_OPTION: SelectionItem[] =  [
    {label:'INDIA', value:'IN'},
    {label:'UNITED KINGDOM', value:'UK'},
    {label:'UNITED STATES', value:'US'},
    {label:'AUSTRALIA', value:'AU'},
    {label:'CANADA', value:'CA'}

];
export const CURRENCY_OPTION: SelectionItem[] =  [
    {label:'INR', value:'INR'},
    {label:'USD', value:'USD'},
    {label:'GBP', value:'GBP'}

];

