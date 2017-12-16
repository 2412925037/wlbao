package com.renhuikeji.wanlb.wanlibao.bean;

/**
 * Created by Administrator on 2017/5/24.
 *
 * 首页商城列表实体类
 */

public class MallListBean extends BaseBean {
    private MallArrBean mallArr;

    private String worngMsg;

    public String getWorngMsg() {
        return worngMsg;
    }

    public void setWorngMsg(String worngMsg) {
        this.worngMsg = worngMsg;
    }

    public MallArrBean getMallArr() {
        return mallArr;
    }

    public void setMallArr(MallArrBean mallArr) {
        this.mallArr = mallArr;
    }


    public static class MallArrBean extends BaseBean {
        private String mogujie;
        private String jd;
        private String gome;
        private String suning;
        private String vip;
        private String meilishuo;
        private String yhd;
        private String jiu;
        private String te;
        private String ai;
        private String travel;
        private String juhuasuan;

        public String getMogujie() {
            return mogujie;
        }

        public void setMogujie(String mogujie) {
            this.mogujie = mogujie;
        }

        public String getJd() {
            return jd;
        }

        public void setJd(String jd) {
            this.jd = jd;
        }

        public String getGome() {
            return gome;
        }

        public void setGome(String gome) {
            this.gome = gome;
        }

        public String getSuning() {
            return suning;
        }

        public void setSuning(String suning) {
            this.suning = suning;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getMeilishuo() {
            return meilishuo;
        }

        public void setMeilishuo(String meilishuo) {
            this.meilishuo = meilishuo;
        }

        public String getYhd() {
            return yhd;
        }

        public void setYhd(String yhd) {
            this.yhd = yhd;
        }

        public String getJiu() {
            return jiu;
        }

        public void setJiu(String jiu) {
            this.jiu = jiu;
        }

        public String getTe() {
            return te;
        }

        public void setTe(String te) {
            this.te = te;
        }

        public String getAi() {
            return ai;
        }

        public void setAi(String ai) {
            this.ai = ai;
        }

        public String getTravel() {
            return travel;
        }

        public void setTravel(String travel) {
            this.travel = travel;
        }

        public String getJuhuasuan() {
            return juhuasuan;
        }

        public void setJuhuasuan(String juhuasuan) {
            this.juhuasuan = juhuasuan;
        }
    }
}
