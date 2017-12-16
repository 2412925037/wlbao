package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/12/13.
 * 分享
 */

public class ShareBean {


    /**
     * result : SHARE_SUCESS
     * uid : 75986
     * shareData : {"title":"万利宝，指尖上的省钱专家","content":"万利宝是一家专门做返利、优惠券的特卖网站，与淘宝/天猫，京东，一号店，蘑菇街，美丽说，苏宁易购，当当，国美在线等国内外著名网站直接合作，返利高，提现快！","logo":"http://m.yasbao.com/Public/images/yasbao_yuan_120.png","url":"http://m.yasbao.com?uid=75986"}
     */

    private String result;
    private String uid;
    private ShareDataBean shareData;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ShareDataBean getShareData() {
        return shareData;
    }

    public void setShareData(ShareDataBean shareData) {
        this.shareData = shareData;
    }

    public static class ShareDataBean {
        /**
         * title : 万利宝，指尖上的省钱专家
         * content : 万利宝是一家专门做返利、优惠券的特卖网站，与淘宝/天猫，京东，一号店，蘑菇街，美丽说，苏宁易购，当当，国美在线等国内外著名网站直接合作，返利高，提现快！
         * logo : http://m.yasbao.com/Public/images/yasbao_yuan_120.png
         * url : http://m.yasbao.com?uid=75986
         */

        private String title;
        private String content;
        private String logo;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
