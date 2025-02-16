package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.EndEffectorConstants;

public class EndEffectorSubsystem extends SubsystemBase
{
    private final SparkMax endEffectorAlgae;
    private final SparkMax endEffectorFinger;
    private final SparkMax endEffectorMotor;
    private final SparkMax endEffectorTilt;

    public EndEffectorSubsystem()
    {
        endEffectorAlgae = new SparkMax(EndEffectorConstants.kEndEffectorAlgaeCANId, MotorType.kBrushless);
        endEffectorFinger = new SparkMax(EndEffectorConstants.kEndEffectorFingerCANId, MotorType.kBrushless);
        endEffectorMotor = new SparkMax(EndEffectorConstants.kEndEffectorMotorCANId, MotorType.kBrushless);
        endEffectorTilt = new SparkMax(EndEffectorConstants.kEndEffectorTiltCANId, MotorType.kBrushless);
    }
}