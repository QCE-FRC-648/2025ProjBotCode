package frc.robot.commands.EndEffectorCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.Robot;

public class IntakeCommand extends Command 
{
    private final double speed;

    public IntakeCommand(Double _speed)
    {
        addRequirements(RobotContainer.endEffector);
        speed = _speed;
    }

    @Override
    public void initialize() {
        RobotContainer.endEffector.setSpeedEndEffectorIntake(0);
        RobotContainer.endEffector.setSpeedEndEffectorMotor(0);
    }

    @Override
    public void execute() 
    {
        boolean isStalled = Robot.isStalled(); 
        RobotContainer.endEffector.setSpeedEndEffectorIntake(speed/3);
        // Intake moves much faster than Motor!
        RobotContainer.endEffector.setSpeedEndEffectorMotor(isStalled?0:speed*1.5);
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.endEffector.setSpeedEndEffectorIntake(0);
        RobotContainer.endEffector.setSpeedEndEffectorMotor(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}