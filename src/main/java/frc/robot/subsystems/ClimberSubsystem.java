package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.ClimberConstants;

public class ClimberSubsystem extends SubsystemBase
{
    private final SparkMax climberWinch = new SparkMax(ClimberConstants.kClimberWinchCANId, MotorType.kBrushless);
    private final SparkMax climberGrab = new SparkMax(ClimberConstants.kClimberGrabCANId, MotorType.kBrushless);
    
    private PowerDistribution PDH;
    
    public ClimberSubsystem()
    {

    }
}