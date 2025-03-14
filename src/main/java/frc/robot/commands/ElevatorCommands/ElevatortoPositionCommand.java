package frc.robot.commands.ElevatorCommands;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj.Timer;

public class ElevatortoPositionCommand extends Command 
{
    private Debouncer m_debouncer;
    private final double speed;
    private final int level_1_position = 50;
    private final int level_2_position = 150;
    private final int level_3_position = 300; 
    private final int level_4_position = 600;
    private final int level; 

    public ElevatortoPositionCommand(Double _speed, int _level)
    {
        addRequirements(RobotContainer.elevator);
        speed = _speed;
        level = _level; 
        double debounceTime = .25;
        DebounceType debounceType = DebounceType.kRising;
        m_debouncer = new Debouncer(debounceTime, debounceType);
    }

    @Override
    public void initialize() {}

    public void moveToPosition(int position){
        boolean isAtPosition = false;
        boolean isStalled = m_debouncer.calculate(isAtPosition);
        while(!isStalled){
            if (!isAtPosition && RobotContainer.elevator.getPositionElevator1() >= level_3_position && RobotContainer.elevator.getPositionElevator2() >= level_3_position) {
                RobotContainer.elevator.setSpeedEndEffectorTilt(speed);
                Timer.delay(1.0);
                RobotContainer.elevator.setSpeedEndEffectorTilt(0);
            }
            RobotContainer.elevator.setSpeedElevator1(speed);
            RobotContainer.elevator.setSpeedElevator2(speed);
            isAtPosition = (RobotContainer.elevator.getPositionElevator1() >= position && RobotContainer.elevator.getPositionElevator2() >= position);
            isStalled = m_debouncer.calculate(isAtPosition);
            Timer.delay(.1);
        }
        RobotContainer.elevator.setSpeedElevator1(0);
        RobotContainer.elevator.setSpeedElevator2(0);
    }

    @Override
    public void execute() 
    {
        switch(level){
            case 1:
                moveToPosition(level_1_position);
            case 2:
                moveToPosition(level_2_position);
            case 3:
                moveToPosition(level_3_position);
            case 4:
                moveToPosition(level_4_position);
            default:
                moveToPosition(level_1_position);
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}