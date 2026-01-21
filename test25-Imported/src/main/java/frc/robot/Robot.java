// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class Robot extends TimedRobot {

  
  RevMotor fuelMotor = null;



  // CAN IDs for drivetrain

  // Joystick
  private final Joystick stick = new Joystick(0);

  // Shooter button mappings
  private final int intakeButton = 5;

  @Override
  public void teleopInit() {}


  @Override
  public void teleopPeriodic() {

    if (fuelMotor == null) fuelMotor = new RevMotor(2, MotorType.kBrushed);

    double fuelInSpeed = 0.4;

    // Shooter Control 
    if (stick.getRawButton(intakeButton)) {
      
      fuelMotor.Motor.set(fuelInSpeed);

    } 
}
  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}
}
