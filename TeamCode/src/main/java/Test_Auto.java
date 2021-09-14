import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@Autonomous(name = "Test_Auto", group ="Autonomus" )
public class Test_Auto extends LinearOpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
 public void hardware(){
     frontRight = hardwareMap.dcMotor.get("fr");
     frontLeft = hardwareMap.dcMotor.get("fl");
     backRight = hardwareMap.dcMotor.get("br");
     backLeft = hardwareMap.dcMotor.get("bl");

     frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
     backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
     frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
     backRight.setDirection(DcMotorSimple.Direction.REVERSE);
 }
    public  void setDriveMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);

    }
    private double ticksPerRevNR40 = 1120;

    //The post gear box gear ratio.
    private double gearRatio = 1.0;
    //The circumference of the drive wheel.
    private double wheelCircumference = 31.9024; // ??
    //Formula to calculate ticks per centimeter for the current drive set up.SIDEWAYS

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
    public void drive(){
        forward(1, 1);
        stop();
    }

    @Override
    public void runOpMode() throws InterruptedException {
 hardware();
 waitForStart();
 drive();
 }
}
