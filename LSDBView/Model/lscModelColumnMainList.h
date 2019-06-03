//---------------------------------------------------------------------------

#ifndef lscModelColumnMainListH
#define lscModelColumnMainListH

#include <Classes.hpp>
//---------------------------------------------------------------------------

enum eColumnType{DC, IC, R};

struct lscColumnMainList {
	eColumnType m_type;
	String m_columnName;

	// ��� ��� ����������� ������ �������, ����� ������������ �� ������ ������� ��������
	String m_columnMaxValue;

	lscColumnMainList(eColumnType type, String columnName);
	~lscColumnMainList();

	eColumnType getType();
	String getColumnParam();

	void calcColumnSize(String value);
	String getMaxValue();

//  ����� �����������, ���� ��������� ��� �� ��������� ������� ���������� �� ���������:
//	String getColumnName();
};

#endif
