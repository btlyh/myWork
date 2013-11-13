package com.cambrian.dfhm.slaveAndWar.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.back.GameCFG;

/**
 * 类说明：身份对象-用于当壕功能
 * 
 * @author：LazyBear
 */
public class Identity {

	/* static fields */
	/** 身份状态 1奴隶，2自由人，3土豪 */
	public static final int SLAVE = 1, FREEMAN = 2, OWNER = 3;
	/* static methods */

	/* fields */
	/** 级别 */
	private int grade;
	/** 级别名称 */
	private String gradeName;
	/** 马仔列表 */
	private List<Slave> slaveList = new ArrayList<Slave>();
	/** 主人ID(此属性为0表示不是奴隶) */
	private int ownerId;
	/** 办事次数 */
	private int workTimes;
	/** 信息列表 */
	private List<Information> informations = new ArrayList<Information>();
	/** 今日攻击次数 */
	private int attTimes;
	/** 今日反抗次数 */
	private int reactTimes;

	/* constructors */

	/* properties */
	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
		gradeNameChange();
	}

	public String getGradeName() {
		return gradeName;
	}

	public List<Slave> getSlaveList() {
		return slaveList;
	}

	public void setSlaveList(List<Slave> slaveList) {
		this.slaveList = slaveList;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getWorkTimes() {
		return workTimes;
	}

	public void setWorkTimes(int workTimes) {
		this.workTimes = workTimes;
	}

	public List<Information> getInformations() {
		return informations;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}

	public int getAttTimes() {
		return attTimes;
	}

	public void setAttTimes(int attTime) {
		this.attTimes = attTime;
	}

	public int getReactTimes() {
		return reactTimes;
	}

	public void setReactTimes(int reactTimes) {
		this.reactTimes = reactTimes;
	}

	/**
	 * 增加攻击次数
	 */
	public void inAttTimes() {
		attTimes++;
	}

	/**
	 * 增加反抗次数
	 */
	public void inReactTimes() {
		reactTimes++;
	}

	/* init start */

	/* methods */
	/**
	 * 根据级别变更级别名称
	 */
	private void gradeNameChange() {
		switch (grade) {
		case SLAVE:
			gradeName = Lang.SLAVE;
			break;
		case FREEMAN:
			gradeName = Lang.FREEMAN;
		case OWNER:
			gradeName = Lang.OWNER;
		default:
			gradeName = "error";
			break;
		}
	}

	/** 序列化 和DC通信 存 */
	public void dbBytesWrite(ByteBuffer data) {
		data.writeInt(grade);
		data.writeUTF(gradeName);
		data.writeInt(slaveList.size());
		{
			for (Slave slave : slaveList)
				slave.dbBytesWrite(data);
		}
		data.writeInt(ownerId);
		data.writeInt(workTimes);
		data.writeInt(informations.size());
		for (Information information : informations) {
			information.dbBytesWrite(data);
		}
		data.writeInt(attTimes);
		data.writeInt(reactTimes);
	}

	/** 序列化 和DC通信 取 */
	public void dbBytesRead(ByteBuffer data) {
		grade = data.readInt();
		gradeName = data.readUTF();
		int len = data.readInt();
		for (int i = 0; i < len; i++) {
			Slave slave = new Slave();
			slave.dbBytesRead(data);
			slaveList.add(slave);
		}
		ownerId = data.readInt();
		workTimes = data.readInt();
		len = data.readInt();
		for (int i = 0; i < len; i++) {
			Information information = new Information();
			information.dbBytesRead(data);
			informations.add(information);
		}
		attTimes = data.readInt();
		reactTimes = data.readInt();
	}

	/**
	 * 增加奴隶 身份发生变化
	 * 
	 * @param slave
	 */
	public void addSlave(Slave slave) {
		if (slaveList.size() == 0) {
			setGrade(OWNER);
		}
		if (slaveList.size() == GameCFG.getSlaveConfine()) {
			Collections.sort(slaveList);
			slaveList.remove((slaveList.size() - 1));
		}
		slaveList.add(slave);
	}

	/**
	 * 减少奴隶 身份发生变化
	 * 
	 * @param slaveId
	 *            奴隶ID
	 */
	public void cutSlave(int slaveId) {
		for (int i = 0; i < slaveList.size(); i++) {
			if (slaveList.get(i).getUserId() == slaveId) {
				slaveList.remove(i);
			}
		}
		if (slaveList.size() == 0) {
			setGrade(FREEMAN);
		}
	}

	/**
	 * 减少奴隶 身份发生变化
	 */
	public Slave cutSlave() {
		int index = MathKit.randomValue(0, (slaveList.size() - 1));
		Slave slave = slaveList.get(index);
		slaveList.remove(index);
		if (slaveList.size() == 0) {
			setGrade(FREEMAN);
		}
		return slave;
	}

	/**
	 * 添加信息对象
	 * 
	 * @param information
	 */
	public void addInformation(Information information) {
		if (informations.size() >= GameCFG.getInformationSize()) {
			Collections.sort(informations);
			informations.remove((informations.size() - 1));
		}
		informations.add(information);
	}
}
