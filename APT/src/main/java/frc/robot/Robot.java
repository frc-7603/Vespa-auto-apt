package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Transform3d;

public class Robot extends TimedRobot {

    private PhotonCamera camera; // Declare the camera instance

    private final VictorSPX motorR1 = new VictorSPX(8);
    private final VictorSPX motorR2 = new VictorSPX(2);

    private final VictorSPX motorL2 = new VictorSPX(1);
    private final VictorSPX motorL1 = new VictorSPX(9);

    private final GenericHID stick = new GenericHID(0);

    // PID-like constants
    private final double kP_turn = 0.03;     // how hard to turn toward yaw correction
    private final double kP_forward = 0.1;   // how hard to drive toward target

    private boolean autoEnabled = false;

    @Override
    public double getPeriod(){
        return 0.2; // run every 5ms
    }
    
    @Override
    public void robotInit() {
        // Initialize PhotonCamera
        camera = new PhotonCamera("cam1");
        
        // Start camera streaming (optional for visualization)
        UsbCamera usbCamera = CameraServer.startAutomaticCapture();
        usbCamera.setResolution(352, 288);
    }

    @Override
    public void robotPeriodic() {
        // Get the latest result from the PhotonCamera
        PhotonPipelineResult result = camera.getLatestResult();
        // Check if the camera has detected any targets
        if (result.hasTargets()) {
            // Get the best target
            PhotonTrackedTarget target = result.getBestTarget();

            // Retrieve target information
            double yaw = target.getYaw(); // Horizontal angle to target
            //double pitch = target.getPitch(); // Vertical angle to target

            // Get the transform from the camera to the target (if available)
            Transform3d camToTarget = target.getBestCameraToTarget();

            if (camToTarget != null) {
                System.out.println("Camera to Target Transform: " + camToTarget);
//67
                // Calculate the 3D distance from the camera to the target
                double distance = Math.sqrt(
                    camToTarget.getTranslation().getX() * camToTarget.getTranslation().getX() +
                    camToTarget.getTranslation().getY() * camToTarget.getTranslation().getY() +
                    camToTarget.getTranslation().getZ() * camToTarget.getTranslation().getZ()
                );

                // Print the distance to the console
                System.out.println("Distance to Target: " + distance);
                // Get the id of the apriltag
                int AprilId = target.getFiducialId();
                // Print apriltag id
                System.out.println("Detected ID: " + AprilId);
                // Debug output for yaw and pitch
                System.out.println("Yaw: " + yaw);
                //System.out.println( "Pitch: " + pitch);
            }
        } else {
            // No targets detected
            System.out.println("No targets detected.");
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
    public void autonomousInit() {
        autoEnabled = true;
        System.out.println("AUTO STARTED");
    }

    @Override
    public void autonomousPeriodic() {
        if (!autoEnabled) return;

        PhotonPipelineResult result = camera.getLatestResult();

        if (result.hasTargets()) {
            PhotonTrackedTarget target = result.getBestTarget();

            double yaw = target.getYaw();  // angle left/right to tag

            // Distance from camera to tag (meters)
            Transform3d camToTarget = target.getBestCameraToTarget();
            double distance = camToTarget.getTranslation().getX();

            // Turning control
            double turnSpeed = yaw * kP_turn;

            // Forward control
            double forwardSpeed = distance * kP_forward;

            // Clamp speeds so robot doesn’t go crazy (Crazy like LE SRFM Crazy?)
            turnSpeed = Math.max(-0.4, Math.min(0.4, turnSpeed));
            forwardSpeed = Math.max(-0.5, Math.min(0.5, forwardSpeed));

            // Stop when within 0.5 meters
            if (distance < 0.5) {
                forwardSpeed = 0;
                turnSpeed = 0;
                System.out.println("Reached Tag!");
            }

            // Tank drive output
            double leftSpeed = forwardSpeed + turnSpeed;
            double rightSpeed = forwardSpeed - turnSpeed;

            motorL1.set(ControlMode.PercentOutput, leftSpeed);
            motorL2.set(ControlMode.PercentOutput, leftSpeed);

            motorR1.set(ControlMode.PercentOutput, rightSpeed);
            motorR2.set(ControlMode.PercentOutput, rightSpeed);

            System.out.println("AUTO: yaw=" + yaw + " dist=" + distance);

        } else {
            motorL1.set(ControlMode.PercentOutput, 0);
            motorL2.set(ControlMode.PercentOutput, 0);
            motorR1.set(ControlMode.PercentOutput, 0);
            motorR2.set(ControlMode.PercentOutput, 0);

            System.out.println("AUTO: No tag!");
        }
    }
}