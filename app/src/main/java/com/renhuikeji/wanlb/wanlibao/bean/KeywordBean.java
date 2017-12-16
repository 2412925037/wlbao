package com.renhuikeji.wanlb.wanlibao.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 *
 * 推荐关键词的实体类
 */

public class KeywordBean extends BaseBean {
    private List<KeywordsBean> keywords;
    private String worngMsg;

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }


    public List<KeywordsBean> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<KeywordsBean> keywords) {
        this.keywords = keywords;
    }

    public static class KeywordsBean {
        /**
         * q : innisfree
         * cat : 50010788
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
