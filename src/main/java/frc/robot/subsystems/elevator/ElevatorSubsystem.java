package frc.robot.subsystems.elevator;

import com.revrobotics.REVLibError;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.CANConfig;

public class ElevatorSubsystem extends SubsystemBase
{
    public DigitalInput elevatorLowerSwitch;

    public final SparkMax elevator1 = new SparkMax(CANConfig.ELEVATOR_LEFT, MotorType.kBrushless);
    public final SparkMax elevator2 = new SparkMax(CANConfig.ELEVATOR_RIGHT, MotorType.kBrushless);

    private SparkMaxConfig elevator1Config = new SparkMaxConfig();
    private SparkMaxConfig elevator2Config = new SparkMaxConfig();

    private double startingHeight = 15.25;
    private double endingHeight = 59.25;
    private double startingEncoder = 0;
    private double endingEncoder = 110;

    private double inchesPerEncoder = (endingHeight - startingHeight)/(endingEncoder - startingEncoder);

   private PIDController elevatorPID = new PIDController(1,0,0);

   private double lastSetpoint = 0;
    
    public ElevatorSubsystem()
    {
        this.elevatorLowerSwitch = new DigitalInput(2);

        elevator1Config.inverted(true);
        // elevator2Config.inverted(false);
        elevator1Config.idleMode(IdleMode.kBrake);
        elevator2Config.idleMode(IdleMode.kBrake);
        elevator1Config.smartCurrentLimit(40);
        elevator2Config.smartCurrentLimit(40);
        elevator2Config.follow(CANConfig.ELEVATOR_LEFT, true);

        elevator1.configure(elevator1Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        elevator2.configure(elevator2Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        elevatorPID.setTolerance(1);
    }

    public void goToHeight(double height){
        double currentHeight = elevator1.getEncoder().getPosition();
        height = MathUtil.clamp(height, currentHeight - 12, currentHeight + 12);
        double voltage = elevatorPID.calculate(currentHeight, height);
        voltage = MathUtil.clamp(voltage, -12, 12);
        SmartDashboard.putNumber("elevator PID Voltage", voltage);
        elevator1.setVoltage(voltage);
        // elevator2.setVoltage(voltage);

        lastSetpoint = height;
    }

    public double getLastSetpoint() {
        return lastSetpoint;
    }

    public void resetEncoder(){
        SmartDashboard.putBoolean("ElevatorZero1",elevator1.getEncoder().setPosition(0) == REVLibError.kOk);
        
        SmartDashboard.putBoolean("ElevatorZero2",elevator2.getEncoder().setPosition(0) == REVLibError.kOk);
    }
    public double getHeight(){
        return elevator1.getEncoder().getPosition();
    }
    
    public void setVoltage(double voltage) {
        elevator1.setVoltage(voltage);
        // elevator2.setVoltage(voltage);
    }

    public boolean atSetpoint(){
        return elevatorPID.atSetpoint();
    }

    public void setSpeedElevator1(double speed) 
    {
        elevator1.set(speed);
    }
    
    public void setSpeedElevator2(double speed) 
    {
        elevator2.set(speed);
    }

    public void setSpeed(double speed){
        elevator1.set(speed);
        // elevator2.set(speed);
    }


    public Boolean lowerLimitReached() {
        if(elevatorLowerSwitch.get())
            return false;
            else{
            return true;
            }
    }
    

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Elevator Motor1 Temp", elevator1.getMotorTemperature());
        SmartDashboard.putNumber("Elevator Motor2 Temp", elevator2.getMotorTemperature());
        SmartDashboard.putNumber("Elevator Motor1 Position", elevator1.getEncoder().getPosition());
        SmartDashboard.putNumber("Elevator Motor2 Position", elevator2.getEncoder().getPosition());
        SmartDashboard.putBoolean("Elevator Limit Switch", lowerLimitReached());
    }
}