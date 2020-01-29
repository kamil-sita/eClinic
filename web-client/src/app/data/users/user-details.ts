import {UserRole} from "./user-role.enum";

export class UserDetails {

  userId: number;
  firstName: string;
  lastName: string;
  role: UserRole;
  username: string;


  constructor(userId: number, firstName: string, lastName: string, role: string, username: string) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = UserRole[role];
    this.username = username;
  }
}
