package io.github.davidchild.bitter.test.initMockData;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class MockRandomData {

    //复姓出现的几率(0--100)
    private static int surnameProbability = 5;
    private static Random random = new Random();
    private static String familyOneName = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻水云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任" +
            "袁柳鲍史唐费岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计成戴宋茅庞熊纪舒屈项祝董粱杜阮" +
            "席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田胡凌霍万柯卢莫房缪干解应宗丁宣邓郁单杭洪包诸左石崔吉龚程邢滑裴陆荣翁荀羊甄家封芮储靳邴" +
            "松井富乌焦巴弓牧隗山谷车侯伊宁仇祖武符刘景詹束龙叶幸司韶黎乔苍双闻莘劳逄姬冉宰桂牛寿通边燕冀尚农温庄晏瞿茹习鱼容向古戈终居衡步都耿满弘" +
            "国文东殴沃曾关红游盖益桓公晋楚闫";

    private static String familyTwoName = "欧阳太史端木上官司马东方独孤南宫万俟闻人夏侯诸葛尉迟公羊赫连澹台皇甫宗政濮阳公冶太叔申屠公孙慕容仲孙钟离长孙宇" +
            "文司徒鲜于司空闾丘子车亓官司寇巫马公西颛孙壤驷公良漆雕乐正宰父谷梁拓跋夹谷轩辕令狐段干百里呼延东郭南门羊舌微生公户公玉公仪梁丘公仲公上" +
            "公门公山公坚左丘公伯西门公祖第五公乘贯丘公皙南荣东里东宫仲长子书子桑即墨达奚褚师吴铭";

    private static String boyName = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达" +
            "安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽" +
            "晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";

    private static String girlName = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧" +
            "璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥" +
            "筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽";

    private static String phoneTwoNum = "3578";


    private static String[] province = {"河北省", "山西省", "辽宁省", "吉林省", "黑龙江省", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省", "海南省", "四川省", "贵州省", "云南省", "陕西省", "甘肃省", "青海省", "台湾省",};
    private static String[] city = {"安康市", "安庆市", "安顺市", "安阳市", "鞍山市", "巴彦淖尔市", "巴中市", "白城市", "白山市", "白银市", "百色市", "蚌埠市", "包头市", "宝鸡市", "保定市", "保山市", "北海市", "本溪市", "滨州市", "沧州市", "昌都地区", "长春市", "长沙市", "长治市", "常德市", "常州市", "巢湖市", "朝阳市", "潮州市", "郴州市", "成都市", "承德市", "池州市", "赤峰市", "崇左市", "滁州市", "达州市", "大连市", "大庆市", "大同市", "丹东市", "德阳市", "德州市", "定西市", "东莞市", "东营市", "鄂尔多斯市", "鄂州市", "防城港市", "佛山市", "福州市", "抚顺市", "抚州市", "阜新市", "阜阳市", "甘南州", "赣州市", "固原市", "广安市", "广元市", "广州市", "贵港市", "贵阳市", "桂林市", "哈尔滨市", "哈密地区", "海北藏族自治州", "海东地区", "海口市", "邯郸市", "汉中市", "杭州市", "毫州市", "合肥市", "河池市", "河源市", "菏泽市", "贺州市", "鹤壁市", "鹤岗市", "黑河市", "衡水市", "衡阳市", "呼和浩特市", "呼伦贝尔市", "湖州市", "葫芦岛市", "怀化市", "淮安市", "淮北市", "淮南市", "黄冈市", "黄山市", "黄石市", "惠州市", "鸡西市", "吉安市", "吉林市", "济南市", "济宁市", "佳木斯市", "嘉兴市", "嘉峪关市", "江门市", "焦作市", "揭阳市", "金昌市", "金华市", "锦州市", "晋城市", "晋中市", "荆门市", "荆州市", "景德镇市", "九江市", "酒泉市", "开封市", "克拉玛依市", "昆明市", "拉萨市", "来宾市", "莱芜市", "兰州市", "廊坊市", "乐山市", "丽江市", "丽水市", "连云港市", "辽阳市", "辽源市", "聊城市", "临沧市", "临汾市", "临沂市", "柳州市", "六安市", "六盘水市", "龙岩市", "陇南市", "娄底市", "泸州市", "吕梁市", "洛阳市", "漯河市", "马鞍山市", "茂名市", "眉山市", "梅州市", "绵阳市", "牡丹江市", "内江市", "南昌市", "南充市", "南京市", "南宁市", "南平市", "南通市", "南阳市", "宁波市", "宁德市", "攀枝花市", "盘锦市", "平顶山市", "平凉市", "萍乡市", "莆田市", "濮阳市", "普洱市", "七台河市", "齐齐哈尔市", "钦州市", "秦皇岛市", "青岛市", "清远市", "庆阳市", "曲靖市", "衢州市", "泉州市", "日照市", "三门峡市", "三明市", "三亚市", "汕头市", "汕尾市", "商洛市", "商丘市", "上饶市", "韶关市", "邵阳市", "绍兴市", "深圳市", "沈阳市", "十堰市", "石家庄市", "石嘴山市", "双鸭山市", "朔州市", "四平市", "松原市", "苏州市", "宿迁市", "宿州市", "绥化市", "随州市", "遂宁市", "台州市", "太原市", "泰安市", "泰州市", "唐山市", "天水市", "铁岭市", "通化市", "通辽市", "铜川市", "铜陵市", "铜仁市", "吐鲁番地区", "威海市", "潍坊市", "渭南市", "温州市", "乌海市", "乌兰察布市", "乌鲁木齐市", "无锡市", "吴忠市", "芜湖市", "梧州市", "武汉市", "武威市", "西安市", "西宁市", "锡林郭勒盟", "厦门市", "咸宁市", "咸阳市", "湘潭市", "襄樊市", "孝感市", "忻州市", "新乡市", "新余市", "信阳市", "兴安盟", "邢台市", "徐州市", "许昌市", "宣城市", "雅安市", "烟台市", "延安市", "盐城市", "扬州市", "阳江市", "阳泉市", "伊春市", "伊犁哈萨克自治州", "宜宾市", "宜昌市", "宜春市", "益阳市", "银川市", "鹰潭市", "营口市", "永州市", "榆林市", "玉林市", "玉溪市", "岳阳市", "云浮市", "运城市", "枣庄市", "湛江市", "张家界市", "张家口市", "张掖市", "漳州市", "昭通市", "肇庆市", "镇江市", "郑州市", "中山市", "中卫市", "舟山市", "周口市", "株洲市", "珠海市", "驻马店市", "资阳市", "淄博市", "自贡市", "遵义市",};
    private static String[] area = {"伊春区", "带岭区", "南岔区", "金山屯区", "西林区", "美溪区", "乌马河区", "翠峦区", "友好区", "新青区", "上甘岭区", "五营区", "红星区", "汤旺河区", "乌伊岭区", "榆次区"};
    private static String[] road = {"黄河路", "中原路", "安波路", "新四路", "安汾路", "安福路", "安国路", "安化路", "安澜路", "安龙路", "安仁路", "安顺路", "安亭路", "安图路", "安业路", "安义路", "安远路", "鞍山路", "鞍山支路", "澳门路", "八一路", "巴林路", "白城路", "白城南路", "白渡路", "白渡桥", "白兰路", "白水路", "白玉路", "百安路（方泰镇）", "百官街", "百花街", "百色路", "板泉路", "半淞园路", "包头路", "包头南路", "宝安公路", "宝安路", "宝昌路", "宝联路", "宝林路", "宝祁路", "宝山路", "宝通路", "宝杨路", "宝源路", "保德路", "保定路", "保屯路", "保屯路", "北艾路",};
    private static String[] home = {"金色家园", "耀江花园", "阳光翠竹苑", "东新大厦", "溢盈河畔别墅", "真新六街坊", "和亭佳苑", "协通公寓", "博泰新苑", "菊园五街坊", "住友嘉馨名园", "复华城市花园", "爱里舍花园"};

    private static String passwordForm = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz~!@#$%^&*()_+{}|<>?";

    /**
     * 生成随机数
     */
    public static int randomInt() {
        return random.nextInt();
    }

    /**
     * 生成随机数(最大值限制)
     */
    public static int randomInt(int maxNum) {
        return random.nextInt(maxNum);
    }

    /**
     * 获取随机男生姓名
     */
    public static String getRandomBoyName() {
        int bodNameIndexOne = randomInt(boyName.length());
        int bodNameIndexTwo = randomInt(boyName.length());
        return getNameStr(bodNameIndexOne, bodNameIndexTwo, boyName);
    }


    /**
     * 获取随机女生姓名
     */
    public static String getRandomGirlName() {
        int bodNameIndexOne = randomInt(girlName.length());
        int bodNameIndexTwo = randomInt(girlName.length());
        return getNameStr(bodNameIndexOne, bodNameIndexTwo, girlName);
    }

    /**
     * 获取随机手机号
     */
    public static String getRandomPhone() {
        int phoneTwoRandomIndex = randomInt(4);
        return "1" + phoneTwoNum.substring(phoneTwoRandomIndex, phoneTwoRandomIndex + 1) + (100000000 + randomInt(899999999));
    }

    /**
     * 获取随机qq邮箱
     */
    public static String getRandomQQEmail() {
        return ("" + random.nextLong()).substring(10) + "@qq.com";
    }

    /**
     * 获取随机性别
     */
    public static String getRandomSex() {
        return randomInt(2) % 2 == 0 ? "男" : "女";
    }

    /**
     * 获取随机年龄
     *
     * @param min 最小年龄
     * @param max 最大年龄
     * @return
     */
    public static int getRandomAge(int min, int max) {
        return min + random.nextInt(max - min);
    }

    /**
     * 获取随机金额
     */
    public static Double getRandomMoney(int min, int max) {
        int randomAge = getRandomAge(min, max);
        double v = random.nextDouble();
        return randomAge + v;
    }

    /**
     * 获取随机金额
     */
    public static BigDecimal getRandomDecimal(Integer min, Integer max) {
        return getRandomRedPacket(min, max, BigDecimal.ROUND_DOWN);
    }

    /**
     * 获取金额
     *
     * @param min
     * @param max
     * @return
     */
    public static BigDecimal getRandomRedPacket(Integer min, Integer max, int roundingMode) {
        float minF = min.floatValue();
        float maxF = max.floatValue();
        //生成随机数
        BigDecimal db = new BigDecimal(Math.random() * (maxF - minF) + minF);
        //返回保留两位小数的随机数。不进行四舍五入
        return db.setScale(2, roundingMode);
    }

    /**
     * 获取随机住址
     */
    public static String getRandomAddress() {
        return province[randomInt(province.length)] +
                city[randomInt(city.length)] +
                area[randomInt(area.length)] +
                road[randomInt(road.length)] +
                home[randomInt(home.length)];
    }

    /**
     * 获取随机时间
     */
    public static Date getRandonTime() {
        Random rndYear = new Random();
        int year = rndYear.nextInt(18) + 2000;  //生成[2000,2017]的整数；年
        Random rndMonth = new Random();
        int month = rndMonth.nextInt(12) + 1;   //生成[1,12]的整数；月
        Random rndDay = new Random();
        int Day = rndDay.nextInt(30) + 1;       //生成[1,30)的整数；日
        Random rndHour = new Random();
        int hour = rndHour.nextInt(23);       //生成[0,23)的整数；小时
        Random rndMinute = new Random();
        int minute = rndMinute.nextInt(60);   //生成分钟
        Random rndSecond = new Random();
        int second = rndSecond.nextInt(60);   //秒
        String dateStr = year + "-" + month + "-" + Day + "  " + hour + ":" + minute + ":" + second;

        return dateTime(YYYY_MM_DD_HH_MM_SS, dateStr);
    }

    /**
     * 获取随机密码(指定长度)
     */
    public static String getRandomPassword(int length) {
        StringBuilder springBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = randomInt(passwordForm.length());
            springBuilder.append(passwordForm.substring(index, index + 1));
        }
        return springBuilder.toString();
    }

    /**
     * 获取随机密码(指定长度)
     */
    public static String getRandomPassword() {
        return getRandomPassword(12);
    }

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String toSeconds(Date date) {
        return format(date, YYYY_MM_DD_HH_MM_SS);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return date == null ? "" : sdf.format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getNameStr(int bodNameIndexOne, int bodNameIndexTwo, String nameType) {
        if (randomInt(100) > surnameProbability) {
            int familyOneNameIndex = randomInt(familyOneName.length());
            return familyOneName.substring(familyOneNameIndex, familyOneNameIndex + 1) +
                    nameType.substring(bodNameIndexOne, bodNameIndexOne + 1) +
                    nameType.substring(bodNameIndexTwo, bodNameIndexTwo + 1);
        } else {
            int familyTwoNameIndex = randomInt(familyTwoName.length());
            familyTwoNameIndex = familyTwoNameIndex % 2 == 0 ? familyTwoNameIndex : familyTwoNameIndex - 1;
            return familyTwoName.substring(familyTwoNameIndex, familyTwoNameIndex + 2) +
                    nameType.substring(bodNameIndexOne, bodNameIndexOne + 1) +
                    nameType.substring(bodNameIndexTwo, bodNameIndexTwo + 1);
        }
    }


    public static void main(String[] args) {
        int randomInt = randomInt(); // 获取随机数
        System.out.println("randomInt = " + randomInt);
        int randomInt2 = randomInt(8); // 获取随机数
        System.out.println("randomInt2 = " + randomInt2);
        String randomBoyName = getRandomBoyName(); // 获取男士姓名
        System.out.println("randomBoyName = " + randomBoyName);
        String randomGirlName = getRandomGirlName(); // 获取女士姓名
        System.out.println("randomGirlName = " + randomGirlName);
        String randomPhone = getRandomPhone(); // 获取手机号码
        System.out.println("randomPhone = " + randomPhone);
        String randomQQEmail = getRandomQQEmail(); // 获取qq邮箱
        System.out.println("randomQQEmail = " + randomQQEmail);
        String randomSex = getRandomSex(); // 获取性别
        System.out.println("randomSex = " + randomSex);
        int randomAge = getRandomAge(0, 100); // 获取年龄
        System.out.println("randomAge = " + randomAge);
        Double randomMoney = getRandomMoney(0, 100); // 获取金额double
        System.out.println("randomMoney = " + randomMoney);
        BigDecimal randomDecimal = getRandomDecimal(0, 200); // 获取金额保留两位小数decimal
        System.out.println("randomDecimal = " + randomDecimal);
        String randomAddress = getRandomAddress(); // 获取地址
        System.out.println("randomAddress = " + randomAddress);
        Date randonTime = getRandonTime(); // 获取时间
        System.out.println("randonTime = " + randonTime);
        String format = toSeconds(randonTime); // 获取时间
        System.out.println("format = " + format);
        String randomPassword = getRandomPassword(); // 获取密码
        System.out.println("randomPassword = " + randomPassword);
        String randomPassword1 = getRandomPassword(10); // 获取密码(10位)
        System.out.println("randomPassword1 = " + randomPassword1);
    }

}

