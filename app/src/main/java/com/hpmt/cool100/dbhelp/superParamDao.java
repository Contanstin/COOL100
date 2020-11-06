package com.hpmt.cool100.dbhelp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table SUPER_PARAM.
*/
public class superParamDao extends AbstractDao<superParam, Long> {

    public static final String TABLENAME = "SUPER_PARAM";

    /**
     * Properties of entity superParam.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ExplainParam = new Property(1, String.class, "explainParam", false, "EXPLAIN_PARAM");
        public final static Property ArgsParam = new Property(2, String.class, "argsParam", false, "ARGS_PARAM");
        public final static Property ParamID = new Property(3, String.class, "paramID", false, "PARAM_ID");
    };


    public superParamDao(DaoConfig config) {
        super(config);
    }
    
    public superParamDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'SUPER_PARAM' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'EXPLAIN_PARAM' TEXT," + // 1: explainParam
                "'ARGS_PARAM' TEXT," + // 2: argsParam
                "'PARAM_ID' TEXT);"); // 3: paramID
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'SUPER_PARAM'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, superParam entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String explainParam = entity.getExplainParam();
        if (explainParam != null) {
            stmt.bindString(2, explainParam);
        }
 
        String argsParam = entity.getArgsParam();
        if (argsParam != null) {
            stmt.bindString(3, argsParam);
        }
 
        String paramID = entity.getParamID();
        if (paramID != null) {
            stmt.bindString(4, paramID);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public superParam readEntity(Cursor cursor, int offset) {
        superParam entity = new superParam( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // explainParam
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // argsParam
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // paramID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, superParam entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setExplainParam(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setArgsParam(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setParamID(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(superParam entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(superParam entity) {
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