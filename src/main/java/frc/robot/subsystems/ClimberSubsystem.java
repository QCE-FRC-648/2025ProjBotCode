package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.ClimberConstants;

public class ClimberSubsystem extends SubsystemBase
{
    private final SparkMax climberWinch;
    private final SparkMax climberGrab;
    
    public ClimberSubsystem()
    {
        climberWinch = new SparkMax(ClimberConstants.kClimberWinchCANId, MotorType.kBrushless);
        climberGrab = new SparkMax(ClimberConstants.kClimberGrabCANId, MotorType.kBrushless);
    }

    public void Winch(double speed) 
    {
        climberWinch.set(speed);
    }
    
    public void Grab(double speed) 
    {
        climberGrab.set(speed);
    }

    @Override
    public void periodic()
    {
        
    }
}