
//package org.firstinspires.ftc.teamcode.TeleOp; //import packages needed for ftc programs

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

@TeleOp(name="TeleOpGood", group="First") //initialize teleop
public class TeleOpGood extends LinearOpMode {

    DcMotor frontLeft; //initializes dcmotor types to be called later
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    DcMotor armdc;
    Servo armservo; //initializes servo to be called later
    Servo flipper;
    DcMotor leftFlight;
    DcMotor rightFlight;
    DcMotor intake;

    public void initTeleop()
    {
        // set wheel power variables and servo position
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        armservo.setPosition(0);
        flipper.setPosition(0);
        leftFlight.setPower(0);
        rightFlight.setPower(0);
        intake.setPower(0);
        armdc.setPower(0);
        // set deadzone
        gamepad1.setJoystickDeadzone(0.2f);
    }

    //Controller 1
    //controls movement and arm
    public void movement() {
        double r = Math.hypot(gamepad1.right_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4;
        double rightX = gamepad1.left_stick_x;
        final double fr = r * Math.cos(robotAngle) + rightX;
        final double fl = r * Math.sin(robotAngle) - rightX;
        final double bl = r * Math.sin(robotAngle) + rightX;
        final double br = r * Math.cos(robotAngle) - rightX;
        //used to half power and quarter power driving
        if (gamepad1.left_trigger > 0) {
            frontRight.setPower(fr / 2);
            frontLeft.setPower(fl / 2);
            backLeft.setPower(bl / 2);
            backRight.setPower(br / 2);
        } else if (gamepad1.right_trigger > 0) {
            frontRight.setPower(fr / 4);
            frontLeft.setPower(fl / 4);
            backLeft.setPower(bl / 4);
            backRight.setPower(br / 4);
        } else {
            frontRight.setPower(fr);
            frontLeft.setPower(fl);
            backLeft.setPower(bl);
            backRight.setPower(br);
        }
    }

    public void runOpMode() {
        frontLeft = hardwareMap.dcMotor.get("fl"); //sets the hardware maps to variables to be used for controller mapping (dc motors)
        frontRight = hardwareMap.dcMotor.get("fr");
        backLeft = hardwareMap.dcMotor.get("bl");
        backRight = hardwareMap.dcMotor.get("br");
        leftFlight = hardwareMap.dcMotor.get("leftFlight");
        rightFlight = hardwareMap.dcMotor.get("rightFlight");
        armdc = hardwareMap.dcMotor.get("arm_dc");
        armservo = hardwareMap.servo.get("arm"); //sets the hardware maps to variables to be used for controller mapping (servo)
        flipper = hardwareMap.servo.get("flipper");
        intake = hardwareMap.dcMotor.get("intake");

        waitForStart();


        while (opModeIsActive()) {
            frontLeft.setPower(gamepad1.left_stick_y); //mapping previously set variables to the controller
            backLeft.setPower(gamepad1.left_stick_y);
            frontRight.setPower(gamepad1.right_stick_y);
            backRight.setPower(gamepad1.right_stick_y);
            leftFlight.setPower(gamepad2.left_stick_y);
            rightFlight.setPower(-gamepad2.left_stick_y);
            intake.setPower(gamepad2.left_stick_y);
            armdc.setPower(gamepad2.right_stick_y);
        }

    }

    public void arm() {
        if (gamepad1.left_bumper){
            armservo.setPosition(1);
        }else{
            armservo.setPosition(0);//Was 1, changed to .8 so the servo does not push on the frame.
        }if (gamepad2.square) {
             telemetry.addData("square", "pressed");
            armservo.setPosition(0);
            telemetry.update();
        } else {
            armservo.setPosition(1);
        }
        if (gamepad2.square) {
            armservo.setPosition(1);
            telemetry.addData("square", " not pressed");
            telemetry.update();
        }
        telemetry.addData("Servo Position", armservo.getPosition());
        telemetry.addData("Status", "Running");
        telemetry.update();

    }

    /*public void Lift_arm () {

        if (gamepad2.square) {
            // telemetry.addData("square", "pressed");
            armdc.setPower(.25);
            //telemetry.update();
        } else {
            armdc.setPower(0);
        }
        }*/

    //Controller 2
    //controls intake,flywheels,and flipper
    public void flipper () {
        if (gamepad1.dpad_up) {
            flipper.setPosition(1);
        }
        if (gamepad1.dpad_down) {
            flipper.setPosition(0);
        }
    }
    /*public void runintake () {
        if (gamepad1.a) {
            telemetry.addData("a Button", "pressed");
            intake.setPower(1);
            telemetry.update();
        }
    }
    public void flight() {

        }*/
    public void run(){
        initTeleop();
        waitForStart();
        while (opModeIsActive()){
            movement();
            arm();
            flipper();
            //runintake();
            //Lift_arm();
            //flight();
        }
    }
}