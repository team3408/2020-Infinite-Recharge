// ???????????????????????????????????????????
package frc.robot;

// Imports the TimedRobot class.
import edu.wpi.first.wpilibj.TimedRobot;

// Imports the SendableChooser class.
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

// Imports the SmartDashboard class.
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Imports the DriverStation class.
import edu.wpi.first.wpilibj.DriverStation;

// Imports the Joystick class.
import edu.wpi.first.wpilibj.Joystick;

// Imports the I2C class.
import edu.wpi.first.wpilibj.I2C;

// Imports the Color class.
import edu.wpi.first.wpilibj.util.Color;

// Imports the ColorSensorV3 class.
import com.revrobotics.ColorSensorV3;

// Imports the ColorMatch class.
import com.revrobotics.ColorMatch;

// Imports the ColorMatchResults class.
import com.revrobotics.ColorMatchResult;

// Imports the CANSparkMax class.
import com.revrobotics.CANSparkMax;

// Imports the MotorType class.
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// Imports the CANEncoder class.
import com.revrobotics.CANEncoder;

// Imports the TalonSRX class.
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// Imports the ControlMode class.
import com.ctre.phoenix.motorcontrol.ControlMode;

// Imports the PigeonIMU class.
import com.ctre.phoenix.sensors.PigeonIMU;

// ??????????????????????????????????????????????????????
public class Robot extends TimedRobot {

  // Creates a new Joystick object to represent the first joystick controlling the robot.
  Joystick joystick1 = new Joystick(0);

  // Creates a new Joystick object to represent the second joystick controlling the robot.
  Joystick joystick2 = new Joystick(1);

  // Creates a new CANSparkMax object to represent the SPARK MAX controlling the front-left drive train motor.
  CANSparkMax FL = new CANSparkMax(6, MotorType.kBrushless);

  // Creates a new CANSparkMax object to represent the SPARK MAX controlling the back-left drive train motor.
  CANSparkMax BL = new CANSparkMax(7, MotorType.kBrushless);

  // Creates a new CANSparkMax object to represent the SPARK MAX controlling the front-right drive train motor.
  CANSparkMax FR = new CANSparkMax(8, MotorType.kBrushless);

  // Creates a new CANSparkMax object to represent the SPARK MAX controlling the back-left drive train motor.
  CANSparkMax BR = new CANSparkMax(9, MotorType.kBrushless);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // rename all motorcontrollers according to ARENA names.

  // Creates a new CANSparkMax object to represent the SPARK MAX controlling the first shooter motor.
  CANSparkMax shooter1 = new CANSparkMax(44, MotorType.kBrushless);

  // Creates a new CANSparkMax object to represent the SPARK MAX controlling the second shooter motor.
  CANSparkMax shooter2 = new CANSparkMax(55, MotorType.kBrushless);

  // Creates a new CANEncoder object to represent the encoder on the shooter motors.
  CANEncoder shooterEncoder = shooter1.getEncoder();
  
  // Creates a new TalonSRX object to represent the Talon controlling the first lift motor.
  TalonSRX lift1 = new TalonSRX(1);

  // Creates a new TalonSRX object to represent the Talon controlling the second lift motor.
  TalonSRX lift2 = new TalonSRX(2);

  // Creates a new TalonSRX object to represent the Talon controlling the CONTROL PANEL mechanism.
  TalonSRX controlPanelMechanism = new TalonSRX(0);

  // Creates a new PigeonIMU object to represent the gyroscope.
  PigeonIMU gyroscope = new PigeonIMU(lift2);

  // Declares a new double[] variable.
  double[] ypr = new double[3];

  // Declares a new double variable.
  double power;

  // Declares a new double variable. 
  double leftTurnPower;

  // Declares a new double variable.
  double leftTurnConstant;
  
  // Initializes an I2C.Port object to represent the I2C port on the roboRIO.
  I2C.Port i2cPort = I2C.Port.kOnboard;

  // Creates a new ColorSensorV3 object to represent the color sensor on the robot.
  ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
  
  // Creates a new ColorMatch object to represent known colors.
  ColorMatch colorMatcher = new ColorMatch(); 

  // Declares a new ColorMatchResult variable.
  ColorMatchResult match;

  // Declares a new Color variable.
  Color detectedColor;

  // Declares a new String variable. 
  String colorString;

  // Declares a new String variable.
  String wantedColorString;

  // Declares a new String variable.
  String gameDataString;

  // Creates a new Color object to represent the color blue.
  final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);

  // Creates a new Color object to represent the color red.
  final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);

  // Creates a new Color object to represent the color green.
  final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);

  // Creates a new Color object to represent the color yellow.
  final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  @Override
  public void robotInit() {

    // Adds the color blue to the color matcher.
    colorMatcher.addColorMatch(kBlueTarget);
      
    // Adds the color red to the color matcher.
    colorMatcher.addColorMatch(kRedTarget);

    // Adds the color green to the color matcher.
    colorMatcher.addColorMatch(kGreenTarget);

    // Adds the color yellow to the color matcher.
    colorMatcher.addColorMatch(kYellowTarget); 
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    FL.setInverted(false);
    BL.setInverted(false);
    FR.setInverted(true);
    BR.setInverted(true);
  }

  @Override
  public void teleopPeriodic() {

    //SmartDashboard.putNumber("3:1 encoder", shooterEncoder.getVelocity());
    //shooter1.set(-joystick1.getRawAxis(1));
    //shooter2.set(joystick1.getRawAxis(1));

    FL.set(joystick2.getRawAxis(1));
    BL.set(joystick2.getRawAxis(1));
    FR.set(joystick1.getRawAxis(1));
    BR.set(joystick1.getRawAxis(1));

    if (joystick1.getRawButton(1)) {
      turnLeft(90);
    }


    //rotationControl();
    //positionControl();

    //shooter1.set(-joystick1.getRawAxis(1));
    //shooter2.set(joystick1.getRawAxis(1));
    //if (joystick1.getRawButton(4)) {
      //turnNinetyDegreesLeft();
    //}

  }




  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  // Defines a method for updating color variables and displaying color variables on FRC Driver Station and FRC SmartDashboard.
  public void updateColors() {

    // Initializes the detected color.
    detectedColor = colorSensor.getColor();

    // Initializes the closest match to the detected color.
    match = colorMatcher.matchClosestColor(detectedColor);

    // Checks if the detected color is blue to create a new String object to represent the color string as "B".
    if (match.color == kBlueTarget) {

      // Creates a new String object to represent the color string as "B".
      colorString = new String("B");

    }

    // Checks if the detected color is red to create a new String object to represent the color string as "R".
    else if (match.color == kRedTarget) {

      // Creates a new String object to represent the color string as "R".
      colorString = new String("R");

    } 

    // Checks if the detected color is green to create a new String object to represent the color string as "G".
    else if (match.color == kGreenTarget) {

      // Creates a new String object to represent the color string as "G".
      colorString = new String("G");

    } 
    
    // Checks if the detected color is yellow to create a new String object to represent the color string as "Y".
    else if (match.color == kYellowTarget) {

      // Creates a new String object to represent the color string as "Y".
      colorString = new String("Y");

    } 
    
    // Checks if the detected color is unknown to create a new String object to represent the color string as "U".
    else {

      // Creates a new String object to represent the color string as "U".
      colorString = new String("U");
    
    }

    // Initializes the Game Data string, which represents what color the ARENA's color sensor should look for.
    gameDataString = DriverStation.getInstance().getGameSpecificMessage();
      
    // Checks if the value of the Game Data string is "B" in order to create a new String object to represent the wanted color string as "R".
    if (gameDataString.equals("B")) {

      // Creates a new String object to represent the wanted color string as "R".
      wantedColorString = new String("R");

    }

    // Checks if the value of the Game Data string is "R" in order to create a new String object to represent the wanted color string as "B".
    else if (gameDataString.equals("R")) {

      // Creates a new String object to represent the wanted color string as "B".
      wantedColorString = new String("B");

    }

    // Checks if the value of the Game Data string is "G" in order to create a new String object to represent the wanted color string as "Y".
    else if (gameDataString.equals("G")) {
      
      // Creates a new String object to represent the wanted color string as "Y".
      wantedColorString = new String("Y");
    
    }

    // Checks if the value of the Game Data string is "Y" in order to create a new String object to represent the wanted color string as "G".
    else if (gameDataString.equals("Y")) {

      // Creates a new String object to represent the wanted color string as "G".
      wantedColorString = new String("G");

    }

    // Checks if the value of the Game Data string is "" in order to create a new String object to represent the wanted color string as "".
    else {

      // Creates a new String object to represent the wanted color string as "".
      wantedColorString = new String("");

    }

    // Prints the detected color string to FRC Driver Station.
    DriverStation.reportWarning(colorString, false);

    // Prints the color string to FRC SmartDashboard.
    SmartDashboard.putString("Detected Color", colorString);
    
    // Prints the wanted color string to FRC SmartDashboard.
    SmartDashboard.putString("Wanted Color", wantedColorString);

    // Prints the game data string to FRC SmartDashboard.
    SmartDashboard.putString("Game Data", gameDataString);

  }

  // Defines a method for ROTATION CONTROL, spinning the CONTROL PANEL more than 3 revolutions but fewer than 5 revolutions.
  public void rotationControl() {

    // Checks if 'B' is pressed in order to start the ROTATION CONTROL code.
    if (joystick1.getRawButton(2)) {

      // Loops through 16 iterations in order to spin the CONTROL PANEL approximately 4 times.
      for (int i = 0; i < 16; i++) {

        // Calls the method updateColors()
        updateColors();

        // Checks if 'A' is pressed in order to stop the CONTROL PANEL mechanism.
        if (joystick1.getRawButton(1)) {

          // Stops the CONTROL PANEL mechanism.
          controlPanelMechanism.set(ControlMode.PercentOutput, 0);

          // Breaks the for loop.
          break;
          
        }

        // Checks if the detected color is red in order to spin the CONTROL PANEL to blue.
        if (match.color == kRedTarget) {

          // Loops as long as the detected color is not blue in order to spin the CONTROL PANEL to blue.
          while (match.color != kBlueTarget) {

            // Calls the method updateColors()
            updateColors();

            // Spins the CONTROL PANEL mechanism. 
            controlPanelMechanism.set(ControlMode.PercentOutput, 0.4);

            // Checks if 'A' is pressed in order to stop the CONTROL PANEL mechanism.
            if (joystick1.getRawButton(1)) {

              // Stops the CONTROL PANEL mechanism.
              controlPanelMechanism.set(ControlMode.PercentOutput, 0);

              // Breaks the while loop.
              break;

            }

          }

        } 
        
        // Checks if the detected color is blue in order to spin the CONTROL PANEL to red.
        else if (match.color == kBlueTarget) {

          // Loops as long as the detected color is not red in order to spin the CONTROL PANEL to red.
          while (match.color != kRedTarget) {

            // Calls the method updateColors()
            updateColors();

            // Spins the CONTROL PANEL mechanism.
            controlPanelMechanism.set(ControlMode.PercentOutput, 0.4);

            // Checks is 'A' is pressed in order to stop the CONTROL PANEL mechanism.
            if (joystick1.getRawButton(1)) {

              // Stops the CONTROL PANEL mechanism.
              controlPanelMechanism.set(ControlMode.PercentOutput, 0);

              // Breaks the while loop.
              break;

            }

          }

        } 
        
        // Checks if the detected color is neither blue nor red in order to spin the CONTROL PANEL to either red.
        else {

          // Loops as long as the detected color is neither red nor blue in order to spin the CONTROL PANEL to red.
          while (match.color != kRedTarget) {

            // Calls the method updateColors()
            updateColors();

            // Spins the CONTROL PANEL mechanism.
            controlPanelMechanism.set(ControlMode.PercentOutput, 0.4);

            // Checks if 'A' is pressed in order to stop the CONTROL PANEL mechanism.
            if (joystick1.getRawButton(1)) {

              // Stops the CONTROL PANEL mechanism.
              controlPanelMechanism.set(ControlMode.PercentOutput, 0);

              // Breaks the while loop.
              break;

            }

          }

        }

      }

      // Stops the CONTROL PANEL mechanism.
      controlPanelMechanism.set(ControlMode.PercentOutput, 0);

    }

  }

  // Defines a method for POSITION CONTROL, spinning the CONTROL PANEL until the specified color is aligned with the ARENA's color sensor.
  public void positionControl() {
    
    // Checks if 'Y' is pressed in order to start the POSITION CONTROL code.
    if (joystick1.getRawButton(4)) {

      // Calls the method updateColors().
      updateColors();

      // Loops as long as the detected color is not the wanted color in order to align the specified color with the ARENA's color sensor.
      while (!colorString.equals(wantedColorString)) {

        // Calls the method updateColors().
        updateColors();

        // Spins the CONTROL PANEL mechanism.
        controlPanelMechanism.set(ControlMode.PercentOutput, 0.3);

        // Checks to see if button 1 on joystick1 is pressed in order to stop the CONTROL PANEL mechanism.
        if (joystick1.getRawButton(1)) {

          // Stops the CONTROL PANEL mechanism.
          controlPanelMechanism.set(ControlMode.PercentOutput, 0);
          
          // Breaks the while loop.
          break;

        }

      }

      // Stops the CONTROL PANEL mechanism.
      controlPanelMechanism.set(ControlMode.PercentOutput, 0);     

    }

  }

  public void updateGyroscopeValues() {

    // Updates the orientation array with the new values for yaw, pitch, and roll.
    gyroscope.getYawPitchRoll(ypr);

    // Prints the yaw value to the SmartDashboard.
    SmartDashboard.putNumber("Yaw", ypr[0]);

  }
  
  public void turnLeft(float numberOfDegrees) {
    
    // Sets the gyroscope's yaw value to 0.
    gyroscope.setYaw(0, 10);

    updateGyroscopeValues();

    double yaw = ypr[0];

    leftTurnConstant = 0.0006;

    leftTurnPower = (numberOfDegrees - ypr[0]) * leftTurnConstant;

    while (yaw >= -numberOfDegrees) {
      
      updateGyroscopeValues();

      yaw = ypr[0];

      FL.set(leftTurnPower);
      BL.set(leftTurnPower);
      FR.set(-leftTurnPower);
      BR.set(-leftTurnPower);

      if (joystick1.getRawButton(5)) {

        FL.set(0);
        BL.set(0);
        FR.set(0);
        BR.set(0);

        break;

      }

    }
    gyroscope.setYaw(0, 10);

  }

  // This method spins the robot ninety degrees. This method is old. I am writing a new one that's better.
  public void turnNinetyDegreesLeft() {
    gyroscope.setYaw(0, 10);
    gyroscope.getYawPitchRoll(ypr);
    double yaw;
    power = 0.2;
    while (ypr[0] >= -90) {
      gyroscope.getYawPitchRoll(ypr);
      yaw = ypr[0] + 0.001;
      SmartDashboard.putNumber("Yaw", ypr[0]);
      FL.set(power);
      BL.set(power);
      FR.set(-power);
      BR.set(-power);
      if (joystick1.getRawButton(2)) {
        FL.set(0);
        BL.set(0);
        FR.set(0);
        BR.set(0);
        break;
      }
      power *= 0.99999;
      SmartDashboard.putNumber("Power", power);
    }
    FL.set(-0.1);
    BL.set(-0.1);
    FR.set(0.1);
    BR.set(0.1);
    FL.set(0);
    BL.set(0);
    FR.set(0);
    BR.set(0);
  }

}
