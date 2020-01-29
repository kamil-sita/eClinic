export class MedicalService {
  medicalServiceId: number;
  serviceName: string;
  duration: number;

  constructor(medicalServiceId: number,
              serviceName: string,
              duration: number) {
    this.medicalServiceId = medicalServiceId;
    this.serviceName = serviceName;
    this.duration = duration;
  }
}
