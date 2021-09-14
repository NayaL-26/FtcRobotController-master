/*This is bringing in all of the information from the SDK we downloaded
An SDK is a kit of programs needed and making your life easier because you don't have to write them*/
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
//import com.qualcomm.robotcore.hardware.DigitalChannel;

//import java.security.PublicKey;

//Here we are registering the name

@Autonomous(name="DemoAuto", group = "Autonomous")
public class DemoAuto extends LinearOpMode {
    //Here we are declaring the motors and sensors
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    private Servo arm;
    // private DigitalChannel touch;

    //Here we are giving "backing" to the declaration!!
    public void intihardware() {
        frontRight = hardwareMap.dcMotor.get("fr");
        frontLeft = hardwareMap.dcMotor.get("fl");
        backRight = hardwareMap.dcMotor.get("br");
        backLeft = hardwareMap.dcMotor.get("bl");
        arm = hardwareMap.servo.get("arm");
        // touch = hardwareMap.get(DigitalChannel.class, "touch_sensor" );

        // set wheel direction
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //set servo open
        arm.setPosition(0);

    }


    //Here we are telling the motors to start moving at half power forward
  /* public void forwardHalfPower() {
        frontRight.setPower(.5);
        frontLeft.setPower(.5);
        backRight.setPower(.5);
        backLeft.setPower(.5);
    }

    //By having the two wheels on the same side at negative power it will cause the robot to turn
    public void turn() {
        frontRight.setPower(1);
        frontLeft.setPower(-1);
        backRight.setPower(1);
        backLeft.setPower(-1);
    }*/
    public void reverse(double targetDistance,double power){
        frontRight.setPower(-1);
        frontLeft.setPower(-1);
        backLeft.setPower(-1);
        backRight.setPower(-1);
    }

    private double ticksPerRevNR40 = 1120;

    //The post gear box gear ratio.
    private double gearRatio = 1.0;
    //The circumference of the drive wheel.
    private double wheelCircumference = 31.9024; // ??
    //Formula to calculate ticks per centimeter for the current drive set up.SIDEWAYS



    public void Red1() {
        waitForStart();
        while (opModeIsActive()) {
            arm.setPosition(0);
            forward(10, .5);
            sideRight(10, .5);
            forward(25,.5);
            arm.setPosition(1);
            reverse(15,.5);
            break;
        }

    }
    public void Red2() {
        waitForStart();
        while (opModeIsActive()){
            arm.setPosition(0);
            forward(10,.5);
            sideLeft(10,.5);
            forward(25,.5);
            arm.setPosition(1);
            reverse(15,.5);
            break;
        }
    }

    public void Red3() {
        waitForStart();
        while (opModeIsActive()){
            arm.setPosition(0);
            forward(10,.5);
            sideRight(10,.5);
            forward(25,.5);
            arm.setPosition(1);
            reverse(15,.5);
            break;
        }
    }

    public void Blue1() {
        waitForStart();
        while (opModeIsActive()){
            arm.setPosition(0);
            forward(10,.5);
            sideLeft(10,.5);
            forward(25,.5);
            arm.setPosition(1);
            reverse(15,.5);
            break;
        }
    }

    public void Blue2() {
        waitForStart();
        while (opModeIsActive()){
            arm.setPosition(0);
            forward(10,.5);
            sideRight(10,.5);
            forward(25,.5);
            arm.setPosition(1);
            reverse(15,.5);
            break;
        }
    }

    public void Blue3() {
        waitForStart();
        while (opModeIsActive()){
            arm.setPosition(0);
            forward(10,.5);
            sideLeft(10,.5);
            forward(25,.5);
            arm.setPosition(1);
            reverse(15,.5);
            break;
        }
    }

    public void chooseprogram() {
        telemetry.addData("ChoosePosition", "RED1-A, RED2-B, RED3-X, BLUE1-A,BLUE2-B,BLUE3-X");
        while (!isStarted()) {
            if (gamepad1.triangle) {
                telemetry.addData("RobotPosition", "RED1");
                Red1();
                telemetry.update();
                break;
            } else if (gamepad1.square) {
                telemetry.addData("RobotPosition", "RED2");
                Red2();
                telemetry.update();
                break;
            } else if (gamepad1.circle) {
                telemetry.addData("RobotPosition", "RED3");
                Red3();
                telemetry.update();
                break;
            } else if (gamepad1.cross) {
                telemetry.addData("RobotPosition", "BLUE1");
                Blue1();
                telemetry.update();
                break;
            } else if (gamepad1.dpad_down) {
                telemetry.addData("RobotPosition", "BLUE2");
                Blue2();
                telemetry.update();
                break;
            } else if (gamepad1.dpad_left) {
                telemetry.addData("RobotPosition", "BLUE3");
                Blue3();
                telemetry.update();
                break;
            } else {
                telemetry.addData("waiting", "help");
                telemetry.update();
            }
        }
    }

    public  void setDriveMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);

    }
    public void forward(double targetDistance, double power){
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Formula to calculate ticks per centimeter for the current drive set up.FORWARDS/BACKWARD ONLY
        double ticksPerCm = (ticksPerRevNR40 * gearRatio) / wheelCircumference;
        double targetDistanceTicks = Math.abs(targetDistance * ticksPerCm);
        double currentDistanceTicks = 0;
        while ((Math.abs(currentDistanceTicks) < targetDistanceTicks) && opModeIsActive()) {
            telemetry.addData("Target pos ticks: ", targetDistanceTicks);
            telemetry.addData("Target Distance:", targetDistance + "cm");
            currentDistanceTicks = (frontRight.getCurrentPosition() +
                    frontLeft.getCurrentPosition() +
                    backRight.getCurrentPosition() +
                    backLeft.getCurrentPosition()) / 4.0;
            telemetry.addData("Current pos ticks Avg: ", currentDistanceTicks);
            telemetry.addData("Current Distance cm", currentDistanceTicks / ticksPerCm);
            telemetry.update();
        }
        stopMotors();
    }
    public void sideLeft(double targetDistance, double power){
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double currentDistance = 0;
        while (Math.abs(currentDistance) < targetDistance && opModeIsActive()) {
            currentDistance = frontRight.getCurrentPosition();
            frontLeft.setPower(power);
            frontRight.setPower(-power);
            backLeft.setPower(-power);
            backRight.setPower(power);
        }
        stopMotors();
    }
    public void sideRight(double targetDistance, double power) {
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double currentDistance = 0;
        while ((Math.abs(currentDistance) < targetDistance) && opModeIsActive()) {
            currentDistance = frontLeft.getCurrentPosition();
            frontLeft.setPower(-power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(-power);
        }
        stopMotors();
    }
    //Here the motors are stopping
    public void stopMotors(){
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    @Override
    public void runOpMode(){

        intihardware();
        chooseprogram();
        waitForStart();

    }

}
