// Places the class Robot in the package frc.robot.
package frc.robot;

// Imports the DriverStation class.
import edu.wpi.first.wpilibj.DriverStation;

// Imports the SmartDashboard class.
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Imports the TimedRobot class.
import edu.wpi.first.wpilibj.TimedRobot;

// Imports the I2C class.
import edu.wpi.first.wpilibj.I2C;

// Imports the Color class.
import edu.wpi.first.wpilibj.util.Color;

// Imports the Joystick class.
import edu.wpi.first.wpilibj.Joystick;

// Imports the ControlMode class.
import com.ctre.phoenix.motorcontrol.ControlMode;

// Imports the TalonSRX class.
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// Imports the MotorType class.
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// Imports the CANSparkMax class.
import com.revrobotics.CANSparkMax;

// Imports the CANEncoder class.
import com.revrobotics.CANEncoder;

// Imports the ColorSensorV3 class.
import com.revrobotics.ColorSensorV3;

// Imports the ColorMatch class.
import com.revrobotics.ColorMatch;

// Imports the ColorMatchResults class.
import com.revrobotics.ColorMatchResult;

// Creates the Robot class.
public class Robot extends TimedRobot {

  // Creates a new Joystick object to represent the joystick controlling the robot.
  Joystick joystick = new Joystick(0);

  // Creates a new TalonSRX object to represent the Talon controlling the CONTROL PANEL mechanism.
  TalonSRX controlPanelMechanism = new TalonSRX(5);

  // Creates a new CANSparkMax object to represent the SPARK MAX controlling the front-left drive train motor.
  CANSparkMax FL = new CANSparkMax(8, MotorType.kBrushless);

  // Creates a new CANSparkMax object to represent the SPARK MAX controlling the back-left drive train motor.
  CANSparkMax BL = new CANSparkMax(9, MotorType.kBrushless);

  // Creates a new CANSparkMax object to represent the SPARK MAX controlling the front-right drive train motor.
  CANSparkMax FR = new CANSparkMax(10, MotorType.kBrushless);

  // Creates a new CANSparkMax object to represent the SPARK MAX controlling the back-right drive train motor.
  CANSparkMax BR = new CANSparkMax(11, MotorType.kBrushless);

  // Initializes a CANEncoder object to represent the encoder on the front-left drive train motor.
  CANEncoder encoder = FL.getEncoder();
  
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

  //
  @Override

  // Defines a method for actions involving the initialization of the robot.
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

  //
  @Override
  
  // Defines a method for actions involving the initialization of autonomous mode.
  public void autonomousInit() {
  
  }

  //
  @Override

  // Defines a method for actions involving autonomous mode.
  public void autonomousPeriodic() {
  
  }

  //
  @Override
  
  // Defines a method for actions involving the initialization of teleoperated mode.
  public void teleopInit() {
  
  }

  //
  @Override
  
  // Defines a method for actions involving teleoperated mode
  public void teleopPeriodic() {

    if (joystick.getRawButton(3)) {
      FL.restoreFactoryDefaults();
    }
    SmartDashboard.putNumber("Encoder Position", encoder.getPosition());
    SmartDashboard.putNumber("Encoder Velocity", encoder.getVelocity());

    FL.set(joystick.getRawAxis(1));

    // Calls the method updateColors()
    updateColors();

    // Calls the method rotationControl()
    rotationControl();

    // Calls the method positionControl()
    positionControl();

  }

  //
  @Override

  // Defines a method for actions involving the test mode.
  public void testPeriodic() {

  }

  // Defines a method for updating color variables and printing the detected color to FRC Driver Station.
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
    if (joystick.getRawButton(2)) {

      // Loops through 16 iterations in order to spin the CONTROL PANEL approximately 4 times.
      for (int i = 0; i < 16; i++) {

        // Calls the method updateColors()
        updateColors();

        // Checks if 'A' is pressed in order to stop the CONTROL PANEL mechanism.
        if (joystick.getRawButton(1)) {

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
            controlPanelMechanism.set(ControlMode.PercentOutput, 0.5);

            // Checks if 'A' is pressed in order to stop the CONTROL PANEL mechanism.
            if (joystick.getRawButton(1)) {

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
            controlPanelMechanism.set(ControlMode.PercentOutput, 0.5);

            // Checks is 'A' is pressed in order to stop the CONTROL PANEL mechanism.
            if (joystick.getRawButton(1)) {

              // Stops the CONTROL PANEL mechanism.
              controlPanelMechanism.set(ControlMode.PercentOutput, 0);

              // Breaks the while loop.
              break;

            }

          }

        } 
        
        // Checks if the detected color is neither blue nor red in order to spin the CONTROL PANEL to either blue or red.
        else {

          // Loops as long as the detected color is neither red nor blue in order to spin the CONTROL PANEL to either red or blue.
          while (match.color != kRedTarget || match.color != kBlueTarget) {

            // Calls the method updateColors()
            updateColors();

            // Spins the CONTROL PANEL mechanism.
            controlPanelMechanism.set(ControlMode.PercentOutput, 0.5);

            // Checks if 'A' is pressed in order to stop the CONTROL PANEL mechanism.
            if (joystick.getRawButton(1)) {

              // Stops the CONTROL PANEL mechanism.
              controlPanelMechanism.set(ControlMode.PercentOutput, 0);

              // Breaks the while loop.
              break;

            }

          }

        }

      }

    }

  }

  // Defines a method for POSITION CONTROL, spinning the CONTROL PANEL until the specified color is aligned with the ARENA's color sensor.
  public void positionControl() {
    
    // Checks if 'Y' is pressed in order to start the POSITION CONTROL code.
    if (joystick.getRawButton(4)) {

      // Calls the method updateColors()
      updateColors();

      // Spins the CONTROL PANEL mechanism.
      controlPanelMechanism.set(ControlMode.PercentOutput, 0.5);

      // Checks to see if the wanted color is met in order to stop the CONTROL PANEL mechanism. 
      if (colorString.equals(wantedColorString)) {

        // Stops the CONTROL PANEL mechanism.
        controlPanelMechanism.set(ControlMode.PercentOutput, 0);

      }

    }

  }

}