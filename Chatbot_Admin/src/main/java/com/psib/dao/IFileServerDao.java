package com.psib.dao;

import com.psib.model.FileServer;

public interface IFileServerDao {

    FileServer getByName(String name);
}
