//---------------------------------------------------------------------------

#ifndef lscModelMainH
#define lscModelMainH

#include "CSingleEngine.h"
#include "CSingleParams.h"
#include "CFilter.h"
#include "lscInteractorExtendDocCircul.h"
#include "lscModelColumnMainList.h"
#include "lscModelPaging.h"
//---------------------------------------------------------------------------

class lscModelMain
{
public:
	lscModelMain();
	~lscModelMain();

	void __fastcall init(String basePath);
	void __fastcall update();
	void __fastcall findDocCircul(CFilter *_filter);

	// Колонки
	int getDocCirculListColumnCount();
	lscColumnMainList *getDocCirculColumnParams(int index);

	// Список найденных документооборотов
	int getDocCirculCount();
	lscExtendDocCircul *getDocCirculListItem(int index);

	// Пагинация
	lscModelPaging *getPaging();

private:
	CEngine *m_dbEngine;
	CSetOrder *m_programParams;

	lscModelPaging m_paging;

	// Список найденных документооборотов
	vector<lscExtendDocCircul*> *m_findDocCirculList;
	// Колонки
	vector<lscColumnMainList> m_columnDocCirculList;

	void clearDocCirculList();
};

#endif
