package frc.robot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends TimedRobot {

  // CIM motors are BRUSHED
  private final SparkMax motorR1 = new SparkMax(8, MotorType.kBrushed);
  private final SparkMax motorR2 = new SparkMax(2, MotorType.kBrushed);

  private final SparkMax motorL1 = new SparkMax(9, MotorType.kBrushed);
  private final SparkMax motorL2 = new SparkMax(1, MotorType.kBrushed);

  private final XboxController controller = new XboxController(0);

  @Override
  public void robotInit() {
   
  }

  @Override
  public void teleopPeriodic() {

    double forward = -controller.getLeftY();   // Forward / backward
    double rotation = controller.getRightX();  // Turn

    // Speed limit
    forward *= 0.5;
    rotation *= 0.5;

    double leftSpeed = forward + rotation;
    double rightSpeed = forward - rotation;

    motorL1.set(leftSpeed);
    motorL2.set(leftSpeed);

    motorR1.set(rightSpeed);
    motorR2.set(rightSpeed);
  }
}