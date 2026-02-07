package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import android.graphics.Color;

/**
 * This file contains a minimal example of a Linear "OpMode". An OpMode is a 'program' that runs
 * in either the autonomous or the TeleOp period of an FTC match. The names of OpModes appear on
 * the menu of the FTC Driver Station. When an selection is made from the menu, the corresponding
 * OpMode class is instantiated on the Robot Controller and executed.
 *
 * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
 * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
 * added to the Driver Station.
 */
@TeleOp

public class Meow extends LinearOpMode {
    private Blinker control_Hub;
    private DcMotor m1;
    private DcMotor m2;
    private DcMotor m3;
    private DcMotor m4;
    private RevColorSensorV3 c1;
    private RevColorSensorV3 c2;
    private RevColorSensorV3 c3;
    
    @Override
    public void runOpMode() {
        control_Hub = hardwareMap.get(Blinker.class, "Control Hub");
        m1 = hardwareMap.get(DcMotor.class, "m1");
        m2 = hardwareMap.get(DcMotor.class, "m2");
        m3 = hardwareMap.get(DcMotor.class, "m3");
        m4 = hardwareMap.get(DcMotor.class, "m4");
        c1 = hardwareMap.get(RevColorSensorV3.class, "c1");
        c2 = hardwareMap.get(RevColorSensorV3.class, "c2");
        c3 = hardwareMap.get(RevColorSensorV3.class, "c3");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        double timer1 = 0;
        double timer2 = 0;
        double timer3 = 0;
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            m1.setPower(-gamepad1.right_stick_x+gamepad1.left_stick_x+gamepad1.left_stick_y);
            m2.setPower(-gamepad1.right_stick_x-gamepad1.left_stick_x+gamepad1.left_stick_y);
            m3.setPower(-gamepad1.right_stick_x+gamepad1.left_stick_x-gamepad1.left_stick_y);
            m4.setPower(-gamepad1.right_stick_x-gamepad1.left_stick_x-gamepad1.left_stick_y);
            timer1 -= 50;
            timer2 -= 50;
            timer3 -= 50;
            if(gamepad2.xWasPressed()){
                timer1 = 1000;
            }
            if(gamepad2.yWasPressed()){
                timer2 = 1000;
            }
            if(gamepad2.bWasPressed()){
                timer3 = 1000;
            }
            if(timer1<=0){
                s1.setPosition(0.01);
            }
            if(timer2<=0){
                s2.setPosition(0.98);
            }
            if(timer3<=0){
                s3.setPosition(0.98);
            }
            
            }
        }
}
