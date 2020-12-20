package com.core.db.greenDao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.core.db.greenDao.entity.ShopOrder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SHOP_ORDER".
*/
public class ShopOrderDao extends AbstractDao<ShopOrder, String> {

    public static final String TABLENAME = "SHOP_ORDER";

    /**
     * Properties of entity ShopOrder.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property ImagUrl = new Property(2, String.class, "imagUrl", false, "IMAG_URL");
        public final static Property Creator = new Property(3, String.class, "creator", false, "CREATOR");
        public final static Property ColorName = new Property(4, String.class, "colorName", false, "COLOR_NAME");
        public final static Property ColorNum = new Property(5, String.class, "colorNum", false, "COLOR_NUM");
        public final static Property Content = new Property(6, String.class, "content", false, "CONTENT");
        public final static Property Price = new Property(7, double.class, "price", false, "PRICE");
        public final static Property Total = new Property(8, double.class, "total", false, "TOTAL");
        public final static Property Num = new Property(9, double.class, "num", false, "NUM");
    }


    public ShopOrderDao(DaoConfig config) {
        super(config);
    }
    
    public ShopOrderDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SHOP_ORDER\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"IMAG_URL\" TEXT," + // 2: imagUrl
                "\"CREATOR\" TEXT," + // 3: creator
                "\"COLOR_NAME\" TEXT," + // 4: colorName
                "\"COLOR_NUM\" TEXT," + // 5: colorNum
                "\"CONTENT\" TEXT," + // 6: content
                "\"PRICE\" REAL NOT NULL ," + // 7: price
                "\"TOTAL\" REAL NOT NULL ," + // 8: total
                "\"NUM\" REAL NOT NULL );"); // 9: num
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SHOP_ORDER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ShopOrder entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String imagUrl = entity.getImagUrl();
        if (imagUrl != null) {
            stmt.bindString(3, imagUrl);
        }
 
        String creator = entity.getCreator();
        if (creator != null) {
            stmt.bindString(4, creator);
        }
 
        String colorName = entity.getColorName();
        if (colorName != null) {
            stmt.bindString(5, colorName);
        }
 
        String colorNum = entity.getColorNum();
        if (colorNum != null) {
            stmt.bindString(6, colorNum);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
        stmt.bindDouble(8, entity.getPrice());
        stmt.bindDouble(9, entity.getTotal());
        stmt.bindDouble(10, entity.getNum());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ShopOrder entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String imagUrl = entity.getImagUrl();
        if (imagUrl != null) {
            stmt.bindString(3, imagUrl);
        }
 
        String creator = entity.getCreator();
        if (creator != null) {
            stmt.bindString(4, creator);
        }
 
        String colorName = entity.getColorName();
        if (colorName != null) {
            stmt.bindString(5, colorName);
        }
 
        String colorNum = entity.getColorNum();
        if (colorNum != null) {
            stmt.bindString(6, colorNum);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
        stmt.bindDouble(8, entity.getPrice());
        stmt.bindDouble(9, entity.getTotal());
        stmt.bindDouble(10, entity.getNum());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ShopOrder readEntity(Cursor cursor, int offset) {
        ShopOrder entity = new ShopOrder( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // imagUrl
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // creator
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // colorName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // colorNum
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // content
            cursor.getDouble(offset + 7), // price
            cursor.getDouble(offset + 8), // total
            cursor.getDouble(offset + 9) // num
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ShopOrder entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setImagUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreator(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setColorName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setColorNum(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setContent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPrice(cursor.getDouble(offset + 7));
        entity.setTotal(cursor.getDouble(offset + 8));
        entity.setNum(cursor.getDouble(offset + 9));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ShopOrder entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(ShopOrder entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ShopOrder entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
