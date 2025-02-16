// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AlgaeIntakeCommands.DeployArmCommand;
import frc.robot.commands.AlgaeIntakeCommands.IntakeRollerBar;
import frc.robot.commands.AlgaeIntakeCommands.OuttakeRollerBar;
import frc.robot.commands.AlgaeIntakeCommands.RetractArmCommand;
import frc.robot.commands.ClimberCommands.ExtendWinchCommand;
import frc.robot.commands.ClimberCommands.GrabCageCommand;
import frc.robot.commands.ClimberCommands.RetractWinchCommand;
import frc.robot.subsystems.AlgaeIntakeSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.EndEffectorSubsystem;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  public static PowerDistribution pdh;

  public static DriveSubsystem driveTrain;
  public static ClimberSubsystem climber;
  public static AlgaeIntakeSubsystem algaeIntake;
  public static ElevatorSubsystem elevator;
  public static EndEffectorSubsystem endEffector;

  public static CommandXboxController driverController;
  public static CommandXboxController operatorController;

  private final SendableChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    pdh = new PowerDistribution();

    driveTrain = new DriveSubsystem();
    climber = new ClimberSubsystem();
    algaeIntake = new AlgaeIntakeSubsystem();
    elevator = new ElevatorSubsystem();
    endEffector = new EndEffectorSubsystem();

    driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    operatorController = new CommandXboxController(OperatorConstants.kOperatorControllerPort);

    autoChooser = AutoBuilder.buildAutoChooser();
    autoChooser.setDefaultOption("NOTHING!!!", new InstantCommand());
 
    driveTrain.setDefaultCommand(new RunCommand(
      //left joystick controls translation
      //right joystick controls rotation of the robot
      () -> driveTrain.drive(
        -MathUtil.applyDeadband(driverController.getLeftY(), OperatorConstants.kDriverDeadband), 
        -MathUtil.applyDeadband(driverController.getLeftX(), OperatorConstants.kDriverDeadband), 
        -MathUtil.applyDeadband(driverController.getRightX(), OperatorConstants.kDriverDeadband), 
        true), 
      driveTrain));
  }

  private void configureBindings() 
  {
    // Algae Intake Commands
    operatorController.a().whileTrue(new DeployArmCommand(0.1)); // TODO change speed and button
    operatorController.b().whileTrue(new RetractArmCommand(0.1));
    operatorController.leftBumper().whileTrue(new IntakeRollerBar(0.1));
    operatorController.rightBumper().whileTrue(new OuttakeRollerBar(0.1));

    // Climber
    operatorController.leftTrigger().whileTrue(new ExtendWinchCommand(0.1));
    operatorController.rightTrigger().whileTrue(new RetractWinchCommand(0.1));
    operatorController.povDown().whileTrue(new GrabCageCommand(0.1));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }
}
