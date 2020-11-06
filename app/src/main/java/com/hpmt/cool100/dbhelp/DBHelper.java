package com.hpmt.cool100.dbhelp;

import android.content.Context;
import android.util.Log;

import com.hpmt.cool100.base.MyApplication;
import com.hpmt.cool100.dbhelp.childParamsDao.Properties;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

public class DBHelper {

	private static Context mContext;

	private static DBHelper instance;

	private static DaoSession daoSession;

	private DBHelper() {
	}

	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper();
			if (mContext == null) {
				mContext = context;
			}

			// 数据库对象
			daoSession = MyApplication.getBleDaoSession();
		}
		return instance;
	}

	/**
	 * 添加修改的记录
	 * 
	 * @param item
	 */
	public void addToModitiParamsTable(modifiParam item) {
		if (isContainModifi(item)) {
			daoSession.getModifiParamDao().update(item);

			Log.e("数据已存在", "********已存在******");
		} else {
			daoSession.getModifiParamDao().insert(item);
		}

	}

	/**
	 * 修改历史上传数据，主要是改名称
	 */
	public void UpdateHistoryData(downLoadItem item) {
		daoSession.getDownLoadItemDao().update(item);
	}

	/**
	 * 添加历史记录
	 * 
	 * @param item
	 */
	public void addHistoryParam(downLoadItem item) {

		daoSession.getDownLoadItemDao().insert(item);

	}

	/**
	 * 删除历史记录
	 * 
	 * @param item
	 */
	public void deleteHistoryTable(String tag) {
		QueryBuilder<downLoadItem> qb = daoSession.getDownLoadItemDao()
				.queryBuilder();
		qb.where((com.hpmt.cool100.dbhelp.downLoadItemDao.Properties.DwTag
				.eq(tag)));
		daoSession.getDownLoadItemDao().deleteInTx(qb.list());

	}

	/**
	 * 删除指定修改的记录
	 * 
	 * @param item
	 */
	public void deleteModitiParamsTable(String tag) {
		QueryBuilder<modifiParam> qb = daoSession.getModifiParamDao()
				.queryBuilder();
		qb.where((com.hpmt.cool100.dbhelp.modifiParamDao.Properties.Downloadtag
				.eq(tag)));
		daoSession.getModifiParamDao().deleteInTx(qb.list());

	}

	/**
	 * 删除指定修改的记录
	 * 
	 * @param item
	 */
	public void deleteModitiParamsAll() {
//		QueryBuilder<modifiParam> qb = daoSession.getModifiParamDao()
//				.queryBuilder();
//		qb.where((com.hpmt.mtbluetooth.dbhelp.modifiParamDao.Properties.Downloadtag
//				.eq(tag)));
		daoSession.getModifiParamDao().deleteAll();

	}

	private boolean isContainModifi(modifiParam item) {
		QueryBuilder<modifiParam> qb = daoSession.getModifiParamDao()
				.queryBuilder();
		qb.where((com.hpmt.cool100.dbhelp.modifiParamDao.Properties.Id)
				.eq(item.getName()));

		return qb.buildCount().count() > 0 ? true : false;

	}

	/**
	 * 获取修改记录的数据
	 */

	public List<modifiParam> getModifiParam(String tag) {
		QueryBuilder<modifiParam> qb = daoSession.getModifiParamDao()
				.queryBuilder();
		qb.where((com.hpmt.cool100.dbhelp.modifiParamDao.Properties.Downloadtag
				.eq(tag)));
		return qb.list();
	}

	/**
	 * 添加父数据
	 * 
	 * @param item
	 */
	public void addTosuperParamsTable(superParam item) {
		daoSession.getSuperParamDao().insert(item);
	}

	/**
	 * 添加子数据
	 */
	public void addTochildParamsTable(childParams item) {
		daoSession.getChildParamsDao().insert(item);
	}

	/**
	 * 添加帮助
	 */
	public void addToHelpTable(faultHelp item) {
		daoSession.getFaultHelpDao().insert(item);
	}

	/**
	 * 查询父数据表所有数据
	 * 
	 * @return
	 */
	public List<superParam> getsuperParams(String ParamID) {

		QueryBuilder<superParam> qb = daoSession.getSuperParamDao()
				.queryBuilder();
		qb.where((com.hpmt.cool100.dbhelp.superParamDao.Properties.ParamID
				.eq(ParamID)));
		return qb.list();
	}

	/**
	 * 查询帮助表所有数据
	 * 
	 * @return
	 */
	public List<faultHelp> getHelpData() {

		QueryBuilder<faultHelp> qb = daoSession.getFaultHelpDao()
				.queryBuilder();
		return qb.list();
	}

	/**
	 * 查询上传历史数据表里边的数据
	 * 
	 * @return
	 */
	public List<downLoadItem> getHistoryData() {

		QueryBuilder<downLoadItem> qb = daoSession.getDownLoadItemDao()
				.queryBuilder();
		return qb.list();
	}

	/**
	 * 判断帮助数据有没有保存
	 * 
	 * @param Id
	 * @return
	 */
	public boolean isHelpSaved() {
		QueryBuilder<faultHelp> qb = daoSession.getFaultHelpDao()
				.queryBuilder();
		qb.buildCount().count();
		return qb.buildCount().count() > 0 ? true : false;
	}

	/**
	 * 判断某个父数据有没有保存
	 * 
	 * @param Id
	 * @return
	 */
	public boolean isParamSaved(String Id) {
		QueryBuilder<superParam> qb = daoSession.getSuperParamDao()
				.queryBuilder();
		qb.where(Properties.ParamID.eq(Id));
		qb.buildCount().count();
		return qb.buildCount().count() > 0 ? true : false;
	}

	/** 删除 */
	public void deletesuperParamsList(String Id) {
		QueryBuilder<superParam> qb = daoSession.getSuperParamDao()
				.queryBuilder();
		DeleteQuery<superParam> bd = qb.where(Properties.ParamID.eq(Id))
				.buildDelete();
		bd.executeDeleteWithoutDetachingEntities();
	}

	/** 删除 */
	public void clearsuperParams() {
		daoSession.getSuperParamDao().deleteAll();
		daoSession.getChildParamsDao().deleteAll();
		daoSession.getFaultHelpDao().deleteAll();
	}

	/**
	 * 
	 * 根据条件获取数据
	 * */
	public List<childParams> getChildParams(String paramID) {
		QueryBuilder<childParams> qb = daoSession.getChildParamsDao()
				.queryBuilder();
		qb.where((Properties.ParamID.eq(paramID)));
		// qb.orderAsc(Properties.ParamID);// 排序依据
		return qb.list();
	}

	/**
	 * 
	 * 获取所有的数据
	 * */
	public List<childParams> getAllChildParams() {
		QueryBuilder<childParams> qb = daoSession.getChildParamsDao()
				.queryBuilder();

		// qb.where((Properties.ParamID.eq(paramID)));
		// qb.orderAsc(Properties.ParamID);// 排序依据
		return qb.list();
	}
}
