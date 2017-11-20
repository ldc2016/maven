package com.ldc.common.enums;

/**
 * Created by dacheng.liu on 2017/8/24.
 */
public class SimpleEnumTest {
    public static void main(String[] args) {

        // 1.switch-case
        testSwitchUseEnum(SeasonEnum.SPRING);

        // 2.测试枚举实例可否用“==”
        testCompareUseEnum(SeasonEnum.SPRING,SeasonEnum.AUTUMN);
    }

    private static void testCompareUseEnum(SeasonEnum seasonEnum1, SeasonEnum seasonEnum2) {
        if(seasonEnum1 == null || seasonEnum2 == null){
            System.out.println("比较对象为空！");
            return;
        }

        boolean result = seasonEnum1==seasonEnum2;
        System.out.println("testCompareUseEnum ， the result : " + result);
    }


    private static void testSwitchUseEnum(SeasonEnum seasonEnum) {
        if(seasonEnum == null){
            System.out.println("seasonEnum is null!");
            return ;
        }
        switch (seasonEnum){
            case SPRING:
                seasonEnum.disPlay(seasonEnum);
                break;
            case SUMMER:
                seasonEnum.disPlay(seasonEnum);
                break;
            case AUTUMN:
                seasonEnum.disPlay(seasonEnum);
                break;
            case WINTER:
                seasonEnum.disPlay(seasonEnum);
                break;
            default:
                break;
        }
    }
}
