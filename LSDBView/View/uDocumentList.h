//---------------------------------------------------------------------------

#ifndef uDocumentListH
#define uDocumentListH
//---------------------------------------------------------------------------
#include <System.Classes.hpp>
#include <Vcl.Controls.hpp>
#include <Vcl.StdCtrls.hpp>
#include <Vcl.Forms.hpp>
#include <Vcl.ComCtrls.hpp>
#include <Vcl.ExtCtrls.hpp>
#include <Vcl.ToolWin.hpp>

#include "lscPresenterDocList.h"
#include "lsiDocumentList.h"
//---------------------------------------------------------------------------
class TfrmDocumentList : public TForm, public lsiListColumnView, public lsiDocumentList
{
__published:	// IDE-managed Components
	TPanel *pnlBase;
	TPanel *sPanel1;
	TToolBar *sToolBar1;
	TToolButton *btnView;
	TToolButton *btnResend;
	TListView *lvDocumentsList;
	TListView *ListView1;
	void __fastcall lvDocumentsListDblClick(TObject *Sender);
	void __fastcall btnResendClick(TObject *Sender);
private:	// User declarations
	lscPresenterDocList *m_PresenterDocList;
public:		// User declarations
	__fastcall TfrmDocumentList(TComponent* Owner);

	// Это дожно быть в интерфейсе
	void addItemList();
	void addValueItemList(int, int, wchar_t *);
	void addStatusImage(int, int);
	void setColumnSize(int, const String&);
	void setColumnSize(int indexColumn, int size);
	void addColumn(const String&);
	int getDocumentIndex();

	void clearList();

	void fillDocumentList(lscExtendDocCircul *extendDocCircul);
};
//---------------------------------------------------------------------------
//extern PACKAGE TfrmDocumentList *frmDocumentList;
//---------------------------------------------------------------------------
#endif
