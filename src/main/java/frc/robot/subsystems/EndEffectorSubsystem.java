package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.EndEffectorConstants;

public class EndEffectorSubsystem extends SubsystemBase
{
    private final SparkMax endEffectorAlgae = new SparkMax(EndEffectorConstants.kEndEffectorAlgaeCANId, MotorType.kBrushless);
    private final SparkMax endEffectorFinger = new SparkMax(EndEffectorConstants.kEndEffectorFingerCANId, MotorType.kBrushless);
    private final SparkMax endEffectorMotor = new SparkMax(EndEffectorConstants.kEndEffectorMotorCANId, MotorType.kBrushless);
    private final SparkMax endEffectorTilt = new SparkMax(EndEffectorConstants.kEndEffectorTiltCANId, MotorType.kBrushless);
}