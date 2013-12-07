package com.cambrian.dfhm.common.entity;

import com.cambrian.common.net.ByteBuffer;

/**�û��ɾ�ϵͳ**/
public class Achievements
{
	
	private boolean  token;//�������ĳɾ��Ƿ���
	private boolean  buleCard;//��ɫ���ƻ���Ƿ���
	private boolean  cardLevelThirty;//������ʮ��
	private boolean  passWoSong;//ͨ������ ��������
	private boolean   passLuZhiShen;//ͨ��³�������λ��
	private boolean   passZhangJiao;//ͨ���Ž�
	private boolean   fristPassCross;//��һ�δ�Խ����
	private boolean   passZhangManCheng;//ͨ��������
	private boolean   passHuangJinQianShao;//ͨ�ػƽ�ǰ��
	
	

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


	/** ���л� ��ǰ̨ͨ�� */
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
	
	
	/** ���л� ��DCͨ�� �� */
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
	
	
	/** ���л� ��DCͨ�� ȡ */
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
