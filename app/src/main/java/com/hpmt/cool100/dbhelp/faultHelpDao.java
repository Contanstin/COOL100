package com.hpmt.cool100.dbhelp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table FAULT_HELP.
*/
public class faultHelpDao extends AbstractDao<faultHelp, Long> {

    public static final String TABLENAME = "FAULT_HELP";

    /**
     * Properties of entity faultHelp.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Code = new Property(1, String.class, "Code", false, "CODE");
        public final static Property ChildCode = new Property(2, String.class, "childCode", false, "CHILD_CODE");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Reason = new Property(4, String.class, "reason", false, "REASON");
        public final static Property Solution = new Property(5, String.class, "solution", false, "SOLUTION");
        public final static Property ChildName = new Property(6, String.class, "childName", false, "CHILD_NAME");
        public final static Property Type = new Property(7, String.class, "type", false, "TYPE");
    };


    public faultHelpDao(DaoConfig config) {
        super(config);
    }
    
    public faultHelpDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'FAULT_HELP' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'CODE' TEXT," + // 1: Code
                "'CHILD_CODE' TEXT," + // 2: childCode
                "'NAME' TEXT," + // 3: name
                "'REASON' TEXT," + // 4: reason
                "'SOLUTION' TEXT," + // 5: solution
                "'CHILD_NAME' TEXT," + // 6: childName
                "'TYPE' TEXT);"); // 7: type
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'FAULT_HELP'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, faultHelp entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String Code = entity.getCode();
        if (Code != null) {
            stmt.bindString(2, Code);
        }
 
        String childCode = entity.getChildCode();
        if (childCode != null) {
            stmt.bindString(3, childCode);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(5, reason);
        }
 
        String solution = entity.getSolution();
        if (solution != null) {
            stmt.bindString(6, solution);
        }
 
        String childName = entity.getChildName();
        if (childName != null) {
            stmt.bindString(7, childName);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(8, type);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public faultHelp readEntity(Cursor cursor, int offset) {
        faultHelp entity = new faultHelp( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // Code
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // childCode
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // reason
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // solution
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // childName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // type
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, faultHelp entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setChildCode(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setReason(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSolution(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setChildName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setType(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(faultHelp entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(faultHelp entity) {
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