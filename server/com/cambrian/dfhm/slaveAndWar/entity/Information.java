package com.cambrian.dfhm.slaveAndWar.entity;

import java.util.ArrayList;
import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.SampleFactory;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;

/**
 * ��˵������Ϣ����(���ڵ���������)
 * 
 * @author��LazyBear
 */
public class Information extends Sample implements Comparable<Information> {

	/* static fields */
	/* �¼����� 1���� 2��� 3·����ƽ 4�������� 5���� 6���� 7�ͷ� 8��ݹ�ϵʱ�䵽 */
	public static final int TYPE_WORKDONE = 1, TYPE_FIGHT = 2, TYPE_SAVE = 3,
			TYPE_HELP = 4, TYPE_REACT = 5, TYPE_RANSOM = 6, TYPE_RELEASE = 7,
			TYPE_TIMEOVER = 8;
	/* �¼� 1�ɹ� 0ʧ�� */
	public static final int EVENT_SUCCESS = 1, EVENT_FAIL = 0;
	/* static methods */

	/* fields */
	/** �¼����� */
	private int type;
	/** �¼���� */
	private int result;
	/** �¼�Ӱ����� 1���� 2���� 3���� */
	private int identity;
	/** �¼���Ϣ���� */
	private String content;
	/** �¼�����ʱ�� */
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
	/** ���л� ��DCͨ�� �� */
	public void dbBytesWrite(ByteBuffer data) {
		data.writeInt(type);
		data.writeInt(result);
		data.writeInt(identity);
		data.writeUTF(content);
		data.writeLong(createTime);
	}

	/** ���л� ��DCͨ�� ȡ */
	public void dbBytesRead(ByteBuffer data) {
		type = data.readInt();
		result = data.readInt();
		identity = data.readInt();
		content = data.readUTF();
		createTime = data.readLong();
	}

	/**
	 * ��д����
	 */
	public int compareTo(Information information) {
		long i = this.createTime;
		long j = information.getCreateTime();
		return ((i == j) ? 0 : (i > j) ? -1 : 1);
	}

	/**
	 * ��ȡ��Ϣ����
	 * 
	 * @param identity
	 *            ��ݶ���
	 * @param playerA
	 *            ���A����
	 * @param playerB
	 *            ���B����
	 * @param playerC
	 *            ���C����
	 * @param type
	 *            �¼�����
	 * @param result
	 *            �¼�ִ�н��
	 * @param value
	 *            ����ֵ
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
