//---------------------------------------------------------------------------

#ifndef UMainFormH
#define UMainFormH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <Menus.hpp>
#include <ComCtrls.hpp>
#include <Vcl.ToolWin.hpp>
#include <ImgList.hpp>
#include <Vcl.ExtCtrls.hpp>
#include <System.ImageList.hpp>

#include "CSingleEngine.h"
#include "uViewerData.h"
#include "UDocCirculImage.h"
#include "uDocumentList.h"

#include "lscPresenterMain.h"

//---------------------------------------------------------------------------
class TfrmMain : public TForm, lsiDBViewerView, lsiPaggingView, lsiLoadingView, lsiFilterView
{
__published:	// IDE-managed Components
	TMainMenu *MainMenu1;
	TMenuItem *N1;
	TMenuItem *N4;
	TMenuItem *N6;
	TMenuItem *N13;
	TMenuItem *N12;
	TMenuItem *N16;
	TMenuItem *N17;
	TToolBar *tbarMainMenu;
	TToolButton *ToolButton7;
	TMenuItem *N19;
	TMenuItem *N21;
	TMenuItem *N22;
	TPanel *panelFilter;
	TMenuItem *N27;
	TPopupMenu *PopupMenu1;
	TMenuItem *N38;
	TMenuItem *N44;
	TMenuItem *mnTreeUnExpand2;
	TMenuItem *mnTreeExpand2;
	TToolButton *btnRefreshDB;
	TMenuItem *test1;
	TMenuItem *N2;
	TComboBox *cbSearchField;
	TEdit *edSearchValue;
	TButton *btnFindDocCircul;
	TMenuItem *N20;
	TMenuItem *N24;
	TMenuItem *N14;
	TMenuItem *N11;
	TMenuItem *N7;
	TMenuItem *N15;
	TMenuItem *N25;
	TMenuItem *N31;
	TMenuItem *NListPfrRegistration;
	TMenuItem *N32;
	TToolButton *btnOpenBase;
	TLabel *lbDcDate;
	TComboBox *cbDatePeriodFilter;
	TPanel *panelDocCircul;
	TListView *lvDocCirculList;
	TComboBox *cbPageOnScreen;
	TButton *btnNextPage;
	TLabel *lbPageCount;
	TEdit *edCurrentPage;
	TButton *btnPreviousPage;
	TButton *btnLastPage;
	TButton *btnFirstPage;
	TPanel *pnlPaging;
	TDateTimePicker *dtpBeginDate;
	TDateTimePicker *dtpEndDate;
	TLabel *lbBeginDate;
	TLabel *lbEndDate;
	TComboBox *cbDocCirculType;
	TToolBar *tbDocCirculType;
	TEdit *edDcParamText;
	TComboBox *cbDcParam;
	TEdit *edInfComParam;
	TComboBox *cbInfComParam;
	TLabel *lbRoleCaption;
	TLabel *lbInfComCaption;
	TLabel *lbDcCaption;
	TPanel *Panel1;
	TPanel *panelDocumentList;
	TPanel *panelExtendDc;
	TSplitter *splitterDcAndDoc;
	TLabel *lbDocCirculType;
	TLabel *lbDocCurculStatus;
	TComboBox *cbDocCirculStatus;
	TComboBox *cbDocumentParam;
	TLabel *lbDocument;
	TEdit *cbDocumentParamText;
	TLabel *lbDocumentStatus;
	TComboBox *cbDocumentStatus;
	void __fastcall N4Click(TObject *Sender);
	void __fastcall N22Click(TObject *Sender);
	void __fastcall btnRefreshDBClick(TObject *Sender);
	void __fastcall N2Click(TObject *Sender);
	void __fastcall N24Click(TObject *Sender);
	void __fastcall N14Click(TObject *Sender);
	void __fastcall N11Click(TObject *Sender);
	void __fastcall N7Click(TObject *Sender);
	void __fastcall N15Click(TObject *Sender);
	void __fastcall N25Click(TObject *Sender);
	void __fastcall N31Click(TObject *Sender);
	void __fastcall NListPfrRegistrationClick(TObject *Sender);
	void __fastcall N32Click(TObject *Sender);
	void __fastcall btnOpenBaseClick(TObject *Sender);
	void __fastcall btnFindDocCirculClick(TObject *Sender);
	void __fastcall FormActivate(TObject *Sender);
	void __fastcall cbDatePeriodFilterChange(TObject *Sender);
	void __fastcall lvDocCirculListDblClick(TObject *Sender);
	void __fastcall edCurrentPageClick(TObject *Sender);
	void __fastcall btnNextPageClick(TObject *Sender);
	void __fastcall btnLastPageClick(TObject *Sender);
	void __fastcall edCurrentPageChange(TObject *Sender);
	void __fastcall cbPageOnScreenChange(TObject *Sender);
	void __fastcall btnPreviousPageClick(TObject *Sender);
	void __fastcall btnFirstPageClick(TObject *Sender);
	void __fastcall lvDocCirculListCustomDrawItem(TCustomListView *Sender, TListItem *Item,
          TCustomDrawState State, bool &DefaultDraw);
	void __fastcall lvDocCirculListCustomDrawSubItem(TCustomListView *Sender, TListItem *Item,
          int SubItem, TCustomDrawState State, bool &DefaultDraw);
	void __fastcall lvDocCirculListClick(TObject *Sender);
	void __fastcall lvDocCirculListKeyDown(TObject *Sender, WORD &Key, TShiftState Shift);
	void __fastcall lvDocCirculListKeyUp(TObject *Sender, WORD &Key, TShiftState Shift);


private:	// User declarations
	bool isActivated;

	lscPresenterMain *m_PresenterMain;
	// todo: это тоже в интерфейс
	TfrmDocumentList *frmDocumentList;
//	lsiListColumnView *frmDocumentList;

public:		// User declarations
	__fastcall TfrmMain(TComponent* Owner);

//	bool __fastcall MakeCommand(String s);

	CEngine *Engine;
	CSetOrder *Params;

	// Это всё в интерфейс:

	// Это главный список документооборотов
	int addItemDocCirculList();
	void addValueToDocCirculList(int row, int column, String text);
	void addColumn(String columnCaption);
	void addStatusImage(int itemIndex, int imageIndex);
	void setColumnSize(int indexColumn, String text);
	void clearExtendDcList();
	int getExtDocCirculIndex();

	// Заполнение визуальных форм
	// Это всё к фильтру относится
    void clearFilter();

	String getRoleFilterText();
	int getRoleFilterParam();
	void addRoleFilterParam(String param);

	String getDcFilterText();
	int getDcFilterParam();
	void addDcFilterParam(String param);

	String getInfComFilterText();
	int getInfComFilterParam();
	void addInfComFilterParam(String param);

	int getDateFilterParam();
	void setBeginDate(TDateTime);
	void setEndDate(TDateTime);
	TDateTime getBeginDate();
	TDateTime getEndDate();
	void addDateFilterParam(String param);

	void addDcTypeFilterParam(String param);
	int getDcTypeFilterParam();

	void addDcStatusFilterParam(String param);
	int getDcStatusFilterParam();

	// Документы
	void addDocumentStatusFilterParam(String param);
	int getDocumentStatusFilterParam();

	String getDocumentFilterText();
	int getDocumentFilterParam();
	void addDocumentFilterParam(String param);

	// Это относится к пагинации:
	int getPage();
	void setPage(int);
	void setPageCount(int page);
	int getPaggingLimitIndex();
	void setPaggingLimit(int);
	void addPaggingLimit(int limit);

	// Ожидание загрузки
	void showLoading();
	void hideLoading();
};
//---------------------------------------------------------------------------
extern PACKAGE TfrmMain *frmMain;
//---------------------------------------------------------------------------
#endif
