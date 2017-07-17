package com.neolynk.bankdemoapp.dao;

import java.io.Serializable;
import java.util.List;

import com.neolynk.demoapp.validation.ApplicationException;

/**
 * Created by wassim on 2017/07/16
 */
public interface CrudService<T, ID extends Serializable> {

	/**
	 * Save entity
	 * 
	 * @author wassim
	 * @param entity
	 *            : {@linkplain T} entity domain to persist
	 * @return {@link Boolean}
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public boolean save(T entity); // throws Exception, ApplicationException;

	/**
	 * Loads an entity using its Primary Key
	 * 
	 * @author wassim
	 * @param entityId
	 *            : the id of the entity to find
	 * @return entity
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public T findById(ID entityId); // throws Exception, ApplicationException;

	/**
	 * Loads all entity.
	 * 
	 * @author wassim
	 * @return all entity
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public List<T> findAll(); // throws Exception, ApplicationException;

	/**
	 * Deletes an entity using its Primary Key
	 * 
	 * @author wassim
	 * @param entityId
	 *            : the id of the entity to delete
	 * @return {@link Boolean}
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public boolean delete(ID entityId); // throws Exception,
										// ApplicationException;
}
