package com.example.kolesinski.bmi;

/**
 * Created by stud on 14.03.2017.
 */

public class CountBMIForKgM implements ICountBMI {
   private final static float MIN_WEIGHT=10;
   private final static float MAX_WEIGHT=250;
   private final static float MIN_HEIGHT= 1.5f;
   private final static float MAX_HEIGHT= 2.5f;

    public boolean isValidMass(float mass)
{
    return mass<MAX_WEIGHT && mass > MIN_WEIGHT;
}
    public boolean isValidHeight(float height)
    {
        return height<MAX_HEIGHT && height > MIN_HEIGHT;
    }
    public float countBMI(float mass,float height) throws IllegalArgumentException
    {
        if(!isValidMass(mass) || !isValidHeight(height)) throw new IllegalArgumentException();
        else
        {
            return mass/(height*height);
        }

    }

}
