package com.lovejjfg.circle;

import java.util.List;

/**
 * Created by Joe on 2016-06-21
 * Email: zhangjun166@pingan.com.cn
 */
public class Test {

    /**
     * scope_name : 全部城市
     * scope_cities : [{"code_id":"121","name":"安阳","pinyin":"anyang","short_pinyin":"ay","latitude":38.23,"longitude":52.23},{"code_id":"472","name":"安徽","pinyin":"anhui","short_pinyin":"ah","latitude":38.23,"longitude":52.23},"。。。。"]
     */

    private String scope_name;
    /**
     * code_id : 121
     * name : 安阳
     * pinyin : anyang
     * short_pinyin : ay
     * latitude : 38.23
     * longitude : 52.23
     */

    private List<ScopeCitiesEntity> scope_cities;

    public String getScope_name() {
        return scope_name;
    }

    public void setScope_name(String scope_name) {
        this.scope_name = scope_name;
    }

    public List<ScopeCitiesEntity> getScope_cities() {
        return scope_cities;
    }

    public void setScope_cities(List<ScopeCitiesEntity> scope_cities) {
        this.scope_cities = scope_cities;
    }

    public static class ScopeCitiesEntity {
        private String code_id;
        private String name;
        private String pinyin;
        private String short_pinyin;
        private double latitude;
        private double longitude;

        public String getCode_id() {
            return code_id;
        }

        public void setCode_id(String code_id) {
            this.code_id = code_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getShort_pinyin() {
            return short_pinyin;
        }

        public void setShort_pinyin(String short_pinyin) {
            this.short_pinyin = short_pinyin;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
