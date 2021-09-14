import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Test_Servo", group="First") //initialize teleop
public class Test_Servo extends LinearOpMode {
    Servo Serveo;

    public void intihardware() {
        Serveo = hardwareMap.servo.get("Serveo");
        waitForStart();
    }

    public void testServo() {
        if (gamepad1.x) {
            Serveo.setPosition(1);
        }
        if   (gamepad1.a) {
            Serveo.setPosition(0);
        }
    }
    @Override
    public void runOpMode(){
        intihardware();
        waitForStart();
                while (opModeIsActive()) {
            testServo();
        }
    }
}