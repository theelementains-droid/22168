package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import java.util.concurrent.TimeUnit;
import com.qualcomm.hardware.dfrobot.HuskyLens;
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
import com.qualcomm.robotcore.util.Range;


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
    private DcMotorEx turret;
    private DcMotor spin;
    private DcMotor l1;
    private DcMotor l2;
    private RevColorSensorV3 c1;
    private RevColorSensorV3 c2;
    private RevColorSensorV3 c3;
    private Servo s1;
    private Servo s2;
    private Servo s3;
    private GoBildaPinpointDriver pinpoint;
    private final int READ_PERIOD = 1;
    private HuskyLens huskyLens;
    
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
        huskyLens = hardwareMap.get(HuskyLens.class, "ai");
        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        rateLimit.expire();
        if (!huskyLens.knock()) {
            telemetry.addData(">>", "Problem communicating with " + huskyLens.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);

        telemetry.update();
        waitForStart();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // run until the end of the match (driver presses STOP)
        double timer1 = 0;
        double timer2 = 0;
        double timer3 = 0;
        boolean outtake = false;
        boolean intake = false;
        double spinSpeed = -1;
        double turretangle = 0;
        double bearing = 0;
        double goalX = 0;
        double lastError = 0;
        double angletol = 0.2;
        double kP = 0.0001;
        double kD = 0.0000;
        boolean autoAim = true;
        final double Mpow = 0.6;
        double power = 0;
        final ElapsedTime timer = new ElapsedTime();
        c1.setGain(10);
        c2.setGain(10);
        c3.setGain(10);
        
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            m1.setPower(-gamepad1.right_stick_x+gamepad1.left_stick_x+gamepad1.left_stick_y);
            m2.setPower(-gamepad1.right_stick_x-gamepad1.left_stick_x+gamepad1.left_stick_y);
            m3.setPower(-gamepad1.right_stick_x+gamepad1.left_stick_x-gamepad1.left_stick_y);
            m4.setPower(-gamepad1.right_stick_x-gamepad1.left_stick_x-gamepad1.left_stick_y);
            timer1 -= 25;
            timer2 -= 25;
            timer3 -= 25;

            
            if(gamepad2.dpadDownWasPressed()){
                intake = !intake;
            }
            if(intake){
                l1.setPower(0.7);
                l2.setPower(-0.7);
            }else{
                l1.setPower(0);
                l2.setPower(0);
            }
            HuskyLens.Block[] blocks = huskyLens.blocks();
            telemetry.addData("Block count", blocks.length);

            for (int i = 0; i < blocks.length; i++) {
                telemetry.addData("Block", blocks[i].toString());
                /*
                 * Here inside the FOR loop, you could save or evaluate specific info for the currently recognized Bounding Box:
                 * - blocks[i].width and blocks[i].height   (size of box, in pixels)
                 * - blocks[i].left and blocks[i].top       (edges of box)
                 * - blocks[i].x and blocks[i].y            (center location)
                 * - blocks[i].id                           (Color ID)
                 *
                 * These values have Java type int (integer).
                 */
                if((blocks[i]==0)||(blocks[i]==1)){
                 bearing = ((double)(blocks[i].x-160))*0.1875;
                telemetry.addData("bar",bearing);
                 telemetry.update();
                }
            }
             if(blocks.length<1){
                 bearing = 0;
             }

            //turret.setPower(1);
            double dt = timer.seconds();
            double error = goalX-bearing;
            double pT = error *kP;
            
            double dT = 0;

            if(dt > 0){
                dT = ((error-lastError)/dt)*kD;
            }
            
            //turretangle += 15.*gamepad2.left_stick_x - 15*(pT+dT);
            //turret.setPower(gamepad2.left_stick_x);
            if((Math.abs(error)<angletol)||autoAim){
                power = 0;
            }else{
                power = Range.clip(pT+dT,-Mpow,Mpow);
            }

            if(turret.getCurrentPosition()>1012){
                turretangle=982;
                turret.setPower(-Mpow);
            }
            if(turret.getCurrentPosition()<-2297){
                turretangle=-2217;
                turret.setPower(Mpow);
            }
            turret.setPower(-power*100+gamepad2.left_stick_x);
            if(gamepad2.a){
                turretangle = 0;
            }
            
            if(gamepad2.rightBumperWasPressed()){
                outtake = !outtake;
                spin.setDirection(DcMotor.Direction.FORWARD);
                spinSpeed = -1;
            }
            if(gamepad2.leftBumperWasPressed()){
                outtake = !outtake;
                spin.setDirection(DcMotor.Direction.REVERSE);
                spinSpeed = -0.4;
                
            }
            if(gamepad2.x){
                timer1 = 1000;
                s1.setPosition(0.65);
            }
            if(gamepad2.y){
                timer2 = 1000;
                s2.setPosition(0.35);
            }
            if(gamepad2.b){
                timer3 = 1000;
                s3.setPosition(0.65);
            }
            if(timer1<=0){
                s1.setPosition(0.99);
            }
            if(timer2<=0){
                s2.setPosition(0.01);
            }
            if(timer3<=0){
                s3.setPosition(0.99);
            }
            if(outtake){
                spin.setPower(spinSpeed);
            }else{
                spin.setPower(0);
            }
            telemetry.addData("rot",turret.getCurrentPosition() );
            
            telemetry.update();
            if(gamepad2.dpadUpWasPressed(){
                autoAim = !autoAim;
            }
            if(c1.green()>=400){
                telemetry.addData("b:","green");
            }else{
                telemetry.addData("b:","purple");
            }
            
            if(c3.green()>=400){
                telemetry.addData("y:","green");
            }else{
                telemetry.addData("y:","purple");
            }
            
            }
        }
}
