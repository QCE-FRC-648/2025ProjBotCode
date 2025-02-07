// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class SwerveModuleConstants
  {
    //may need to change to 12T or 14T
    public static final double kDrivingMotorPinionTeeth = 14;

    //turn motor is inverted 
    public static final boolean kTurningEncoderInverted = true;

    public static final double kWheelDiameterMeters = Units.inchesToMeters(2);
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;

    //RPM of the NEO brushless motors 
    public static final double kFreeSpeedRPM = 5676; //rotations per min
    public static final double kDrivingMotorFreeSpeedRPS = kFreeSpeedRPM / 60; //rotations per sec
    //gear ratios of driving motor
    //45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDrivingWheelFreeSpeedRPS = (kDrivingMotorFreeSpeedRPS * kWheelCircumferenceMeters) / kDrivingMotorReduction;

    //conversion factors that may be needed
    public static final double kDrivingEncoderPositionFactor = kWheelCircumferenceMeters / kDrivingMotorReduction; //meters
    public static final double kDrivingEncoderVelocityFactor =  kDrivingEncoderPositionFactor / 60.0; //meters per second

    public static final double kTurningEncoderPositionFactor = (2 * Math.PI); //radians
    public static final double kTurningEncoderVelocityFactor = kTurningEncoderPositionFactor / 60.0; //radians per second

    //constants for the PIDs
    public static final double kDrivingP = 0.006;
    public static final double kDrivingI = 0;
    public static final double kDrivingD = 0;
    public static final double kDrivingFF = 1/kDrivingWheelFreeSpeedRPS;
    public static final double kDrivingMinOutput = -1;
    public static final double kDrivingMaxOutput = 1;

    //0.22
    public static final double kTurningP = 0.4;
    public static final double kTurningI = 0;
    public static final double kTurningD = 0;
    public static final double kTurningMinOutput = -1;
    public static final double kTurningMaxOutput = 1;
    public static final boolean kEnablePIDWrapping = true;
    public static final double kTurningEncoderPositionPIDMinInput = 0;
    public static final double kTurningEncoderPositionPIDMaxInput = (2 * Math.PI);

    public static final IdleMode kDrivingMotorIdleMode = IdleMode.kBrake;
    public static final IdleMode kTurningMotorIdleMode = IdleMode.kBrake;

    public static final int kDrivingMotorCurrentLimit = 50; // amps
    public static final int kTurningMotorCurrentLimit = 20; // amps
  }
}
