package com.psib.dao;

import java.util.List;

public interface IBaseDao<Model, Id> {

	public Model getById(Id id);
	public void insert(Model model);
	public List<Model> getAll();
}
