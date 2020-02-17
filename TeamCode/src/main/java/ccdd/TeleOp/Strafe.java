package ccdd.TeleOp;/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import ccdd.util.STATE;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Strafe", group="Strafebot")
//@Disabled
public class Strafe extends OpMode {

    //decalare the Hardware being used
    HardwareStrafe robot           = new HardwareStrafe();

    //define all STATE variables
    STATE incrementup   = STATE.OFF;
    STATE incrementdown = STATE.OFF;
    STATE clawstatus = STATE.OPEN;
    STATE clawClosed = STATE.OFF;
    STATE clawOpen = STATE.OFF;
    STATE dragStatus = STATE.UP;
    STATE dragDown = STATE.OFF;
    STATE dragUp = STATE.OFF;
    STATE flipstatus = STATE.OPEN;
    STATE flipClosed = STATE.OFF;
    STATE flipOpen = STATE.OFF;
    STATE capUp = STATE.OFF;
    STATE capDown = STATE.OFF;
    STATE capStatus = STATE.UP;
    STATE armPosition = STATE.MID;
    STATE armMoveL = STATE.OFF;
    STATE armMoveR = STATE.OFF;

    //define all double variables
    private double Gear = 0.8;
    private static final double GearChange = .05;
    private double offset = 0;
    private double drive1;
    private double strafe1;
    private double turn1;
    private double drive2;
    private double strafe2;
    private double turn2;
    private double lR;
    private double rR;
    private double lF;
    private double rF;
    private double lR1;
    private double rR1;
    private double lF1;
    private double rF1;
    private double lR2;
    private double rR2;
    private double lF2;
    private double rF2;
    private double lift;
    private double liftup;
    private double liftdown;
    private double armOffset = .0;
    private double dragoffset = 0;
    private double extOffset = .00;
    private double flipOffset = 1;
    private double capPos = .25;
    //set up all variables

    @Override
    public void init() {
        robot.init(hardwareMap);

        robot.clawServo.setPosition(robot.MID_SERVO);
        robot.armServo.setPosition(.0);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("", "Ready");
    }

    @Override
    public void loop() {
        //Defines drive inputs for controller 1
        drive1 = -gamepad1.left_stick_y;
        strafe1 = gamepad1.left_stick_x;
        turn1 = gamepad1.right_stick_x;

        //Defines drive inputs for controller 2
        drive2 = -gamepad2.left_stick_y;
        strafe2 = gamepad2.left_stick_x;
        turn2 = gamepad2.right_stick_x;

        //defines lift inputs
        liftup = gamepad2.right_trigger;
        liftdown = gamepad2.left_trigger;

        //used to change "Gear"
        //Gear is the multiplier on the speed, used to limit max speed
        if(gamepad1.dpad_up && incrementup == STATE.OFF)
        {
            incrementup = STATE.INPROGRESS;
        }
        else if (!gamepad1.dpad_up && incrementup == STATE.INPROGRESS)
        {
            Gear += GearChange;
            incrementup = STATE.OFF;
        }
        if(gamepad1.dpad_down && incrementdown == STATE.OFF)
        {
            incrementdown = STATE.INPROGRESS;
        }
        else if (!gamepad1.dpad_down && incrementdown == STATE.INPROGRESS)
        {
            Gear -= GearChange;
            incrementdown = STATE.OFF;
        }

        //Makes sure that "Gear" is withing the defined range
        Gear = Range.clip(Gear, 0.5, 1.0);

        //All movement calculations for controller 1
        lR1 = ((-strafe1 + drive1) + turn1) * Gear;
        rR1 = ((strafe1 + drive1) - turn1) * Gear;
        lF1 = ((strafe1 + drive1) + turn1) * Gear;
        rF1 = ((-strafe1 + drive1) - turn1) * Gear;

        //All movement calculations for controller 2
        lR2 = ((-strafe2 + drive2) + turn2) * .20;
        rR2 = ((strafe2 + drive2) - turn2) * .20;
        lF2 = ((strafe2 + drive2) + turn2) * .20;
        rF2 = ((-strafe2 + drive2) - turn2) * .20;

        //Combines controller 1 and controller 2 calculations for fluid transfer of controls
        lR = lR1 + lR2;
        rR = rR1 + rR2;
        lF = lF1 + lF2;
        rF = rF1 + rF2;

        //limits the lift so that it can not go past the defined raised and lowered values
        //makes sure that the pulley string remains on the pulley
        if(!robot.bottomedSensor.getState() == true){
            liftdown = 0;
            telemetry.addData("lift","is completely lowered" );

        }
        else if (!robot.maxxedSensor.getState() == true){
            liftup = 0;
            telemetry.addData("lift","is completely raised" );

        }

        //combines lift up and down to allow full control of lift movement
        lift = (liftup - liftdown*.5);

        //makes sure all variables are within the defined range
        lR = Range.clip(lR, -1, 1);
        rR = Range.clip(rR, -1, 1);
        lF = Range.clip(lF, -1, 1);
        rF = Range.clip(rF, -1, 1);
        lift = Range.clip(lift, -1, 1);

        //controls the "tape" servo, allowing it to move the tape measure on the robot
        if (gamepad1.right_trigger>0){
            robot.tape.setPower(1);
        }
        else if (gamepad1.left_trigger>0){
            robot.tape.setPower(-1);
        }
        else if (gamepad1.right_trigger == 0 && gamepad1.left_trigger == 0){
            robot.tape.setPower(0);
        }

        //plugs in all movement values and tells the motors how fast to move
        robot.Drive0.setPower(lF);
        robot.Drive1.setPower(rF);
        robot.Drive2.setPower(lR);
        robot.Drive3.setPower(rR);
        robot.Drive4.setPower(lift);

        //controls the smaller of the 2 grippers and allows it to open and close
        if (gamepad2.a && clawstatus == STATE.OPEN && clawClosed == STATE.OFF) {
            clawClosed = STATE.INPROGRESS;
        }
        else if (!gamepad2.a && clawstatus == STATE.OPEN && clawClosed == STATE.INPROGRESS){
            offset = robot.SERVO_CLOSED;
            clawstatus = STATE.CLOSED;
            clawClosed = STATE.OFF;
        }
        if (gamepad2.a && clawstatus == STATE.CLOSED && clawOpen == STATE.OFF){
            clawOpen = STATE.INPROGRESS;
        }
        else if (!gamepad2.a && clawstatus == STATE.CLOSED && clawOpen == STATE.INPROGRESS){
            offset = .0;
            clawstatus = STATE.OPEN;
            clawOpen = STATE.OFF;
        }

        //controls the larger of the 2 grippers and allows it to open and close
        if (gamepad2.x && flipstatus == STATE.OPEN && flipClosed == STATE.OFF) {
            flipClosed = STATE.INPROGRESS;
        }
        else if (!gamepad2.x && flipstatus == STATE.OPEN && flipClosed == STATE.INPROGRESS){
            flipOffset = .0;
            flipstatus = STATE.CLOSED;
            flipClosed = STATE.OFF;
        }
        if (gamepad2.x && flipstatus == STATE.CLOSED && flipOpen == STATE.OFF){
            flipOpen = STATE.INPROGRESS;
        }
        else if (!gamepad2.x && flipstatus == STATE.CLOSED && flipOpen == STATE.INPROGRESS){
            flipOffset = .28;
            flipstatus = STATE.OPEN;
            flipOpen = STATE.OFF;
        }

        //controls the orientation of the entire front mechanism and allows it to move left to right
        if(gamepad2.left_bumper && (armPosition == STATE.RIGHT || armPosition == STATE.MID) && armMoveL == STATE.OFF){
            armMoveL = STATE.INPROGRESS;
        }
        else if (!gamepad2.left_bumper && armPosition == STATE.MID && armMoveL == STATE.INPROGRESS){
            armOffset = .32;
            armPosition = STATE.LEFT;
            armMoveL = STATE.OFF;
        }
        if(gamepad2.right_bumper && (armPosition == STATE.LEFT || armPosition == STATE.MID) && armMoveR == STATE.OFF){
            armMoveR = STATE.INPROGRESS;
        }
        else if (!gamepad2.right_bumper && armPosition == STATE.LEFT && armMoveR == STATE.INPROGRESS){
            armOffset = 0;
            armPosition = STATE.MID;
            armMoveR = STATE.OFF;
        }

        //resets the arm to its original, center position
        //moves the longer of the 2 arms up and out of the way
        if (gamepad2.y){
            armOffset = .0;
            armPosition = STATE.MID;
            flipOffset = 1;
            flipstatus = STATE.OPEN;
        }

        //controls the 2 rotating plates at the front of the robot, allowing them to swing up or down
        if (gamepad2.b && dragStatus == STATE.UP && dragDown == STATE.OFF) {
            dragDown = STATE.INPROGRESS;
        }
        else if (!gamepad2.b && dragStatus == STATE.UP && dragDown == STATE.INPROGRESS){
            dragoffset = 0;
            dragStatus = STATE.DOWN;
            dragDown = STATE.OFF;
        }
        if (gamepad2.b && dragStatus == STATE.DOWN && dragUp == STATE.OFF){
            dragUp = STATE.INPROGRESS;
        }
        else if (!gamepad2.b && dragStatus == STATE.DOWN && dragUp == STATE.INPROGRESS){
            dragoffset =.75;
            dragStatus = STATE.UP;
            dragUp = STATE.OFF;
        }

        //allows the arm and grip that hold the capstone to move down
        if (gamepad1.b && capStatus == STATE.UP && capDown == STATE.OFF) {
            capDown = STATE.INPROGRESS;
        }
        else if (!gamepad1.b && capStatus == STATE.UP && capDown == STATE.INPROGRESS){
            capPos = .25;
            capStatus = STATE.DOWN;
            capDown = STATE.OFF;
        }
        if (gamepad1.b && capStatus == STATE.DOWN && capUp == STATE.OFF){
            capUp = STATE.INPROGRESS;
        }
        else if (!gamepad1.b && capStatus == STATE.DOWN && capUp == STATE.INPROGRESS){
            capPos = 0;
            capStatus = STATE.UP;
            capUp = STATE.OFF;
        }
        //plugs in all servo variables, moving them to their new position
        robot.clawServo.setPosition(offset);
        robot.armServo.setPosition(armOffset);
        robot.dragServo1.setPosition(dragoffset);
        robot.dragServo2.setPosition(dragoffset);
        robot.flipServo.setPosition(flipOffset);
        robot.cap.setPosition(capPos);

        //gives feedback to the driver(s)
        telemetry.addData("Gear","%.2f", Gear);
        telemetry.addData("Large Claw Status:",flipstatus);
        telemetry.addData("Small Claw Status:", clawstatus);
        telemetry.update();

    }
}
