import {Patient} from "../patients/patient";
import {Employee} from "../doctors/doctor";
import {MedicalService} from "../services/medical-service";

export class Visit {
  visitId: number;
  patient: Patient;
  employee: Employee;
  scheduledDate: string;
  visitState: string;
  estimatedDuration: number;
  medicalServices: MedicalService[];
}
