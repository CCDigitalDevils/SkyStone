package ccdd.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import ccdd.util.AutoEncoder;
import ccdd.util.AutonomousUtilities;
import ccdd.util.GyroUtilities;
import ccdd.util.STATE;

public abstract class AutoBasic extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareStrafeAuto         robot   = new HardwareStrafeAuto();   // Use a Pushbot's hardware
    protected ElapsedTime runtime = new ElapsedTime();

    protected AutonomousUtilities au;
    protected GyroUtilities gu;
    protected AutoEncoder ae;
    protected STATE open = STATE.OPEN;
    protected STATE closed = STATE.CLOSED;
    protected STATE left = STATE.LEFT;
    protected STATE right = STATE.RIGHT;
    protected STATE up = STATE.UP;
    protected STATE down = STATE.DOWN;

    public abstract void runOpMode();

    protected void initAutoBasic(){
        robot.init(hardwareMap);
        au = new AutonomousUtilities(robot, this, runtime);
        gu = new GyroUtilities(robot, this, runtime);
        ae = new AutoEncoder(robot,this,runtime);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
    }
}
