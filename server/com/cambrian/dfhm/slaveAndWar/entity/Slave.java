package com.cambrian.dfhm.slaveAndWar.entity;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.util.TimeKit;
import com.cambrian.dfhm.back.GameCFG;
import com.cambrian.dfhm.common.entity.Player;
import com.cambrian.dfhm.slaveAndWar.dao.SlaveAndWarDao;
import com.cambrian.dfhm.slaveAndWar.logic.SlaveManager;
import com.cambrian.game.Session;
import com.cambrian.game.ds.DataServer;

/**
 * ��˵����������-��-
 * 
 * @author:LazyBear
 */
public class Slave implements Comparable<Slave> {

	/* static fields */
	/** ū��״̬ 1���� 2������ */
	public static final int STATUS_FREE = 1, STATUS_WORK = 2;
	/* static methods */

	/* fields */
	/** ����ID */
	private int userId;
	/** �������� */
	private String name;
	/** ����״̬ */
	private int status;
	/** ����ս�� */
	private int fightPoint;
	/** ���п�ʼ����ʱ�� */
	private long startWorkTime;
	/** ������ʱ�� */
	private long takeTime;
	/** �Ƿ��й� */
	private boolean isManaged;
	/** ����ID */
	private int bossId;

	/* constructors */
	/**
	 * ���й��췽���������µ����У�
	 * 
	 * @param name
	 *            ��������
	 * @param fightPoint
	 *            ս����
	 */
	public Slave(String name, int fightPoint, int bossId, int userId) {
		this.userId = userId;
		this.name = name;
		this.fightPoint = fightPoint;
		this.takeTime = TimeKit.nowTimeMills();
		this.bossId = bossId;
		this.status = STATUS_FREE;
	}

	public Slave() {

	}

	/* properties */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFightPoint() {
		return fightPoint;
	}

	public void setFightPoint(int fightPoint) {
		this.fightPoint = fightPoint;
	}

	public long getStartWorkTime() {
		return startWorkTime;
	}

	public void setStartWorkTime(long lastWorkTime) {
		this.startWorkTime = lastWorkTime;
	}

	public long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(long takeTime) {
		this.takeTime = takeTime;
	}

	public boolean isManaged() {
		return isManaged;
	}

	public void setManaged(boolean isManaged) {
		this.isManaged = isManaged;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
	}

	/* init start */

	/* methods */
	/** ���л� ��ǰ̨ͨ�� д */
	public void BytesWrite(ByteBuffer data) {
		data.writeInt(userId);
		data.writeUTF(name);
		data.writeInt(status);
		data.writeInt(fightPoint);
		data.writeLong(getHowLongForWork());
		data.writeBoolean(isManaged);
	}

	/** ���л� ��DCͨ�� �� */
	public void dbBytesWrite(ByteBuffer data) {
		data.writeInt(userId);
		data.writeUTF(name);
		data.writeInt(status);
		data.writeInt(fightPoint);
		data.writeLong(startWorkTime);
		data.writeLong(takeTime);
		data.writeBoolean(isManaged);
	}

	/** ���л� ��DCͨ�� ȡ */
	public void dbBytesRead(ByteBuffer data) {
		userId = data.readInt();
		name = data.readUTF();
		status = data.readInt();
		fightPoint = data.readInt();
		startWorkTime = data.readLong();
		takeTime = data.readLong();
		isManaged = data.readBoolean();
	}

	/**
	 * ��д�ȽϷ���(����ս������)
	 */
	public int compareTo(Slave slave) {
		int i = this.fightPoint;
		int j = slave.getFightPoint();
		return ((i == j) ? 0 : (i > j) ? -1 : 1);
	}

	/**
	 * ������ʼ��
	 */
	public void changeHandInit(int bossId) {
		this.status = STATUS_FREE;
		this.startWorkTime = 0;
		this.takeTime = TimeKit.nowTimeMills();
		this.isManaged = false;
		this.bossId = bossId;
	}

	/**
	 * ���½�������
	 */
	public void workEndHandle() {
		setStatus(STATUS_FREE);
		setStartWorkTime(0);
		setManaged(false);
	}

	/**
	 * ���¿�ʼ����
	 */
	public void workStartHandle() {
		setStatus(STATUS_WORK);
		setStartWorkTime(TimeKit.nowTimeMills());
	}

	/**
	 * ��ȡ�������ʱ��
	 * 
	 * @return
	 */
	public long getHowLongForWork() {
		long surplusTime = 0;
		if (startWorkTime > 0) {
			surplusTime = (startWorkTime + TimeKit.MIN_MILLS
					* GameCFG.getWorkTime())
					- TimeKit.nowTimeMills();
			if (surplusTime < 0) {
				surplusTime = 0;
			}
		}
		return surplusTime;
	}

	/**
	 * ���������¼�����
	 * 
	 * @param ds
	 *            �ڴ����ݶ���
	 * @param dao
	 *            ���ݿ����ݶ���
	 */
	public void beFreeHandle(DataServer ds, SlaveAndWarDao dao) {
		Player slavePlayer;
		Session session = ds.getSession(userId);
		if (session != null) {
			slavePlayer = (Player) session.getSource();
		} else {
			slavePlayer = dao.getPlayer(userId);
		}
		slavePlayer.becomeFreeMan();
		if (status == STATUS_WORK) // ����״̬��
		{
			competRemove(ds, dao, slavePlayer);
		}
		if (session == null)
			dao.savePlayerVar(slavePlayer);

	}

	/**
	 * ǿ���Ƴ�ū���¼�����
	 * 
	 * @param ds
	 *            �ڴ����ݶ���
	 * @param dao
	 *            ���ݿ����
	 * @param slavePlayer
	 *            ū�����
	 */
	public void competRemove(DataServer ds, SlaveAndWarDao dao,
			Player slavePlayer) {
		Player ownerPlayer;
		int money = (int) ((TimeKit.nowTimeMills() - startWorkTime) / 60000 * fightPoint);
		Session session = ds.getSession(bossId);
		if (session != null) {
			ownerPlayer = (Player) session.getSource();
		} else {
			ownerPlayer = dao.getPlayer(bossId);
		}
		ownerPlayer.getIdentity().cutSlave(userId);
		ownerPlayer.incrMoney(money);
		Information.CreatandSave(ownerPlayer.getIdentity(), slavePlayer
				.getNickname(), "", Information.TYPE_WORKDONE,
				Information.EVENT_SUCCESS, money);
		Information.CreatandSave(slavePlayer.getIdentity(), ownerPlayer
				.getNickname(), "", Information.TYPE_WORKDONE,
				Information.EVENT_SUCCESS, money);
		SlaveManager.getInstance().slavePool.remove(this);
		this.workEndHandle();
		if (session == null)
			dao.savePlayerVar(ownerPlayer);
	}
}
