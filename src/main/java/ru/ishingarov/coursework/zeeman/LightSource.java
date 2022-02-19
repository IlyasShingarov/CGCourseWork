package ru.ishingarov.coursework.zeeman;


import org.springframework.stereotype.Service;

public class LightSource {

    private final double wavelength;
    private final double intensity;
    private final double eMass = 9E-31;
    private final double eCh = 1.6E-19;
    private final double lightSpeed = 3E8;

    public LightSource(double wavelength, double intensity) {
        this.wavelength = wavelength;
        this.intensity = intensity;
    }

    public double getWavelength() {
        return wavelength;
    }

    public double ZeemanShift(double B) {
        return (eCh * Math.pow(wavelength, 2) * B) / (4 * Math.PI * eMass * lightSpeed);
    }
}
