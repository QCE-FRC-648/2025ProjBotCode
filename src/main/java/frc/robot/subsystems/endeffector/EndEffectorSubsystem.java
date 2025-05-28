package frc.robot.subsystems.endeffector;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CANConfig;
import frc.robot.Constants.tiltConstants;
import edu.wpi.first.wpilibj.DigitalInput;

public class EndEffectorSubsystem extends SubsystemBase
{
    public final SparkMax endEffectorMotor;
    public final SparkMax endEffectorTilt;
    public final SparkMax endEffectorIntake;

    private DigitalInput coralBeam;
    private PIDController tiltPIDController = new PIDController(.06, 0.0, 0.0);
    private PIDController endEffectorPositionPIDController = new PIDController(.1, 0.0, 0.0);
    private boolean runOnce = false; 

    public EndEffectorSubsystem()
    {
        endEffectorIntake = new SparkMax(CANConfig.END_EFFECTOR_INTAKE, MotorType.kBrushless);
        endEffectorMotor = new SparkMax(CANConfig.END_EFFECTOR_MOTOR, MotorType.kBrushless);
        endEffectorTilt = new SparkMax(CANConfig.END_EFFECTOR_TILT, MotorType.kBrushless);
        tiltPIDController.setTolerance(.5);
        coralBeam = new DigitalInput(6);

        endEffectorPositionPIDController.setTolerance(3);
    }
    
    public void intake(double speed) {
        endEffectorIntake.set(speed);
      }
      
    public void setSpeedEndEffectorMotor(double speed) 
    {
        endEffectorIntake.set(speed);
        endEffectorMotor.set(speed);
    }

    public void setSpeedEndEffectorTilt(double speed) 
    {
        endEffectorTilt.set(speed);
    }

    private double endEffectorPosition;
    public void feedCoralIntoEndEffector(double speed) 
    {
        runOnce = true; 
        boolean breakBeamCurrent = coralBeam.get();
        if (breakBeamCurrent == true)
        {
            // break beam not triggered
            endEffectorIntake.set(speed);
            endEffectorMotor.set(speed);

        }
        // if beam broken, and boolean runOnce is false, get position, set runOnce to true
        else if (breakBeamCurrent == false && runOnce == false)
        {
            // if break beam was not triggered and is now is triggered
            endEffectorPosition = endEffectorMotor.getEncoder().getPosition();
            runOnce = true;
        }

        if (breakBeamCurrent == false)
        {
            // we have a coral
            // so use a PID on position of current position + 3 rotations
            double pidSpeed = endEffectorPositionPIDController.calculate(endEffectorPosition, endEffectorPosition + 3);
            pidSpeed = MathUtil.clamp(pidSpeed, -1, 1);
            
            if (!endEffectorPositionPIDController.atSetpoint())
            {
                endEffectorIntake.set(pidSpeed);
                endEffectorMotor.set(pidSpeed);
            }
            else{
                endEffectorIntake.set(0);
                endEffectorMotor.set(0);
            }
        }
    }

    public void setPIDGains(double P, double I, double D){
        tiltPIDController.setPID(P, I, D); 
    }

    public void goToTilt(double encoders){ 
        double speed = tiltPIDController.calculate(endEffectorTilt.getEncoder().getPosition(), encoders); 
        if(speed > .85){
            speed = (speed/Math.abs(speed))*.85;
        }
        endEffectorTilt.set(speed);
    }
    /*
     *     public void goToHeight(double height){
        double voltage = elevatorPID.calculate(elevator1.getEncoder().getPosition(), height);
        if(Math.abs(voltage) > 3){
            voltage = (voltage)/Math.abs(voltage)*3;
        }
        SmartDashboard.putNumber("elevator PID Voltage", voltage);
        elevator1.setVoltage(voltage);
        elevator2.setVoltage(voltage);

    }
     */

    @Override
    public void periodic()
    {
        SmartDashboard.putBoolean("End Effector Coral Beam", coralBeam.get());
        SmartDashboard.putNumber("End Effector Encoder", endEffectorMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("End Effector Tilt", endEffectorTilt.getEncoder().getPosition());
        SmartDashboard.putNumber("End Effector Tilt Absolute Encoder", endEffectorTilt.getAlternateEncoder().getPosition());
    }

    public boolean atSetPoint() {
        return tiltPIDController.atSetpoint(); 
    }
}