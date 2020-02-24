package ccdd.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import ccdd.TeleOp.HardwareStrafe;

public class AutonomousUtilities {
    private HardwareStrafe robot;
    private LinearOpMode linearOpMode;
    private ElapsedTime runtime;

    public AutonomousUtilities(HardwareStrafe robot, LinearOpMode linearOpMode, ElapsedTime runtime) {
        this.robot = robot;
        this.linearOpMode = linearOpMode;
        this.runtime = runtime;
    }

    private double liftpower;

    public void strafe(double speed, double angle) {
        angle = angle + 45;
        double lFrR = Math.sin(Math.toRadians(angle)) * speed;
        double rFlR = Math.cos(Math.toRadians(angle)) * speed;
        lFrR = Range.clip(lFrR, -1, 1);
        rFlR = Range.clip(rFlR, -1, 1);
        if (linearOpMode.opModeIsActive()) {
            robot.Drive0.setPower(lFrR);
            robot.Drive1.setPower(rFlR);
            robot.Drive2.setPower(rFlR);
            robot.Drive3.setPower(lFrR);
            System.out.printf("lFrR %s , rFlR %s %n", lFrR, rFlR );

        }
    }

    public void stopMotors() {
        robot.Drive0.setPower(0);
        robot.Drive1.setPower(0);
        robot.Drive2.setPower(0);
        robot.Drive3.setPower(0);
        robot.Drive4.setPower(0);
        pause();
    }

    public void strafeTime(double speed, double angle, double time) {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < time)) {
            strafe(speed, angle);
            linearOpMode.telemetry.addData("Path", "Leg: %2.5f S Elapsed", runtime.seconds());
            linearOpMode.telemetry.update();
        }
        stopMotors();
    }

    public void strafeTime(double speed, double angle, double time, STATE state) {
        if (state == STATE.OPEN) {
            clawOpen();
        } else if (state == STATE.CLOSED) {
            clawClosed();
        }
        strafeTime(speed, angle, time);
    }

    public void strafeLeft(double speed, double time){
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < time)) {
            robot.Drive0.setPower(-speed);
            robot.Drive1.setPower(speed);
            robot.Drive2.setPower(speed);
            robot.Drive3.setPower(-speed);
            linearOpMode.telemetry.addData("Path", "Leg: %2.5f S Elapsed", runtime.seconds());
            linearOpMode.telemetry.update();
        }
        stopMotors();
    }
    public void strafeRight(double speed, double time){
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < time)) {
            robot.Drive0.setPower(speed);
            robot.Drive1.setPower(-speed);
            robot.Drive2.setPower(-speed);
            robot.Drive3.setPower(speed);
            linearOpMode.telemetry.addData("Path", "Leg: %2.5f S Elapsed", runtime.seconds());
            linearOpMode.telemetry.update();
        }
        stopMotors();
    }

    public void doubleOpen() {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < .5)) {
            robot.clawServo.setPosition(robot.MID_SERVO);
            robot.flipServo.setPosition(1);
        }
    }

    public void doubleClosed() {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < .5)) {
            robot.clawServo.setPosition(robot.SERVO_CLOSED);
            robot.flipServo.setPosition(0);
        }

    }

    public void clawOpen() {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < .5)) {
            robot.clawServo.setPosition(robot.MID_SERVO);
        }
    }

    public void clawClosed() {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < .5)) {
            robot.clawServo.setPosition(robot.SERVO_CLOSED);
        }

    }

    public void extOpen() {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < .5)) {
            robot.flipServo.setPosition(1);
        }
    }

    public void extClosed() {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < .5)) {
            robot.flipServo.setPosition(0);
        }

    }

    public void rotate(double speed, STATE state, double time) {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < time)) {
            if (state == STATE.LEFT) {
                robot.Drive0.setPower(-1 * speed);
                robot.Drive1.setPower(1 * speed);
                robot.Drive2.setPower(-1 * speed);
                robot.Drive3.setPower(1 * speed);
            } else if (state == STATE.RIGHT) {
                robot.Drive0.setPower(1 * speed);
                robot.Drive1.setPower(-1 * speed);
                robot.Drive2.setPower(1 * speed);
                robot.Drive3.setPower(-1 * speed);
            }
        }
    }

    public void strafeTime(double speed, double angle, double time, STATE state, double liftTime) {
        liftpower = (liftTime / time) * .5;
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < time)) {
            strafe(speed, angle);
            if (state == STATE.UP) {
                robot.Drive4.setPower(liftpower);
            } else if (state == STATE.DOWN) {
                robot.Drive4.setPower(-liftpower);
            }
            linearOpMode.telemetry.addData("Path", "Leg: %.2f S Elapsed", runtime.seconds());
            linearOpMode.telemetry.update();
        }
        stopMotors();
    }

    public void liftTime(double speed, STATE state, double time) {
        runtime.reset();
        while (linearOpMode.opModeIsActive() && (runtime.seconds() < time)) {
            if (state == STATE.UP) {
                robot.Drive4.setPower(speed);
            } else if (state == STATE.DOWN) {
                robot.Drive4.setPower(-speed);
            }
        }
        stopMotors();
    }

    public void pause(){
        try {
            Thread.sleep(200);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void pause(double times){
        for(int i=0; i<(times*10); i++) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            linearOpMode.telemetry.addData("time left = ", times - i/10);
            linearOpMode.telemetry.update();
        }

    }
    public void liftDown (){
        Long startTime = System.currentTimeMillis();
        Long duration = 0l;

        while (linearOpMode.opModeIsActive() && robot.bottomedSensor.getState() == true && duration < 1000){
            robot.Drive4.setPower(-.5);
            duration = System.currentTimeMillis() - startTime;
        }
        robot.Drive4.setPower(0);
    }

    public void noDrag(){
        robot.dragServo1.setPosition(0);
        robot.dragServo2.setPosition(0);
    }

    public void drag(){
        robot.dragServo1.setPosition(.75);
        robot.dragServo2.setPosition(.75);
    }

    public void armReset(){
        robot.clawServo.setPosition(robot.SERVO_CLOSED);
        robot.armServo.setPosition(.32);
        robot.flipServo.setPosition(1);
    }

    public void armOut(){
        robot.armServo.setPosition(0);
        robot.clawServo.setPosition(0);
    }

    public void hoeIn(){
        robot.hoe2.setPosition(0);
        robot.hoe3.setPosition(0);
        robot.hoe4.setPosition(.5);
    }

    public void hoeOut(){
        robot.hoe2.setPosition(.2);
        robot.hoe3.setPosition(.5);
        robot.hoe4.setPosition(0);
    }

    public void hoeRight(){
        robot.hoe1.setPosition(0);
    }

    public void hoeMid(){
        robot.hoe1.setPosition(.34);
    }

    public void hoeLeft(){
        robot.hoe1.setPosition(.68);
    }

    public void hoeOpen(){
    robot.hoe4.setPosition(0);
    }

    public void hoeClose(){
        robot.hoe4.setPosition(.3);
    }
}

