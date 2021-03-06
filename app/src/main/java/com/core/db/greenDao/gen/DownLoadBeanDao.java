package com.core.db.greenDao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.core.db.greenDao.entity.DownLoadBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DOWN_LOAD_BEAN".
*/
public class DownLoadBeanDao extends AbstractDao<DownLoadBean, String> {

    public static final String TABLENAME = "DOWN_LOAD_BEAN";

    /**
     * Properties of entity DownLoadBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Url = new Property(1, String.class, "url", false, "URL");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property DownSize = new Property(3, long.class, "downSize", false, "DOWN_SIZE");
        public final static Property FileSize = new Property(4, long.class, "fileSize", false, "FILE_SIZE");
        public final static Property TaskStatus = new Property(5, int.class, "taskStatus", false, "TASK_STATUS");
        public final static Property TaskId = new Property(6, long.class, "taskId", false, "TASK_ID");
        public final static Property ErrorCode = new Property(7, int.class, "errorCode", false, "ERROR_CODE");
    }


    public DownLoadBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DownLoadBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DOWN_LOAD_BEAN\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"URL\" TEXT," + // 1: url
                "\"NAME\" TEXT," + // 2: name
                "\"DOWN_SIZE\" INTEGER NOT NULL ," + // 3: downSize
                "\"FILE_SIZE\" INTEGER NOT NULL ," + // 4: fileSize
                "\"TASK_STATUS\" INTEGER NOT NULL ," + // 5: taskStatus
                "\"TASK_ID\" INTEGER NOT NULL ," + // 6: taskId
                "\"ERROR_CODE\" INTEGER NOT NULL );"); // 7: errorCode
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DOWN_LOAD_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DownLoadBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
        stmt.bindLong(4, entity.getDownSize());
        stmt.bindLong(5, entity.getFileSize());
        stmt.bindLong(6, entity.getTaskStatus());
        stmt.bindLong(7, entity.getTaskId());
        stmt.bindLong(8, entity.getErrorCode());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DownLoadBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
        stmt.bindLong(4, entity.getDownSize());
        stmt.bindLong(5, entity.getFileSize());
        stmt.bindLong(6, entity.getTaskStatus());
        stmt.bindLong(7, entity.getTaskId());
        stmt.bindLong(8, entity.getErrorCode());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public DownLoadBean readEntity(Cursor cursor, int offset) {
        DownLoadBean entity = new DownLoadBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // url
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.getLong(offset + 3), // downSize
            cursor.getLong(offset + 4), // fileSize
            cursor.getInt(offset + 5), // taskStatus
            cursor.getLong(offset + 6), // taskId
            cursor.getInt(offset + 7) // errorCode
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DownLoadBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDownSize(cursor.getLong(offset + 3));
        entity.setFileSize(cursor.getLong(offset + 4));
        entity.setTaskStatus(cursor.getInt(offset + 5));
        entity.setTaskId(cursor.getLong(offset + 6));
        entity.setErrorCode(cursor.getInt(offset + 7));
     }
    
    @Override
    protected final String updateKeyAfterInsert(DownLoadBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(DownLoadBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DownLoadBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
