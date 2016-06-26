package com.psib.dao;

import java.util.List;

/**
 * @author DatHT Jun 4, 2016
 */
public interface IBaseDao<Model, Id> {

	public Model getById(Id id);

	public void insert(Model model);

	public void update(Model model);

	public List<Model> getAll();
}
