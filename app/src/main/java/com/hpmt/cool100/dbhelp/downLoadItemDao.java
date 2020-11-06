package com.hpmt.cool100.dbhelp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table DOWN_LOAD_ITEM.
*/
public class downLoadItemDao extends AbstractDao<downLoadItem, Long> {

    public static final String TABLENAME = "DOWN_LOAD_ITEM";

    /**
     * Properties of entity downLoadItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property DownName = new Property(1, String.class, "downName", false, "DOWN_NAME");
        public final static Property Date = new Property(2, String.class, "date", false, "DATE");
        public final static Property Times = new Property(3, String.class, "times", false, "TIMES");
        public final static Property DwTag = new Property(4, String.class, "dwTag", false, "DW_TAG");
    };


    public downLoadItemDao(DaoConfig config) {
        super(config);
    }
    
    public downLoadItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DOWN_LOAD_ITEM' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'DOWN_NAME' TEXT," + // 1: downName
                "'DATE' TEXT," + // 2: date
                "'TIMES' TEXT," + // 3: times
                "'DW_TAG' TEXT);"); // 4: dwTag
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DOWN_LOAD_ITEM'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, downLoadItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String downName = entity.getDownName();
        if (downName != null) {
            stmt.bindString(2, downName);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(3, date);
        }
 
        String times = entity.getTimes();
        if (times != null) {
            stmt.bindString(4, times);
        }
 
        String dwTag = entity.getDwTag();
        if (dwTag != null) {
            stmt.bindString(5, dwTag);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public downLoadItem readEntity(Cursor cursor, int offset) {
        downLoadItem entity = new downLoadItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // downName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // date
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // times
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // dwTag
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, downLoadItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDownName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDate(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTimes(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDwTag(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(downLoadItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(downLoadItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
