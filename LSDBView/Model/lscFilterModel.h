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

	// ��������� �������

	// ������ ���������� ��
	vector<String> m_dcPrmFilterList;
	// ������ ����� ��
	vector<pair<String, String> > m_DcTypeFilterList;
	// ������ ������ ������� ��
	vector<pair<String, String> > m_DcStatusFilterList;
	// ������ ������ ���� �������� ��
	vector<lscModelDateFilter> m_dateDcFilterList;

	// ������ ���������� ����
	vector<String> m_rolePrmFilterList;

	// ������ ���������� �����������
	vector<String> m_infcomPrmFilterList;

	// ������ ���������� ���������
	vector<String> m_documentPrmFilterList;
	// ������ ������ ������� ���������
	vector<String> m_documentStatusFilterList;
public:
	CFilter m_Filter;

	lscFilterModel();
	~lscFilterModel();

	void init();

    // ����
	int getRolePrmFilterCount() const;
	String getRolePrmFilterItem(int index) const;
	void setRoleFilterParam(int index, String text);

		// ���� �������� ��
	int getDatePrmFilterCount() const;
	lscModelDateFilter getDatePrmFilterItem(int index) const;
	void setDateFilterPrm(TDateTime beginDate, TDateTime endDate);

	    // ��� ��
	int getDcTypeFilterCount() const;
	String getDcTypeFilterItem(int index) const;
	void setDcTypeFilterParam(int index);

		// ������ ��
	int getDcStatusFilterCount() const;
	String getDcStatusFilterItem(int index) const;
	void setDcStatusFilterParam(int index);

		// ��������� ��
	int getDcPrmFilterCount() const;
	String getDcPrmFilterItem(int index) const;
	void setDcFilterParam(int index, String text);

	    // ��������� �����������
	int getInfComPrmFilterCount() const;
	String getInfComPrmFilterItem(int index) const;
	void setInfComFilterParam(int index, String text);

	const vector<pair<String, String> > &getDcStatusParam();


	// ��������� ���������
	int getDocumentPrmFilterCount() const;
	String getDocumentPrmFilterItem(int index) const;
	void setDocumentFilterParam(int index, String text);

	// ������ ���������
	int getDocumentStatusFilterCount() const;
	String getDocumentStatusFilterItem(int index) const;
	void setDocumentStatusFilterParam(int index);
};

#endif
