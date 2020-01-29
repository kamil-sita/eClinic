import { Employee } from './doctor';

describe('Doctor', () => {
  it('should create an instance', () => {
    expect(new Employee()).toBeTruthy();
  });
});
