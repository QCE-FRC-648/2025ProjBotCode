package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.ElevatorConstants;

public class ElevatorSubsystem extends SubsystemBase
{
    private final SparkMax elevator1 = new SparkMax(ElevatorConstants.kElevator1CANId, MotorType.kBrushless);
    private final SparkMax elevator2 = new SparkMax(ElevatorConstants.kElevator2CANId, MotorType.kBrushless);
}