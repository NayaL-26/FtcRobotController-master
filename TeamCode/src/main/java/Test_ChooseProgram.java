import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Test_ChooseProgram", group = "Autonomous")
public class Test_ChooseProgram extends LinearOpMode {
    //Here we are declaring the motors and sensors
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    private Servo arm;
    private Servo flipper;
    // private DigitalChannel touch;

    //Here we are giving "backing" to the declaration!!
    public void intihardware() {
        frontRight = hardwareMap.dcMotor.get("fr");
        frontLeft = hardwareMap.dcMotor.get("fl");
        backRight = hardwareMap.dcMotor.get("br");
        backLeft = hardwareMap.dcMotor.get("bl");
        arm = hardwareMap.servo.get("arm");
        flipper = hardwareMap.servo.get("flipper");
        // touch = hardwareMap.get(DigitalChannel.class, "touch_sensor" );

        // set wheel direction
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //set servo open
        arm.setPosition(0);
        flipper.setPosition(0);

    }
    private double ticksPerRevNR40 = 1120;

    //The post gear box gear ratio.
    private double gearRatio = 1.0;
    //The circumference of the drive wheel.
    private double wheelCircumference = 31.9024;
    //Formula to calculate ticks per centimeter for the current drive set up.SIDEWAYS
    
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
            currentDistanceTicks =
                    (frontRight.getCurrentPosition() +
                            frontLeft.getCurrentPosition() +
                            backRight.getCurrentPosition() +
                            backLeft.getCurrentPosition()) / 4.0;
            telemetry.addData("Current pos ticks Avg: ", currentDistanceTicks);
            telemetry.addData("Current Distance cm", currentDistanceTicks / ticksPerCm);
            telemetry.update();

            frontLeft.setPower(-power);
            frontRight.setPower(-power);
            backLeft.setPower(-power);
            backRight.setPower(-power);
        }
        // stopMotors();
    }
   /* public void sideLeft(double targetDistance, double power){
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
    public void reverse(double targetDistance,double power){
        frontRight.setPower(-1);
        frontLeft.setPower(-1);
        backLeft.setPower(-1);
        backRight.setPower(-1);
    }*/
    //Here the motors are stopping
    public void stopMotors(){
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    public void Red1() {
        waitForStart();
        while (opModeIsActive()) {
           // arm.setPosition(0);
            forward(60, .5);
            //sideRight(30, .5);
           //forward(45,.5);
            //arm.setPosition(1);
            //reverse(15,.5);
           // break;
        }

    }
    @Override
    public void runOpMode() throws InterruptedException {
        intihardware();
        waitForStart();
        Red1();
    }

}
