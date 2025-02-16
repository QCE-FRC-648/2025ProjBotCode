package frc.robot.commands.ElevatorCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class ElevatorDownCommand extends Command 
{
    private final double speed;

    public ElevatorDownCommand(Double _speed)
    {
        addRequirements(RobotContainer.climber);
        speed = _speed;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        RobotContainer.climber.Winch(speed);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
