package com.psib.service;

import java.util.List;

import com.psib.dto.BootGirdDto;
import com.psib.model.Synonym;

public interface ISynonymManager {

    BootGirdDto getAllOriginForPaging(int current, int rowCount, String searchPhrase, String sortName);

    BootGirdDto getAllSynonymsForPaging(int current, int rowCount, String searchPhrase, String sortName,
                                        int originId);

    int insertWord(String name);

    int insertWord(String name, int synonymId);

    int updateWord(int id, String name, int synonymId);

    void deleteWord(int deleteWordId);

    String calcSynonym(String productName);
    
    List<Synonym> searchSynonym(String phrase);
    
    public String replaceSentenceBySynonym(String sentence);
}
