package demo;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;


/**
 * @author hundun
 * Created on 2020/08/10
 */
public class JapaneseTest {
    static String splict = "\\r\\n";
    
    public static void main(String[] args) {
        JapaneseTest japaneseTest = new JapaneseTest();
        japaneseTest.testFun();
    }
    
    public void testFun() {
        JapaneseTool.fun(whyIAmHere, splict);
    }
    
    
    public void testIsPlain() {
        String text = "を";
        assertEquals(true, JapaneseTool.isAllKana(text));
        
        text = "ハァーヨイショ";
        assertEquals(true, JapaneseTool.isAllKana(text));
        
        text = "分ける";
        assertEquals(false, JapaneseTool.isAllKana(text));
    }
    
    
    static String moonIsBeautiful = "夕暮れ落ちる前に白い月が昇る\r\n" + 
            "春風優しく2つの影　ささやくように包んでいたよ\r\n" + 
            "\r\n" + 
            "初めてこんなに誰かを想った\r\n" + 
            "\r\n" + 
            "いつもどうしていいのかわからなかった\r\n" + 
            "君への想いはこぼれるほどあるのに\r\n" + 
            "つないだ右手もぎこちないキスも\r\n" + 
            "それだけがこの世界の全てだった\r\n" + 
            "今日も君からもらった言葉を抱きしめている\r\n" + 
            "私にとってそれはまるで月明り\r\n" + 
            "二人で見上げたあの時みたいにきれい\r\n" + 
            "\r\n" + 
            "暑い夏の日差し　揺れる風鈴の音\r\n" + 
            "深呼吸して見つめた背中は　思ったよりもすごく大きくて\r\n" + 
            "\r\n" + 
            "どうしてこんなに切なくさせるの？\r\n" + 
            "\r\n" + 
            "あの日二人で歩いた菓子屋横丁\r\n" + 
            "君の歩幅に合わせてついていったね\r\n" + 
            "いつもの寝ぐせとほどけた靴ひも\r\n" + 
            "このまま時を止めてしまいたかった\r\n" + 
            "夏祭りに二人で見上げた花火は今でも\r\n" + 
            "私にとってかけがえない宝物\r\n" + 
            "二人を見ていたあの月みたいにきれい\r\n" + 
            "\r\n" + 
            "どんなに遠く　離れていても君と\r\n" + 
            "ずっといつまででも　一緒にいられますようにと\r\n" + 
            "祈った\r\n" + 
            "\r\n" + 
            "夜空に今日も瞬く星を見つめて\r\n" + 
            "こぼれる涙を一人こらえていたよ\r\n" + 
            "言葉を持たない月が私なら\r\n" + 
            "君という星見つめつづける\r\n" + 
            "\r\n" + 
            "いつもどうしていいのかわからなかった\r\n" + 
            "君への想いはこぼれるほどあるのに\r\n" + 
            "つないだ右手もぎこちないキスも\r\n" + 
            "それだけがこの世界の全てだった\r\n" + 
            "今日も君からもらった言葉を抱きしめている\r\n" + 
            "私にとってそれはまるで月明り\r\n" + 
            "二人で見上げたあの時みたいにきれい";
    
    static String whyIAmHere = "昼：夢の前では大きく\r\n" + 
            "人の前では小さくなる\r\n" + 
            "でも本当の私を知りたくて\r\n" + 
            "いつもその笑顔に憧れていた\r\n" + 
            "真矢：期待されてきた未来\r\n" + 
            "当然のセンターポジション\r\n" + 
            "親の七光りと言われようとも\r\n" + 
            "これが私に与えられた人生の舞台だから\r\n" + 
            "克洛：負けられない誰にも\r\n" + 
            "美しさならぼ皆が持っている\r\n" + 
            "でも私だけの何かで輝きたい\r\n" + 
            "なな:慣れてきた当たり前の孤独\r\n" + 
            "舞台が変えてくれたわ\r\n" + 
            "香:私ならば目指せる\r\n" + 
            "なな：変わりたくないこのまま\r\n" + 
            "香：次のステージへ\r\n" + 
            "なな：次には私まだ進めない\r\n" + 
            "時間よ止まれ 大人にならないで\r\n" + 
            "純：期待されてきた未来\r\n" + 
            "立派に生きることの期待\r\n" + 
            "親の言うレールを破ってここまできたの\r\n" + 
            "合：これが私に与えられた人生の舞台だから\r\n" + 
            "葉：負けられない誰にも\r\n" + 
            "強がりならば本当はいらない\r\n" + 
            "合：でも私だけの何かで輝きたい（なな：時間よ 止めて）\r\n" + 
            "恋：大切な人と\r\n" + 
            "香：一人じゃない\r\n" + 
            "恋：夢の舞台へと（昼：恐怖を）\r\n" + 
            "ライトを浴びるまで（葉：打さ破って）\r\n" + 
            "真矢：負けられない自分に\r\n" + 
            "恋：諦めない\r\n" + 
            "克洛：強くなりたいから\r\n" + 
            "恋：私がここに居る理由（わけ）\r\n" + 
            "答えなんてきっとーつじゃない（純：私の答えなんて 合：私だけの何かを）\r\n" + 
            "合：何かを 何かを\r\n" + 
            "見つけるまで進むしかない\r\n" + 
            "光：私にとってたったーつの夢のために\r\n" + 
            "それが私がここに居る理由";
}
