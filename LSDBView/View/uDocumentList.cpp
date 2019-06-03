//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "uDocumentList.h"
#include "uViewerData.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
//TfrmDocumentList *frmDocumentList;
//---------------------------------------------------------------------------
__fastcall TfrmDocumentList::TfrmDocumentList(TComponent* Owner)
	: TForm(Owner)
{
	m_PresenterDocList = new lscPresenterDocList(this);
}

//---------------------------------------------------------------------------
void TfrmDocumentList::addItemList()
{
	TListItem *item = lvDocumentsList->Items->Add();

	for (int i = 0; i < lvDocumentsList->Columns->Count; i++)
	{
		item->SubItems->Add("");
	}
}
//---------------------------------------------------------------------------
void TfrmDocumentList::addValueItemList(int row, int column, wchar_t *text)
{
	String value = text;
	if (row < lvDocumentsList->Items->Count)
	{
		if (column == 0)
		{
			lvDocumentsList->Items->Item[row]->Caption = value;
		}
		else
		{
			lvDocumentsList->Items->Item[row]->SubItems->Strings[column-1] = value;
		}
	}
}
//---------------------------------------------------------------------------
void TfrmDocumentList::addStatusImage(int itemIndex, int imageIndex)
{
	lvDocumentsList->Items->Item[itemIndex]->ImageIndex = imageIndex;
}
//---------------------------------------------------------------------------
void TfrmDocumentList::setColumnSize(int indexColumn, const String &text)
{
	lvDocumentsList->Columns->Items[indexColumn]->Width = lvDocumentsList->Canvas->TextWidth(text) + 20;
}
//---------------------------------------------------------------------------
void TfrmDocumentList::setColumnSize(int indexColumn, int size)
{
	lvDocumentsList->Columns->Items[indexColumn]->Width = size + 20;
}
//---------------------------------------------------------------------------
void TfrmDocumentList::addColumn(const String &columnCaption)
{
	TListColumn *listColumn = lvDocumentsList->Columns->Add();
	listColumn->Caption = columnCaption;
}
//---------------------------------------------------------------------------
int TfrmDocumentList::getDocumentIndex()
{
	return lvDocumentsList->ItemIndex;
}
//---------------------------------------------------------------------------
void __fastcall TfrmDocumentList::lvDocumentsListDblClick(TObject *Sender)
{
	m_PresenterDocList->viewDocument();
}

//---------------------------------------------------------------------------
void TfrmDocumentList::fillDocumentList(lscExtendDocCircul *extendDocCircul)
{
	m_PresenterDocList->fillDocumentList(extendDocCircul);
}

//---------------------------------------------------------------------------
void TfrmDocumentList::clearList()
{
    lvDocumentsList->Columns->Clear();
    lvDocumentsList->Clear();
}

//---------------------------------------------------------------------------

void __fastcall TfrmDocumentList::btnResendClick(TObject *Sender)
{
    m_PresenterDocList->resendPack();
}
//---------------------------------------------------------------------------

