package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.wpilibj.GenericHID;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.FieldCentricDrivetrain;

public class RobotContainer {
    private final double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond)/2;
    private final double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond)/2;

    private final SwerveRequest.FieldCentric drive = new FieldCentricDrivetrain()
            .withDeadband(MaxSpeed * 0.1)
            .withRotationalDeadband(MaxAngularRate * 0.1)
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    // private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    // private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    // private final Telemetry logger = new Telemetry(MaxSpeed);

    private final GenericHID joystick = new GenericHID(0);

    // Subsystems
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        drivetrain.setDefaultCommand(
            drivetrain.applyRequest(() -> {

                // Get joystick input
                double forward = -joystick.getRawAxis(1);  // Left stick Y (invert so up is forward)
                double strafe = joystick.getRawAxis(0);    // Left stick X
                double rotation = joystick.getRawAxis(4);  // Right stick X (rotation control)

                // Deadband to ignore small movements
                if (Math.abs(forward) < 0.05) forward = 0;
                if (Math.abs(strafe) < 0.05) strafe = 0;
                if (Math.abs(rotation) < 0.05) rotation = 0;

                return drive
                    .withVelocityX(forward * MaxSpeed)
                    .withVelocityY(strafe * MaxSpeed)
                    .withRotationalRate(rotation * MaxAngularRate);
            })
        );
    }

    public void getAutonomousCommand() {}
}