//---------------------------------------------------------------------------

#pragma hdrstop

#include "lscModelColumnMainList.h"
//---------------------------------------------------------------------------

lscColumnMainList::lscColumnMainList(eColumnType type, String columnName) : m_type(type), m_columnName(columnName)
{
}
//---------------------------------------------------------------------------
lscColumnMainList::~lscColumnMainList()
{
}
//---------------------------------------------------------------------------
eColumnType lscColumnMainList::getType()
{
	return m_type;
}
//---------------------------------------------------------------------------
String lscColumnMainList::getColumnParam()
{
	return m_columnName;
}
//---------------------------------------------------------------------------
void lscColumnMainList::calcColumnSize(String value)
{
	// не больше 60, что бы аномально большие значения не портили вид
	if (value.Length() > m_columnMaxValue.Length() &&  value.Length() < 100)
		m_columnMaxValue = value;
}
//---------------------------------------------------------------------------
String lscColumnMainList::getMaxValue()
{
	return m_columnMaxValue;
}
//---------------------------------------------------------------------------

#pragma package(smart_init)
