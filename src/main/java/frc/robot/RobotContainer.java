// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;
import swervelib.SwerveInputStream;


import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.climb.*;
import frc.robot.subsystems.elevator.*;
import frc.robot.subsystems.endeffector.*;
import frc.robot.subsystems.swervedrive.*;
import frc.robot.commands.ClimberCommands.*;
import frc.robot.commands.ElevatorCommands.*;
import frc.robot.commands.EndEffectorCommands.*;
import frc.robot.commands.SwervedriveCommands.auto.*;
import frc.robot.commands.SwervedriveCommands.drivebase.*;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
// import frc.robot.subsystems.drive.DriveSubsystem;


//https://docs.wpilib.org/en/stable/docs/software/hardware-apis/misc/addressable-leds.html

 /* This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
  //Define Subsystems
  public static SwerveSubsystem driveTrain = new SwerveSubsystem();
  public static ClimberSubsystem climber = new ClimberSubsystem();
  public static ElevatorSubsystem elevator = new ElevatorSubsystem();
  public static EndEffectorSubsystem endEffector = new EndEffectorSubsystem();

  //Define Controllers
  public static CommandXboxController driverController = new CommandXboxController(0);
  public static CommandXboxController operatorController = new CommandXboxController(1);

  //Auto Chooser
  private final SendableChooser<Command> autoChooser;

  /**
   * Converts driver input into a field-relative ChassisSpeeds that is controlled by angular velocity.
   */
  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(driveTrain.getSwerveDrive(),
                                                                () -> driverController.getLeftY() * -1,
                                                                () -> driverController.getLeftX() * -1)
                                                            .withControllerRotationAxis(driverController::getRightX)
                                                            .deadband(OperatorConstants.DEADBAND)
                                                            .scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    // Configure the trigger bindings
    configureBindings();
    DriverStation.silenceJoystickConnectionWarning(true);
    autoChooser = AutoBuilder.buildAutoChooser("Pass The Line Auto");

    Command driveFieldOrientedAnglularVelocity = driveTrain.driveFieldOriented(driveAngularVelocity);

    driveTrain.setDefaultCommand(driveFieldOrientedAnglularVelocity);


    climber.setDefaultCommand(new RunCommand(() -> {
    }, climber));

    elevator.setDefaultCommand(new RunCommand(() -> {
      elevator.setSpeed(-operatorController.getLeftY()*.1); //Multiply by .1 for testing
    }, elevator));

    endEffector.setDefaultCommand(new RunCommand(() -> {
      endEffector.setSpeedEndEffectorTilt(-operatorController.getRightY()*.1);  //We are using this to test, the .1 is to make it go slow
    }, endEffector));
  }
 
  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {
    
    //To do: Set Buttons, Set Speeds, Verify Directions

    // Operator Controls
    
    operatorController.rightTrigger().whileTrue(new ShootCommand(-.1));
    operatorController.leftTrigger().whileTrue(new ShootCommand(.1));

    operatorController.rightBumper().whileTrue(new IntakeCommand(-.1));
    operatorController.leftBumper().whileTrue(new IntakeCommand(.1));


 
    // Driver Controls
    driverController.y().onTrue(new InstantCommand(() -> {climber.LatchServo();}));
    driverController.b().onTrue(new InstantCommand(() -> {climber.UnlatchServo();}));

    driverController.rightTrigger().whileTrue(new ExtendWinchCommand(-.1));
    driverController.leftTrigger().whileTrue(new ExtendWinchCommand(.1));

    driverController.rightBumper().whileTrue(new GrabCageCommand(.1));
    driverController.leftBumper().whileTrue(new GrabCageCommand(-1.));
     
  }
  public void setMotorBrake(boolean brake)
  {
    driveTrain.setMotorBrake(brake);
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    // Pass the auto line for points
    return autoChooser.getSelected();
  }
}

