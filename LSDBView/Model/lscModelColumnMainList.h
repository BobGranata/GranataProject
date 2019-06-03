//---------------------------------------------------------------------------

#ifndef lscModelColumnMainListH
#define lscModelColumnMainListH

#include <Classes.hpp>
//---------------------------------------------------------------------------

enum eColumnType{DC, IC, R};

struct lscColumnMainList {
	eColumnType m_type;
	String m_columnName;

	// Это для определения длинны колонки, будет определяться по самому жирному значению
	String m_columnMaxValue;

	lscColumnMainList(eColumnType type, String columnName);
	~lscColumnMainList();

	eColumnType getType();
	String getColumnParam();

	void calcColumnSize(String value);
	String getMaxValue();

//  Можно реализовать, если захочется что бы заголовки колонок отличались от параметра:
//	String getColumnName();
};

#endif
