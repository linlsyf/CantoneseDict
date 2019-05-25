package com.core.db.manger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.easysoft.utils.lib.string.StringUtils;

/**
 * 创建者：zw<br>
 * 修改时间：2015-2-28 下午4:05:30<br>
 * 注释：该数据库操作类，不允许随意修改。<br>
 */
@SuppressLint("NewApi")
public class DbUtil {
	static String TAG= DbUtil.class.getSimpleName();
	public static DbAdapter mDbAdapter;
	/**
	 * 创建者：zw<br>
	 * 时间：2015-4-8 下午1:43:27<br>
	 * 注释： 初始化app最基本的数据库<br>
	 * @param context<br>
	 */
	public synchronized static void initDB(Context context,String dbName,String userid){
         mDbAdapter = new DbAdapter(context,dbName,userid);
         mDbAdapter.openDb();
	}
	
    /**
     * 创建者：zw<br>
     * 时间：2015-3-30 上午11:46:24<br>
     * 注释：插入数据。根据bean 的getXxx方法获取值 ,首字母大写的获取方式，必须严格规范,否则会报错 <br>
                    例子：<br>
       UserBean tom = new UserBean("tomcat", 19, 2, "china");  <br>
       DbUtil.insertData(Table_User.DATABASE_TABLE, tom);<br>
     * @param tableName 表名 <br>
     * @param obj<br>
     * @return 返回插入数据成功失败<br>
     */
    public synchronized static boolean insertData(String tableName, Object obj)  
    {  
    	if(!DbTableUtil.isTableExist(tableName))
    	{
    		DebugUtil.setErrorLog(TAG, "insertData:"+tableName+" 表不存在！");  
    		return false;
    	}
    	
        Class<?> cls = obj.getClass();  
        Field[] fields = cls.getDeclaredFields();  
        ContentValues cv = getContentValues(obj,fields); 
        long result = DbAdapter.sqliteDatabase.insert(tableName, null, cv);
        
		return result>0;  
    }  
     
    /**
    * 方法1：检查某表列是否存在
    * @param db
    * @param tableName 表名
    * @param columnName 列名
    * @return
    */
    public static boolean checkColumnExist(String tableName, String columnName) {
        boolean result = false ;
        Cursor cursor = null ;
        if(DbAdapter.sqliteDatabase!=null){
        	 try{
                 //查询一行
                 cursor = DbAdapter.sqliteDatabase.rawQuery( "SELECT * FROM " + tableName + " LIMIT 0", null );
                 result = cursor != null && cursor.getColumnIndex(columnName) != -1 ;
             }catch (Exception e){
                  Log.e(TAG,"checkColumnExists..." + e.getMessage()) ;
             }finally{
             	closeCursor(cursor);
             }
        }
        return result ;
    }
    /**
     * 创建者：zw<br>
     * 时间：2015-3-30 下午3:37:33<br>
     * 注释：插入一个map<br>
     * @param tableName<br>
     * @param obj<br>
     * @return 返回插入数据成功失败<br>
     */
    public synchronized static boolean insertMap(String tableName, Map<String, Object> obj)  
    {  
    	if(!DbTableUtil.isTableExist(tableName))
    	{
    		DebugUtil.setErrorLog(TAG, "insertMap:"+tableName+" 表不存在！");  
    		return false;
    	}
    	
        ContentValues cv = new ContentValues();  
        for (String key: obj.keySet()) {  
            cv.put(key, obj.get(key).toString());  
        }  
		return (DbAdapter.sqliteDatabase.insert(tableName, null, cv)>0);
    }  
      
    /** 
     * 根据set方法设置bean对象属性值 <br>
     */  
    private  static <T> T setObjValue(T obj , Cursor cursor) throws Exception  
    {  
        int index;  
        String mname = null;        //方法名  
        Method method = null;       //对应set方法  
        String[] cns = cursor.getColumnNames();  
        for (String cname: cns) {  
            index = cursor.getColumnIndex(cname);  
            mname = setMethodName(cname);  
            int type = cursor.getType(index);  
            switch (type) {  
                case Cursor.FIELD_TYPE_FLOAT:  
                    //获取指定参数类型的方法  
                    method  = obj.getClass().getMethod(mname, float.class);  
                    method.invoke(obj, cursor.getFloat(index));  
                    break;  
                case Cursor.FIELD_TYPE_BLOB:  
                    method  = obj.getClass().getMethod(mname, byte[].class);  
                    method.invoke(obj, cursor.getBlob(index));  
                    break;  
                case Cursor.FIELD_TYPE_INTEGER:  
                    method  = obj.getClass().getMethod(mname, int.class);  
                    method.invoke(obj, cursor.getInt(index));  
                    break;  
			    case Cursor.FIELD_TYPE_STRING:
				    method = obj.getClass().getMethod(mname, String.class);
				    method.invoke(obj, cursor.getString(index));
				    break;
			    default:
				    break;
            }  
        }  
        return obj;  
    }  
    //==========================================查询记录=============================
    /**
     * 创建者：zw<br>
     * 时间：2015-3-30 下午2:10:16<br>
     * 注释：查询并返回list，这里仅先实现查询所有<br>
     * 
                 例子<br>
      List<UserBean> list = DbUtil.queryAllData(Table_User.DATABASE_TABLE, UserBean.class);  <br>
     *  
     * @param tableName 表名<br>
     * @param cls 想转换的对象类型<br>
     * @param select 如果此参数不写，将会查询每条数据的所有属性值<br>
     * @return
     */
    public synchronized static <T> List<T> queryAllData(String tableName, Class<T> cls, String... select)  
    {  
    	if(!DbTableUtil.isTableExist(tableName))
    	{
    		DebugUtil.setErrorLog(TAG, "queryAllData:"+tableName+" 表不存在！");  
    		return null;
    	}
    	
        StringBuilder query = new StringBuilder("select * from ");  
        query.append(tableName);  
          
        List<T> result = new ArrayList<T>();  
        Cursor cursor = null;
        try {  
            //根据查询条件返回查询游标  
            cursor = DbAdapter.sqliteDatabase.rawQuery(query.toString(), select);  
            while (cursor.moveToNext()) { 
            	//实例化一个对象，此bean必须有一个无参数构造方法  
                T obj = cls.newInstance(); 
                //根据set方法设置obj的属性值  
                obj = setObjValue(obj, cursor);  
                result.add(obj);  
            }  
        } catch (Exception e) { 
            DebugUtil.setErrorLog(TAG, "error queryAllData:" + e);
        }finally{
        	closeCursor(cursor);
        }
        return result;  
    } 
    /**
     * 根据某个字段查询所有相关的数据
     * @param tableName
     * @param cls
     * @param where
     * @param key
     * @param select
     * @return
     */
    public synchronized static <T> List<T> queryAllDataByWhere(String tableName, Class<T> cls,String where,String... key)  
    {  
    	if(!DbTableUtil.isTableExist(tableName))
    	{
    		DebugUtil.setErrorLog(TAG, "queryAllData:"+tableName+" 表不存在！");  
    		return null;
    	}
          
        List<T> result = new ArrayList<T>();  
        Cursor cursor = null;
        try {  
            //根据查询条件返回查询游标  
            cursor = DbAdapter.sqliteDatabase.rawQuery("SELECT * FROM " + tableName+ " where " +where+ "=?", key);
            if(cursor==null){return null; }
            while (cursor.moveToNext()) { 
            	//实例化一个对象，此bean必须有一个无参数构造方法  
                T obj = cls.newInstance(); 
                //根据set方法设置obj的属性值  
                obj = setObjValue(obj, cursor);  
                result.add(obj);  
            }  
        } catch (Exception e) { 
            DebugUtil.setErrorLog(TAG, "error queryAllData:" + e);
        } finally{
        	closeCursor(cursor);
        } 
        return result;  
    } 
    
    
    /**
     * 模糊查找数据表名称
     */
    public synchronized static List<String> queryAlltableLike(String like){
          
        List<String> tablenames = new ArrayList<String>();  
        Cursor cursor = null;
        //SELECT name FROM sqlite_master WHERE type='table' and name like 'Chat\\_%' escape '\\'
        try {  
        	String  query = "select name from " + "sqlite_master"+ " where type='table' and name like '"+like+"' escape '\\'";
            //根据查询条件返回查询游标  
            cursor = DbAdapter.sqliteDatabase.rawQuery(query, null);
            if(cursor==null){return null; }
            String name;
            while (cursor.moveToNext()) { 
            	//根据列名获取列索引   
            	int nameColumnIndex = cursor.getColumnIndex("name");
            	name=cursor.getString(nameColumnIndex); 
            	tablenames.add(name);
            }  
        } catch (Exception e) { 
            DebugUtil.setErrorLog(TAG, "error queryAlltableLike:" + e);
        } finally{
        	closeCursor(cursor);
        } 
    	
    	return tablenames;
    }
    
    /**
     * 模糊查找数据表名称
     */
    public synchronized static boolean createIndex(String table_name,String index_name,String columns){
          
        try {  
        	//CREATE INDEX index_name IF NOT EXISTS on table_name (column1, column2, .....);
        	String  sqlStr = "create index "+index_name+" on "+ table_name +" "+columns;
        	execSQL(sqlStr);
        	
        } catch (Exception e) { 
            DebugUtil.setErrorLog(TAG, "error createIndex:" + e);
            return false;
        } finally{
        }
		return true; 
    	
    }
    
    
    /**
     * 
       *<br> 创建者：林党宏
       *<br>时间：2016年4月7日 上午9:26:44
       *<br>注释：查询所有数据根据指定条件排序
     */
    public synchronized static <T> List<T> queryAllDataOrderByWhere(String tableName, Class<T> cls,String orderby)  
    {  
    	if(!DbTableUtil.isTableExist(tableName))
    	{
    		DebugUtil.setErrorLog(TAG, "queryAllData:"+tableName+" 表不存在！");  
    		return null;
    	}
    	
    	List<T> result = new ArrayList<T>();  
    	Cursor cursor = null;
    
    	try {  
    		//根据查询条件返回查询游标  
    		cursor = DbAdapter.sqliteDatabase.rawQuery("SELECT * FROM " + tableName+ " ORDER BY " +orderby,null);
    		if(cursor==null){return null; }
    		while (cursor.moveToNext()) { 
    			//实例化一个对象，此bean必须有一个无参数构造方法  
    			T obj = cls.newInstance(); 
    			//根据set方法设置obj的属性值  
    			obj = setObjValue(obj, cursor);  
    			result.add(obj);  
    		}  
    	} catch (Exception e) { 
    		DebugUtil.setErrorLog(TAG, "error queryAllData:" + e);
    	} finally{
    		closeCursor(cursor);
    	} 
    	return result;  
    } 
  
    /**
	 * 创建者:王婷玉
	 * 时间:2015-5-28上午10:45:01
	 * 注释:关联表查询数据()
	 * @param cls 需要返回的对象
	 * @param second_tableName 第二张表
	 * @param first_tableName 第一张表
	 * @param Id 关联id
	 */
	public synchronized static <T> List<T> queryDataThroughTwoTable(Class<T> returncls,String second_tableName, String first_tableName,String firstFromId,String firstWhereId, String... Id) {
		if (!DbTableUtil.isTableExist(second_tableName)) {
			DebugUtil.setErrorLog(TAG, "queryAddressData:" + second_tableName+ " 表不存在！");
			return null;
		}
		List<T> result = new ArrayList<T>();
		List<String> firstIdList = new ArrayList<String>();
		Cursor firstCursor = null;
		Cursor cursor = null;
		if (DbAdapter.sqliteDatabase != null) {
			try {
				
				cursor = DbAdapter.sqliteDatabase.rawQuery("SELECT " +firstFromId+ " FROM " + first_tableName+ " where " +firstWhereId+ "=?", Id);
				if (cursor != null) {
					while (cursor.moveToNext()) {
						if(firstIdList.size()>0){
							if (!firstIdList.contains(cursor.getString(cursor.getColumnIndex(firstFromId)))) {
								// 添加数据
								firstIdList.add(cursor.getString(cursor.getColumnIndex(firstFromId)));
							}
						}else{
							firstIdList.add(cursor.getString(cursor.getColumnIndex(firstFromId)));
						}
					}
				}
				for (int i = 0; i < firstIdList.size(); i++) {
					if (!firstIdList.get(i).equals("")) {
						firstCursor = DbAdapter.sqliteDatabase.rawQuery("SELECT * FROM " + second_tableName+ " where " +firstFromId+ "=?",
								new String[] { firstIdList.get(i).toString() });
						while (firstCursor.moveToNext()) {
							// 实例化一个对象，此bean必须有一个无参数构造方法
							T obj = returncls.newInstance();
							// 根据set方法设置obj的属性值
							obj = setObjValue(obj, firstCursor);
							result.add(obj);
						}
					}
				}
			} catch (Exception e) {
				DebugUtil.setErrorLog(TAG, "error queryAddressData:" + e);
			}finally{
	        	closeCursor(cursor);
	        	closeCursor(firstCursor);
	        }
		}
		return result;
	}
	/**
	 * 
	   *<br> 创建者：ldh
	   *<br>时间：2016年2月25日 上午11:05:38
	   *<br>注释：自定义sql  语句查询所有对象
	 */
	public synchronized static <T> List<T> queryAllDataBySql(Class<T> returncls,String sql) {

		List<T> result = new ArrayList<T>();
		Cursor firstCursor = null;
		Cursor cursor = null;
		if (DbAdapter.sqliteDatabase != null) {
			try {
				firstCursor = DbAdapter.sqliteDatabase.rawQuery(sql, null);
				while (firstCursor.moveToNext()) {
					// 实例化一个对象，此bean必须有一个无参数构造方法
					T obj = returncls.newInstance();
					// 根据set方法设置obj的属性值
					obj = setObjValue(obj, firstCursor);
					result.add(obj);
				}

			} catch (Exception e) {
				DebugUtil.setErrorLog(TAG, "error queryAddressData:" + e);
			}finally{
				closeCursor(cursor);
				closeCursor(firstCursor);
			}
		}
		return result;
	}
	
	private synchronized static void closeCursor(Cursor mCursor){
		if(mCursor!=null){
			mCursor.close();
		}
	}
	/**
	 * 创建者:王婷玉
	 * 时间:2015-7-17下午4:21:41
	 * 注释:查找无主键数据表重复数据id
	 */
	public synchronized static List<Integer> querySameDataId(String tableName,String userId,String departmentId) {
		Cursor cursor = null;
		cursor = DbAdapter.sqliteDatabase.rawQuery("SELECT * FROM " + tableName, null);
		List<Integer> autoidList=new ArrayList<Integer>();
		int autoid = 0;
		if (cursor != null) {
			while (cursor.moveToNext()) {
				try {
					String mUserId=cursor.getString(cursor.getColumnIndex("userId"));
					String mDepartmentId=cursor.getString(cursor.getColumnIndex("departmentId"));
					if(StringUtils.isBlank(mDepartmentId)){
						if (userId.equals(mUserId)) {
							autoid = cursor.getInt(cursor.getColumnIndex("autoid"));
							autoidList.add(autoid);
						}
					}else{
						if (userId.equals(mUserId)&&departmentId.equals(mDepartmentId)) {
							autoid = cursor.getInt(cursor.getColumnIndex("autoid"));
							autoidList.add(autoid);
						}
					}
				} catch (Exception e) {
					closeCursor(cursor);
					e.printStackTrace();
					break;
				}
			}
			closeCursor(cursor);
		}
		
		return autoidList;
	}
	/**
	 * 创建者:王婷玉
	 * 时间:2015-7-23上午9:40:02
	 * 注释:查找HelloColleague无主键数据表重复数据id
	 * @return 
	 */
	public synchronized static int queryHelloColleagueSameDataId(String tableName,String targetId,int des) {
		Cursor cursor = null;
		cursor = DbAdapter.sqliteDatabase.rawQuery("SELECT * FROM " + tableName, null);
		int autoid = -1;
		if (cursor != null) {
			while (cursor.moveToNext()) {
				try {
					String mUserId=cursor.getString(cursor.getColumnIndex("targetId"));
					int mdes=cursor.getInt(cursor.getColumnIndex("des"));
					if (targetId.equals(mUserId)&&des==mdes) {
						autoid = cursor.getInt(cursor.getColumnIndex("autoid"));
					}
				} catch (Exception e) {
					closeCursor(cursor);
					e.printStackTrace();
					break;
				}
			}
			closeCursor(cursor);
		}
		
		return autoid;
	
	}
	/**
	 * 创建者:王婷玉
	 * 时间:2015-7-27下午5:31:11
	 * 注释:模糊查询数据
	 * @param <T>
	 * @return 
	 */
	public synchronized static <T> List<T> queryFuzzyData(String tableName, Class<T> cls,String... select) {
		if (!DbTableUtil.isTableExist(tableName)) {
			DebugUtil.setErrorLog(TAG, "queryFuzzyData:" + tableName + " 表不存在！");
			return null;
		}
		String params=select[0];
		String  query="select * from " + tableName+ " where name like '%"+params+"%'";
		List<T> result = new ArrayList<T>();
		Cursor cursor = null;
		try {
			// 根据查询条件返回查询游标
			cursor = DbAdapter.sqliteDatabase.rawQuery(query.toString(), null);
			while (cursor.moveToNext()) {
				// 实例化一个对象，此bean必须有一个无参数构造方法
				T obj = cls.newInstance();
				// 根据set方法设置obj的属性值
				obj = setObjValue(obj, cursor);
				result.add(obj);
			}
		} catch (Exception e) {
			DebugUtil.setErrorLog(TAG, "error queryAllData:" + e);
		}finally{
        	closeCursor(cursor);
        }
		return result;
	}
	
    /**
     * 创建者：zw<br>
     * 时间：2015-3-30 下午3:08:53<br>
     * 注释： 根据ID获取一条记录,并返回对象<br>
     * 例子：<br>
     * DbUtil.queryOneDataById(Table_User.DATABASE_TABLE,UserBean.class,Table_User.KEY_ID,1,Table_User.fields);<br>
     * 
     * @param tableName 表名<br>
     * @param cls 需要从数据库中获取的对象类型<br>
     * @param where 主键字段名称<br>
     * @param id 主键id<br>
     * @param fields 指定需要查询的哪些数据库表属性字段<br>
     * @return<br>
     */
	public static synchronized <T> T queryOneDataById(String tableName,Class<T> cls,String where,String id,String... fields){
		
		if(!DbTableUtil.isTableExist(tableName))
    	{
    		return null;
    	}
		
		int count = getCount(tableName);	//获取所有记录条数
		
		if(count==0){
			return null;
		}
    	
		// 实例化一个对象，此bean必须有一个无参数构造方法
		T obj = null;
		Cursor cursor =null;
		try {
			// 筛选条件 userId=?
			String m_where = where + "=?";
			String[] args = { String.valueOf(id) };
			cursor = DbAdapter.sqliteDatabase.query(true, tableName, fields, m_where, args, null, null, null, null);
			if(cursor.getCount()>0){
				obj = cls.newInstance();
				// 都尽量这样做
				if (cursor != null) {
					cursor.moveToFirst();
					obj = setObjValue(obj, cursor);
					closeCursor(cursor);
					return obj;
				} 
			}
		} catch (Exception e) {

		}finally{
        	closeCursor(cursor);
        }
		return obj;
	}
	//======================================分页查询==================================================
	/**
	 * 创建者：zw
	 * 时间：2015-6-11 上午11:26:43
	 * 注释：分页查询表中的数据
	 * @param tableName 表名
	 * @param cls 实体类型
	 * @param offset 起始位置
	 * @param maxResult 查询最大多少个记录
	 * @return
	 */
	public  static <T> List<T> queryLitmiData(String tableName, Class<T> cls,String PrimaryKey,boolean isDESC,String mesSvrId,int maxResult)  
	{  
		if(!DbTableUtil.isTableExist(tableName))
		{
			DebugUtil.setErrorLog(TAG, "queryLitmiData:"+tableName+" 表不存在！");  
			return null;
		}

		int count = getCount(tableName);	//获取所有记录条数
		if(count==0){return null;}
		
		String query = "";
		String shunxu = "ASC"; //查询顺序:'desc' 倒序 'asc' 升序
		if(isDESC){
			shunxu = "DESC";
		}
		if(!mesSvrId.equals("")){
			//查询以某个消息id为基准，向前面查询N条记录
			query = "SELECT * FROM "+tableName+" a "
					+ "WHERE a.time < ( SELECT time FROM "+tableName+" b WHERE b.mesSvrId = '" + mesSvrId + "' GROUP BY mesSvrId )  ORDER BY time "+shunxu+" LIMIT " + maxResult;
			
		}else{
			
			query = "SELECT * FROM "+tableName+" a ORDER BY time "+shunxu+" LIMIT "+maxResult;
			
		}
		
		List<T> result = null;  
		Cursor cursor = null;
		try {  
			//根据查询条件返回查询游标  
			cursor = DbAdapter.sqliteDatabase.rawQuery(query.toString(),null);
			if(cursor!=null){
				result = new ArrayList<T>();
				while (cursor.moveToNext()) { 
					//实例化一个对象，此bean必须有一个无参数构造方法  
					T obj = cls.newInstance(); 
					//根据set方法设置obj的属性值  
					obj = setObjValue(obj, cursor);  
					result.add(obj);  
				}  
			}
		} catch (Exception e) {  
			DebugUtil.setErrorLog(TAG, "error queryLitmiData:" + e);
		}finally{ 
			closeCursor(cursor);
		}
		return result;  
	}
	
	/**
	 * 查询最后或者最新的记录，顺序查询，倒序查询
	 * @param tableName
	 * @param cls
	 * @param isDESC 查询顺序:'desc' 倒序 'asc' 升序
	 * @return
	 */
	public synchronized static <T> T queryLastData(String tableName, Class<T> cls,boolean isDESC)  
	{  
		if(!DbTableUtil.isTableExist(tableName))
		{
			DebugUtil.setErrorLog(TAG, "queryLitmiData:"+tableName+" 表不存在！");  
			return null;
		}

		int count = getCount(tableName);	//获取所有记录条数
		if(count==0){return null;}
		String shunxu = "ASC"; //查询顺序:'desc' 倒序 'asc' 升序
		if(isDESC){
			shunxu = "DESC";
		}
	
		StringBuilder query = new StringBuilder("SELECT * FROM "+tableName+" a ORDER BY time "+shunxu+" LIMIT "+1);  
		
		Cursor cursor = null;
		T obj  = null;
		try {  
			//根据查询条件返回查询游标  
			cursor = DbAdapter.sqliteDatabase.rawQuery(query.toString(),null);
			if(cursor!=null&&cursor.getCount()>0){
				while (cursor.moveToNext()) { 
					//实例化一个对象，此bean必须有一个无参数构造方法  
					obj = cls.newInstance(); 
					//根据set方法设置obj的属性值  
					obj = setObjValue(obj, cursor);  
				}  
			}
		} catch (Exception e) {  
			DebugUtil.setErrorLog(TAG, "error queryLitmiData:" + e);
		}finally{ 
			closeCursor(cursor);
		}
		return obj;  
	}
	
    //======================================分页查询==================================================
    /**
     * 创建者：zw
     * 时间：2015-6-10 下午6:05:26
     * 注释：通过表名称,获取表中记录的总数
     * @param tableName 数据表
     * @return 记录的总数
     */
    public synchronized static int getCount(String tableName) {
    	int count = 0;
    	Cursor cursor =null;
    	try {
    		if(!DbTableUtil.isTableExist(tableName))
        	{
        		DebugUtil.setErrorLog(TAG, "getCount:"+tableName+" 表不存在！");  
        		return 0;
        	}
        	
        	cursor = DbAdapter.sqliteDatabase.rawQuery("select count(*) from "+tableName,null);
        	cursor.moveToFirst();	//因为查找的结果有且只有一条，所以直接将游标的指向为first即可
        	count = cursor.getInt(0);
        	
		} catch (Exception e) {
			
		}finally{
			closeCursor(cursor);
		}
    	
    	return count;
    }
    
   
    /**
     * 创建者：zw<br>
     * 时间：2015-3-30 上午11:55:05<br>
     * 注释：更新操作 (根据指定obj属性值更新数据库中信息) <br>
     * 例子：<br>
     * UserBean tom = new UserBean("linux", 19, 2, "englist");  <br>
     * DbUtil.updateSomeFields(Table_User.DATABASE_TABLE, tom, Table_User.KEY_ID, 2 , Table_User.fields); <br>
     * @param tableName 表名<br>
     * @param obj 更新某个对象到数据库中<br>
     * @param id  指定某个需要更新的id<br>
     * @param fields 指定需要更改哪些数据库表属性字段<br>
     * @return <br>
     */
    public synchronized static boolean updateSomeFields(String tableName, Object obj,String where,String id,String[] fields)  
    {  
    	if(!DbTableUtil.isTableExist(tableName))
    	{
    		DebugUtil.setErrorLog(TAG, "updateSomeFields:"+tableName+" 表不存在！");  
    		return false;
    	}
    	
    	String m_where = where + "=" + id;
    	ContentValues cv = getContentValues(obj,fields);
    	return DbAdapter.sqliteDatabase.update(tableName, cv, m_where, null)>0;
    } 
    /**
     * 创建者：zw
     * 时间：2015-6-16 下午4:20:03
     * 注释：使用此函数，不需要指定class成员变量名称
     * @param tableName
     * @param where
     * @param id
     * @param cv
     * @return
     */
    public synchronized static boolean updateSomeFields(String tableName,String where,String id,ContentValues cv)  
    {  
    	if(!DbTableUtil.isTableExist(tableName))
    	{
    		DebugUtil.setErrorLog(TAG, "updateSomeFields:"+tableName+" 表不存在！");  
    		return false;
    	}
    	String m_where = where + "=?";
    	String[] args = { String.valueOf(id) };
    	return DbAdapter.sqliteDatabase.update(tableName, cv, m_where, args)>0;
    }
	
    /**
     * 创建者：zw<br>
     * 时间：2015-3-30 下午3:01:55<br>
     * 注释：根据ID,删除某条记录<br>
     * deleteOneDataById(Table_User.DATABASE_TABLE,Table_User.KEY_ID,2);<br>
     * @param tableName 表名<br>
     * @param where 主键字段名称<br>
     * @param id 指定某个id<br>
     * @return
     */
    public static synchronized boolean deleteOneDataById(String tableName,String where,String id) {
    	if(!DbTableUtil.isTableExist(tableName))
    	{
    		DebugUtil.setErrorLog(TAG, "deleteOneDataById:"+tableName+" 表不存在！");  
    		return false;
    	}
    	
    	String m_where = where + "=?";
    	String[] args = { String.valueOf(id) };
    	
    	return DbAdapter.sqliteDatabase.delete(tableName, m_where, args)>0;
    	
    }
    
    private synchronized static ContentValues getContentValues(Object obj,String... fields){
    	
    	 Class<?> cls = obj.getClass();  
         ContentValues cv = new ContentValues();  
         for (String f: fields) {  
             String mname = getMethodName(f);  
             try {  
                 Method method = cls.getMethod(mname);  
                 cv = contentPutValue(cv, f, method.invoke(obj));  
             } catch (Exception e) {  
                 e.printStackTrace();  
             }  
         } 
		return cv;
    }
    
    /**
     * 创建者：zw
     * 时间：2015-6-10 下午6:15:22
     * 注释：把实体类中的参数与值赋值给ContentValues
     * @param obj 实体
     * @param fields 参数数组
     * @return
     */
    private synchronized static ContentValues getContentValues(Object obj,Field[] fields){
    	ContentValues cv = new ContentValues();  
    	Class<?> cls = obj.getClass();
    	for (Field f: fields) {
    		String name = f.getName();
    		
    		if(name.equals("serialVersionUID")){continue;}	//特殊处理一下，凡是反射出有序列化的ID变量名称就直接略过。
    		
    		String mname = getMethodName(name);  
    		try {  
    			Method method = cls.getMethod(mname);  
    			Object object = method.invoke(obj);
    			if(object==null){
    				cv = contentPutValue(cv, name, "");  					//避免某些成员变量不存在，造成异常提醒。
    			}else{
    				if(object.toString().equals("-100000")){continue;};		//当出现自增字段(变量初始值为-100000)，我们不允许给它设置相关值，直接返回。
    				cv = contentPutValue(cv, name, object);  
    			}
    		} catch (Exception e) {
    			DebugUtil.setLog(TAG, "getContentValues "+name +" 数据表字段不存在或者为自增字段！");  
    		} 
    	}
    	return cv;
    }
    
    /**
	 * 获取对象的get函数，让首字母变成大写<br>
     * @param    name 符合javabean规范的类的方法名 <br>
     * @return   getXXX形式无参数方法名 <br>
     */  
    private synchronized static String getMethodName(String name)  
    {  
        StringBuilder sb = new StringBuilder(name);  
        //替换首字符为大写  
        char first = sb.charAt(0);  
        sb.setCharAt(0, Character.toUpperCase(first));  
        sb.insert(0, "get");  
        return sb.toString();  
    }  
      
    /**
     * 获取对象的set函数，让首字母变成大写<br>
     * @param    name 符合javabean规范的类的方法名 <br>
     * @return   setXXX形式无参数方法名 <br>
     */  
    private static String setMethodName(String name)  
    {  
        StringBuilder sb = new StringBuilder(name);  
        //替换首字符为大写  
        char first = sb.charAt(0);  
        sb.setCharAt(0, Character.toUpperCase(first));  
        sb.insert(0, "set");  
        return sb.toString();  
    }  
      
    /**
     * 根据指定参数调用合适的ContentValues的put方法 
     */  
    private synchronized static ContentValues contentPutValue(ContentValues cv, String key, Object value) throws Exception  
    {   
        Method method = ContentValues.class.getMethod("put", String.class, value.getClass());  
        method.invoke(cv, key, value);  
        return cv;  
    } 
    /**
     * 创建者：zw<br>
     * 时间：2015-3-30 上午11:37:44<br>
     * 注释：用于单独执行某些字符串sql语句<br>
     * @param sql<br>
     */
    public synchronized static void execSQL(String sql)  
    {  
    	DbAdapter.sqliteDatabase.execSQL(sql);  
    }  
    /**
     * 清除某张表内全部记录
     * @param tableName
     */
    public synchronized static void deleteAllTable(String tableName){
    	if(StringUtils.isBlank(tableName)) return;
    	if(!DbTableUtil.isTableExist(tableName)) return;
    	String sql = "delete from "+tableName;
    	execSQL(sql);
    }
    /**
     * 
       *<br> 创建者：林党宏
       *<br>时间：2016年3月29日 下午3:32:33
       *<br>注释：根据指定条件删除记录
     */
    public synchronized static boolean deleteByWhere(String tableName,String whereClause,String[] whereArgs){
    	if(StringUtils.isBlank(tableName)) return false;
    	if(!DbTableUtil.isTableExist(tableName)) return false;
         long result =	DbAdapter.sqliteDatabase.delete(tableName, whereClause, whereArgs);
    	 return result>0;
    }
    /**
     * 
     *<br> 创建者：林党宏
     *<br>时间：2016年5月3日 下午5:11:14
     *<br>注释：删除所有符合条件的数据
     */
    public synchronized static boolean deleteAllDataByWhere(String tableName,String whereClause,String[] whereArgs){
    	if(StringUtils.isBlank(tableName)) return false;
    	if(!DbTableUtil.isTableExist(tableName)) return false;
    	long result =	DbAdapter.sqliteDatabase.delete(tableName, whereClause, whereArgs);
    	return result>0;
    }
}

