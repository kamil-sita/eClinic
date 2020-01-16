import {Patient} from "../patients/patient";
import {Employee} from "../doctors/doctor";

export class Visit {
  visitId: Number;
  patient: Patient;
  employee: Employee;
}
