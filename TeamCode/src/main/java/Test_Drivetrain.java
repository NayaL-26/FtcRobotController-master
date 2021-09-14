import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Test_Drivetrain", group="First") //initialize teleop
public class Test_Drivetrain extends LinearOpMode {
   DcMotor Wheel;
    DcMotor Marvel;
    Servo Serveo;
    public void runOpMode() {
        Wheel = hardwareMap.dcMotor.get("Wheel");
        Marvel = hardwareMap.dcMotor.get("Marvel");
        Serveo = hardwareMap.servo.get("Serveo");
    waitForStart();


        while (opModeIsActive()) {

            Wheel.setPower(gamepad1.left_stick_y); //mapping previously set variables to the controller
            Marvel.setPower(gamepad2.left_stick_y);

        }


    }
    private void testServo() {
        if (gamepad1.a) {
            telemetry.addData("a Button", "pressed");
            Serveo.setPosition(0);
            telemetry.update();
        }
    }
    public void run (){
        waitForStart();
        testServo();
    }

}
