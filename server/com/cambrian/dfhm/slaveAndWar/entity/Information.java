package com.cambrian.dfhm.slaveAndWar.entity;

import java.util.ArrayList;
import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.SampleFactory;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;

/**
 * 类说明：信息对象(用于当壕功购能)
 * 
 * @author：LazyBear
 */
public class Information extends Sample implements Comparable<Information> {

	/* static fields */
	/* 事件类型 1办事 2打架 3路见不平 4好友求助 5反抗 6赎身 7释放 8身份关系时间到 */
	public static final int TYPE_WORKDONE = 1, TYPE_FIGHT = 2, TYPE_SAVE = 3,
			TYPE_HELP = 4, TYPE_REACT = 5, TYPE_RANSOM = 6, TYPE_RELEASE = 7,
			TYPE_TIMEOVER = 8;
	/* 事件 1成功 0失败 */
	public static final int EVENT_SUCCESS = 1, EVENT_FAIL = 0;
	/* static methods */

	/* fields */
	/** 事件类型 */
	private int type;
	/** 事件结果 */
	private int result;
	/** 事件影响身份 1马仔 2侠客 3土豪 */
	private int identity;
	/** 事件信息内容 */
	private String content;
	/** 事件产生时间 */
	private long createTime;

	/* constructors */

	/* properties */
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long takeTime) {
		this.createTime = takeTime;
	}

	/* init start */

	/* methods */
	/** 序列化 和DC通信 存 */
	public void dbBytesWrite(ByteBuffer data) {
		data.writeInt(type);
		data.writeInt(result);
		data.writeInt(identity);
		data.writeUTF(content);
		data.writeLong(createTime);
	}

	/** 序列化 和DC通信 取 */
	public void dbBytesRead(ByteBuffer data) {
		type = data.readInt();
		result = data.readInt();
		identity = data.readInt();
		content = data.readUTF();
		createTime = data.readLong();
	}

	/**
	 * 重写排序
	 */
	public int compareTo(Information information) {
		long i = this.createTime;
		long j = information.getCreateTime();
		return ((i == j) ? 0 : (i > j) ? -1 : 1);
	}

	/**
	 * 获取信息对象
	 * 
	 * @param identity
	 *            身份对象
	 * @param playerA
	 *            玩家A姓名
	 * @param playerB
	 *            玩家B姓名
	 * @param playerC
	 *            玩家C姓名
	 * @param type
	 *            事件类型
	 * @param result
	 *            事件执行结果
	 * @param value
	 *            附属值
	 * @return
	 */
	public static void CreatandSave(Identity identity, String playerA,
			String playerB, int type, int result, int value) {
		int[] informationSids = GameCFG.getSlaveInformations();
		for (Integer integer : informationSids) {
			Information information = (Information) Sample.getFactory()
					.newSample(integer);
			if (information.type == type && information.result == result
					&& information.identity == identity.getGrade()) {
				information.content.replace("A", playerA);
				information.content.replace("B", playerB);
				information.content.replace("C", playerC);
				information.content.replace("value", String.valueOf(value));
				identity.addInformation(information);
				break;
			}
		}
	}
}
