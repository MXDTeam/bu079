package constants;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.ArrayList;
import tools.Pair;
import gui.Start;
import tools.FileoutputUtil;
import tools.packet.MobPacket;
import server.life.MapleMonster;
import client.MapleCharacter;
import java.util.List;

public class tzjc
{
    private static List<tz_model> tz_list = (List<tz_model>)new LinkedList();
    
    public long star_damage(final MapleCharacter player, long totDamageToOneMonster, final MapleMonster monster) {
        try {
            if (player.get套装伤害加成() > 0.0) {
                final double jc_damage = (double)totDamageToOneMonster * player.get套装伤害加成();
                totDamageToOneMonster = (long)((double)totDamageToOneMonster + jc_damage);
                player.getMap().broadcastMessage(MobPacket.healMonster(monster.getObjectId(), (int)jc_damage));
            }
        }
        catch (Exception e) {
            FileoutputUtil.outError("logs/套装伤害异常.txt", (Throwable)e);
            return totDamageToOneMonster;
        }
        return totDamageToOneMonster;
    }
    
    public static void sr_tz() {
        tzjc.tz_list.clear();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][========================================]");
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化套装加成");
        for (int i = 0; i < Start.套装加成表.size(); ++i) {
            if (((Integer)(Start.套装加成表.get(i)).getLeft()).intValue() == 0) {
                final tz_model tz = new tz_model();
                tz.setName((String)((Start.套装加成表.get(i)).getRight()).getLeft());
                tz.setJc((double)((Integer)(((Start.套装加成表.get(i)).getRight()).getRight()).getRight()).intValue() / 100.0);
                final int[] list = { Integer.valueOf((String)(((Start.套装加成表.get(i)).getRight()).getRight()).getLeft()).intValue() };
                tz.setList(list);
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][套装]:[" + tz.getName() + "] 添加成功 加成伤害：" + tz.getJc() * 100.0 + "%");
                tzjc.tz_list.add(tz);
            }
        }
        for (int b = 1; b < ((Integer)Start.ConfigValuesMap.get("套装个数")).intValue(); ++b) {
            final List<Integer> 套装 = (List<Integer>)new ArrayList();
            int 加成 = 0;
            String 套装名 = "";
            for (int j = 0; j < Start.套装加成表.size(); ++j) {
                if (((Integer)(Start.套装加成表.get(j)).getLeft()).intValue() == b) {
                    套装.add(Integer.valueOf((String)(((Start.套装加成表.get(j)).getRight()).getRight()).getLeft()));
                    加成 += ((Integer)(((Start.套装加成表.get(j)).getRight()).getRight()).getRight()).intValue();
                    套装名 = (String)((Start.套装加成表.get(j)).getRight()).getLeft();
                }
            }
            final tz_model tz2 = new tz_model();
            tz2.setName(套装名);
            tz2.setJc((double)加成 / 100.0);
            final int[] list2 = new int[套装.size()];
            for (int k = 0; k < list2.length; ++k) {
                list2[k] = ((Integer)套装.get(k)).intValue();
            }
            tz2.setList(list2);
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][套装]:[" + tz2.getName() + "] 添加成功 加成伤害：" + tz2.getJc() * 100.0 + "%");
            tzjc.tz_list.add(tz2);
        }
    }
    
    public static double check_tz(final MapleCharacter chr) {
        if (((Integer)Start.ConfigValuesMap.get("套装属性加成开关")).intValue() > 0) {
            double number = 0.0;
            for (final tz_model tz : tzjc.tz_list) {
                final int[] list = tz.getList();
                boolean is_tz = true;
                for (int j = 0; j < list.length; ++j) {
                    if (!chr.hasEquipped(list[j])) {
                        is_tz = false;
                    }
                }
                if (is_tz) {
                    chr.dropMessage(5, "检测到佩戴了 [" + tz.getName() + "] 套装  加成伤害：" + tz.getJc() * 100.0 + "%");
                    number += tz.getJc();
                }
            }
            return number;
        }
        return 0.0;
    }
}
