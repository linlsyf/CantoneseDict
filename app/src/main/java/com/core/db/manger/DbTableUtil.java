package com.core.db.manger;

import com.easysoft.utils.lib.string.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 创建者：zw
 * 修改时间：2015-2-28 下午4:05:30
 * 作用：用于管理创建数据库表
 */
public class DbTableUtil {
	
	static String TAG = "";
	
	/**
	 * 创建者：zw<br>
	 * 时间：2015-4-23 下午4:30:05<br>
	 * 注释：通过实例的类获取表名,支持生成动态表名<br>
	 * @param cls
	 * @param extStr 根据需求，有些表名需要动态创建，并需要类名+动态名称 ，比如Chat+useid的MD5值
	 * @return 表名
	 */
	public static <T> String getTableName(Class<T> cls,String... extStr){
		
		String tablename = cls.getSimpleName();
		if(extStr!=null&&extStr.length==1&&!StringUtils.isBlank(extStr[0])){
			//例如： Chat_userid的MD5值
			String ext = MD5StringUtil.stringToMD5(extStr[0]);
			tablename = tablename + "_"+ext;
		}
		return tablename;
	}
	
	/**
	 * 创建者：zw<br>
	 * 时间：2015-4-27 下午6:21:45<br>
	 * 注释：通过实例类型，动态的创建数据库表。检查并判断是否需要创建表，如果没有该表就创建，否则不创建。<br>
	 * @param cls
	 * @param   是否需要让主键id自增
	 * @param extStr
	 */
	public static <T> boolean createTable(Class<T> cls,String primaryKey,boolean isPrimaryKeyAutoInCrement,boolean isPrimaryKeyUniqueOnConflictReplace,String... extStr){
		String tableName = getTableName(cls,extStr);
		if(DbUtil.mDbAdapter!=null){
			if(!isTableExist(tableName)){
				String sqlStr = getCreateSqliteTableString(cls,primaryKey,isPrimaryKeyAutoInCrement,isPrimaryKeyUniqueOnConflictReplace,tableName);
				DebugUtil.setLog(TAG, sqlStr);
				DbAdapter.CreateTable(tableName,sqlStr);
			}else{
				return false;
			}
		}else{
			DebugUtil.setErrorLog(TAG, "mDbAdapter == null");
			return false;
		}
		return true;
	}
	/**
	 * 创建者：zw
	 * 时间：2015-6-15 下午7:07:24
	 * 注释：判断数据表是否存在
	 * @param tableName
	 * @return
	 */
	public static boolean isTableExist(String tableName){
		if(!StringUtils.isBlank(tableName)&&DbUtil.mDbAdapter!=null){
			return DbUtil.mDbAdapter.tabIsExist(tableName);
		}else{
			return false;
		}
	}
	/**
	 * 创建者：zw<br>
	 * 时间：2015-3-31 下午4:10:48<br>
	 * 注释：根据实例的成员变量，设置每个数据库表的字段名称<br>
	 * @param cls 类型<br>
	 * @return <br>
	 */
	public static <T> String[] getTableFields(Class<T> cls){
		Field[] fields = cls.getDeclaredFields();
		String[] fieldsArray = new String[fields.length];
		for (int i = 0;i<fields.length;i++) {  
			fieldsArray[i] = fields[i].getName();  
		}
		return fieldsArray;
	}

	/**
	 * 创建者：zw<br>
	 * 时间：2015-4-28 上午9:25:50<br>
	 * 注释：获取创建表的SQL语句，用于创建数据库表<br>
	 * @param cls
	 * @param primaryKey
	 * @param isPrimaryKeyAutoInCrement
	 * @param extStr
	 * @return
	 */
	private static <T> String getCreateSqliteTableString(Class<T> cls,String primaryKey,boolean isPrimaryKeyAutoInCrement,boolean isPrimaryKeyUniqueOnConflictReplace,String tablename){
		String result = "";
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE "+ tablename+" ");
		sb.append("(");
		Field[] fields = cls.getDeclaredFields();//getDeclaredFields()只能获取自己声明的各种字段，包括public，protected，private
		
		for (int i = 0;i<fields.length;i++) {
			
			Field field = fields[i];
			Class<?> fieldClazz = field.getType(); // 得到field的class及类型全路径  
		    
			if(fieldClazz.isAssignableFrom(String.class)){
				appendStringOrIntger(" TEXT",cls,sb,field,primaryKey,isPrimaryKeyAutoInCrement,isPrimaryKeyUniqueOnConflictReplace);
				continue;
			}else if(fieldClazz.isAssignableFrom(Integer.class)||fieldClazz.isAssignableFrom(int.class)){
		    	appendStringOrIntger(" INTEGER",cls,sb,field,primaryKey,isPrimaryKeyAutoInCrement,isPrimaryKeyUniqueOnConflictReplace);
		    	continue;
		    }else if(fieldClazz.isAssignableFrom(float.class)){
		    	sb.append(field.getName()+" REAL,");//REAL（浮点数字）
		    	continue;
		    }else if(fieldClazz.isAssignableFrom(byte[].class)){
		    	sb.append(field.getName()+" BLOB,");//BLOB(二进制对象)数据类型
		    	continue;
		    }
		}
		
		if(sb.toString().endsWith(","))
		{
			result = sb.toString().substring(0, sb.toString().length()-1);//去除尾巴多余的逗号","。
		}
		result = result+");";
		return result;
	}
	
	private static <T> void appendStringOrIntger(String type,Class<T> cls,StringBuffer sb,Field field,String primaryKey,
											boolean isPrimaryKeyAutoInCrement,boolean isPrimaryKeyUniqueOnConflictReplace)
	{
		String name =  field.getName();
		sb.append(name);
		try {
			Object f_Value = null;
			if(type.equals(" TEXT")){
				f_Value = (String)invokeGet(cls.newInstance(),name);
			}else if(type.equals(" INTEGER")){
				f_Value = (Integer)invokeGet(cls.newInstance(),name);
			}else if(type.equals(" LONG")){
				f_Value = (Long)invokeGet(cls.newInstance(),name);
			}
			
			if(f_Value!=null){
				if(f_Value.equals("")){
					sb.append(type+" NOT NULL");//不能存储为值
				}else{
					if(!isPrimaryKeyAutoInCrement){
						sb.append(type+" DEFAULT "+f_Value);//设置默认值存储在数据库表中
					}else{
						sb.append(type);//integer 自增长的时候，不设置默认值
					}
				}
			}else{
				sb.append(type);//允许存储空值
			}
			
			if(name.equals(primaryKey)){
				sb.append(" PRIMARY KEY");
				
	    		if(isPrimaryKeyAutoInCrement){
	    			sb.append(" AUTOINCREMENT");//主键值自增长
	    		}else if(isPrimaryKeyUniqueOnConflictReplace){
	    			sb.append(" UNIQUE ON CONFLICT REPLACE");//防止重复记录
	    		}
	    	}
			sb.append(",");//","结尾
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	 /**
     * java反射bean的get方法
     * @param objectClass
     * @param fieldName
     * @return
     */
	private static Method getGetMethod(Class<? extends Object> objectClass, String fieldName) {
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return objectClass.getMethod(sb.toString());
        } catch (Exception e) {
        }
        return null;
    }
    
    /**
     * 执行get方法
     * 
     * @param o 执行对象
     * @param fieldName 属性
     */
    private static Object invokeGet(Object o, String fieldName) {
    	
        Method method = getGetMethod(o.getClass(), fieldName);
        try {
            return method.invoke(o, new Object[0]);
        } catch (Exception e) {
        	DebugUtil.setLog(TAG, "invokeGet "+fieldName +"不存在get函数");
        }
        return null;
    }
	
}
