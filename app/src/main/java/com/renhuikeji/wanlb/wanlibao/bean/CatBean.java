package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/5/18.
 *
 * 分类的实体类
 */

public class CatBean extends BaseBean {

    /**
     * cid : 11
     * cat : null
     * pict_url : null
     * level : 1
     * father_cid : 0
     * title : 女人
     * q :
     */

    private String cid;
    private String cat;
    private String pict_url;
    private String level;
    private String father_cid;
    private String title;
    private String q;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getPict_url() {
        return pict_url;
    }

    public void setPict_url(String pict_url) {
        this.pict_url = pict_url;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFather_cid() {
        return father_cid;
    }

    public void setFather_cid(String father_cid) {
        this.father_cid = father_cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
