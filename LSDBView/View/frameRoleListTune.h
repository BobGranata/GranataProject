//---------------------------------------------------------------------------

#ifndef frameRoleListTuneH
#define frameRoleListTuneH
//---------------------------------------------------------------------------
#include <System.Classes.hpp>
#include <Vcl.Controls.hpp>
#include <Vcl.StdCtrls.hpp>
#include <Vcl.Forms.hpp>
#include <Vcl.CheckLst.hpp>

#include "CEngine.h"
#include "lsiViewRoleListTune.h"
#include "lscPresenterRoleListTune.h"
#include <Vcl.ComCtrls.hpp>
#include <Vcl.ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TfrmRoleListTune : public TFrame, lsiViewRoleListTune
{
__published:	// IDE-managed Components
	TListBox *listRoleType;
	TButton *btnAddRole;
	TLabel *Label6;
	TButton *btnEditRole;
	TEdit *edSearch;
	TComboBox *comboSearch;
	TButton *btnSearch;
	TListView *roleList;
	TEdit *edCurrentPage;
	TLabel *lbPageCount;
	TButton *btnNextPage;
	TPanel *Panel1;
	TButton *btnPreviousPage;
	TComboBox *cbPageOnScreen;
	TButton *btnVisiParamEdit;
	void __fastcall comboSearchKeyDown(TObject *Sender, WORD &Key, TShiftState Shift);
	void __fastcall edSearchChange(TObject *Sender);
	void __fastcall edSearchKeyDown(TObject *Sender, WORD &Key, TShiftState Shift);
	void __fastcall listRoleTypeClick(TObject *Sender);
	void __fastcall roleList1ClickCheck(TObject *Sender);
	void __fastcall roleList1DblClick(TObject *Sender);
	void __fastcall btnAddRoleClick(TObject *Sender);
	void __fastcall btnEditRoleClick(TObject *Sender);
	void __fastcall comboSearchClick(TObject *Sender);
	void __fastcall edCurrentPageChange(TObject *Sender);
	void __fastcall btnNextPageClick(TObject *Sender);
	void __fastcall btnPreviousPageClick(TObject *Sender);
	void __fastcall cbPageOnScreenClick(TObject *Sender);
	void __fastcall btnVisiParamEditClick(TObject *Sender);


private:	// User declarations
	CSetOrder *Params;
	CEngine *Engine;

	lscPresenterRoleListTune *presenterRoleListTune;
public:		// User declarations
	__fastcall TfrmRoleListTune(TComponent* Owner);

	void __fastcall init(CEngine *Engine, CSetOrder *Params);

/**
  * Список типов коллекций ролей:
  */
	void clearRoleTypeList();
	void addItemRoleTypeList(String roleType, int roleTypeIndex);
	int getCurrentRoleType();
	void setCurrentRoleType(int index);

/**
  * Список ролей:
  */
	void setEditRoleEnable(bool enable);
	int getCurrentIndexRoleList();
	void setCurrentIndexRoleList(int index);
	int addItemRoleList();

	void addColumn(String param, int index);
	void deleteAllColumn();
	void addTextToItemRoleList(int row, int column, String text);
	void clearRoleList();

/**
  * Строка поиска в списке ролей:
  */
	void addFindParams(String param, int index);
	int getSearchType();
	void setSearchType(int index);
	String getSearchText();

	void setRoleVisible(int roleIndex, bool isVisible);

	void setPageCount(int count);
	int getNumPage();
	void setNumPage(int page);

/**
  * Анимация ожидания:
  */
	void StartWaitProccess();
	void StopWaitProccess();
};
//---------------------------------------------------------------------------
extern PACKAGE TfrmRoleListTune *frmRoleListTune;
//---------------------------------------------------------------------------
#endif
