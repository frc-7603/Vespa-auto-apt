// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  // CAN IDs for drivetrain
  private final VictorSPX motorL2 = new VictorSPX(2);
  private final VictorSPX motorL1 = new VictorSPX(1);
  private final VictorSPX motorR2 = new VictorSPX(8);
  private final VictorSPX motorR1 = new VictorSPX(9);

  // project
  private final VictorSPX topShooter = new VictorSPX(3);

  // Joystick
  private final Joystick stick = new Joystick(0);

  @Override
  public void teleopPeriodic() {
    // Drivetrain Control 
    double forward = stick.getRawAxis(0) / 3;   // left stick X
    double rotation = -stick.getRawAxis(5) / 3;   // right stick Y

    double leftSpeed = forward + rotation;
    double rightSpeed = forward - rotation;

    motorL1.set(ControlMode.PercentOutput, leftSpeed);
    motorL2.set(ControlMode.PercentOutput, leftSpeed);
    motorR1.set(ControlMode.PercentOutput, rightSpeed);
    motorR2.set(ControlMode.PercentOutput, rightSpeed);
}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}
}
