package ccdd.auto;/* Copyright (c) 2017 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import ccdd.util.AutoEncoder;
import ccdd.util.AutonomousUtilities;
import ccdd.util.GyroUtilities;
import ccdd.util.STATE;

import static ccdd.TeleOp.HardwareStrafe.LEFT_ORIGIN;
import static ccdd.TeleOp.HardwareStrafe.ORIGIN;
import static ccdd.TeleOp.HardwareStrafe.RIGHT_ORIGIN;
import static ccdd.TeleOp.HardwareStrafe.TURN_SPEED;

/**
 * This file illustrates the concept of driving a path based on time.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backwards for 1 Second
 *   - Stop and close the claw.
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
/*
  Comment by Jeremy To test github push
 */

@Autonomous(name="Bridge, Blue, Blocks"  , group="Blue Bridge")
//@Disabled
public class AutoBlueBlocksBridge extends AutoBasic {

    @Override
    public void runOpMode() {

        initAutoBasic();

        robot.clawServo.setPosition(robot.MID_SERVO);
        robot.armServo.setPosition(.0);

        ae.encoderDrive(.5,37);
        au.extClosed();
        au.pause(.5);
        au.liftTime(.5, up, .3);
        ae.encoderDrive(-.5, -3);
        gu.gyroTurn(TURN_SPEED,LEFT_ORIGIN);
        gu.gyroTurn(TURN_SPEED,LEFT_ORIGIN);
        ae.encoderDrive(1,100);
        au.liftTime(1, up, .5);
        gu.gyroTurn(TURN_SPEED,ORIGIN);
        gu.gyroTurn(TURN_SPEED,ORIGIN);
        ae.encoderDrive(.5,11);
        au.liftTime(.5, down, .25);
        au.extOpen();
        ae.encoderDrive(-.5,-7);
        au.armReset();
        au.pause(.5);
        au.liftDown();
        gu.gyroTurn(TURN_SPEED,RIGHT_ORIGIN);
        gu.gyroTurn(TURN_SPEED,RIGHT_ORIGIN);
        ae.encoderDrive(1,108);
        au.armOut();
        gu.gyroTurn(TURN_SPEED,ORIGIN);
        gu.gyroTurn(TURN_SPEED,ORIGIN);
        ae.encoderDrive(.5,5);
        au.extClosed();
        au.pause(.5);
        ae.encoderDrive(-.5,-4);
        gu.gyroTurn(TURN_SPEED,LEFT_ORIGIN);
        gu.gyroTurn(TURN_SPEED,LEFT_ORIGIN);
        ae.encoderDrive(1,90);
        au.extOpen();
        au.pause(.1);
        gu.gyroTurn(TURN_SPEED,LEFT_ORIGIN);
        ae.encoderDrive(-1,-35);

    }
}