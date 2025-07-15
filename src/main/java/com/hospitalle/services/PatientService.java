package com.hospitalle.services;

import com.hospitalle.models.Auth;

import java.io.Serializable;
import java.util.List;

public interface PatientService extends Serializable {
    List<Auth> getDoctors();
}
