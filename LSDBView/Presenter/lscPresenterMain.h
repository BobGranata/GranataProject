//---------------------------------------------------------------------------

#ifndef lscPresenterMainH
#define lscPresenterMainH

#include "lscModelMain.h"
#include "lscFilterModel.h"
// todo: �������� �� ���������
//#include "UMainForm.h"
// todo: �������� �� ���������
//#include "uDocumentList.h"

#include "lsiPaggingView.h"
#include "lsiLoadingView.h"
#include "lsiFilterView.h"
#include "lsiDBViewerView.h"

#include "lsiDocumentList.h"
//#include "uDocumentList.h"
#include "lscInitDbThread.h"
//---------------------------------------------------------------------------

class lscPresenterMain {
	public:
	// todo: �������� TfrmMain �� ��������� lsiViewMain
//	lscPresenterMain(lsiDBViewerView *view, TfrmDocumentList *documentList, lsiFilterView *filterView, lsiPaggingView *paggingView, lsiLoadingView *loadingView);
	lscPresenterMain(lsiDBViewerView *view,
						lsiDocumentList *documentList,
						lsiFilterView *filterView,
						lsiPaggingView *paggingView,
						lsiLoadingView *loadingView);
	~lscPresenterMain();

	// �������������� ���� � ������
	void __fastcall init();

	void __fastcall initDb();
	void __fastcall initView();

	void __fastcall updateDB();

	void __fastcall fillRoleListColumn();

	void __fastcall setAllFilterParams();
	void __fastcall findDocCirculClick();

	void __fastcall datePeriodFilterChange();

//	void loadDocCircul();
	void loadDocumentList();

	// ��������
	void changeLimit();
	void changePage();
	void nextPage();
	void previousPage();
	void firstPage();
	void lastPage();

	private:
    lscInitDbThread *m_InitThread;

	lscModelMain *m_Model;
	lscFilterModel *m_FilterModel;

	// todo: �������� TfrmMain �� ��������� lsiViewMain
	lsiDBViewerView *m_View;
	lsiFilterView *m_filterView;
	lsiPaggingView *m_paggingView;
	lsiLoadingView *m_loadingView;

	lsiDocumentList *m_documentList;

	String __fastcall setBase();
	void __fastcall initFilter();
	void __fastcall initPagging();

	void __fastcall fillExtendDcList();
};

#endif
