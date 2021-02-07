package io.github.javaherobrine;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.util.HashMap;

import io.github.javaherobrine.ioStream.JSONInputStream;
import io.github.javaherobrine.net.TransmissionFormat;

public final class Constants {
    public static final String CRLF = System.lineSeparator();
    @EasterEgg
    public static final String EASTER_EGG_ELEMENT_SB = new StringBuilder("�ҵ�����ʲôʱ����ô���ˣ�").append(CRLF).append("��һ�£��Ҹոտ����˵ȴ�api.ip.sb����Ӧ").append(CRLF).append("sb��׺���������ǵ���վ�������Ź�").append(CRLF).append("��Ϊ���Ԫ�ط�����Sb������˵���ǰ�com�ĳ���sb").append(CRLF).append("�������Ǹ������ṩ��dns����").append(CRLF).append("����dns�ӿ�").append(CRLF).append("������˼���õ�1.1.1.1������������DNS").append(CRLF).append("����250.250.250.250���������ǵ�dns����û��vsbpn���ʲ���").append(CRLF).append("����DNS�������鲻����10�붼ping��ͨ").append(CRLF).append("�������ǵ�ip�����ȾͲ�����java�飬Ҫ�����薲�ʿ��jvav����Ϊjvav�����ǲ���").append(CRLF).append("����˵�Ҳ���pingͨ������Ϊjava����Ŀǰ���õ�windows10����Cд�ģ���C������ģ��԰�").append(CRLF).append("��ôc--������").append(CRLF).append("c--Ҳ���ԣ���������#outclude <snd>��������snd.bil").append(CRLF).append("�������ٵ���Windows TN�ں˵�ϵͳ������NT").append(CRLF).append("Ҫ��wendossϵͳ").append(CRLF).append("���һ�����letni����dma��cpu").append(CRLF).append("Ҫʲôcpu����cup�ͺ���").append(CRLF).append("����Ҫ��tpcЭ��").append(CRLF).append("snd�õ���dup").append(CRLF).append("����Э�飬һ���ж��յ��˷�����").append(CRLF).append("������վ�ĳ��ı�����Э���õ���tpc").append(CRLF).append("������ֱ������̫���˻����ó��ı�����Э��").append(CRLF).append("�����ó��ı�����Э��").append(CRLF).append("�õ�tpcЭ�飬�������ܳ��ߵĶ����ٶ�").append(CRLF).append("������mttp��mttp��httpǿ����").append(CRLF).append("mttp�ײ��õ�tpc").append(CRLF).append("��tpc�У�������RST�������ӣ���SYN�Ͽ�����").append(CRLF).append("���ΰ����Ĵζ���").append(CRLF).append("��������tcp�����ǽ�������").append(CRLF).append("������򷢵���SYN����").append(CRLF).append("SYN�ǶϿ����ӵ���˼").append(CRLF).append("���ǲ��õ���").append(CRLF).append("�����õ��Ե����������ǵĵ��Խе���").append(CRLF).append("�����Ǻ�����ͨ��ʱ�����ǻᷢRST��������").append(CRLF).append("�������ϵ��豸Ҳ���RST�������ͽ���������").append(CRLF).append("Ȼ�����ǿ�ʼ�������ݣ�������豸һֱRST����������һ������Ҳ������").append(CRLF).append("����������εķ���SYN�Ͽ�����").append(CRLF).append("������豸Ҳ����SYN,ACK�Ͽ�����").append(CRLF).append("Ϊ�������Ǻ�����֮�����ͨ��").append(CRLF).append("���Ƿ�����TeSbЭ��").append(CRLF).append("�ֽ�����SBЭ��").append(CRLF).append("ͨ�������ռ����ʣ�Ť���ռ䣬�ﵽ�����ٴ�����Ϣ��Ŀ��").append(CRLF).append("����ʹ��tpc/piЭ��أ������ܹ��������tcp/ipЭ���ͨ��").toString();
    public static final int versionID = 1851844483;
    public static final HashMap<TransmissionFormat, Class<? extends ObjectInput>> map;
    public static final String RENDERING_FEEDBACK_ADDR = "lmqhlmx@163.com";
    public static final String SORTING_FEEDBACK_ADDR = "javaherobrine@qq.com";
    public static Field CLASSES = null;

    static {
        map = new HashMap<>();
        map.put(TransmissionFormat.JSON, JSONInputStream.class);
        map.put(TransmissionFormat.OBJECT, ObjectInputStream.class);
        try {
            CLASSES = ClassLoader.class.getDeclaredField("classes");
        } catch (NoSuchFieldException e) {
            CLASSES = null;
        }
        CLASSES.setAccessible(true);
    }

    private Constants() {
    }
}
