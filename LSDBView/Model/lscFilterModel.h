//---------------------------------------------------------------------------

#ifndef lscFilterModelH
#define lscFilterModelH
//---------------------------------------------------------------------------
#include "CFilter.h"
#include "lscModelDateFilter.h"

#include <iostream>
#include <vector>

using std::vector;
using std::pair;

class lscFilterModel
{
private:

	// Настройки фильтра

	// Список параметров ДО
	vector<String> m_dcPrmFilterList;
	// Список типов ДО
	vector<pair<String, String> > m_DcTypeFilterList;
	// Список выбора статуса ДО
	vector<pair<String, String> > m_DcStatusFilterList;
	// Список выбора даты отправки ДО
	vector<lscModelDateFilter> m_dateDcFilterList;

	// Список параметров роли
	vector<String> m_rolePrmFilterList;

	// Список параметров направления
	vector<String> m_infcomPrmFilterList;

	// Список параметров документа
	vector<String> m_documentPrmFilterList;
	// Список выбора статуса документа
	vector<String> m_documentStatusFilterList;
public:
	CFilter m_Filter;

	lscFilterModel();
	~lscFilterModel();

	void init();

    // Роль
	int getRolePrmFilterCount() const;
	String getRolePrmFilterItem(int index) const;
	void setRoleFilterParam(int index, String text);

		// дата отправки ДО
	int getDatePrmFilterCount() const;
	lscModelDateFilter getDatePrmFilterItem(int index) const;
	void setDateFilterPrm(TDateTime beginDate, TDateTime endDate);

	    // тип ДО
	int getDcTypeFilterCount() const;
	String getDcTypeFilterItem(int index) const;
	void setDcTypeFilterParam(int index);

		// статус ДО
	int getDcStatusFilterCount() const;
	String getDcStatusFilterItem(int index) const;
	void setDcStatusFilterParam(int index);

		// параметры ДО
	int getDcPrmFilterCount() const;
	String getDcPrmFilterItem(int index) const;
	void setDcFilterParam(int index, String text);

	    // параметры направления
	int getInfComPrmFilterCount() const;
	String getInfComPrmFilterItem(int index) const;
	void setInfComFilterParam(int index, String text);

	const vector<pair<String, String> > &getDcStatusParam();


	// параметры документа
	int getDocumentPrmFilterCount() const;
	String getDocumentPrmFilterItem(int index) const;
	void setDocumentFilterParam(int index, String text);

	// статус документа
	int getDocumentStatusFilterCount() const;
	String getDocumentStatusFilterItem(int index) const;
	void setDocumentStatusFilterParam(int index);
};

#endif
