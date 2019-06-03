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

	// �������
	int getDocCirculListColumnCount();
	lscColumnMainList *getDocCirculColumnParams(int index);

	// ������ ��������� �����������������
	int getDocCirculCount();
	lscExtendDocCircul *getDocCirculListItem(int index);

	// ���������
	lscModelPaging *getPaging();

private:
	CEngine *m_dbEngine;
	CSetOrder *m_programParams;

	lscModelPaging m_paging;

	// ������ ��������� �����������������
	vector<lscExtendDocCircul*> *m_findDocCirculList;
	// �������
	vector<lscColumnMainList> m_columnDocCirculList;

	void clearDocCirculList();
};

#endif
