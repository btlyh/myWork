package com.cambrian.dfhm.theater;

import java.util.ArrayList;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.SampleFactory;

/**
 * ��˵����ս��
 * @author��Sebastian
 * 
 */
public class Theater extends Sample{

	/* static fields */
	/** �������� */
	public static SampleFactory factory = new SampleFactory();

	/* static methods */

	/* fields */
	/** ���� */
	private String name;
	/** ���� */
	private String description;
	/** ��ʼ�ؿ�id */
	private int beginStage;
	/** �����ؿ�id */
	private int endStage;
	/** �ؿ� */
	ArrayList<Stage> stageList = new ArrayList<Stage>();

	/* constructors */

	/* properties */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getBeginStage() {
		return beginStage;
	}
	public void setBeginStage(int beginStage) {
		this.beginStage = beginStage;
	}
	public int getEndStage() {
		return endStage;
	}
	public void setEndStage(int endStage) {
		this.endStage = endStage;
	}

	/* init start */
	public void init()
	{
		System.err.println("beginStage ===" + beginStage);
		System.err.println("endStage ===" + endStage);
		Stage stage;
		for (int i = beginStage; i < endStage + 1; i++) {
			stage = (Stage)Sample.factory.getSample(i);		
			stageList.add(stage);
		}
	}

	/* methods */
	public int getStageSize()
	{
		return stageList.size();
	}
}