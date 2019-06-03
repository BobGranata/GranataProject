//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "frameRoleListTune.h"

//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TfrmRoleListTune *frmRoleListTune;
//---------------------------------------------------------------------------
__fastcall TfrmRoleListTune::TfrmRoleListTune(TComponent* Owner)
	: TFrame(Owner)
{
	presenterRoleListTune = new lscPresenterRoleListTune(this);
}
//---------------------------------------------------------------------------
void __fastcall TfrmRoleListTune::init(CEngine *Engine, CSetOrder *Params) {

	this->Params = Params;
	this->Engine = Engine;

	roleList->Clear();

	this->deleteAllColumn();

	presenterRoleListTune->init(Engine, Params);
}
//---------------------------------------------------------------------------
void TfrmRoleListTune::clearRoleTypeList()
{
	listRoleType->Clear();
	comboSearch->Clear();
}
//---------------------------------------------------------------------------
void TfrmRoleListTune::addItemRoleTypeList(String roleType, int roleTypeIndex)
{
	listRoleType->AddItem(roleType, (TObject*)roleTypeIndex);
}
//---------------------------------------------------------------------------
int TfrmRoleListTune::getCurrentRoleType()
{
	return listRoleType->ItemIndex;
}
//---------------------------------------------------------------------------
void TfrmRoleListTune::setCurrentRoleType(int index)
{
	listRoleType->ItemIndex = 0;
}
//---------------------------------------------------------------------------
int TfrmRoleListTune::getSearchType()
{
	return comboSearch->ItemIndex;
}
//---------------------------------------------------------------------------
void TfrmRoleListTune::setSearchType(int index)
{
	comboSearch->ItemIndex = index;
}
//---------------------------------------------------------------------------
void TfrmRoleListTune::setEditRoleEnable(bool enable)
{
	btnEditRole->Enabled = enable;
}
//---------------------------------------------------------------------------
int TfrmRoleListTune::getCurrentIndexRoleList()
{
	return roleList->ItemIndex;
}
//---------------------------------------------------------------------------
void TfrmRoleListTune::setCurrentIndexRoleList(int index)
{
	roleList->ItemIndex = index;
}
//---------------------------------------------------------------------------
int TfrmRoleListTune::addItemRoleList()
{
	TListItem *item = roleList->Items->Add();

	for (int i = 0; i < roleList->Columns->Count; i++) {
		item->SubItems->Add("");
	}
}

void TfrmRoleListTune::addTextToItemRoleList(int row, int column, String text)
{
	if (row < roleList->Items->Count)
	{
		if (column == 0)
		{
			roleList->Items->Item[row]->Caption = text;
		}
		else
		{
			roleList->Items->Item[row]->SubItems->Strings[column-1] = text;
		}
	}
}

//---------------------------------------------------------------------------
void TfrmRoleListTune::addFindParams(String param, int index)
{
	comboSearch->AddItem(param, (TObject*)index);
}
//---------------------------------------------------------------------------
void TfrmRoleListTune::addColumn(String param, int index)
{
	TListColumn *listColumn = roleList->Columns->Add();
	listColumn->Caption = param;
//	listColumn->AutoSize = true;
	listColumn->Width = 150;
}

//---------------------------------------------------------------------------
void TfrmRoleListTune::deleteAllColumn()
{
	for (int i = roleList->Columns->Count - 1; i >= 0; i--)
	{
		roleList->Columns->Delete(i);
	}
}

//---------------------------------------------------------------------------
void TfrmRoleListTune::clearRoleList()
{
	roleList->Clear();
}
//---------------------------------------------------------------------------
String TfrmRoleListTune::getSearchText()
{
	return edSearch->Text;
}

//---------------------------------------------------------------------------
void __fastcall TfrmRoleListTune::comboSearchKeyDown(TObject *Sender, WORD &Key, TShiftState Shift)
{
	if (Key == 13) {
		btnSearchClick(NULL);
	}
}
//---------------------------------------------------------------------------
void __fastcall TfrmRoleListTune::edSearchChange(TObject *Sender)
{
	presenterRoleListTune->setFilterText(edSearch->Text);
}
//---------------------------------------------------------------------------
void __fastcall TfrmRoleListTune::edSearchKeyDown(TObject *Sender, WORD &Key, TShiftState Shift)
{
	if (Key == 13) {
		btnSearchClick(NULL);
	}
}
//---------------------------------------------------------------------------

void __fastcall TfrmRoleListTune::listRoleTypeClick(TObject *Sender)
{
	presenterRoleListTune->listRoleTypeClick();
}
//---------------------------------------------------------------------------


void __fastcall TfrmRoleListTune::roleList1ClickCheck(TObject *Sender)
{
	// todo: не реализовано
	presenterRoleListTune->roleListClickCheck();
}
//---------------------------------------------------------------------------

void __fastcall TfrmRoleListTune::roleList1DblClick(TObject *Sender)
{
	presenterRoleListTune->editRole();
}
//---------------------------------------------------------------------------

void __fastcall TfrmRoleListTune::btnAddRoleClick(TObject *Sender)
{
	presenterRoleListTune->addRole();
}
//---------------------------------------------------------------------------

void TfrmRoleListTune::setRoleVisible(int roleIndex, bool isVisible)
{
	if (isVisible) {
//		roleList->State[roleList->Count - 1] = cbChecked;
	} else {
//		roleList->State[roleList->Count - 1] = cbUnchecked;
	}
}
//---------------------------------------------------------------------------
void __fastcall TfrmRoleListTune::btnEditRoleClick(TObject *Sender)
{
	presenterRoleListTune->editRole();
}
//---------------------------------------------------------------------------
void TfrmRoleListTune::StartWaitProccess()
{
	Screen->Cursor = crHourGlass;
}
//---------------------------------------------------------------------------
void TfrmRoleListTune::StopWaitProccess()
{
	Screen->Cursor = crDefault;
}
//---------------------------------------------------------------------------
void __fastcall TfrmRoleListTune::comboSearchClick(TObject *Sender)
{
	presenterRoleListTune->setFilterProperty();
}

//---------------------------------------------------------------------------

void __fastcall TfrmRoleListTune::btnNextPageClick(TObject *Sender)
{
	presenterRoleListTune->nextPage();
}
//---------------------------------------------------------------------------
void TfrmRoleListTune::setPageCount(int count)
{
	lbPageCount->Caption = "/" + IntToStr(count);
}
//---------------------------------------------------------------------------
int TfrmRoleListTune::getNumPage()
{
	int num = 1;

	try
	{
		num = StrToInt(edCurrentPage->Text);
	} catch (...)
	{
	}

	return num;
}
//---------------------------------------------------------------------------
void TfrmRoleListTune::setNumPage(int page)
{
	edCurrentPage->Text = IntToStr(page);
}

//---------------------------------------------------------------------------

void __fastcall TfrmRoleListTune::btnPreviousPageClick(TObject *Sender)
{
	presenterRoleListTune->previousPage();
}
//---------------------------------------------------------------------------

void __fastcall TfrmRoleListTune::edCurrentPageChange(TObject *Sender)
{
	presenterRoleListTune->setPage();
}
//---------------------------------------------------------------------------

void __fastcall TfrmRoleListTune::cbPageOnScreenClick(TObject *Sender)
{
	//
}
//---------------------------------------------------------------------------

void __fastcall TfrmRoleListTune::btnVisiParamEditClick(TObject *Sender)
{
	presenterRoleListTune->visiParamEdit();
}
//---------------------------------------------------------------------------

