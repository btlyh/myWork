package com.cambrian.dfhm.theater;

import java.util.ArrayList;
import com.cambrian.common.object.Sample;
import com.cambrian.common.object.SampleFactory;

/**
 * 类说明：战区
 * @author：Sebastian
 * 
 */
public class Theater extends Sample{

	/* static fields */
	/** 样本工厂 */
	public static SampleFactory factory = new SampleFactory();

	/* static methods */

	/* fields */
	/** 名字 */
	private String name;
	/** 介绍 */
	private String description;
	/** 开始关卡id */
	private int beginStage;
	/** 结束关卡id */
	private int endStage;
	/** 关卡 */
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
