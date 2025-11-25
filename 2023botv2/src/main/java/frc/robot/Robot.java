// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  private final VictorSPX motorR1 = new VictorSPX(8);
  private final VictorSPX motorR2 = new VictorSPX(2);

  private final VictorSPX motorL2 = new VictorSPX(1);
  private final VictorSPX motorL1 = new VictorSPX(9);

  private final GenericHID stick = new GenericHID(0);

  @Override
  public void robotInit() {
    double rotation = stick.getRawAxis(0);
    
    if(rotation > 0.2){
      //System.out.println(rotation);
      
      motorR1.setInverted(false);
      motorL2.setInverted(false);
    }


  }

  @Override
  public void teleopPeriodic() {
    double forward = -stick.getRawAxis(0) / 3;
    double rotation = stick.getRawAxis(5) / 3;

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
