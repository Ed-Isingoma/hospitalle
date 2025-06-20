package com.hospitalle.controllers;

import com.hospitalle.dao.*;
import com.hospitalle.models.*;
import com.hospitalle.views.ConsoleView;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class HospitalleContr {
    public static void handleUser(int action, int table) {
        AppointmentDao apptDao = new AppointmentDao();
        AdmissionDao admDao = new AdmissionDao();

        switch (table) {
            case 1: // Auth
                AuthDao authDao = new AuthDao();
                if (action == 1) {         // CREATE
                    Auth a = new Auth();
                    System.out.print("Username: ");
                    a.setUserName(ConsoleView.getStringInput());
                    System.out.print("Email: ");
                    a.setEmail(ConsoleView.getStringInput());
                    System.out.print("Password: ");
                    a.setPassword(ConsoleView.getStringInput());
                    System.out.print("Role: ");
                    a.setRole(ConsoleView.getStringInput());
                    authDao.save(a);

                } else if (action == 2) {  // VIEW
                    System.out.println("------------------------");
                    authDao.findAll().forEach(x ->
                            System.out.println(x.getId() + ": " + x.getUserName() + ": " + x.getEmail() + ": " + x.getRole())
                    );
                    System.out.println("------------------------");

                } else if (action == 3) {  // UPDATE
                    System.out.print("Auth ID to update: ");
                    Long id = ConsoleView.getLongInput();
                    Auth a = authDao.findById(id);
                    if (a != null) {
                        System.out.print("New email: ");
                        a.setEmail(ConsoleView.getStringInput());
                        System.out.print("New role: ");
                        a.setRole(ConsoleView.getStringInput());
                        authDao.save(a);
                    } else {
                        System.out.println("Not found.");
                    }

                } else {                   // DELETE
                    System.out.print("Auth ID to delete: ");
                    Long id = ConsoleView.getLongInput();
                    Auth a = authDao.findById(id);
                    if (a != null) authDao.delete(a);
                    else System.out.println("Not found.");
                }
                break;

            case 2: // PatientData
                PatientDataDao pdDao = new PatientDataDao();
                if (action == 1) {
                    PatientData pd = new PatientData();
                    System.out.print("Auth ID (owner): ");
                    Auth owner = new AuthDao().findById(ConsoleView.getLongInput());
                    if (owner == null) {
                        System.out.println("Owner DB relation failed");
                        break;
                    }
                    pd.setAuth(owner);
                    System.out.print("Allergies: ");
                    pd.setAllergies(ConsoleView.getStringInput());
                    System.out.print("DOB (YYYY-MM-DD): ");
                    pd.setDateOfBirth(LocalDate.parse(ConsoleView.getStringInput()));
                    System.out.print("Other Diseases: ");
                    pd.setOtherDiseases(ConsoleView.getStringInput());
                    pdDao.save(pd);

                } else if (action == 2) {
                    System.out.println("------------------------");
                    pdDao.findAll().forEach(x ->
                            System.out.println(x.getId() + ": " + x.getAuth().getUserName() + ": " + x.getDateOfBirth())
                    );
                    System.out.println("------------------------");

                } else if (action == 3) {
                    System.out.print("PatientData ID to update: ");
                    PatientData pd = pdDao.findById(ConsoleView.getLongInput());
                    if (pd != null) {
                        System.out.print("New allergies: ");
                        pd.setAllergies(ConsoleView.getStringInput());
                        System.out.print("New other diseases: ");
                        pd.setOtherDiseases(ConsoleView.getStringInput());
                        pdDao.save(pd);
                    } else System.out.println("Not found.");

                } else {
                    System.out.print("PatientData ID to delete: ");
                    PatientData pd = pdDao.findById(ConsoleView.getLongInput());
                    if (pd != null) pdDao.delete(pd);
                    else System.out.println("Not found.");
                }
                break;

            case 3: // Admission
                if (action == 1) {
                    Admission adm = new Admission();
                    System.out.print("Patient Auth ID: ");
                    Auth patient = new AuthDao().findById(ConsoleView.getLongInput());
                    if (patient == null) {
                        System.out.println("Patient DB relation failed");
                        break;
                    }
                    adm.setPatient(patient);
                    adm.setStartTime(LocalDate.now());
                    System.out.print("Ward: ");
                    adm.setWard(ConsoleView.getStringInput());
                    admDao.save(adm);

                } else if (action == 2) {
                    System.out.println("------------------------");
                    admDao.findAll().forEach(x ->
                            System.out.println(x.getId() + ": " + x.getPatient().getUserName() + " – " + x.getWard()
                            + ": " + x.getStartTime() + ": " + x.getDischargeDate())
                    );
                    System.out.println("------------------------");

                } else if (action == 3) {
                    System.out.print("Admission ID to update: ");
                    Admission adm = admDao.findById(ConsoleView.getLongInput());
                    if (adm != null) {
                        System.out.print("New discharge date (YYYY-MM-DD): ");
                        adm.setDischargeDate(LocalDate.parse(ConsoleView.getStringInput()));
                        admDao.save(adm);
                    } else System.out.println("Not found.");

                } else {
                    System.out.print("Admission ID to delete: ");
                    Admission adm = admDao.findById(ConsoleView.getLongInput());
                    if (adm != null) admDao.delete(adm);
                    else System.out.println("Not found.");
                }
                break;

            case 4: // Appointment
                if (action == 1) {
                    Appointment appt = new Appointment();
                    System.out.print("Patient Auth ID: ");
                    Auth patient = new AuthDao().findById(ConsoleView.getLongInput());
                    if (patient == null) {
                        System.out.println("Patient DB relation failed");
                        break;
                    }
                    appt.setPatient(patient);
                    System.out.print("Speciality ID: ");
                    Speciality spec = new SpecialityDao().findById(ConsoleView.getLongInput());
                    if (spec == null) {
                        System.out.println("Patient DB relation failed");
                        break;
                    }
                    appt.setSpeciality(spec);
                    System.out.print("Symptoms: ");
                    appt.setSymptoms(ConsoleView.getStringInput());
                    System.out.print("Diagnosis: ");
                    appt.setDiagnosis(ConsoleView.getStringInput());
                    apptDao.save(appt);

                } else if (action == 2) {
                    System.out.println("------------------------");
                    apptDao.findAll().forEach(x ->
                            System.out.println(x.getId() + ": " + x.getSymptoms())
                    );
                    System.out.println("------------------------");

                } else if (action == 3) {
                    System.out.print("Appointment ID to update: ");
                    Appointment appt = apptDao.findById(ConsoleView.getLongInput());
                    if (appt != null) {
                        System.out.print("New diagnosis: ");
                        appt.setDiagnosis(ConsoleView.getStringInput());
                        System.out.print("New prescription: ");
                        appt.setDiagnosis(ConsoleView.getStringInput());
                        apptDao.save(appt);
                    } else System.out.println("Not found.");

                } else {
                    System.out.print("Appointment ID to delete: ");
                    Appointment appt = apptDao.findById(ConsoleView.getLongInput());
                    if (appt != null) apptDao.delete(appt);
                    else System.out.println("Not found.");
                }
                break;

            case 5: // Availability
                AvailabilityDao avDao = new AvailabilityDao();
                if (action == 1) {
                    Availability av = new Availability();
                    System.out.print("Appointment ID: ");
                    Appointment appt = apptDao.findById(ConsoleView.getLongInput());
                    if (appt == null) {
                        System.out.println("Appointment DB relation failed");
                        break;
                    }
                    av.setAppointment(appt);
                    System.out.print("Doctor Auth ID: ");
                    Auth doc = new AuthDao().findById(ConsoleView.getLongInput());
                    if (doc == null) {
                        System.out.println("Doctor DB relation failed");
                        break;
                    }
                    av.setDoctor(doc);
                    av.setStartTime(LocalDateTime.now());
                    av.setEndTime(LocalDateTime.now().plusHours(1));
                    avDao.save(av);

                } else if (action == 2) {
                    System.out.println("------------------------");
                    avDao.findAll().forEach(x ->
                            System.out.println(x.getId() + ": " + x.getStartTime() + ": "
                            + x.getEndTime() + ": " + x.getAppointment().getId() + ": " + x.getDoctor().getUserName())
                    );
                    System.out.println("------------------------");

                } else if (action == 3) {
                    System.out.print("Availability ID to update: ");
                    Availability av = avDao.findById(ConsoleView.getLongInput());
                    if (av != null) {
                        System.out.print("New end time (YYYY-MM-DDTHH:MM): ");
                        av.setEndTime(LocalDateTime.parse(ConsoleView.getStringInput()));
                        avDao.save(av);
                    } else System.out.println("Not found.");

                } else {
                    System.out.print("Availability ID to delete: ");
                    Availability av = avDao.findById(ConsoleView.getLongInput());
                    if (av != null) avDao.delete(av);
                    else System.out.println("Not found.");
                }
                break;

            case 6: // Speciality
                SpecialityDao spDao = new SpecialityDao();
                if (action == 1) {
                    Speciality sp = new Speciality();
                    System.out.print("Name: ");
                    sp.setName(ConsoleView.getStringInput());
                    System.out.print("Hourly Price: ");
                    sp.setHourlyPrice(ConsoleView.getIntInput());
                    System.out.print("Doctor Attached: ");
                    Auth doc = new AuthDao().findById(ConsoleView.getLongInput());
                    if (doc == null) {
                        System.out.println("Doctor DB relation failed");
                        break;
                    }
                    sp.setDoctor(doc);
                    spDao.save(sp);

                } else if (action == 2) {
                    System.out.println("------------------------");
                    spDao.findAll().forEach(x ->
                            System.out.println(x.getId() + ": " + x.getName() + ": " + x.getHourlyPrice()
                            + ": " + x.getDoctor().getUserName())
                    );
                    System.out.println("------------------------");

                } else if (action == 3) {
                    System.out.print("Speciality ID to update: ");
                    Speciality sp = spDao.findById(ConsoleView.getLongInput());
                    if (sp != null) {
                        System.out.print("New name: ");
                        sp.setName(ConsoleView.getStringInput());
                        System.out.print("New price: ");
                        sp.setHourlyPrice(ConsoleView.getIntInput());
                        spDao.save(sp);
                    } else System.out.println("Not found.");

                } else {
                    System.out.print("Speciality ID to delete: ");
                    Speciality sp = spDao.findById(ConsoleView.getLongInput());
                    if (sp != null) spDao.delete(sp);
                    else System.out.println("Not found.");
                }
                break;

            case 7: // Payment
                PaymentDao payDao = new PaymentDao();
                if (action == 1) {
                    Payment p = new Payment();
                    System.out.print("Appointment ID: ");
                    Appointment appt = apptDao.findById(ConsoleView.getLongInput());
                    if (appt == null) {
                        System.out.println("Appointment DB relation failed");
                        break;
                    }
                    p.setAppointment(appt);
                    System.out.print("Admission ID: ");
                    Admission adm = new AdmissionDao().findById(ConsoleView.getLongInput());
                    if (adm == null) {
                        System.out.println("Admission DB relation failed");
                        break;
                    }
                    p.setAdmission(adm);
                    System.out.print("Amount: ");
                    p.setAmount(ConsoleView.getIntInput());
                    p.setPaymentDate(LocalDate.now());
                    payDao.save(p);

                } else if (action == 2) {
                    System.out.println("------------------------");
                    payDao.findAll().forEach(x ->
                            System.out.println(x.getId() + ": " + x.getAmount() + ": " + x.getAdmission().getId() +
                            ": " + x.getAppointment().getId())
                    );
                    System.out.println("------------------------");

                } else if (action == 3) {
                    System.out.print("Payment ID to update: ");
                    Payment p = payDao.findById(ConsoleView.getLongInput());
                    if (p != null) {
                        System.out.print("New amount: ");
                        p.setAmount(ConsoleView.getIntInput());
                        payDao.save(p);
                    } else System.out.println("Not found.");

                } else {
                    System.out.print("Payment ID to delete: ");
                    Payment p = payDao.findById(ConsoleView.getLongInput());
                    if (p != null) payDao.delete(p);
                    else System.out.println("Not found.");
                }
                break;
        }
    }
}
