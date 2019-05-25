package com.core.db.manger;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.core.db.manger.FilePathConfigUtil.FileTypeName;

/**
 * @author zw 
 * 数据操作管理类的通用写法 1. 定义数据库名，版本，表 定义数据库字段 定义建表语句 2. 声明上下文环境context
 * 声明数据操作类SQLiteDatabase
 * 声明并定义一个继承至SQLiteOpenHelper的数据库管理类DBHelper,负责数据库的创建，表的创建和更新 3.在本类中增加数据库操作的打开和关闭连接和增删改查的方法
 */
public class DbAdapter {
	String TAG = DBHelper.class.getSimpleName();
	/** 定义数据库基本信息，数据库名称*/
	String dbName = "temp.db";
	String userid = "-1";
	/**定义数据库基本信息,版本*/
	public final static int DATABASE_VERSION =1;
	/**表名*/
	public static String DATABASE_TABLE = "";
	/**创建表的Sql语句*/
	public static String DATABASE_CREATE = "";

	private Context context;

	/**定义数据库操作的帮助类，主要是用来打开和关闭数据库连接，而其他增删改查的方法应该在当前类(数据库操作管理类)中定义*/
	public static DBHelper dbHelper;

	/** 定义数据库操作的实例*/
	public static SQLiteDatabase sqliteDatabase;
	
	public DbAdapter(Context context,String dbName,String userid) {
		this.context = context;
		this.dbName  = dbName;
		this.userid  = userid;
	}

	// 打开数据库时创建数据库帮助类,获取写的失败时就获取读的
	public  void openDb() {
		
		dbHelper = new DBHelper(context);
		try {
			sqliteDatabase = openDatabase();
			DebugUtil.setErrorLog(TAG, "打开数据库成功！"+sqliteDatabase);
		} catch (Exception e) {
			//如果异常，就存储到系统缓存目录下，data/data/app包名/......
			sqliteDatabase = dbHelper.getReadableDatabase();
			DebugUtil.setErrorLog(TAG, "异常：打开数据库失败！数据库存储到 data/data/app包名/......");
		}
	}
	
	//得到操作数据库的对象
	@SuppressLint("NewApi") public  SQLiteDatabase openDatabase()
	{
		SQLiteDatabase database = null;
		try
		{
			String dirpath = FilePathConfigUtil.getInstance(context).getFilePath(userid, FileTypeName.DB);
			File dir = new File(dirpath);
			//判断是否存在该文件
			if (!dir.exists())
			{     
				dir.mkdirs();
			}
			//得到数据库的完整路径名
			String databaseFilename = dir + "/" + dbName;
			
			//得到SQLDatabase对象
			database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);  
			
			int oldVersion = database.getVersion();
			 if (oldVersion == 0) {
				 dbHelper.onCreate(database);  //子类我们实现的方法（其实什么也没做，要想这第一次创建时做一些操作，
             } else if(oldVersion<DATABASE_VERSION){
            	 dbHelper.onUpgrade(database, oldVersion, DATABASE_VERSION);  //子类我们实现的方法（版本发生变化）
             }
			 database.setVersion(DATABASE_VERSION);
			 database.enableWriteAheadLogging(); 
			 
		}catch (Exception e)
		{
			System.out.println(e.getMessage());
			DebugUtil.setErrorLog(TAG, "异常：打开数据库失败！");
		}finally{
			
		}
		return database;
	}
	
	

	/**
	 * 创建者：zw
	 * 时间：2015-2-28 下午4:20:32
	 * 注释：创建一个新的表
	 * @param tableName
	 * @param sqlTableString
	 */
	public static void CreateTable(String tableName,String sqlTableString){
		DATABASE_TABLE  = tableName;
		DATABASE_CREATE = sqlTableString;
		//表名与表的sql语句都不能为空
		if(!DATABASE_TABLE.equals("")&&!DATABASE_CREATE.equals("")){
			if(dbHelper!=null){
				dbHelper.onCreate(sqliteDatabase);
			}
		}else{
			System.out.println("无法创建表,参数表名或者创建表的Sql语句为空！");
		}
	}
	
	
	/**
	 * 判断某张表是否存在
	 * @param tabName 表名
	 * @return
	 */
	public boolean tabIsExist(String table){
		Cursor cursor = null;
		boolean exits = false;
		try {
			String sql = "select * from sqlite_master where name="+"'"+table+"'";
			if(sqliteDatabase==null){return false;}
			cursor = sqliteDatabase.rawQuery(sql, null);
			if(cursor!=null&&cursor.getCount()!=0){exits = true;}
		} catch (Exception e) {
			
		}finally{
			if(cursor!=null){
				cursor.close();
			}
		}
		return exits;
	}
	
	/**
	 * 创建者：zw
	 * 修改时间：2015-2-28 上午11:57:08
	 * 作用：定义成静态内部类更方法使用和维护
	 */
	private class DBHelper extends SQLiteOpenHelper {
		// 可以在这写数据库各字段名称的静态变量名，但是最好是写在数据库管理类里，这里创建sql的时候，为了一目了然，最好还是以小写来写
		public DBHelper(Context context, String dbName, CursorFactory factory,int version) {
			super(context, dbName, factory, version);
		}

		public DBHelper(Context context) {
			this(context, dbName, null, DATABASE_VERSION);
		}
		
		/*
		 * 初次建表
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (Exception e) {
			}
		}

		/*
		 * 升级数据库
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			System.out.println("onUpgrade  更新数据库表.....!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			try {
				
				db.execSQL("alter table "+"DepartmentColleague"+" add "+"sn"+" INTEGER");
				db.execSQL("alter table "+"FileTaskList"+" add "+"title"+" TEXT");
				db.execSQL("alter table "+"ChatGroup"+" add "+"ownerId"+" TEXT");
				db.execSQL("alter table "+"ChatGroup"+" add "+"joinTime"+" TEXT");
				db.execSQL("alter table "+"ChatGroup"+" add "+"createTime"+" TEXT");
				db.execSQL("alter table "+"ChatGroup"+" add "+"system"+" INTEGER");
				db.execSQL("alter table "+"ChatGroup"+" add "+"count"+" INTEGER");
			} catch (Exception e) {
				
			}
		}

		@Override
	    public void onOpen(SQLiteDatabase db)
	    {
	        super.onOpen(db);
	        // 每次打开数据库之后首先被执行
	        System.out.println("onOpen db...");
	    }

	}
	
}
