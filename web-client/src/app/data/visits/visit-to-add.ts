export class VisitToAdd {

  patientId: number;
  serviceId: number;
  employeeId: number;
  scheduledDate: string;
  startingTime: string;

  constructor(patientId: number,
              serviceId: number,
              employeeId: number,
              scheduledDate: string,
              startingTime: string) {
    this.patientId = patientId;
    this.serviceId = serviceId;
    this.employeeId = employeeId;
    this.scheduledDate = scheduledDate;
    this.startingTime = startingTime;
  }
}
