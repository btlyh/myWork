package com.cambrian.dfhm.message;

import com.cambrian.common.field.ByteArrayField;
import com.cambrian.common.field.Field;
import com.cambrian.common.field.FieldKit;
import com.cambrian.common.field.Fields;
import com.cambrian.common.field.IntField;
import com.cambrian.common.field.LongField;
import com.cambrian.common.log.Logger;
import com.cambrian.common.net.DataAccessException;
import com.cambrian.common.sql.ConnectionManager;
import com.cambrian.common.sql.DBKit;
import com.cambrian.common.sql.SqlKit;
import com.cambrian.dfhm.common.entity.Player;

/**
 * 类说明：消息存取处理器,暂时放置DS层，后面把这个放置数据存取层,再考虑下把这个独立处理
 * 
 * @version 1.0
 * @date 2013-6-9
 * @author maxw<woldits@qq.com>
 */
public class MessageSqlAccess
{

	/* static fields */
	/** 日志记录 */
	private static final Logger log=Logger.getLogger(MessageSqlAccess.class);

	/* static methods */

	/* fields */
	/** 表 */
	String table;
	/** 连接管理器 */
	ConnectionManager cm;
	/** 消息序列号  */
	long uuid;

	/* constructors */

	/* properties */
	/** 设置表名 */
	public void setTable(String table)
	{
		this.table=table;
	}
	/** 设置数据库连接管理器  */
	public void setConnectionManager(ConnectionManager cm)
	{
		this.cm=cm;
	}
	/* init start */
	/** 初始化 */
	public void init()
	{
		uuid=getMaxSerialNumber();
	}

	/* methods */
	/** 获取消息序列号 */
	public long getUUid()
	{
		return ++uuid;
	}

	/** 映射成域对象 */
	public Fields mapping(Message message)
	{
		Field[] array=new Field[7];
		array[0]=FieldKit.create("uuid",message.uuid);
		array[1]=FieldKit.create("type",message.type);
		array[2]=FieldKit.create("status",message.status);
		array[3]=FieldKit.create("scr",message.scr);
		array[4]=FieldKit.create("dest",message.dest);
		array[5]=FieldKit.create("pid",message.pid);
		array[6]=FieldKit.create("value",message.getDBObjs());
		return new Fields(array);
	}

	/** 获得最大的序列号 */
	private long getMaxSerialNumber()
	{
		String sql="select max(uuid) from "+table;
		Fields fields=SqlKit.query(cm,sql);
		return ((LongField)fields.getFields()[0]).value;
	}

	/** 加载拍卖所数据 */
	public void load(Player player)
	{
		if(log.isDebugEnabled())
			log.debug("load ,message userid="+player.getUserId());

		String sql="select * from "+table+" where dest="+player.getUserId();
		Fields[] fields=SqlKit.querys(cm,sql);

		if(fields==null) return;
		Message[] messages=new Message[fields.length];
		for(int i=0;i<messages.length;i++)
		{
			messages[i]=new Message();
			messages[i].uuid=((LongField)fields[i].get("uuid")).value;
			messages[i].type=((IntField)fields[i].get("type")).value;
			messages[i].status=((IntField)fields[i].get("status")).value;
			messages[i].scr=((LongField)fields[i].get("scr")).value;
			messages[i].dest=((LongField)fields[i].get("dest")).value;
			messages[i].pid=((IntField)fields[i].get("pid")).value;
			messages[i]
				.setDBObjs(((ByteArrayField)fields[i].get("value")).value);
		}
		player.message.init(messages);
		if(log.isInfoEnabled())
			log.info("load message ok  ,size="+messages.length);
	}

	/** 保存交易单 */
	public void save(Message message)
	{
		if(log.isDebugEnabled()) log.debug("save, message="+message);
		int t=DBKit.set(table,cm,FieldKit.create("uuid",message.uuid),
			mapping(message));
		if(t==DBKit.EXCEPTION)
			throw new DataAccessException(
				DataAccessException.SERVER_INTERNAL_ERROR,"");
		if(log.isInfoEnabled()) log.info("add ok, message="+message);
	}

	/* common methods */

	/* inner class */
}
