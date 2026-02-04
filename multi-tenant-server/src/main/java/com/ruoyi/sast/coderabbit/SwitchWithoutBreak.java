package com.ruoyi.sast.coderabbit;

/**
 * 【规则13】异味 - 阻断
 * Switch cases should end with an unconditional "break" statement
 * Switch的case应该以无条件的"break"语句结束
 *
 * 问题：缺少break会导致case穿透(fall-through)，执行后续case的代码
 */
public class SwitchWithoutBreak {

    // 🚨 违规：多个case缺少break语句
    public String getDayType(int day) {
        String type;
        switch (day) {
            case 1:
                type = "Monday";
                // 🚨 违规：缺少break，会穿透到case 2
            case 2:
                type = "Tuesday";
                // 🚨 违规：缺少break
            case 3:
                type = "Wednesday";
                break;
            case 4:
                type = "Thursday";
                // 🚨 违规：缺少break
            case 5:
                type = "Friday";
                break;
            case 6:
                type = "Saturday";
                // 🚨 违规：缺少break
            case 7:
                type = "Sunday";
                // 🚨 违规：缺少break，穿透到default
            default:
                type = "Unknown";
                break;
        }
        return type;
    }

    // 🚨 违规：部分case缺少break
    public int calculateBonus(String level) {
        int bonus = 0;
        switch (level) {
            case "S":
                bonus = 10000;
                // 🚨 违规：缺少break
            case "A":
                bonus = 5000;
                break;
            case "B":
                bonus = 3000;
                // 🚨 违规：缺少break，会意外获得C级奖金
            case "C":
                bonus = 1000;
                break;
            default:
                bonus = 0;
        }
        return bonus;
    }

    // 🚨 违规：有返回值但部分路径可能穿透
    public String getColor(int code) {
        switch (code) {
            case 1:
                System.out.println("Processing red");
                // 🚨 违规：没有return也没有break
            case 2:
                return "BLUE";  // code=1时也会返回BLUE
            case 3:
                System.out.println("Processing green");
                // 🚨 违规：缺少break或return
            default:
                return "UNKNOWN";
        }
    }

    // 🚨 违规：嵌套switch中的break缺失
    public void nestedSwitch(int a, int b) {
        switch (a) {
            case 1:
                switch (b) {
                    case 1:
                        System.out.println("a=1, b=1");
                        // 🚨 违规：内层switch缺少break
                    case 2:
                        System.out.println("a=1, b=2");
                }
                // 🚨 违规：外层switch缺少break
            case 2:
                System.out.println("a=2");
                break;
        }
    }
}
