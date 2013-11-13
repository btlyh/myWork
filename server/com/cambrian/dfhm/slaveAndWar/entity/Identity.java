package com.cambrian.dfhm.slaveAndWar.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cambrian.common.net.ByteBuffer;
import com.cambrian.common.util.MathKit;
import com.cambrian.dfhm.Lang;
import com.cambrian.dfhm.back.GameCFG;

/**
 * ��˵������ݶ���-���ڵ�������
 * 
 * @author��LazyBear
 */
public class Identity {

	/* static fields */
	/** ���״̬ 1ū����2�����ˣ�3���� */
	public static final int SLAVE = 1, FREEMAN = 2, OWNER = 3;
	/* static methods */

	/* fields */
	/** ���� */
	private int grade;
	/** �������� */
	private String gradeName;
	/** �����б� */
	private List<Slave> slaveList = new ArrayList<Slave>();
	/** ����ID(������Ϊ0��ʾ����ū��) */
	private int ownerId;
	/** ���´��� */
	private int workTimes;
	/** ��Ϣ�б� */
	private List<Information> informations = new ArrayList<Information>();
	/** ���չ������� */
	private int attTimes;
	/** ���շ������� */
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
	 * ���ӹ�������
	 */
	public void inAttTimes() {
		attTimes++;
	}

	/**
	 * ���ӷ�������
	 */
	public void inReactTimes() {
		reactTimes++;
	}

	/* init start */

	/* methods */
	/**
	 * ���ݼ�������������
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

	/** ���л� ��DCͨ�� �� */
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

	/** ���л� ��DCͨ�� ȡ */
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
	 * ����ū�� ��ݷ����仯
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
	 * ����ū�� ��ݷ����仯
	 * 
	 * @param slaveId
	 *            ū��ID
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
	 * ����ū�� ��ݷ����仯
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
	 * �����Ϣ����
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
