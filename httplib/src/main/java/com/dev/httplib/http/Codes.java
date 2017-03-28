package com.dev.httplib.http;

public class Codes {

    /**
     * 渠道号
     * <p>
     * <pre>
     * 1	柜面
     * 2	生活通门户
     * 3	商户门户
     * 4	个人APP
     * 5	商户APP
     * 6	生活通智能POS
     * 7	生活通传统POS
     * 8	生活通应用终端
     * 9	生活通自助终端
     * 10	生活通读卡器
     * 11	呼叫中心
     * 12	银联POS
     * 13	运营中心
     * 14	第三方终端
     * </pre>
     */
    public static class ChannelCode {
        public static final int GUIMIAN = 1;
    }

    /**
     * 支付方式
     * <p>
     * <pre>
     * 1	现金
     * 2	生活通支付账户
     * 3	生活通卡(脱机)支付
     * 4	银行卡支付
     * 5	支付宝账户
     * 6	支付宝混合快捷
     * 7	支付宝网关
     * 8  支付宝借记卡快捷
     * </pre>
     */
    public static class PayType {

        public static final int XIANJIN = 1;
        public static final int SHENGHUOTONG = 2;
        public static final int MINGSHENG_YINGHANG = 3;
        public static final int FEI_MINGSHENG_YINGHANG = 4;
        public static final int ZHIFUBAO = 5;
        public static final int ZHIFUBAO_KUAIJIE = 6;
        public static final int ZHIFUBAO_WANGGUAN = 7;
        public static final int ZHIFUBAO_JIEJIKAKUAIJIE = 8;
    }

    /**
     * 公共事业缴费类型
     * <p>
     * <pre>
     * 01	水费
     * 02	电费
     * 03	煤气
     * 04	有线电视分
     * 05	物业费
     * 06	固话费
     * </pre>
     */
    public static class PublicFeeType {

    }

    /**
     * 订单状态
     * <p>
     * <pre>
     * 0	未付款
     * 1	已付款
     * 2	交易完成
     * 3	已关闭
     * </pre>
     */
    public static class OrderStatus {

    }

    /**
     * 证件类型
     * <p>
     * <pre>
     * 0	身份证
     * 1	护照
     * </pre>
     */
    public static class IdCardType {

    }

    /**
     * 用户类型
     * <p>
     * <pre>
     * 编号	描述
     * 0	身份证
     * 1	护照
     * </pre>
     */
    public static class UserType {

    }

    /**
     * 交易类型
     * <p>
     * <pre>
     * 类型	子类型	类型编号	子类型编号
     * 充值	支付账户充值	1	1
     * 	生活通钱包充值		2
     * 	电子现金充值		3
     * 	行业钱包1充值		4
     * 	行业钱包2充值		5
     * ***************
     * 支付	缴费	2	1
     * 	银联刷卡消费		2
     * 	脱机消费		3
     * 	网上支付		4
     * ****************
     * 提现	支付账户资金转出	3	1
     * 	脱机账户资金转出		2
     * ****************
     * 转账	转入	4	1
     * 	转出		2
     * </pre>
     */
    public static class TransactionType {

    }

    /**
     * 用户状态
     * <p>
     * <pre>
     * 1	官方锁定
     * 2	用户锁定
     * </pre>
     */
    public static class UserStatus {

    }

    /**
     * 账户状态
     * <p>
     * <pre>
     * 1	官方冻结
     * 2	用户冻结
     * 3	销户
     * 4	司法冻结
     * </pre>
     */
    public static class AccountStatus {

    }

    public static class RespCode {
        /**
         * 成功
         */
        public static final String SUCCESS = "0";

        /**
         * 登陆身份失效
         */
        public static final String TOKEN_INVALIDE = "AS105";
        public static final String TOKEN_INVALIDE2 = "AS108";
        /**
         * 用户被锁定
         */
        public static final String PWD_INVALIDE = "AS405";
    }
}