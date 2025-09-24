// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  // CAN IDs
  private final VictorSPX motorL2 = new VictorSPX(1);
  private final VictorSPX motorL1 = new VictorSPX(2);

  private final VictorSPX motorR2 = new VictorSPX(3);
  private final VictorSPX motorR1 = new VictorSPX(4);


  // joystick
  private final GenericHID stick = new GenericHID(0);

  @Override
  public void teleopPeriodic() {
  // Forward/backward from left stick Y (axis 1)
  double forward = -stick.getRawAxis(1) / 3;

  // Rotation from right stick X (axis 4) 
  double rotation = stick.getRawAxis(4) / 3;

  // Combine for arcade drive
  double leftSpeed = forward + rotation;
  double rightSpeed = forward - rotation;

  // Send outputs to motors
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
