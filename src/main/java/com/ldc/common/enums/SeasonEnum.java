package com.ldc.common.enums;

/**
 * Created by dacheng.liu on 2017/8/24.
 */
public enum SeasonEnum implements Behaviour {

    SPRING("春天", 1){
        @Override
        public String getSeasonName() {
            return "春季";
        }

        @Override
        public int getSeasonOrder() {
            return 0;
        }
    },

    SUMMER("夏天", 2){
        @Override
        public String getSeasonName() {
            return "夏季";
        }

        @Override
        public int getSeasonOrder() {
            return 1;
        }
    },

    AUTUMN("秋天", 3){
        @Override
        public String getSeasonName() {
            return "秋季";
        }

        @Override
        public int getSeasonOrder() {
            return 2;
        }
    },

    WINTER("冬天", 4){
        @Override
        public String getSeasonName() {
            return "冬季";
        }

        @Override
        public int getSeasonOrder() {
            return 3;
        }
    };

    private String seasonName;
    private int seasonOrder;

    private SeasonEnum(String seasonNmae, int seasonOrder) {
        this.seasonName = seasonNmae;
        this.seasonOrder = seasonOrder;
    }

    /**
     * 以下是枚举类中定义的其他方法
     */

    public String getSeasonName() {
        return this.seasonName;
    }

    public int getSeasonOrder() {
        return this.seasonOrder;
    }

    @Override
    public void disPlay(SeasonEnum seasonEnum) {
        if (seasonEnum == null) {
            System.out.println("seasonEnum is null!");
            return ;
        }
        System.out.println(String.format("当前季节是：%s, 属于一年中的第：%s 个季节！",
                seasonEnum.getSeasonName(),
                seasonEnum.getSeasonOrder()));
    }


}
