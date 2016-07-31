package com.psib.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.psib.dao.ISynonymDao;
import com.psib.dto.BootGirdDto;
import com.psib.dto.SynonymJsonDto;
import com.psib.model.Synonym;
import com.psib.service.ISynonymManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SynonymManager implements ISynonymManager {

    private static final Logger LOG = Logger.getLogger(SynonymManager.class);

    private static final Integer LIMIT_RESULT_SYNONYM = 1000;

    @Autowired
    private ISynonymDao synonymDao;

    @Override
    public BootGirdDto getAllOriginForPaging(int current, int rowCount, String searchPhrase, String sortName) {
        LOG.info(new StringBuilder("[getAllOriginForPaging] Start: current = ").append(current)
                .append(" ,rowCount = ").append(rowCount)
                .append(" ,searchPhrase = ").append(searchPhrase)
                .append(" sortName = ").append(sortName));

        List<Synonym> list;
        int start = current * rowCount - rowCount;

        list = synonymDao.getOriginBySearchPhraseAndSort(searchPhrase, sortName, rowCount, start);

        List<SynonymJsonDto> synonymJsonDtoList = new ArrayList<>();
        long size = list.size();

        for (int i = 0; i < size; i++) {
            synonymJsonDtoList.add(new SynonymJsonDto((start + i + 1), list.get(i)));
        }

        BootGirdDto dto = new BootGirdDto();
        dto.setCurrent(current);
        dto.setRowCount(rowCount);
        dto.setRows(synonymJsonDtoList);
        dto.setTotal(synonymDao.countOriginBySearchPhrase(searchPhrase));

        LOG.info("[getAllOriginForPaging] End");
        return dto;
    }

    @Override
    public BootGirdDto getAllSynonymsForPaging(int current, int rowCount, String searchPhrase, String sortName,
                                               int originId) {
        LOG.info(new StringBuilder("[getAllSynonymsForPaging] Start: current = ").append(current)
                .append(" ,rowCount = ").append(rowCount)
                .append(" ,searchPhrase = ").append(searchPhrase)
                .append(" sortName = ").append(sortName)
                .append(", originId = ").append(originId));

        List<Synonym> list;
        int start = current * rowCount - rowCount;

        list = synonymDao.getSynonymsBySearchPhraseAndSort(searchPhrase, sortName, rowCount, start, originId);

        List<SynonymJsonDto> synonymJsonDtoList = new ArrayList<>();
        long size = list.size();

        for (int i = 0; i < size; i++) {
            synonymJsonDtoList.add(new SynonymJsonDto((start + i + 1), list.get(i)));
        }

        BootGirdDto dto = new BootGirdDto();
        dto.setCurrent(current);
        dto.setRowCount(rowCount);
        dto.setRows(synonymJsonDtoList);
        dto.setTotal(synonymDao.countSynonymsBySearchPhrase(searchPhrase, originId));

        LOG.info("[getAllSynonymsForPaging] End");
        return dto;
    }

    @Override
    public int insertWord(String name) {
        LOG.info("[insertWord] name = " + name);
        Synonym synonym = new Synonym();
        synonym.setName(name);
        synonym.setSynonymId(0);
        Synonym tmp = synonymDao.checkWordExist(synonym);

        if (tmp != null) {
            return 0;
        }

        synonymDao.insertWord(synonym);

        LOG.info("[insertWord] End");
        return 1;
    }

    @Override
    public int insertWord(String name, int synonymId) {
        LOG.info("[insertWord] name = " + name);
        Synonym synonym = new Synonym();
        synonym.setName(name);
        synonym.setSynonymId(synonymId);

        Synonym tmp = synonymDao.checkWordExist(synonym);

        if (tmp != null) {
            return 0;
        }

        synonymDao.insertWord(synonym);

        LOG.info("[insertWord] End");
        return 1;
    }

    @Override
    public int updateWord(int id, String name, int synonymId) {
        LOG.info(new StringBuilder("[updateWord] Start: id = ").append(id)
                .append(" ,name = ").append(name)
                .append(" ,synonymId = ").append(synonymId));
        Synonym synonym = new Synonym();
        synonym.setId(id);
        synonym.setName(name);
        synonym.setSynonymId(synonymId);
        Synonym tmp = synonymDao.checkWordExist(synonym);

        if (tmp != null) {
            return 0;
        }

        synonymDao.updateWord(synonym);

        LOG.info("[updateWord] End");
        return 1;
    }

    @Override
    public void deleteWord(int deleteWordId) {
        LOG.info("[deleteProduct] Start: deleteWordId = " + deleteWordId);
        Synonym synonym = new Synonym();
        synonym.setId(deleteWordId);
        synonymDao.deleteById(synonym);
        LOG.info("[deleteWordId] End");
    }

    @Override
    public String calcSynonym(String productName) {
        LOG.info("[calcSynonym] Start: productName = " + productName);
        productName = productName.toLowerCase();
        int skipResultSynonym = 0;
        int synonymListSize = -1;
        int replaceSynonymListSize;
        List<Synonym> synonymList;
        List<String> replaceSynonymList;
        Synonym synonym;
        String synonymName;
        String productSynonym = productName + ";";
        String tmp;
        int j;
        List<String> list;
        List<String> list2;
        Set<String> lookup;
        int lookupSize;
        int startChar;
        int productLength = productName.length();
        int synonymLength;
        int lastPosition;
        boolean isReplaceable;
        while (synonymListSize != 0) {
            // get synonym
            synonymList = synonymDao.getSynonymNameSortById(skipResultSynonym, LIMIT_RESULT_SYNONYM);
            synonymListSize = synonymList.size();

            for (int i = 0; i < synonymListSize; i++) {
                synonym = synonymList.get(i);
                synonymName = synonym.getName().toLowerCase();
                isReplaceable = true;
                if (productName.contains(synonymName)) {
                    startChar = productName.indexOf(synonymName);
                    synonymLength = synonymName.length();
                    lastPosition = startChar + synonymLength;

                    if (lastPosition < productLength) {
                        if (productName.charAt(startChar + synonymLength) != ' ') {
                            isReplaceable = false;
                        }
                    } else if (startChar != 0) {
                        if (productName.charAt(startChar - 1) != ' ') {
                            isReplaceable = false;
                        }
                    }

                    if (isReplaceable) {
                        replaceSynonymList = synonymDao.getByIdAndSynonymId(synonym.getId(), synonym.getSynonymId());
                        replaceSynonymListSize = replaceSynonymList.size();

                        for (j = 0; j < replaceSynonymListSize; j++) {
                            tmp = productSynonym.replace(synonymName, replaceSynonymList.get(j).trim());
                            productSynonym += tmp;
                        }
                    }
                }
            }

            skipResultSynonym += LIMIT_RESULT_SYNONYM;
        }

        list = Lists.newArrayList(Splitter.on(";").split(productSynonym));
        list2 = new ArrayList<>();
        lookup = new HashSet<>();
        for (String item : list) {
            if (lookup.add(item)) {
                // Set.add returns false if item is already in the set
                list2.add(item);
            }
        }
        list = list2;
        productSynonym = "";

        lookupSize = list.size() - 1;

        for (j = 0; j < lookupSize; j++) {
            productSynonym += list.get(j) + ";";
        }

        LOG.info("[calcSynonym] End");
        return productSynonym;
    }

	@Override
	public List<Synonym> searchSynonym(String phrase) {
		return synonymDao.getSynonyms(phrase);
	}
	
	@Override
	public String replaceSentenceBySynonym(String sentence) {
		String[] words = sentence.split(" ");
		String result = "";
		
		int currIndex = 0;
		int lastIndex = currIndex;
		String currPhrase = words[currIndex];
		String lastPhrase = currPhrase;
		boolean flag = true;
		
		boolean isAdded = true;
		
		List<Synonym> synonyms = searchSynonym(currPhrase);
		
		while(flag) {
			if (!synonyms.isEmpty()) {
				int maxAcceptableResults = currPhrase.split(" ").length == 1 ? 5 : currPhrase.split(" ").length * 5;
				isAdded = false;
				if (synonyms.size() == 1) {
					if (currPhrase.equalsIgnoreCase(synonyms.get(0).getName().trim())) {
						Synonym synonym = synonymDao.getSynonymById(synonyms.get(0).getSynonymId());
						result += " " + synonym.getName();
						isAdded = true;
						if ((currIndex + 1) == words.length) {
							flag = false;
							continue;
						}
						currPhrase = words[currIndex = currIndex + 1];
						lastIndex = currIndex;
						lastPhrase = currPhrase;
					} else {
						lastPhrase = currPhrase;
						currPhrase = currPhrase + " " + words[currIndex = currIndex + 1];
					}
					
					synonyms = searchSynonym(currPhrase);
				} else if (synonyms.size() < maxAcceptableResults) {
					for (Synonym synonym : synonyms) {
						if (currPhrase.equals(synonym.getName().trim())) {
							result += " " + synonymDao.getSynonymById(synonym.getSynonymId()).getName();
							isAdded = true;
							if ((currIndex + 1) == words.length) {
								flag = false;
								continue;
							}
							currPhrase = words[currIndex = currIndex + 1];
							lastIndex = currIndex;
							lastPhrase = currPhrase;
							break;
						}
					}
					if (!isAdded) {
						lastPhrase = currPhrase;
						currPhrase = currPhrase + " " + words[currIndex = currIndex + 1];
					}
					synonyms = searchSynonym(currPhrase);
				} else {
					if ((currIndex + 1) == words.length) {
						flag = false;
						continue;
					}
					lastPhrase = currPhrase;
					currPhrase = currPhrase + " " + words[currIndex = currIndex + 1];
					synonyms = searchSynonym(currPhrase);
				}
			} else {
				if (!isAdded) {
					result += " " + words[lastIndex];
					currIndex = lastIndex;
				} else {
					result += " " + lastPhrase;
				}
				if ((currIndex + 1) == words.length) {
					flag = false;
					continue;
				}
				isAdded = true;
				currPhrase = words[currIndex = currIndex + 1];
				lastIndex = currIndex;
				lastPhrase = currPhrase;
				synonyms = searchSynonym(currPhrase);
			}
			result = result.trim();
		}
		
		return result;
	}
}
