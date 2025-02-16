package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.AlgaeIntakeConstants;

public class AlgaeIntakeSubsystem extends SubsystemBase
{
    private final SparkMax algaeRoller = new SparkMax(AlgaeIntakeConstants.kAlgaeRollerCANId, MotorType.kBrushless);
    private final SparkMax algaeTilt = new SparkMax(AlgaeIntakeConstants.kAlgaeTiltCANId, MotorType.kBrushless);

    public void IntakeRoller(double speed) 
    {
        algaeRoller.set(speed);
    }

    public void OuttakeRoller(double speed) 
    {
        algaeRoller.set(speed);
    }

    public void DeployArmTilt(double speed) 
    {
        algaeTilt.set(speed);
    }

    public void RetractArmTilt(double speed) 
    {
        algaeTilt.set(speed);
    }

    @Override
    public void periodic()
    {
        
    }
}