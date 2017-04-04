package com.example.kolesinski.bmi;

/**
 * Created by stud on 14.03.2017.
 */

public interface ICountBMI {
   public float countBMI(float mass, float height);
   public boolean isValidMass(float mass);
   public boolean isValidHeight(float height);
}
