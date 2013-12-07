package com.cambrian.dfhm.common.entity;

import com.cambrian.common.net.ByteBuffer;

/**用户成就系统**/
public class Achievements
{
	
	private boolean  token;//军令消耗成就是否达成
	private boolean  buleCard;//蓝色卡牌获得是否达成
	private boolean  cardLevelThirty;//卡牌三十级
	private boolean  passWoSong;//通关武松 开启当壕
	private boolean   passLuZhiShen;//通关鲁智深开启排位赛
	private boolean   passZhangJiao;//通关张角
	private boolean   fristPassCross;//第一次穿越副本
	private boolean   passZhangManCheng;//通关张曼成
	private boolean   passHuangJinQianShao;//通关黄巾前哨
	
	

	public boolean isToken() {
		return token;
	}


	public void setToken(boolean token) {
		this.token = token;
	}


	public boolean isBuleCard() {
		return buleCard;
	}


	public void setBuleCard(boolean buleCard) {
		this.buleCard = buleCard;
	}


	public boolean isCardLevelThirty() {
		return cardLevelThirty;
	}


	public void setCardLevelThirty(boolean cardLevelThirty) {
		this.cardLevelThirty = cardLevelThirty;
	}


	public boolean isPassWoSong() {
		return passWoSong;
	}


	public void setPassWoSong(boolean passWoSong) {
		this.passWoSong = passWoSong;
	}


	public boolean isPassLuZhiShen() {
		return passLuZhiShen;
	}


	public void setPassLuZhiShen(boolean passLuZhiShen) {
		this.passLuZhiShen = passLuZhiShen;
	}


	public boolean isPassZhangJiao() {
		return passZhangJiao;
	}


	public void setPassZhangJiao(boolean passZhangJiao) {
		this.passZhangJiao = passZhangJiao;
	}


	public boolean isFristPassCross() {
		return fristPassCross;
	}


	public void setFristPassCross(boolean fristPassCross) {
		this.fristPassCross = fristPassCross;
	}


	public boolean isPassZhangManCheng() {
		return passZhangManCheng;
	}


	public void setPassZhangManCheng(boolean passZhangManCheng) {
		this.passZhangManCheng = passZhangManCheng;
	}


	public boolean isPassHuangJinQianShao() {
		return passHuangJinQianShao;
	}


	public void setPassHuangJinQianShao(boolean passHuangJinQianShao) {
		this.passHuangJinQianShao = passHuangJinQianShao;
	}


	/** 序列化 和前台通信 */
	public void bytesWrite(ByteBuffer data)
	{
		data.writeBoolean(token);
		data.writeBoolean(buleCard);
		data.writeBoolean(cardLevelThirty);
		data.writeBoolean(passWoSong);
		data.writeBoolean(passLuZhiShen);
		data.writeBoolean(passZhangJiao);
		data.writeBoolean(fristPassCross);
		data.writeBoolean(passZhangManCheng);
		data.writeBoolean(passHuangJinQianShao);
	}
	
	
	/** 序列化 和DC通信 存 */
	public void dbBytesWrite(ByteBuffer data)
	{
		data.writeBoolean(token);
		data.writeBoolean(buleCard);
		data.writeBoolean(cardLevelThirty);
		data.writeBoolean(passWoSong);
		data.writeBoolean(passLuZhiShen);
		data.writeBoolean(passZhangJiao);
		data.writeBoolean(fristPassCross);
		data.writeBoolean(passZhangManCheng);
		data.writeBoolean(passHuangJinQianShao);
	}
	
	
	/** 序列化 和DC通信 取 */
	public void dbBytesRead(ByteBuffer data)
	{
		token = data.readBoolean();
		buleCard = data.readBoolean();
		cardLevelThirty = data.readBoolean();
		passWoSong = data.readBoolean();
		passLuZhiShen = data.readBoolean();
		passZhangJiao = data.readBoolean();
		fristPassCross = data.readBoolean();
		passZhangManCheng = data.readBoolean();
		passHuangJinQianShao = data.readBoolean();
	}
}
