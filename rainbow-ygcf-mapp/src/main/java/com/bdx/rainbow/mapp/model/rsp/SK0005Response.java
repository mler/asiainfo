package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

import java.util.List;
import java.util.Map;

/**
 * Created by core on 15/10/14.
 * 台帐列表
 */
public class SK0005Response extends BDXBody {

    /**
     * 按天显示台账列表
     */
    private List<Day> days;

    public SK0005Response() {
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    /**
     * 按天的台账列表对象
     */
    public static class Day extends BDXBody {
        /**
         * 日期
         */
        private String date;

        /**
         * 台账明细
         */
        private List<Item> items;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
    }

    /**
     * 台账明细列表对象
     */
    public static class Item extends BDXBody {
        /**
         * 台账ID
         */
        private String id;

        /**
         * 台账票据
         *  URL
         */
        private String ticket;

        /**
         * 标题
         */
        private String name;

        /**
         * 数量
         */
        private String quantity;

        /**
         * 单位
         */
        private String unit;

        /**
         * 类型
         */
        private String type;

        /**
         * 是否可以修改，true不能修改，false可以修改
         */
        private boolean outOfDate;

        /**
         * 台账业务数据，key-value
         */
        private Map<String, String> map;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isOutOfDate() {
            return outOfDate;
        }

        public void setOutOfDate(boolean outOfDate) {
            this.outOfDate = outOfDate;
        }

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public Map<String, String> getMap() {
            return map;
        }

        public void setMap(Map<String, String> map) {
            this.map = map;
        }
    }
}
