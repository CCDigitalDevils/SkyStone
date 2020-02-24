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
import com.qualcomm.robotcore.util.ElapsedTime;

import ccdd.util.AutoEncoder;
import ccdd.util.AutonomousUtilities;
import ccdd.util.GyroUtilities;
import ccdd.util.STATE;

import static ccdd.TeleOp.HardwareStrafe.LEFT_ORIGIN;
import static ccdd.TeleOp.HardwareStrafe.ORIGIN;
import static ccdd.TeleOp.HardwareStrafe.RIGHT_ORIGIN;
import static ccdd.TeleOp.HardwareStrafe.TURN_SPEED;

@Autonomous(name="Bridge, Red, Blocks + Plate", group="Red Bridge")
//@Disabled
public class AutoRedBlocksPlateBridge extends AutoBasic {

    @Override
    public void runOpMode() {

        initAutoBasic();

        robot.clawServo.setPosition(robot.MID_SERVO);
        robot.armServo.setPosition(.0);

        ae.encoderDrive(.5,36);
        au.extClosed();
        au.pause(.5);
        au.liftTime(.5, up, .2);
        ae.encoderDrive(-.5, -1);
        gu.gyroTurn(TURN_SPEED,RIGHT_ORIGIN);
        gu.gyroTurn(TURN_SPEED,RIGHT_ORIGIN);
        ae.encoderDrive(1,95);
        au.liftTime(1, up, .5);
        gu.gyroTurn(TURN_SPEED,ORIGIN);
        gu.gyroTurn(TURN_SPEED,ORIGIN);
        ae.encoderDrive(.5,9);
        au.drag();
        au.extOpen();
        au.pause(.5);
        ae.encoderDrive(-1,-20);
        gu.gyroTurn(1,RIGHT_ORIGIN,2000l);
        gu.gyroTurn(TURN_SPEED,RIGHT_ORIGIN,2000l);
        ae.encoderDrive(1,7);
        au.noDrag();
        au.pause(.5);
        au.strafeTime(.75,100,1.5);
        au.liftDown();
        au.strafeTime(.75,-90,1.6);
        gu.gyroTurn(TURN_SPEED,RIGHT_ORIGIN);
        gu.gyroTurn(TURN_SPEED,RIGHT_ORIGIN);
        ae.encoderDrive(-.5,-45);
    }
}
