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
public class Identity
{

	/* static fields */
	/** ���״̬ 1ū����2�����ˣ�3���� */
	public static final int SLAVE=0x1,FREEMAN=0x2,OWNER=0x3;
	/** ������������ 1���� 2���� 3���� 4������ 5����ͨ�� 6���� 7���� */
	public static final int TYPE_ATT=0x1,TYPE_REACT=0x2,TYPE_HELP=0x3,
					TYPE_SAVE=0x4,TYPE_SAVENOR=0x5,TYPE_WORK=0x6,
					TYPE_RANSOM=0x7;
	/* static methods */

	/* fields */
	/** ���� */
	private int grade=FREEMAN;
	/** �������� */
	private String gradeName=Lang.FREEMAN;
	/** �����б� */
	private List<Slave> slaveList=new ArrayList<Slave>();
	/** ����ID(������Ϊ0��ʾ����ū��) */
	private int ownerId;
	/** ��Ϣ�б� */
	private List<Information> informations=new ArrayList<Information>();
	/** ���չ������� */
	private int attTimes;
	/** ���շ������� */
	private int reactTimes;
	/** ������������ */
	private int helpTimes;
	/** �������Ⱥ��Ѵ��� */
	private int saveTimes;
	/** ��������İ���˴��� */
	private int saveNorTimes;
	/** ���հ��´��� */
	private int workTimes;
	/** ����������� */
	private int ransomTimes;

	/** ���չ��򹥻����� */
	private int buyAttTimes;
	/** ���չ��򷴿����� */
	private int buyReactTimes;
	/** ���չ����������� */
	private int buyHelpTimes;
	/** ���չ������Ⱥ��Ѵ��� */
	private int buySaveTimes;
	/** ���չ�������İ���˴��� */
	private int buySaveNorTimes;
	/** ���չ�����´��� */
	private int buyWorkTimes;
	/** ���չ���������� */
	private int buyRansomTimes;
	/** ���չ�����������б� */
	private List<Integer> helpList=new ArrayList<Integer>();

	/* constructors */

	/* properties */
	public int getGrade()
	{
		return grade;
	}

	public void setGrade(int grade)
	{
		this.grade=grade;
		gradeNameChange();
	}

	public String getGradeName()
	{
		return gradeName;
	}

	public List<Slave> getSlaveList()
	{
		return slaveList;
	}

	public void setSlaveList(List<Slave> slaveList)
	{
		this.slaveList=slaveList;
	}

	public int getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(int ownerId)
	{
		this.ownerId=ownerId;
	}

	public int getWorkTimes()
	{
		return workTimes;
	}

	public void setWorkTimes(int workTimes)
	{
		this.workTimes=workTimes;
	}

	public List<Information> getInformations()
	{
		return informations;
	}

	public void setInformations(List<Information> informations)
	{
		this.informations=informations;
	}

	public int getAttTimes()
	{
		return attTimes;
	}

	public void setAttTimes(int attTime)
	{
		this.attTimes=attTime;
	}

	public int getReactTimes()
	{
		return reactTimes;
	}

	public void setReactTimes(int reactTimes)
	{
		this.reactTimes=reactTimes;
	}

	public List<Integer> getHelpList()
	{
		return helpList;
	}

	public void setHelpList(List<Integer> helpList)
	{
		this.helpList=helpList;
	}

	public int getHelpTimes()
	{
		return helpTimes;
	}

	public void setHelpTimes(int helpTimes)
	{
		this.helpTimes=helpTimes;
	}

	public int getSaveTimes()
	{
		return saveTimes;
	}

	public void setSaveTimes(int saveTimes)
	{
		this.saveTimes=saveTimes;
	}

	public int getSaveNorTimes()
	{
		return saveNorTimes;
	}

	public void setSaveNorTimes(int saveNorTimes)
	{
		this.saveNorTimes=saveNorTimes;
	}
	public int getRansomTimes()
	{
		return ransomTimes;
	}

	public void setRansomTimes(int ransomTimes)
	{
		this.ransomTimes=ransomTimes;
	}

	public int getBuyAttTimes()
	{
		return buyAttTimes;
	}

	public void setBuyAttTimes(int buyAttTimes)
	{
		this.buyAttTimes=buyAttTimes;
	}

	public int getBuyReactTimes()
	{
		return buyReactTimes;
	}

	public void setBuyReactTimes(int buyReactTimes)
	{
		this.buyReactTimes=buyReactTimes;
	}

	public int getBuyHelpTimes()
	{
		return buyHelpTimes;
	}

	public void setBuyHelpTimes(int buyHelpTimes)
	{
		this.buyHelpTimes=buyHelpTimes;
	}

	public int getBuySaveTimes()
	{
		return buySaveTimes;
	}

	public void setBuySaveTimes(int buySaveTimes)
	{
		this.buySaveTimes=buySaveTimes;
	}

	public int getBuySaveNorTimes()
	{
		return buySaveNorTimes;
	}

	public void setBuySaveNorTimes(int buySaveNorTimes)
	{
		this.buySaveNorTimes=buySaveNorTimes;
	}

	public int getBuyWorkTimes()
	{
		return buyWorkTimes;
	}

	public void setBuyWorkTimes(int buyWorkTimes)
	{
		this.buyWorkTimes=buyWorkTimes;
	}

	public int getBuyRansomTimes()
	{
		return buyRansomTimes;
	}

	public void setBuyRansomTimes(int buyRansomTimes)
	{
		this.buyRansomTimes=buyRansomTimes;
	}

	/**
	 * ���ӹ�������
	 */
	public void inAttTimes()
	{
		attTimes++;
	}

	/**
	 * ���ӷ�������
	 */
	public void inReactTimes()
	{
		reactTimes++;
	}

	/**
	 * ������ȴ���
	 */
	public void inHelpTimes()
	{
		helpTimes++;
	}

	/**
	 * �������ȴ���
	 */
	public void inSaveTimes()
	{
		saveTimes++;
	}

	/**
	 * ��������İ���˴���
	 */
	public void inSaveNorTimes()
	{
		saveNorTimes++;
	}
	/**
	 * ���Ӱ��´���
	 */
	public void inWorkTimes()
	{
		workTimes++;
	}
	
	/**
	 * �����������
	 */
	public void inRansomTimes()
	{
		ransomTimes++;
	}
	
	/**
	 * ���ٹ�������
	 */
	public void deAttTimes()
	{
		attTimes--;
	}

	/**
	 * ���ٷ�������
	 */
	public void deReactTimes()
	{
		reactTimes--;
	}

	/**
	 * ������ȴ���
	 */
	public void deHelpTimes()
	{
		helpTimes--;
	}

	/**
	 * �������ȴ���
	 */
	public void deSaveTimes()
	{
		saveTimes--;
	}

	/**
	 * ��������İ���˴���
	 */
	public void deSaveNorTimes()
	{
		saveNorTimes--;
	}
	/**
	 * ���ٰ��´���
	 */
	public void deWorkTimes()
	{
		workTimes--;
	}
	
	/**
	 * �����������
	 */
	public void deRansomTimes()
	{
		ransomTimes--;
	}
	
	/**
	 * ���ӹ��򹥻�����
	 */
	public void inBuyAttTimes()
	{
		buyAttTimes++;
	}

	/**
	 * ���ӹ��򷴿�����
	 */
	public void inBuyReactTimes()
	{
		buyReactTimes++;
	}

	/**
	 * ���ӹ�����ȴ���
	 */
	public void inBuyHelpTimes()
	{
		buyHelpTimes++;
	}

	/**
	 * ���ӹ������ȴ���
	 */
	public void inBuySaveTimes()
	{
		buySaveTimes++;
	}

	/**
	 * ���ӹ�������İ���˴���
	 */
	public void inBuySaveNorTimes()
	{
		buySaveNorTimes++;
	}
	/**
	 * ���ӹ�����´���
	 */
	public void inBuyWorkTimes()
	{
		buyWorkTimes++;
	}
	
	/**
	 * ���ӹ����������
	 */
	public void inBuyRansomTimes()
	{
		buyRansomTimes++;
	}

	/* init start */

	/* methods */
	/**
	 * ���ݼ�������������
	 */
	private void gradeNameChange()
	{
		switch(grade)
		{
			case SLAVE:
				gradeName=Lang.SLAVE;
				break;
			case FREEMAN:
				gradeName=Lang.FREEMAN;
				break;
			case OWNER:
				gradeName=Lang.OWNER;
				break;
			default:
				gradeName="error";
				break;
		}
	}

	/**
	 * ǰ̨���л� д
	 * 
	 * @param data
	 */
	public void BytesWrite(ByteBuffer data)
	{
		data.writeInt(grade);
		data.writeUTF(gradeName);
		data.writeInt(slaveList.size());
		{
			for(Slave slave:slaveList)
				slave.BytesWrite(data);
		}
		data.writeInt(ownerId);
		data.writeInt(GameCFG.getWorkConfine()-workTimes);
		data.writeInt(GameCFG.getAttConfine()-attTimes);
		data.writeInt(GameCFG.getReactConfine()-reactTimes);
		data.writeInt(helpList.size());
		for(Integer integer:helpList)
		{
			data.writeInt(integer);
		}
		data.writeInt(GameCFG.getSaveConfine()-helpTimes);
		data.writeInt(GameCFG.getSaveNorConfine()-saveNorTimes);
		data.writeInt(GameCFG.getSaveConfine()-saveTimes);
		//data.writeInt(GameCFG.getRansomConfine()-ransomTimes);

		data.writeInt(buyWorkTimes);
		data.writeInt(buyAttTimes);
		data.writeInt(buyReactTimes);
		data.writeInt(buyHelpTimes);
		data.writeInt(buySaveNorTimes);
		data.writeInt(buySaveTimes);
		data.writeInt(buyRansomTimes);
	}

	/** ���л� ��DCͨ�� �� */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeInt(grade);
		data.writeUTF(gradeName);
		data.writeInt(slaveList.size());
		{
			for(Slave slave:slaveList)
				slave.dbBytesWrite(data);
		}
		data.writeInt(ownerId);
		data.writeInt(workTimes);
		data.writeInt(informations.size());
		for(Information information:informations)
		{
			information.dbBytesWrite(data);
		}
		data.writeInt(attTimes);
		data.writeInt(reactTimes);
		data.writeInt(helpList.size());
		for(Integer integer:helpList)
		{
			data.writeInt(integer);
		}
		data.writeInt(helpTimes);
		data.writeInt(saveNorTimes);
		data.writeInt(saveTimes);
		data.writeInt(ransomTimes);

		data.writeInt(buyWorkTimes);
		data.writeInt(buyAttTimes);
		data.writeInt(buyReactTimes);
		data.writeInt(buyHelpTimes);
		data.writeInt(buySaveNorTimes);
		data.writeInt(buySaveTimes);
		data.writeInt(buyRansomTimes);
	}

	/** ���л� ��DCͨ�� ȡ */
	public void dbBytesRead(ByteBuffer data)
	{
		grade=data.readInt();
		gradeName=data.readUTF();
		int len=data.readInt();
		for(int i=0;i<len;i++)
		{
			Slave slave=new Slave();
			slave.dbBytesRead(data);
			slaveList.add(slave);
		}
		ownerId=data.readInt();
		workTimes=data.readInt();
		len=data.readInt();
		for(int i=0;i<len;i++)
		{
			Information information=new Information();
			information.dbBytesRead(data);
			informations.add(information);
		}
		attTimes=data.readInt();
		reactTimes=data.readInt();
		len=data.readInt();
		for(int i=0;i<len;i++)
		{
			helpList.add(data.readInt());
		}
		helpTimes=data.readInt();
		saveNorTimes=data.readInt();
		saveTimes=data.readInt();
		ransomTimes=data.readInt();

		buyWorkTimes=data.readInt();
		buyAttTimes=data.readInt();
		buyReactTimes=data.readInt();
		buyHelpTimes=data.readInt();
		buySaveNorTimes=data.readInt();
		buySaveTimes=data.readInt();
		buyRansomTimes=data.readInt();

	}

	/**
	 * ��ȡū������
	 * 
	 * @param slaveId ū��ID
	 */
	public Slave getSlave(int slaveId)
	{
		Slave slave=null;
		for(Slave slave_:slaveList)
		{
			if(slave_.getUserId()==slaveId)
			{
				slave=slave_;
				break;
			}
		}
		return slave;
	}

	/**
	 * ����ū�� ��ݷ����仯
	 * 
	 * @param slave
	 */
	public void addSlave(Slave slave,int bossId)
	{
		if(slaveList.size()==0)
		{
			setGrade(OWNER);
		}
		if(slaveList.size()==GameCFG.getSlaveConfine())
		{
			Collections.sort(slaveList);
			slaveList.remove((slaveList.size()-1));
		}
		slave.changeHandInit(bossId);
		slaveList.add(slave);
	}

	/**
	 * ����ū�� ��ݷ����仯
	 * 
	 * @param slaveId ū��ID
	 */
	public Slave cutSlave(int slaveId)
	{
		Slave slave=null;
		for(int i=0;i<slaveList.size();i++)
		{
			if(slaveList.get(i).getUserId()==slaveId)
			{
				slave=slaveList.get(i);
				slaveList.remove(i);
				break;
			}
		}
		if(slaveList.size()==0)
		{
			setGrade(FREEMAN);
		}
		return slave;
	}

	/**
	 * ����ū�� ��ݷ����仯
	 */
	public Slave cutSlave()
	{
		int index=MathKit.randomValue(0,(slaveList.size()-1));
		Slave slave=slaveList.get(index);
		slaveList.remove(index);
		if(slaveList.size()==0)
		{
			setGrade(FREEMAN);
		}
		return slave;
	}

	/**
	 * �����Ϣ����
	 * 
	 * @param information
	 */
	public void addInformation(Information information)
	{
		if(informations.size()>=GameCFG.getInformationSize())
		{
			Collections.sort(informations);
			informations.remove((informations.size()-1));
		}
		informations.add(information);
	}
}
