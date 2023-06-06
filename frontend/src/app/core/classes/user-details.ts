import { SelectionItem } from "../interfaces/interface";

export class UserDetails {
    email: string;
    contactNo: string;
    firstName: string;
    lastName: string;
    menuList: any[];
    roleId: number;
    roleName: string;
    userId: string;
    approverMap: SelectionItem[];

    constructor(obj: any) {
        this.email = obj.emailId;
        this.contactNo = obj.contactNo;
        this.firstName = obj.firstName;
        this.lastName = obj.lastName;
        this.menuList = obj.menuList;
        this.roleId = obj.roleId;
        this.roleName = obj.roleName;
        this.userId = obj.userId;
        this.approverMap = Object.keys(obj.approverMap).map(key => {
            return {
                value: key,
                label: obj.approverMap[key]
            };
        })
    }


    public get fullName() {
        return `${this.firstName} ${this.lastName}`
    }
}

export interface approverInterface {
    approverId: string;
    approverName: string;
}