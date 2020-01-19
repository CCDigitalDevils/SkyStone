package ccdd.auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import ccdd.TeleOp.HardwareStrafe;

public class HardwareStrafeAuto extends HardwareStrafe {
    @Override
    public void init(HardwareMap ahwMap) {
        super.init(ahwMap);
        Drive0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Drive1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Drive2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Drive3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
