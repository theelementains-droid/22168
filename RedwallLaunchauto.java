package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
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
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

@Autonomous

public class RedwallLaunchauto extends LinearOpMode {
    private Blinker control_Hub;
    private DcMotor m1;
    private DcMotor m2;
    private DcMotor m3;
    private DcMotor m4;
    private DcMotorEx turret;
    private DcMotor spin;
    private DcMotor l1;
    private DcMotor l2;
    private Servo s1;
    private Servo s2;
    private Servo s3;
    // p1 controlls back and forth while p2 controls strafing and p3 controls turning
    public void setPow(double p1 double p2 double p3){
            m1.setPower(-p3+p2+p1);
            m2.setPower(-p3-p2+p1);
            m3.setPower(-p3+p2-p1);
            m4.setPower(-p3-p2-p1);
    }
    @Override
    public void runOpMode() {
        control_Hub = hardwareMap.get(Blinker.class, "Control Hub");
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        m1 = hardwareMap.get(DcMotor.class, "m1");
        m2 = hardwareMap.get(DcMotor.class, "m2");
        m3 = hardwareMap.get(DcMotor.class, "m3");
        m4 = hardwareMap.get(DcMotor.class, "m4");
        c1 = hardwareMap.get(RevColorSensorV3.class, "c1");
        c2 = hardwareMap.get(RevColorSensorV3.class, "c2");
        c3 = hardwareMap.get(RevColorSensorV3.class, "c3");
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        spin = hardwareMap.get(DcMotor.class, "spin");
        l1 = hardwareMap.get(DcMotor.class, "l1");
        l2 = hardwareMap.get(DcMotor.class, "l2");
        s1 = hardwareMap.get(Servo.class, "s1");
        s2 = hardwareMap.get(Servo.class, "s2");
        s3 = hardwareMap.get(Servo.class, "s3");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        double timer1 = 0;
        double timer2 = 0;
        double timer3 = 0;
        boolean outtake = false;
        boolean intake = false;
        double spinSpeed = -1;
        setPow(0.6,0.,0.);
        sleep(1500);
        setPow(0.,0.,0.);
        spin.setPower(spinSpeed);
        sleep(3500);
        //Launch 1
        s1.setPosition(0.65);
        sleep(1000);
        s1.setPosition(0.99);
        //Launch 2
        sleep(500)
        s2.setPosition(0.65);
        sleep(1000);
        s2.setPosition(0.99);
        //Launch 3
        sleep(500);
        s3.setPosition(0.65);
        sleep(1000);
        s3.setPosition(0.99);
        sleep(500);
        spin.setPower(0);
        setPow(0.,.6,0.);
        sleep(500);
        setPow(0.,0.,0.);
    }
