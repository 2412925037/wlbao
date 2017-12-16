package com.renhuikeji.wanlb.wanlibao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 * <p>
 * 活动搜索热词实体类
 */

public class HotKeyBean extends BaseBean {
    private String worngMsg;
    private List<SalesAcriveBean> salesActive;
    private List<HotWordsBean> hotWords;

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }



    public List<SalesAcriveBean> getSalesAcrive() {
        return salesActive;
    }

    public void setSalesAcrive(List<SalesAcriveBean> salesAcrive) {
        this.salesActive = salesAcrive;
    }

    public List<HotWordsBean> getHotWords() {
        return hotWords;
    }

    public void setHotWords(List<HotWordsBean> hotWords) {
        this.hotWords = hotWords;
    }

    public static class SalesAcriveBean {
        /**
         * title : 3.8妇女节
         * pict_url : /Uploads/images/%E4%BF%83%E9%94%80%E6%B4%BB%E5%8A%A8/20170522005845.jpg
         * click_url : https://s.click.taobao.com/BVZoa2x
         * ent_start_time : 2017-05-22
         * ent_end_time : 2017-05-31
         */

        private String title;
        private String pict_url;
        private String click_url;
        private String ent_start_time;
        private String ent_end_time;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPict_url() {
            return pict_url;
        }

        public void setPict_url(String pict_url) {
            this.pict_url = pict_url;
        }

        public String getClick_url() {
            return click_url;
        }

        public void setClick_url(String click_url) {
            this.click_url = click_url;
        }

        public String getEnt_start_time() {
            return ent_start_time;
        }

        public void setEnt_start_time(String ent_start_time) {
            this.ent_start_time = ent_start_time;
        }

        public String getEnt_end_time() {
            return ent_end_time;
        }

        public void setEnt_end_time(String ent_end_time) {
            this.ent_end_time = ent_end_time;
        }
    }

    public static class HotWordsBean {
        /**
         * q : 面膜
         * cat : 1801
         */

        private String q;
        private String cat;

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }
    }
}
