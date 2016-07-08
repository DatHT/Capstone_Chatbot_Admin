package com.psib.service;

import com.psib.dto.BootGirdDto;

public interface ISynonymManager {

    BootGirdDto getAllOriginForPaging(int current, int rowCount, String searchPhrase, String sortName);

    BootGirdDto getAllSynonymsForPaging(int current, int rowCount, String searchPhrase, String sortName);
}
