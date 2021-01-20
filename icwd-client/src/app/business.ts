import { Address } from "./address";

export class Business {
    id: string;
    name: string;
    ownerFirstName: string;
    ownerLastName: string;
    address: Address;
    email: string;
    website: string;
    phone: string;
    logo: string;
    images: string[];
    description: string;
    tags: string[];
}
