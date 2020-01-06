/* Copyright (c) 2017 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

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

@TeleOp(name="Light Test", group="Strafebot")
//@Disabled
public class Light_Test extends OpMode {

    /* Declare OpMode members. */
    HardwareStrafe robot           = new HardwareStrafe();   // Use a Pushbot's hardware
    STATE incrementup   = STATE.OFF;
    STATE incrementdown = STATE.OFF;
    STATE clawstatus = STATE.OPEN;
    private double Gear = 0.25;
    private static final Double GearChange = .05;
    private double offset = 0;
    private double drive;
    private double strafe;
    private double turn;
    private double leftR;
    private double rightR;
    private double leftF;
    private double rightF;
    //set up all variables

    @Override
    public void init() {
        robot.init(hardwareMap);

    }
public int calibrateRAW (int raw){
       double tmp = Math.round(raw/10240.0*255.0);
        return (int) tmp;
}
    @Override
    public void loop() {
        telemetry.addData("red level", calibrateRAW(robot.colorSensor.red()));
        telemetry.addData("green level",  calibrateRAW(robot.colorSensor.green()));
        telemetry.addData("blue level",  calibrateRAW(robot.colorSensor.blue()));
        telemetry.addData("alpha level",  calibrateRAW(robot.colorSensor.alpha()));
//        telemetry.addData("red level RAW", robot.colorSensor.red());
//        telemetry.addData("green level RAW",  robot.colorSensor.green());
//        telemetry.addData("blue level RAW",  robot.colorSensor.blue());
//        telemetry.addData("alpha level RAW",  robot.colorSensor.alpha());
        telemetry.addData("distance inches", robot.distanceSensor.getDistance(DistanceUnit.CM));
        telemetry.update();



    }
}
